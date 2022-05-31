package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SocialDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Social}.
 */
public interface SocialService {
    /**
     * Save a social.
     *
     * @param socialDTO the entity to save.
     * @return the persisted entity.
     */
    SocialDTO save(SocialDTO socialDTO);

    /**
     * Updates a social.
     *
     * @param socialDTO the entity to update.
     * @return the persisted entity.
     */
    SocialDTO update(SocialDTO socialDTO);

    /**
     * Partially updates a social.
     *
     * @param socialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocialDTO> partialUpdate(SocialDTO socialDTO);

    /**
     * Get all the socials.
     *
     * @return the list of entities.
     */
    List<SocialDTO> findAll();

    /**
     * Get the "id" social.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialDTO> findOne(Long id);

    /**
     * Delete the "id" social.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
