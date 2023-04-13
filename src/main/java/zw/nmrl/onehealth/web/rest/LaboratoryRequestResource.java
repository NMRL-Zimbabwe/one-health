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
import zw.nmrl.onehealth.domain.LaboratoryRequest;
import zw.nmrl.onehealth.repository.LaboratoryRequestRepository;
import zw.nmrl.onehealth.service.LaboratoryRequestService;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.LaboratoryRequest}.
 */
@RestController
@RequestMapping("/api")
public class LaboratoryRequestResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestResource.class);

    private static final String ENTITY_NAME = "laboratoryRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoryRequestService laboratoryRequestService;

    private final LaboratoryRequestRepository laboratoryRequestRepository;

    public LaboratoryRequestResource(
        LaboratoryRequestService laboratoryRequestService,
        LaboratoryRequestRepository laboratoryRequestRepository
    ) {
        this.laboratoryRequestService = laboratoryRequestService;
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    /**
     * {@code POST  /laboratory-requests} : Create a new laboratoryRequest.
     *
     * @param laboratoryRequest the laboratoryRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laboratoryRequest, or with status {@code 400 (Bad Request)} if the laboratoryRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laboratory-requests")
    public ResponseEntity<LaboratoryRequest> createLaboratoryRequest(@Valid @RequestBody LaboratoryRequest laboratoryRequest)
        throws URISyntaxException {
        log.debug("REST request to save LaboratoryRequest : {}", laboratoryRequest);
        if (laboratoryRequest.getId() != null) {
            throw new BadRequestAlertException("A new laboratoryRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LaboratoryRequest result = laboratoryRequestService.save(laboratoryRequest);
        return ResponseEntity
            .created(new URI("/api/laboratory-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /laboratory-requests/:id} : Updates an existing laboratoryRequest.
     *
     * @param id the id of the laboratoryRequest to save.
     * @param laboratoryRequest the laboratoryRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoryRequest,
     * or with status {@code 400 (Bad Request)} if the laboratoryRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the laboratoryRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/laboratory-requests/{id}")
    public ResponseEntity<LaboratoryRequest> updateLaboratoryRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LaboratoryRequest laboratoryRequest
    ) throws URISyntaxException {
        log.debug("REST request to update LaboratoryRequest : {}, {}", id, laboratoryRequest);
        if (laboratoryRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratoryRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LaboratoryRequest result = laboratoryRequestService.update(laboratoryRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoryRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /laboratory-requests/:id} : Partial updates given fields of an existing laboratoryRequest, field will ignore if it is null
     *
     * @param id the id of the laboratoryRequest to save.
     * @param laboratoryRequest the laboratoryRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratoryRequest,
     * or with status {@code 400 (Bad Request)} if the laboratoryRequest is not valid,
     * or with status {@code 404 (Not Found)} if the laboratoryRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the laboratoryRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/laboratory-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LaboratoryRequest> partialUpdateLaboratoryRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LaboratoryRequest laboratoryRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update LaboratoryRequest partially : {}, {}", id, laboratoryRequest);
        if (laboratoryRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, laboratoryRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!laboratoryRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LaboratoryRequest> result = laboratoryRequestService.partialUpdate(laboratoryRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, laboratoryRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /laboratory-requests} : get all the laboratoryRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of laboratoryRequests in body.
     */
    @GetMapping("/laboratory-requests")
    public ResponseEntity<List<LaboratoryRequest>> getAllLaboratoryRequests(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LaboratoryRequests");
        Page<LaboratoryRequest> page = laboratoryRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /laboratory-requests/:id} : get the "id" laboratoryRequest.
     *
     * @param id the id of the laboratoryRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the laboratoryRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratory-requests/{id}")
    public ResponseEntity<LaboratoryRequest> getLaboratoryRequest(@PathVariable Long id) {
        log.debug("REST request to get LaboratoryRequest : {}", id);
        Optional<LaboratoryRequest> laboratoryRequest = laboratoryRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(laboratoryRequest);
    }

    /**
     * {@code DELETE  /laboratory-requests/:id} : delete the "id" laboratoryRequest.
     *
     * @param id the id of the laboratoryRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/laboratory-requests/{id}")
    public ResponseEntity<Void> deleteLaboratoryRequest(@PathVariable Long id) {
        log.debug("REST request to delete LaboratoryRequest : {}", id);
        laboratoryRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
