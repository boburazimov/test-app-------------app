package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PriceType;
import com.mycompany.myapp.domain.enumeration.GeneralStatusEnum;
import com.mycompany.myapp.repository.PriceTypeRepository;
import com.mycompany.myapp.service.dto.PriceTypeDTO;
import com.mycompany.myapp.service.mapper.PriceTypeMapper;
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
 * Integration tests for the {@link PriceTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PriceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INCLUDE_VAT = false;
    private static final Boolean UPDATED_INCLUDE_VAT = true;

    private static final GeneralStatusEnum DEFAULT_STATUS = GeneralStatusEnum.ACTIVE;
    private static final GeneralStatusEnum UPDATED_STATUS = GeneralStatusEnum.INACTIVE;

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/price-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PriceTypeRepository priceTypeRepository;

    @Autowired
    private PriceTypeMapper priceTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceTypeMockMvc;

    private PriceType priceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceType createEntity(EntityManager em) {
        PriceType priceType = new PriceType().name(DEFAULT_NAME).includeVat(DEFAULT_INCLUDE_VAT).status(DEFAULT_STATUS).info(DEFAULT_INFO);
        return priceType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceType createUpdatedEntity(EntityManager em) {
        PriceType priceType = new PriceType().name(UPDATED_NAME).includeVat(UPDATED_INCLUDE_VAT).status(UPDATED_STATUS).info(UPDATED_INFO);
        return priceType;
    }

    @BeforeEach
    public void initTest() {
        priceType = createEntity(em);
    }

    @Test
    @Transactional
    void createPriceType() throws Exception {
        int databaseSizeBeforeCreate = priceTypeRepository.findAll().size();
        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);
        restPriceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PriceType testPriceType = priceTypeList.get(priceTypeList.size() - 1);
        assertThat(testPriceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPriceType.getIncludeVat()).isEqualTo(DEFAULT_INCLUDE_VAT);
        assertThat(testPriceType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPriceType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createPriceTypeWithExistingId() throws Exception {
        // Create the PriceType with an existing ID
        priceType.setId(1L);
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        int databaseSizeBeforeCreate = priceTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceTypeRepository.findAll().size();
        // set the field null
        priceType.setName(null);

        // Create the PriceType, which fails.
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        restPriceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPriceTypes() throws Exception {
        // Initialize the database
        priceTypeRepository.saveAndFlush(priceType);

        // Get all the priceTypeList
        restPriceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].includeVat").value(hasItem(DEFAULT_INCLUDE_VAT.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getPriceType() throws Exception {
        // Initialize the database
        priceTypeRepository.saveAndFlush(priceType);

        // Get the priceType
        restPriceTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, priceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.includeVat").value(DEFAULT_INCLUDE_VAT.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingPriceType() throws Exception {
        // Get the priceType
        restPriceTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPriceType() throws Exception {
        // Initialize the database
        priceTypeRepository.saveAndFlush(priceType);

        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();

        // Update the priceType
        PriceType updatedPriceType = priceTypeRepository.findById(priceType.getId()).get();
        // Disconnect from session so that the updates on updatedPriceType are not directly saved in db
        em.detach(updatedPriceType);
        updatedPriceType.name(UPDATED_NAME).includeVat(UPDATED_INCLUDE_VAT).status(UPDATED_STATUS).info(UPDATED_INFO);
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(updatedPriceType);

        restPriceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, priceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(priceTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
        PriceType testPriceType = priceTypeList.get(priceTypeList.size() - 1);
        assertThat(testPriceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPriceType.getIncludeVat()).isEqualTo(UPDATED_INCLUDE_VAT);
        assertThat(testPriceType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPriceType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingPriceType() throws Exception {
        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();
        priceType.setId(count.incrementAndGet());

        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, priceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(priceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPriceType() throws Exception {
        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();
        priceType.setId(count.incrementAndGet());

        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(priceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPriceType() throws Exception {
        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();
        priceType.setId(count.incrementAndGet());

        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(priceTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePriceTypeWithPatch() throws Exception {
        // Initialize the database
        priceTypeRepository.saveAndFlush(priceType);

        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();

        // Update the priceType using partial update
        PriceType partialUpdatedPriceType = new PriceType();
        partialUpdatedPriceType.setId(priceType.getId());

        partialUpdatedPriceType.name(UPDATED_NAME);

        restPriceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPriceType))
            )
            .andExpect(status().isOk());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
        PriceType testPriceType = priceTypeList.get(priceTypeList.size() - 1);
        assertThat(testPriceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPriceType.getIncludeVat()).isEqualTo(DEFAULT_INCLUDE_VAT);
        assertThat(testPriceType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPriceType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdatePriceTypeWithPatch() throws Exception {
        // Initialize the database
        priceTypeRepository.saveAndFlush(priceType);

        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();

        // Update the priceType using partial update
        PriceType partialUpdatedPriceType = new PriceType();
        partialUpdatedPriceType.setId(priceType.getId());

        partialUpdatedPriceType.name(UPDATED_NAME).includeVat(UPDATED_INCLUDE_VAT).status(UPDATED_STATUS).info(UPDATED_INFO);

        restPriceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPriceType))
            )
            .andExpect(status().isOk());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
        PriceType testPriceType = priceTypeList.get(priceTypeList.size() - 1);
        assertThat(testPriceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPriceType.getIncludeVat()).isEqualTo(UPDATED_INCLUDE_VAT);
        assertThat(testPriceType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPriceType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingPriceType() throws Exception {
        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();
        priceType.setId(count.incrementAndGet());

        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, priceTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(priceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPriceType() throws Exception {
        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();
        priceType.setId(count.incrementAndGet());

        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(priceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPriceType() throws Exception {
        int databaseSizeBeforeUpdate = priceTypeRepository.findAll().size();
        priceType.setId(count.incrementAndGet());

        // Create the PriceType
        PriceTypeDTO priceTypeDTO = priceTypeMapper.toDto(priceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(priceTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PriceType in the database
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePriceType() throws Exception {
        // Initialize the database
        priceTypeRepository.saveAndFlush(priceType);

        int databaseSizeBeforeDelete = priceTypeRepository.findAll().size();

        // Delete the priceType
        restPriceTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, priceType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PriceType> priceTypeList = priceTypeRepository.findAll();
        assertThat(priceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
