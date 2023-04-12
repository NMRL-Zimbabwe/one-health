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
import zw.nmrl.onehealth.domain.AnalysisService;
import zw.nmrl.onehealth.repository.AnalysisServiceRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.AnalysisService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnalysisServiceResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceResource.class);

    private static final String ENTITY_NAME = "analysisService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisServiceRepository analysisServiceRepository;

    public AnalysisServiceResource(AnalysisServiceRepository analysisServiceRepository) {
        this.analysisServiceRepository = analysisServiceRepository;
    }

    /**
     * {@code POST  /analysis-services} : Create a new analysisService.
     *
     * @param analysisService the analysisService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysisService, or with status {@code 400 (Bad Request)} if the analysisService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analysis-services")
    public ResponseEntity<AnalysisService> createAnalysisService(@Valid @RequestBody AnalysisService analysisService)
        throws URISyntaxException {
        log.debug("REST request to save AnalysisService : {}", analysisService);
        if (analysisService.getId() != null) {
            throw new BadRequestAlertException("A new analysisService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalysisService result = analysisServiceRepository.save(analysisService);
        return ResponseEntity
            .created(new URI("/api/analysis-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analysis-services/:id} : Updates an existing analysisService.
     *
     * @param id the id of the analysisService to save.
     * @param analysisService the analysisService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisService,
     * or with status {@code 400 (Bad Request)} if the analysisService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysisService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analysis-services/{id}")
    public ResponseEntity<AnalysisService> updateAnalysisService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnalysisService analysisService
    ) throws URISyntaxException {
        log.debug("REST request to update AnalysisService : {}, {}", id, analysisService);
        if (analysisService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalysisService result = analysisServiceRepository.save(analysisService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analysis-services/:id} : Partial updates given fields of an existing analysisService, field will ignore if it is null
     *
     * @param id the id of the analysisService to save.
     * @param analysisService the analysisService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisService,
     * or with status {@code 400 (Bad Request)} if the analysisService is not valid,
     * or with status {@code 404 (Not Found)} if the analysisService is not found,
     * or with status {@code 500 (Internal Server Error)} if the analysisService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analysis-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnalysisService> partialUpdateAnalysisService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnalysisService analysisService
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalysisService partially : {}, {}", id, analysisService);
        if (analysisService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysisService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalysisService> result = analysisServiceRepository
            .findById(analysisService.getId())
            .map(existingAnalysisService -> {
                if (analysisService.getName() != null) {
                    existingAnalysisService.setName(analysisService.getName());
                }

                return existingAnalysisService;
            })
            .map(analysisServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysisService.getId().toString())
        );
    }

    /**
     * {@code GET  /analysis-services} : get all the analysisServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analysisServices in body.
     */
    @GetMapping("/analysis-services")
    public List<AnalysisService> getAllAnalysisServices() {
        log.debug("REST request to get all AnalysisServices");
        return analysisServiceRepository.findAll();
    }

    /**
     * {@code GET  /analysis-services/:id} : get the "id" analysisService.
     *
     * @param id the id of the analysisService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysisService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analysis-services/{id}")
    public ResponseEntity<AnalysisService> getAnalysisService(@PathVariable Long id) {
        log.debug("REST request to get AnalysisService : {}", id);
        Optional<AnalysisService> analysisService = analysisServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(analysisService);
    }

    /**
     * {@code DELETE  /analysis-services/:id} : delete the "id" analysisService.
     *
     * @param id the id of the analysisService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analysis-services/{id}")
    public ResponseEntity<Void> deleteAnalysisService(@PathVariable Long id) {
        log.debug("REST request to delete AnalysisService : {}", id);
        analysisServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
