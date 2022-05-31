package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BarcodeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Barcode}.
 */
public interface BarcodeService {
    /**
     * Save a barcode.
     *
     * @param barcodeDTO the entity to save.
     * @return the persisted entity.
     */
    BarcodeDTO save(BarcodeDTO barcodeDTO);

    /**
     * Updates a barcode.
     *
     * @param barcodeDTO the entity to update.
     * @return the persisted entity.
     */
    BarcodeDTO update(BarcodeDTO barcodeDTO);

    /**
     * Partially updates a barcode.
     *
     * @param barcodeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BarcodeDTO> partialUpdate(BarcodeDTO barcodeDTO);

    /**
     * Get all the barcodes.
     *
     * @return the list of entities.
     */
    List<BarcodeDTO> findAll();

    /**
     * Get the "id" barcode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BarcodeDTO> findOne(Long id);

    /**
     * Delete the "id" barcode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
