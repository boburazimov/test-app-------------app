package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PriceTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PriceType}.
 */
public interface PriceTypeService {
    /**
     * Save a priceType.
     *
     * @param priceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PriceTypeDTO save(PriceTypeDTO priceTypeDTO);

    /**
     * Updates a priceType.
     *
     * @param priceTypeDTO the entity to update.
     * @return the persisted entity.
     */
    PriceTypeDTO update(PriceTypeDTO priceTypeDTO);

    /**
     * Partially updates a priceType.
     *
     * @param priceTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PriceTypeDTO> partialUpdate(PriceTypeDTO priceTypeDTO);

    /**
     * Get all the priceTypes.
     *
     * @return the list of entities.
     */
    List<PriceTypeDTO> findAll();

    /**
     * Get the "id" priceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" priceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
