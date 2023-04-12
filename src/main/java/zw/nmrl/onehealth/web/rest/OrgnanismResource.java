package zw.nmrl.onehealth.web.rest;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.nmrl.onehealth.domain.Orgnanism;
import zw.nmrl.onehealth.repository.OrgnanismRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Orgnanism}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrgnanismResource {

    private final Logger log = LoggerFactory.getLogger(OrgnanismResource.class);

    private static final String ENTITY_NAME = "orgnanism";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgnanismRepository orgnanismRepository;

    public OrgnanismResource(OrgnanismRepository orgnanismRepository) {
        this.orgnanismRepository = orgnanismRepository;
    }

    /**
     * {@code POST  /orgnanisms} : Create a new orgnanism.
     *
     * @param orgnanism the orgnanism to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgnanism, or with status {@code 400 (Bad Request)} if the orgnanism has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orgnanisms")
    public ResponseEntity<Orgnanism> createOrgnanism(@Valid @RequestBody Orgnanism orgnanism) throws URISyntaxException {
        log.debug("REST request to save Orgnanism : {}", orgnanism);
        if (orgnanism.getId() != null) {
            throw new BadRequestAlertException("A new orgnanism cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Orgnanism result = orgnanismRepository.save(orgnanism);
        return ResponseEntity
            .created(new URI("/api/orgnanisms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orgnanisms/:id} : Updates an existing orgnanism.
     *
     * @param id the id of the orgnanism to save.
     * @param orgnanism the orgnanism to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgnanism,
     * or with status {@code 400 (Bad Request)} if the orgnanism is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgnanism couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orgnanisms/{id}")
    public ResponseEntity<Orgnanism> updateOrgnanism(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Orgnanism orgnanism
    ) throws URISyntaxException {
        log.debug("REST request to update Orgnanism : {}, {}", id, orgnanism);
        if (orgnanism.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgnanism.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgnanismRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Orgnanism result = orgnanismRepository.save(orgnanism);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgnanism.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /orgnanisms/:id} : Partial updates given fields of an existing orgnanism, field will ignore if it is null
     *
     * @param id the id of the orgnanism to save.
     * @param orgnanism the orgnanism to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgnanism,
     * or with status {@code 400 (Bad Request)} if the orgnanism is not valid,
     * or with status {@code 404 (Not Found)} if the orgnanism is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgnanism couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orgnanisms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Orgnanism> partialUpdateOrgnanism(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Orgnanism orgnanism
    ) throws URISyntaxException {
        log.debug("REST request to partial update Orgnanism partially : {}, {}", id, orgnanism);
        if (orgnanism.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgnanism.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgnanismRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Orgnanism> result = orgnanismRepository
            .findById(orgnanism.getId())
            .map(existingOrgnanism -> {
                if (orgnanism.getName() != null) {
                    existingOrgnanism.setName(orgnanism.getName());
                }
                if (orgnanism.getCode() != null) {
                    existingOrgnanism.setCode(orgnanism.getCode());
                }

                return existingOrgnanism;
            })
            .map(orgnanismRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgnanism.getId().toString())
        );
    }

    /**
     * {@code GET  /orgnanisms} : get all the orgnanisms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgnanisms in body.
     */
    @GetMapping("/orgnanisms")
    public List<Orgnanism> getAllOrgnanisms() {
        log.debug("REST request to get all Orgnanisms");
        return orgnanismRepository.findAll();
    }

    /**
     * {@code GET  /orgnanisms/:id} : get the "id" orgnanism.
     *
     * @param id the id of the orgnanism to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgnanism, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orgnanisms/{id}")
    public ResponseEntity<Orgnanism> getOrgnanism(@PathVariable Long id) {
        log.debug("REST request to get Orgnanism : {}", id);
        Optional<Orgnanism> orgnanism = orgnanismRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orgnanism);
    }

    /**
     * {@code DELETE  /orgnanisms/:id} : delete the "id" orgnanism.
     *
     * @param id the id of the orgnanism to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orgnanisms/{id}")
    public ResponseEntity<Void> deleteOrgnanism(@PathVariable Long id) {
        log.debug("REST request to delete Orgnanism : {}", id);
        orgnanismRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
