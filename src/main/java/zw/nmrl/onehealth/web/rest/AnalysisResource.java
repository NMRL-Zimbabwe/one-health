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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.nmrl.onehealth.domain.Analysis;
import zw.nmrl.onehealth.repository.AnalysisRepository;
import zw.nmrl.onehealth.service.AnalysisService;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Analysis}.
 */
@RestController
@RequestMapping("/api")
public class AnalysisResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisResource.class);

    private static final String ENTITY_NAME = "analysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisService analysisService;

    private final AnalysisRepository analysisRepository;

    public AnalysisResource(AnalysisService analysisService, AnalysisRepository analysisRepository) {
        this.analysisService = analysisService;
        this.analysisRepository = analysisRepository;
    }

    /**
     * {@code POST  /analyses} : Create a new analysis.
     *
     * @param analysis the analysis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysis, or with status {@code 400 (Bad Request)} if the analysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyses")
    public ResponseEntity<Analysis> createAnalysis(@Valid @RequestBody Analysis analysis) throws URISyntaxException {
        log.debug("REST request to save Analysis : {}", analysis);
        if (analysis.getId() != null) {
            throw new BadRequestAlertException("A new analysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Analysis result = analysisService.save(analysis);
        return ResponseEntity
            .created(new URI("/api/analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyses/:id} : Updates an existing analysis.
     *
     * @param id the id of the analysis to save.
     * @param analysis the analysis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysis,
     * or with status {@code 400 (Bad Request)} if the analysis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyses/{id}")
    public ResponseEntity<Analysis> updateAnalysis(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Analysis analysis
    ) throws URISyntaxException {
        log.debug("REST request to update Analysis : {}, {}", id, analysis);
        if (analysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Analysis result = analysisService.update(analysis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analyses/:id} : Partial updates given fields of an existing analysis, field will ignore if it is null
     *
     * @param id the id of the analysis to save.
     * @param analysis the analysis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysis,
     * or with status {@code 400 (Bad Request)} if the analysis is not valid,
     * or with status {@code 404 (Not Found)} if the analysis is not found,
     * or with status {@code 500 (Internal Server Error)} if the analysis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analyses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Analysis> partialUpdateAnalysis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Analysis analysis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Analysis partially : {}, {}", id, analysis);
        if (analysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analysis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analysisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Analysis> result = analysisService.partialUpdate(analysis);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysis.getId().toString())
        );
    }

    /**
     * {@code GET  /analyses} : get all the analyses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyses in body.
     */
    @GetMapping("/analyses")
    public ResponseEntity<List<Analysis>> getAllAnalyses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Analyses");
        Page<Analysis> page = analysisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /analyses/:id} : get the "id" analysis.
     *
     * @param id the id of the analysis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyses/{id}")
    public ResponseEntity<Analysis> getAnalysis(@PathVariable Long id) {
        log.debug("REST request to get Analysis : {}", id);
        Optional<Analysis> analysis = analysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analysis);
    }

    /**
     * {@code DELETE  /analyses/:id} : delete the "id" analysis.
     *
     * @param id the id of the analysis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyses/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete Analysis : {}", id);
        analysisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
