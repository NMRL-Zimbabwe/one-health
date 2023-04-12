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
import zw.nmrl.onehealth.domain.Sample;
import zw.nmrl.onehealth.repository.SampleRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Sample}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SampleResource {

    private final Logger log = LoggerFactory.getLogger(SampleResource.class);

    private static final String ENTITY_NAME = "sample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SampleRepository sampleRepository;

    public SampleResource(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    /**
     * {@code POST  /samples} : Create a new sample.
     *
     * @param sample the sample to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sample, or with status {@code 400 (Bad Request)} if the sample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/samples")
    public ResponseEntity<Sample> createSample(@Valid @RequestBody Sample sample) throws URISyntaxException {
        log.debug("REST request to save Sample : {}", sample);
        if (sample.getId() != null) {
            throw new BadRequestAlertException("A new sample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sample result = sampleRepository.save(sample);
        return ResponseEntity
            .created(new URI("/api/samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /samples/:id} : Updates an existing sample.
     *
     * @param id the id of the sample to save.
     * @param sample the sample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sample,
     * or with status {@code 400 (Bad Request)} if the sample is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/samples/{id}")
    public ResponseEntity<Sample> updateSample(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sample sample
    ) throws URISyntaxException {
        log.debug("REST request to update Sample : {}, {}", id, sample);
        if (sample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sample result = sampleRepository.save(sample);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sample.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /samples/:id} : Partial updates given fields of an existing sample, field will ignore if it is null
     *
     * @param id the id of the sample to save.
     * @param sample the sample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sample,
     * or with status {@code 400 (Bad Request)} if the sample is not valid,
     * or with status {@code 404 (Not Found)} if the sample is not found,
     * or with status {@code 500 (Internal Server Error)} if the sample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/samples/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sample> partialUpdateSample(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sample sample
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sample partially : {}, {}", id, sample);
        if (sample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sample> result = sampleRepository
            .findById(sample.getId())
            .map(existingSample -> {
                if (sample.getSampleTypeId() != null) {
                    existingSample.setSampleTypeId(sample.getSampleTypeId());
                }
                if (sample.getAnalysisServiceId() != null) {
                    existingSample.setAnalysisServiceId(sample.getAnalysisServiceId());
                }
                if (sample.getLongitude() != null) {
                    existingSample.setLongitude(sample.getLongitude());
                }
                if (sample.getLatitude() != null) {
                    existingSample.setLatitude(sample.getLatitude());
                }

                return existingSample;
            })
            .map(sampleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sample.getId().toString())
        );
    }

    /**
     * {@code GET  /samples} : get all the samples.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of samples in body.
     */
    @GetMapping("/samples")
    public List<Sample> getAllSamples() {
        log.debug("REST request to get all Samples");
        return sampleRepository.findAll();
    }

    /**
     * {@code GET  /samples/:id} : get the "id" sample.
     *
     * @param id the id of the sample to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sample, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/samples/{id}")
    public ResponseEntity<Sample> getSample(@PathVariable Long id) {
        log.debug("REST request to get Sample : {}", id);
        Optional<Sample> sample = sampleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sample);
    }

    /**
     * {@code DELETE  /samples/:id} : delete the "id" sample.
     *
     * @param id the id of the sample to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/samples/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        log.debug("REST request to delete Sample : {}", id);
        sampleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
