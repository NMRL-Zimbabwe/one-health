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
import zw.nmrl.onehealth.domain.Organism;
import zw.nmrl.onehealth.repository.OrganismRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Organism}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrganismResource {

    private final Logger log = LoggerFactory.getLogger(OrganismResource.class);

    private static final String ENTITY_NAME = "organism";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganismRepository organismRepository;

    public OrganismResource(OrganismRepository organismRepository) {
        this.organismRepository = organismRepository;
    }

    /**
     * {@code POST  /organisms} : Create a new organism.
     *
     * @param organism the organism to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organism, or with status {@code 400 (Bad Request)} if the organism has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisms")
    public ResponseEntity<Organism> createOrganism(@Valid @RequestBody Organism organism) throws URISyntaxException {
        log.debug("REST request to save Organism : {}", organism);
        if (organism.getId() != null) {
            throw new BadRequestAlertException("A new organism cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Organism result = organismRepository.save(organism);
        return ResponseEntity
            .created(new URI("/api/organisms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisms/:id} : Updates an existing organism.
     *
     * @param id the id of the organism to save.
     * @param organism the organism to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organism,
     * or with status {@code 400 (Bad Request)} if the organism is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organism couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisms/{id}")
    public ResponseEntity<Organism> updateOrganism(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Organism organism
    ) throws URISyntaxException {
        log.debug("REST request to update Organism : {}, {}", id, organism);
        if (organism.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organism.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organismRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Organism result = organismRepository.save(organism);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organism.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organisms/:id} : Partial updates given fields of an existing organism, field will ignore if it is null
     *
     * @param id the id of the organism to save.
     * @param organism the organism to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organism,
     * or with status {@code 400 (Bad Request)} if the organism is not valid,
     * or with status {@code 404 (Not Found)} if the organism is not found,
     * or with status {@code 500 (Internal Server Error)} if the organism couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organisms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Organism> partialUpdateOrganism(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Organism organism
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organism partially : {}, {}", id, organism);
        if (organism.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organism.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organismRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Organism> result = organismRepository
            .findById(organism.getId())
            .map(existingOrganism -> {
                if (organism.getName() != null) {
                    existingOrganism.setName(organism.getName());
                }
                if (organism.getCode() != null) {
                    existingOrganism.setCode(organism.getCode());
                }

                return existingOrganism;
            })
            .map(organismRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organism.getId().toString())
        );
    }

    /**
     * {@code GET  /organisms} : get all the organisms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisms in body.
     */
    @GetMapping("/organisms")
    public List<Organism> getAllOrganisms() {
        log.debug("REST request to get all Organisms");
        return organismRepository.findAll();
    }

    /**
     * {@code GET  /organisms/:id} : get the "id" organism.
     *
     * @param id the id of the organism to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organism, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisms/{id}")
    public ResponseEntity<Organism> getOrganism(@PathVariable Long id) {
        log.debug("REST request to get Organism : {}", id);
        Optional<Organism> organism = organismRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organism);
    }

    /**
     * {@code DELETE  /organisms/:id} : delete the "id" organism.
     *
     * @param id the id of the organism to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisms/{id}")
    public ResponseEntity<Void> deleteOrganism(@PathVariable Long id) {
        log.debug("REST request to delete Organism : {}", id);
        organismRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
