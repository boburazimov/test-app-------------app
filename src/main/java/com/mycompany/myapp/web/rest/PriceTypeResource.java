package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PriceTypeRepository;
import com.mycompany.myapp.service.PriceTypeService;
import com.mycompany.myapp.service.dto.PriceTypeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PriceType}.
 */
@RestController
@RequestMapping("/api")
public class PriceTypeResource {

    private final Logger log = LoggerFactory.getLogger(PriceTypeResource.class);

    private static final String ENTITY_NAME = "priceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriceTypeService priceTypeService;

    private final PriceTypeRepository priceTypeRepository;

    public PriceTypeResource(PriceTypeService priceTypeService, PriceTypeRepository priceTypeRepository) {
        this.priceTypeService = priceTypeService;
        this.priceTypeRepository = priceTypeRepository;
    }

    /**
     * {@code POST  /price-types} : Create a new priceType.
     *
     * @param priceTypeDTO the priceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priceTypeDTO, or with status {@code 400 (Bad Request)} if the priceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/price-types")
    public ResponseEntity<PriceTypeDTO> createPriceType(@Valid @RequestBody PriceTypeDTO priceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PriceType : {}", priceTypeDTO);
        if (priceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceTypeDTO result = priceTypeService.save(priceTypeDTO);
        return ResponseEntity
            .created(new URI("/api/price-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /price-types/:id} : Updates an existing priceType.
     *
     * @param id the id of the priceTypeDTO to save.
     * @param priceTypeDTO the priceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the priceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/price-types/{id}")
    public ResponseEntity<PriceTypeDTO> updatePriceType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PriceTypeDTO priceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PriceType : {}, {}", id, priceTypeDTO);
        if (priceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PriceTypeDTO result = priceTypeService.update(priceTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /price-types/:id} : Partial updates given fields of an existing priceType, field will ignore if it is null
     *
     * @param id the id of the priceTypeDTO to save.
     * @param priceTypeDTO the priceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the priceTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the priceTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the priceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/price-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PriceTypeDTO> partialUpdatePriceType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PriceTypeDTO priceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PriceType partially : {}, {}", id, priceTypeDTO);
        if (priceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PriceTypeDTO> result = priceTypeService.partialUpdate(priceTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priceTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /price-types} : get all the priceTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priceTypes in body.
     */
    @GetMapping("/price-types")
    public List<PriceTypeDTO> getAllPriceTypes() {
        log.debug("REST request to get all PriceTypes");
        return priceTypeService.findAll();
    }

    /**
     * {@code GET  /price-types/:id} : get the "id" priceType.
     *
     * @param id the id of the priceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-types/{id}")
    public ResponseEntity<PriceTypeDTO> getPriceType(@PathVariable Long id) {
        log.debug("REST request to get PriceType : {}", id);
        Optional<PriceTypeDTO> priceTypeDTO = priceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceTypeDTO);
    }

    /**
     * {@code DELETE  /price-types/:id} : delete the "id" priceType.
     *
     * @param id the id of the priceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/price-types/{id}")
    public ResponseEntity<Void> deletePriceType(@PathVariable Long id) {
        log.debug("REST request to delete PriceType : {}", id);
        priceTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
