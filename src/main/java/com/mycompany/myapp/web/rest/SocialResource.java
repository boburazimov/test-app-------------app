package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SocialRepository;
import com.mycompany.myapp.service.SocialService;
import com.mycompany.myapp.service.dto.SocialDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Social}.
 */
@RestController
@RequestMapping("/api")
public class SocialResource {

    private final Logger log = LoggerFactory.getLogger(SocialResource.class);

    private static final String ENTITY_NAME = "social";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialService socialService;

    private final SocialRepository socialRepository;

    public SocialResource(SocialService socialService, SocialRepository socialRepository) {
        this.socialService = socialService;
        this.socialRepository = socialRepository;
    }

    /**
     * {@code POST  /socials} : Create a new social.
     *
     * @param socialDTO the socialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialDTO, or with status {@code 400 (Bad Request)} if the social has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/socials")
    public ResponseEntity<SocialDTO> createSocial(@Valid @RequestBody SocialDTO socialDTO) throws URISyntaxException {
        log.debug("REST request to save Social : {}", socialDTO);
        if (socialDTO.getId() != null) {
            throw new BadRequestAlertException("A new social cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialDTO result = socialService.save(socialDTO);
        return ResponseEntity
            .created(new URI("/api/socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /socials/:id} : Updates an existing social.
     *
     * @param id the id of the socialDTO to save.
     * @param socialDTO the socialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialDTO,
     * or with status {@code 400 (Bad Request)} if the socialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/socials/{id}")
    public ResponseEntity<SocialDTO> updateSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocialDTO socialDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Social : {}, {}", id, socialDTO);
        if (socialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocialDTO result = socialService.update(socialDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, socialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /socials/:id} : Partial updates given fields of an existing social, field will ignore if it is null
     *
     * @param id the id of the socialDTO to save.
     * @param socialDTO the socialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialDTO,
     * or with status {@code 400 (Bad Request)} if the socialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the socialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the socialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/socials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocialDTO> partialUpdateSocial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocialDTO socialDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Social partially : {}, {}", id, socialDTO);
        if (socialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocialDTO> result = socialService.partialUpdate(socialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, socialDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /socials} : get all the socials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socials in body.
     */
    @GetMapping("/socials")
    public List<SocialDTO> getAllSocials() {
        log.debug("REST request to get all Socials");
        return socialService.findAll();
    }

    /**
     * {@code GET  /socials/:id} : get the "id" social.
     *
     * @param id the id of the socialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/socials/{id}")
    public ResponseEntity<SocialDTO> getSocial(@PathVariable Long id) {
        log.debug("REST request to get Social : {}", id);
        Optional<SocialDTO> socialDTO = socialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialDTO);
    }

    /**
     * {@code DELETE  /socials/:id} : delete the "id" social.
     *
     * @param id the id of the socialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/socials/{id}")
    public ResponseEntity<Void> deleteSocial(@PathVariable Long id) {
        log.debug("REST request to delete Social : {}", id);
        socialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
