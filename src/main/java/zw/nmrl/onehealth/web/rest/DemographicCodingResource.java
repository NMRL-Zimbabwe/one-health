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
import zw.nmrl.onehealth.domain.DemographicCoding;
import zw.nmrl.onehealth.repository.DemographicCodingRepository;
import zw.nmrl.onehealth.service.DemographicCodingService;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.DemographicCoding}.
 */
@RestController
@RequestMapping("/api")
public class DemographicCodingResource {

    private final Logger log = LoggerFactory.getLogger(DemographicCodingResource.class);

    private static final String ENTITY_NAME = "demographicCoding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemographicCodingService demographicCodingService;

    private final DemographicCodingRepository demographicCodingRepository;

    public DemographicCodingResource(
        DemographicCodingService demographicCodingService,
        DemographicCodingRepository demographicCodingRepository
    ) {
        this.demographicCodingService = demographicCodingService;
        this.demographicCodingRepository = demographicCodingRepository;
    }

    /**
     * {@code POST  /demographic-codings} : Create a new demographicCoding.
     *
     * @param demographicCoding the demographicCoding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demographicCoding, or with status {@code 400 (Bad Request)} if the demographicCoding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demographic-codings")
    public ResponseEntity<DemographicCoding> createDemographicCoding(@Valid @RequestBody DemographicCoding demographicCoding)
        throws URISyntaxException {
        log.debug("REST request to save DemographicCoding : {}", demographicCoding);
        if (demographicCoding.getId() != null) {
            throw new BadRequestAlertException("A new demographicCoding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemographicCoding result = demographicCodingService.save(demographicCoding);
        return ResponseEntity
            .created(new URI("/api/demographic-codings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demographic-codings/:id} : Updates an existing demographicCoding.
     *
     * @param id the id of the demographicCoding to save.
     * @param demographicCoding the demographicCoding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demographicCoding,
     * or with status {@code 400 (Bad Request)} if the demographicCoding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demographicCoding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demographic-codings/{id}")
    public ResponseEntity<DemographicCoding> updateDemographicCoding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemographicCoding demographicCoding
    ) throws URISyntaxException {
        log.debug("REST request to update DemographicCoding : {}, {}", id, demographicCoding);
        if (demographicCoding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demographicCoding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demographicCodingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemographicCoding result = demographicCodingService.update(demographicCoding);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demographicCoding.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demographic-codings/:id} : Partial updates given fields of an existing demographicCoding, field will ignore if it is null
     *
     * @param id the id of the demographicCoding to save.
     * @param demographicCoding the demographicCoding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demographicCoding,
     * or with status {@code 400 (Bad Request)} if the demographicCoding is not valid,
     * or with status {@code 404 (Not Found)} if the demographicCoding is not found,
     * or with status {@code 500 (Internal Server Error)} if the demographicCoding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demographic-codings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemographicCoding> partialUpdateDemographicCoding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemographicCoding demographicCoding
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemographicCoding partially : {}, {}", id, demographicCoding);
        if (demographicCoding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demographicCoding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demographicCodingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemographicCoding> result = demographicCodingService.partialUpdate(demographicCoding);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demographicCoding.getId().toString())
        );
    }

    /**
     * {@code GET  /demographic-codings} : get all the demographicCodings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demographicCodings in body.
     */
    @GetMapping("/demographic-codings")
    public ResponseEntity<List<DemographicCoding>> getAllDemographicCodings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of DemographicCodings");
        Page<DemographicCoding> page = demographicCodingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demographic-codings/:id} : get the "id" demographicCoding.
     *
     * @param id the id of the demographicCoding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demographicCoding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demographic-codings/{id}")
    public ResponseEntity<DemographicCoding> getDemographicCoding(@PathVariable Long id) {
        log.debug("REST request to get DemographicCoding : {}", id);
        Optional<DemographicCoding> demographicCoding = demographicCodingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demographicCoding);
    }

    /**
     * {@code DELETE  /demographic-codings/:id} : delete the "id" demographicCoding.
     *
     * @param id the id of the demographicCoding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demographic-codings/{id}")
    public ResponseEntity<Void> deleteDemographicCoding(@PathVariable Long id) {
        log.debug("REST request to delete DemographicCoding : {}", id);
        demographicCodingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
