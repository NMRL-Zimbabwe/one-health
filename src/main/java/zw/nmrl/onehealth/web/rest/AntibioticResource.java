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
import zw.nmrl.onehealth.domain.Antibiotic;
import zw.nmrl.onehealth.repository.AntibioticRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Antibiotic}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AntibioticResource {

    private final Logger log = LoggerFactory.getLogger(AntibioticResource.class);

    private static final String ENTITY_NAME = "antibiotic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntibioticRepository antibioticRepository;

    public AntibioticResource(AntibioticRepository antibioticRepository) {
        this.antibioticRepository = antibioticRepository;
    }

    /**
     * {@code POST  /antibiotics} : Create a new antibiotic.
     *
     * @param antibiotic the antibiotic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antibiotic, or with status {@code 400 (Bad Request)} if the antibiotic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antibiotics")
    public ResponseEntity<Antibiotic> createAntibiotic(@Valid @RequestBody Antibiotic antibiotic) throws URISyntaxException {
        log.debug("REST request to save Antibiotic : {}", antibiotic);
        if (antibiotic.getId() != null) {
            throw new BadRequestAlertException("A new antibiotic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Antibiotic result = antibioticRepository.save(antibiotic);
        return ResponseEntity
            .created(new URI("/api/antibiotics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antibiotics/:id} : Updates an existing antibiotic.
     *
     * @param id the id of the antibiotic to save.
     * @param antibiotic the antibiotic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antibiotic,
     * or with status {@code 400 (Bad Request)} if the antibiotic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antibiotic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antibiotics/{id}")
    public ResponseEntity<Antibiotic> updateAntibiotic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Antibiotic antibiotic
    ) throws URISyntaxException {
        log.debug("REST request to update Antibiotic : {}, {}", id, antibiotic);
        if (antibiotic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, antibiotic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!antibioticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Antibiotic result = antibioticRepository.save(antibiotic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antibiotic.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /antibiotics/:id} : Partial updates given fields of an existing antibiotic, field will ignore if it is null
     *
     * @param id the id of the antibiotic to save.
     * @param antibiotic the antibiotic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antibiotic,
     * or with status {@code 400 (Bad Request)} if the antibiotic is not valid,
     * or with status {@code 404 (Not Found)} if the antibiotic is not found,
     * or with status {@code 500 (Internal Server Error)} if the antibiotic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/antibiotics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Antibiotic> partialUpdateAntibiotic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Antibiotic antibiotic
    ) throws URISyntaxException {
        log.debug("REST request to partial update Antibiotic partially : {}, {}", id, antibiotic);
        if (antibiotic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, antibiotic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!antibioticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Antibiotic> result = antibioticRepository
            .findById(antibiotic.getId())
            .map(existingAntibiotic -> {
                if (antibiotic.getName() != null) {
                    existingAntibiotic.setName(antibiotic.getName());
                }
                if (antibiotic.getCode() != null) {
                    existingAntibiotic.setCode(antibiotic.getCode());
                }
                if (antibiotic.getStatus() != null) {
                    existingAntibiotic.setStatus(antibiotic.getStatus());
                }
                if (antibiotic.getDescription() != null) {
                    existingAntibiotic.setDescription(antibiotic.getDescription());
                }

                return existingAntibiotic;
            })
            .map(antibioticRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antibiotic.getId().toString())
        );
    }

    /**
     * {@code GET  /antibiotics} : get all the antibiotics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antibiotics in body.
     */
    @GetMapping("/antibiotics")
    public List<Antibiotic> getAllAntibiotics() {
        log.debug("REST request to get all Antibiotics");
        return antibioticRepository.findAll();
    }

    /**
     * {@code GET  /antibiotics/:id} : get the "id" antibiotic.
     *
     * @param id the id of the antibiotic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antibiotic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antibiotics/{id}")
    public ResponseEntity<Antibiotic> getAntibiotic(@PathVariable Long id) {
        log.debug("REST request to get Antibiotic : {}", id);
        Optional<Antibiotic> antibiotic = antibioticRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(antibiotic);
    }

    /**
     * {@code DELETE  /antibiotics/:id} : delete the "id" antibiotic.
     *
     * @param id the id of the antibiotic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antibiotics/{id}")
    public ResponseEntity<Void> deleteAntibiotic(@PathVariable Long id) {
        log.debug("REST request to delete Antibiotic : {}", id);
        antibioticRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
