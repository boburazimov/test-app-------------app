package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PositionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Position}.
 */
public interface PositionService {
    /**
     * Save a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    PositionDTO save(PositionDTO positionDTO);

    /**
     * Updates a position.
     *
     * @param positionDTO the entity to update.
     * @return the persisted entity.
     */
    PositionDTO update(PositionDTO positionDTO);

    /**
     * Partially updates a position.
     *
     * @param positionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PositionDTO> partialUpdate(PositionDTO positionDTO);

    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    List<PositionDTO> findAll();

    /**
     * Get the "id" position.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PositionDTO> findOne(Long id);

    /**
     * Delete the "id" position.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
