package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Barcode;
import com.mycompany.myapp.repository.BarcodeRepository;
import com.mycompany.myapp.service.dto.BarcodeDTO;
import com.mycompany.myapp.service.mapper.BarcodeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BarcodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BarcodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/barcodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private BarcodeMapper barcodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBarcodeMockMvc;

    private Barcode barcode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barcode createEntity(EntityManager em) {
        Barcode barcode = new Barcode().code(DEFAULT_CODE);
        return barcode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barcode createUpdatedEntity(EntityManager em) {
        Barcode barcode = new Barcode().code(UPDATED_CODE);
        return barcode;
    }

    @BeforeEach
    public void initTest() {
        barcode = createEntity(em);
    }

    @Test
    @Transactional
    void createBarcode() throws Exception {
        int databaseSizeBeforeCreate = barcodeRepository.findAll().size();
        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);
        restBarcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(barcodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeCreate + 1);
        Barcode testBarcode = barcodeList.get(barcodeList.size() - 1);
        assertThat(testBarcode.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createBarcodeWithExistingId() throws Exception {
        // Create the Barcode with an existing ID
        barcode.setId(1L);
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        int databaseSizeBeforeCreate = barcodeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(barcodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = barcodeRepository.findAll().size();
        // set the field null
        barcode.setCode(null);

        // Create the Barcode, which fails.
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        restBarcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(barcodeDTO)))
            .andExpect(status().isBadRequest());

        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBarcodes() throws Exception {
        // Initialize the database
        barcodeRepository.saveAndFlush(barcode);

        // Get all the barcodeList
        restBarcodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getBarcode() throws Exception {
        // Initialize the database
        barcodeRepository.saveAndFlush(barcode);

        // Get the barcode
        restBarcodeMockMvc
            .perform(get(ENTITY_API_URL_ID, barcode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(barcode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingBarcode() throws Exception {
        // Get the barcode
        restBarcodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBarcode() throws Exception {
        // Initialize the database
        barcodeRepository.saveAndFlush(barcode);

        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();

        // Update the barcode
        Barcode updatedBarcode = barcodeRepository.findById(barcode.getId()).get();
        // Disconnect from session so that the updates on updatedBarcode are not directly saved in db
        em.detach(updatedBarcode);
        updatedBarcode.code(UPDATED_CODE);
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(updatedBarcode);

        restBarcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, barcodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(barcodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
        Barcode testBarcode = barcodeList.get(barcodeList.size() - 1);
        assertThat(testBarcode.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingBarcode() throws Exception {
        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();
        barcode.setId(count.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, barcodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBarcode() throws Exception {
        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();
        barcode.setId(count.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBarcode() throws Exception {
        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();
        barcode.setId(count.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(barcodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBarcodeWithPatch() throws Exception {
        // Initialize the database
        barcodeRepository.saveAndFlush(barcode);

        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();

        // Update the barcode using partial update
        Barcode partialUpdatedBarcode = new Barcode();
        partialUpdatedBarcode.setId(barcode.getId());

        partialUpdatedBarcode.code(UPDATED_CODE);

        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBarcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBarcode))
            )
            .andExpect(status().isOk());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
        Barcode testBarcode = barcodeList.get(barcodeList.size() - 1);
        assertThat(testBarcode.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateBarcodeWithPatch() throws Exception {
        // Initialize the database
        barcodeRepository.saveAndFlush(barcode);

        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();

        // Update the barcode using partial update
        Barcode partialUpdatedBarcode = new Barcode();
        partialUpdatedBarcode.setId(barcode.getId());

        partialUpdatedBarcode.code(UPDATED_CODE);

        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBarcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBarcode))
            )
            .andExpect(status().isOk());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
        Barcode testBarcode = barcodeList.get(barcodeList.size() - 1);
        assertThat(testBarcode.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingBarcode() throws Exception {
        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();
        barcode.setId(count.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, barcodeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBarcode() throws Exception {
        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();
        barcode.setId(count.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(barcodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBarcode() throws Exception {
        int databaseSizeBeforeUpdate = barcodeRepository.findAll().size();
        barcode.setId(count.incrementAndGet());

        // Create the Barcode
        BarcodeDTO barcodeDTO = barcodeMapper.toDto(barcode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarcodeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(barcodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Barcode in the database
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBarcode() throws Exception {
        // Initialize the database
        barcodeRepository.saveAndFlush(barcode);

        int databaseSizeBeforeDelete = barcodeRepository.findAll().size();

        // Delete the barcode
        restBarcodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, barcode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Barcode> barcodeList = barcodeRepository.findAll();
        assertThat(barcodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
