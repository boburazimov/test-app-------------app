package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.BarcodeRepository;
import com.mycompany.myapp.service.BarcodeService;
import com.mycompany.myapp.service.dto.BarcodeDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Barcode}.
 */
@RestController
@RequestMapping("/api")
public class BarcodeResource {

    private final Logger log = LoggerFactory.getLogger(BarcodeResource.class);

    private static final String ENTITY_NAME = "barcode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BarcodeService barcodeService;

    private final BarcodeRepository barcodeRepository;

    public BarcodeResource(BarcodeService barcodeService, BarcodeRepository barcodeRepository) {
        this.barcodeService = barcodeService;
        this.barcodeRepository = barcodeRepository;
    }

    /**
     * {@code POST  /barcodes} : Create a new barcode.
     *
     * @param barcodeDTO the barcodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barcodeDTO, or with status {@code 400 (Bad Request)} if the barcode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/barcodes")
    public ResponseEntity<BarcodeDTO> createBarcode(@Valid @RequestBody BarcodeDTO barcodeDTO) throws URISyntaxException {
        log.debug("REST request to save Barcode : {}", barcodeDTO);
        if (barcodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new barcode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BarcodeDTO result = barcodeService.save(barcodeDTO);
        return ResponseEntity
            .created(new URI("/api/barcodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /barcodes/:id} : Updates an existing barcode.
     *
     * @param id the id of the barcodeDTO to save.
     * @param barcodeDTO the barcodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barcodeDTO,
     * or with status {@code 400 (Bad Request)} if the barcodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the barcodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/barcodes/{id}")
    public ResponseEntity<BarcodeDTO> updateBarcode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BarcodeDTO barcodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Barcode : {}, {}", id, barcodeDTO);
        if (barcodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, barcodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!barcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BarcodeDTO result = barcodeService.update(barcodeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, barcodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /barcodes/:id} : Partial updates given fields of an existing barcode, field will ignore if it is null
     *
     * @param id the id of the barcodeDTO to save.
     * @param barcodeDTO the barcodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barcodeDTO,
     * or with status {@code 400 (Bad Request)} if the barcodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the barcodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the barcodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/barcodes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BarcodeDTO> partialUpdateBarcode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BarcodeDTO barcodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Barcode partially : {}, {}", id, barcodeDTO);
        if (barcodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, barcodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!barcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BarcodeDTO> result = barcodeService.partialUpdate(barcodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, barcodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /barcodes} : get all the barcodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of barcodes in body.
     */
    @GetMapping("/barcodes")
    public List<BarcodeDTO> getAllBarcodes() {
        log.debug("REST request to get all Barcodes");
        return barcodeService.findAll();
    }

    /**
     * {@code GET  /barcodes/:id} : get the "id" barcode.
     *
     * @param id the id of the barcodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the barcodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/barcodes/{id}")
    public ResponseEntity<BarcodeDTO> getBarcode(@PathVariable Long id) {
        log.debug("REST request to get Barcode : {}", id);
        Optional<BarcodeDTO> barcodeDTO = barcodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barcodeDTO);
    }

    /**
     * {@code DELETE  /barcodes/:id} : delete the "id" barcode.
     *
     * @param id the id of the barcodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/barcodes/{id}")
    public ResponseEntity<Void> deleteBarcode(@PathVariable Long id) {
        log.debug("REST request to delete Barcode : {}", id);
        barcodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
