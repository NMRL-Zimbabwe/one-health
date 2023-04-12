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
import zw.nmrl.onehealth.domain.Demography;
import zw.nmrl.onehealth.repository.DemographyRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Demography}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DemographyResource {

    private final Logger log = LoggerFactory.getLogger(DemographyResource.class);

    private static final String ENTITY_NAME = "demography";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemographyRepository demographyRepository;

    public DemographyResource(DemographyRepository demographyRepository) {
        this.demographyRepository = demographyRepository;
    }

    /**
     * {@code POST  /demographies} : Create a new demography.
     *
     * @param demography the demography to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demography, or with status {@code 400 (Bad Request)} if the demography has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demographies")
    public ResponseEntity<Demography> createDemography(@Valid @RequestBody Demography demography) throws URISyntaxException {
        log.debug("REST request to save Demography : {}", demography);
        if (demography.getId() != null) {
            throw new BadRequestAlertException("A new demography cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Demography result = demographyRepository.save(demography);
        return ResponseEntity
            .created(new URI("/api/demographies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demographies/:id} : Updates an existing demography.
     *
     * @param id the id of the demography to save.
     * @param demography the demography to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demography,
     * or with status {@code 400 (Bad Request)} if the demography is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demography couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demographies/{id}")
    public ResponseEntity<Demography> updateDemography(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Demography demography
    ) throws URISyntaxException {
        log.debug("REST request to update Demography : {}, {}", id, demography);
        if (demography.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demography.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demographyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Demography result = demographyRepository.save(demography);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demography.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demographies/:id} : Partial updates given fields of an existing demography, field will ignore if it is null
     *
     * @param id the id of the demography to save.
     * @param demography the demography to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demography,
     * or with status {@code 400 (Bad Request)} if the demography is not valid,
     * or with status {@code 404 (Not Found)} if the demography is not found,
     * or with status {@code 500 (Internal Server Error)} if the demography couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demographies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Demography> partialUpdateDemography(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Demography demography
    ) throws URISyntaxException {
        log.debug("REST request to partial update Demography partially : {}, {}", id, demography);
        if (demography.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demography.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demographyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Demography> result = demographyRepository
            .findById(demography.getId())
            .map(existingDemography -> {
                if (demography.getRecordId() != null) {
                    existingDemography.setRecordId(demography.getRecordId());
                }
                if (demography.getDemographicCodingId() != null) {
                    existingDemography.setDemographicCodingId(demography.getDemographicCodingId());
                }
                if (demography.getValue() != null) {
                    existingDemography.setValue(demography.getValue());
                }

                return existingDemography;
            })
            .map(demographyRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demography.getId().toString())
        );
    }

    /**
     * {@code GET  /demographies} : get all the demographies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demographies in body.
     */
    @GetMapping("/demographies")
    public List<Demography> getAllDemographies() {
        log.debug("REST request to get all Demographies");
        return demographyRepository.findAll();
    }

    /**
     * {@code GET  /demographies/:id} : get the "id" demography.
     *
     * @param id the id of the demography to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demography, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demographies/{id}")
    public ResponseEntity<Demography> getDemography(@PathVariable Long id) {
        log.debug("REST request to get Demography : {}", id);
        Optional<Demography> demography = demographyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(demography);
    }

    /**
     * {@code DELETE  /demographies/:id} : delete the "id" demography.
     *
     * @param id the id of the demography to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demographies/{id}")
    public ResponseEntity<Void> deleteDemography(@PathVariable Long id) {
        log.debug("REST request to delete Demography : {}", id);
        demographyRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
