package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Social;
import com.mycompany.myapp.repository.SocialRepository;
import com.mycompany.myapp.service.dto.SocialDTO;
import com.mycompany.myapp.service.mapper.SocialMapper;
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
 * Integration tests for the {@link SocialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/socials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private SocialMapper socialMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialMockMvc;

    private Social social;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Social createEntity(EntityManager em) {
        Social social = new Social().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return social;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Social createUpdatedEntity(EntityManager em) {
        Social social = new Social().name(UPDATED_NAME).info(UPDATED_INFO);
        return social;
    }

    @BeforeEach
    public void initTest() {
        social = createEntity(em);
    }

    @Test
    @Transactional
    void createSocial() throws Exception {
        int databaseSizeBeforeCreate = socialRepository.findAll().size();
        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);
        restSocialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialDTO)))
            .andExpect(status().isCreated());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeCreate + 1);
        Social testSocial = socialList.get(socialList.size() - 1);
        assertThat(testSocial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocial.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createSocialWithExistingId() throws Exception {
        // Create the Social with an existing ID
        social.setId(1L);
        SocialDTO socialDTO = socialMapper.toDto(social);

        int databaseSizeBeforeCreate = socialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialRepository.findAll().size();
        // set the field null
        social.setName(null);

        // Create the Social, which fails.
        SocialDTO socialDTO = socialMapper.toDto(social);

        restSocialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialDTO)))
            .andExpect(status().isBadRequest());

        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocials() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        // Get all the socialList
        restSocialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(social.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getSocial() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        // Get the social
        restSocialMockMvc
            .perform(get(ENTITY_API_URL_ID, social.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(social.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingSocial() throws Exception {
        // Get the social
        restSocialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocial() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        int databaseSizeBeforeUpdate = socialRepository.findAll().size();

        // Update the social
        Social updatedSocial = socialRepository.findById(social.getId()).get();
        // Disconnect from session so that the updates on updatedSocial are not directly saved in db
        em.detach(updatedSocial);
        updatedSocial.name(UPDATED_NAME).info(UPDATED_INFO);
        SocialDTO socialDTO = socialMapper.toDto(updatedSocial);

        restSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialDTO))
            )
            .andExpect(status().isOk());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
        Social testSocial = socialList.get(socialList.size() - 1);
        assertThat(testSocial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocial.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();
        social.setId(count.incrementAndGet());

        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();
        social.setId(count.incrementAndGet());

        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();
        social.setId(count.incrementAndGet());

        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocialWithPatch() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        int databaseSizeBeforeUpdate = socialRepository.findAll().size();

        // Update the social using partial update
        Social partialUpdatedSocial = new Social();
        partialUpdatedSocial.setId(social.getId());

        restSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocial))
            )
            .andExpect(status().isOk());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
        Social testSocial = socialList.get(socialList.size() - 1);
        assertThat(testSocial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocial.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateSocialWithPatch() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        int databaseSizeBeforeUpdate = socialRepository.findAll().size();

        // Update the social using partial update
        Social partialUpdatedSocial = new Social();
        partialUpdatedSocial.setId(social.getId());

        partialUpdatedSocial.name(UPDATED_NAME).info(UPDATED_INFO);

        restSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocial))
            )
            .andExpect(status().isOk());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
        Social testSocial = socialList.get(socialList.size() - 1);
        assertThat(testSocial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocial.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();
        social.setId(count.incrementAndGet());

        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();
        social.setId(count.incrementAndGet());

        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();
        social.setId(count.incrementAndGet());

        // Create the Social
        SocialDTO socialDTO = socialMapper.toDto(social);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(socialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocial() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        int databaseSizeBeforeDelete = socialRepository.findAll().size();

        // Delete the social
        restSocialMockMvc
            .perform(delete(ENTITY_API_URL_ID, social.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
