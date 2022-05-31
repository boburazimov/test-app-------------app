package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Partner;
import com.mycompany.myapp.domain.enumeration.GenderEnum;
import com.mycompany.myapp.domain.enumeration.PartnerTypeEnum;
import com.mycompany.myapp.repository.PartnerRepository;
import com.mycompany.myapp.service.dto.PartnerDTO;
import com.mycompany.myapp.service.mapper.PartnerMapper;
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
 * Integration tests for the {@link PartnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartnerResourceIT {

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final GenderEnum DEFAULT_GENDER = GenderEnum.MALE;
    private static final GenderEnum UPDATED_GENDER = GenderEnum.FAMALE;

    private static final Integer DEFAULT_AGE = 16;
    private static final Integer UPDATED_AGE = 17;

    private static final Boolean DEFAULT_IS_BLOCKED = false;
    private static final Boolean UPDATED_IS_BLOCKED = true;

    private static final PartnerTypeEnum DEFAULT_PARTNER_TYPE = PartnerTypeEnum.CUSTOMER;
    private static final PartnerTypeEnum UPDATED_PARTNER_TYPE = PartnerTypeEnum.SUPPLIER;

    private static final Integer DEFAULT_INN = 9;
    private static final Integer UPDATED_INN = 10;

    private static final Integer DEFAULT_PINFL = 14;
    private static final Integer UPDATED_PINFL = 15;

    private static final String ENTITY_API_URL = "/api/partners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerMapper partnerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartnerMockMvc;

    private Partner partner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partner createEntity(EntityManager em) {
        Partner partner = new Partner()
            .phonenumber(DEFAULT_PHONENUMBER)
            .code(DEFAULT_CODE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .age(DEFAULT_AGE)
            .isBlocked(DEFAULT_IS_BLOCKED)
            .partnerType(DEFAULT_PARTNER_TYPE)
            .inn(DEFAULT_INN)
            .pinfl(DEFAULT_PINFL);
        return partner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partner createUpdatedEntity(EntityManager em) {
        Partner partner = new Partner()
            .phonenumber(UPDATED_PHONENUMBER)
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .isBlocked(UPDATED_IS_BLOCKED)
            .partnerType(UPDATED_PARTNER_TYPE)
            .inn(UPDATED_INN)
            .pinfl(UPDATED_PINFL);
        return partner;
    }

    @BeforeEach
    public void initTest() {
        partner = createEntity(em);
    }

    @Test
    @Transactional
    void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();
        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);
        restPartnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testPartner.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPartner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPartner.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPartner.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPartner.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPartner.getIsBlocked()).isEqualTo(DEFAULT_IS_BLOCKED);
        assertThat(testPartner.getPartnerType()).isEqualTo(DEFAULT_PARTNER_TYPE);
        assertThat(testPartner.getInn()).isEqualTo(DEFAULT_INN);
        assertThat(testPartner.getPinfl()).isEqualTo(DEFAULT_PINFL);
    }

    @Test
    @Transactional
    void createPartnerWithExistingId() throws Exception {
        // Create the Partner with an existing ID
        partner.setId(1L);
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhonenumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setPhonenumber(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCode(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList
        restPartnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].isBlocked").value(hasItem(DEFAULT_IS_BLOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].partnerType").value(hasItem(DEFAULT_PARTNER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].pinfl").value(hasItem(DEFAULT_PINFL)));
    }

    @Test
    @Transactional
    void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get the partner
        restPartnerMockMvc
            .perform(get(ENTITY_API_URL_ID, partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partner.getId().intValue()))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.isBlocked").value(DEFAULT_IS_BLOCKED.booleanValue()))
            .andExpect(jsonPath("$.partnerType").value(DEFAULT_PARTNER_TYPE.toString()))
            .andExpect(jsonPath("$.inn").value(DEFAULT_INN))
            .andExpect(jsonPath("$.pinfl").value(DEFAULT_PINFL));
    }

    @Test
    @Transactional
    void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = partnerRepository.findById(partner.getId()).get();
        // Disconnect from session so that the updates on updatedPartner are not directly saved in db
        em.detach(updatedPartner);
        updatedPartner
            .phonenumber(UPDATED_PHONENUMBER)
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .isBlocked(UPDATED_IS_BLOCKED)
            .partnerType(UPDATED_PARTNER_TYPE)
            .inn(UPDATED_INN)
            .pinfl(UPDATED_PINFL);
        PartnerDTO partnerDTO = partnerMapper.toDto(updatedPartner);

        restPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partnerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testPartner.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPartner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPartner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPartner.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPartner.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPartner.getIsBlocked()).isEqualTo(UPDATED_IS_BLOCKED);
        assertThat(testPartner.getPartnerType()).isEqualTo(UPDATED_PARTNER_TYPE);
        assertThat(testPartner.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testPartner.getPinfl()).isEqualTo(UPDATED_PINFL);
    }

    @Test
    @Transactional
    void putNonExistingPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();
        partner.setId(count.incrementAndGet());

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();
        partner.setId(count.incrementAndGet());

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();
        partner.setId(count.incrementAndGet());

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartnerWithPatch() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner using partial update
        Partner partialUpdatedPartner = new Partner();
        partialUpdatedPartner.setId(partner.getId());

        partialUpdatedPartner
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .isBlocked(UPDATED_IS_BLOCKED)
            .inn(UPDATED_INN);

        restPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartner))
            )
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testPartner.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPartner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPartner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPartner.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPartner.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPartner.getIsBlocked()).isEqualTo(UPDATED_IS_BLOCKED);
        assertThat(testPartner.getPartnerType()).isEqualTo(DEFAULT_PARTNER_TYPE);
        assertThat(testPartner.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testPartner.getPinfl()).isEqualTo(DEFAULT_PINFL);
    }

    @Test
    @Transactional
    void fullUpdatePartnerWithPatch() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner using partial update
        Partner partialUpdatedPartner = new Partner();
        partialUpdatedPartner.setId(partner.getId());

        partialUpdatedPartner
            .phonenumber(UPDATED_PHONENUMBER)
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .isBlocked(UPDATED_IS_BLOCKED)
            .partnerType(UPDATED_PARTNER_TYPE)
            .inn(UPDATED_INN)
            .pinfl(UPDATED_PINFL);

        restPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartner))
            )
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testPartner.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPartner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPartner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPartner.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPartner.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPartner.getIsBlocked()).isEqualTo(UPDATED_IS_BLOCKED);
        assertThat(testPartner.getPartnerType()).isEqualTo(UPDATED_PARTNER_TYPE);
        assertThat(testPartner.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testPartner.getPinfl()).isEqualTo(UPDATED_PINFL);
    }

    @Test
    @Transactional
    void patchNonExistingPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();
        partner.setId(count.incrementAndGet());

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partnerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();
        partner.setId(count.incrementAndGet());

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();
        partner.setId(count.incrementAndGet());

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Delete the partner
        restPartnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, partner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
