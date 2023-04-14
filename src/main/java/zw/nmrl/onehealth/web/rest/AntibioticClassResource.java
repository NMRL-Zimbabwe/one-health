package zw.nmrl.onehealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import zw.nmrl.onehealth.domain.AntibioticClass;
import zw.nmrl.onehealth.repository.AntibioticClassRepository;
import zw.nmrl.onehealth.service.AntibioticClassService;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.AntibioticClass}.
 */
@RestController
@RequestMapping("/api")
public class AntibioticClassResource {

    private final Logger log = LoggerFactory.getLogger(AntibioticClassResource.class);

    private static final String ENTITY_NAME = "antibioticClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntibioticClassService antibioticClassService;

    private final AntibioticClassRepository antibioticClassRepository;

    public AntibioticClassResource(AntibioticClassService antibioticClassService, AntibioticClassRepository antibioticClassRepository) {
        this.antibioticClassService = antibioticClassService;
        this.antibioticClassRepository = antibioticClassRepository;
    }

    /**
     * {@code POST  /antibiotic-classes} : Create a new antibioticClass.
     *
     * @param antibioticClass the antibioticClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antibioticClass, or with status {@code 400 (Bad Request)} if the antibioticClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antibiotic-classes")
    public ResponseEntity<AntibioticClass> createAntibioticClass(@RequestBody AntibioticClass antibioticClass) throws URISyntaxException {
        log.debug("REST request to save AntibioticClass : {}", antibioticClass);
        if (antibioticClass.getId() != null) {
            throw new BadRequestAlertException("A new antibioticClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntibioticClass result = antibioticClassService.save(antibioticClass);
        return ResponseEntity
            .created(new URI("/api/antibiotic-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antibiotic-classes/:id} : Updates an existing antibioticClass.
     *
     * @param id the id of the antibioticClass to save.
     * @param antibioticClass the antibioticClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antibioticClass,
     * or with status {@code 400 (Bad Request)} if the antibioticClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antibioticClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antibiotic-classes/{id}")
    public ResponseEntity<AntibioticClass> updateAntibioticClass(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AntibioticClass antibioticClass
    ) throws URISyntaxException {
        log.debug("REST request to update AntibioticClass : {}, {}", id, antibioticClass);
        if (antibioticClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, antibioticClass.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!antibioticClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AntibioticClass result = antibioticClassService.update(antibioticClass);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antibioticClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /antibiotic-classes/:id} : Partial updates given fields of an existing antibioticClass, field will ignore if it is null
     *
     * @param id the id of the antibioticClass to save.
     * @param antibioticClass the antibioticClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antibioticClass,
     * or with status {@code 400 (Bad Request)} if the antibioticClass is not valid,
     * or with status {@code 404 (Not Found)} if the antibioticClass is not found,
     * or with status {@code 500 (Internal Server Error)} if the antibioticClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/antibiotic-classes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AntibioticClass> partialUpdateAntibioticClass(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AntibioticClass antibioticClass
    ) throws URISyntaxException {
        log.debug("REST request to partial update AntibioticClass partially : {}, {}", id, antibioticClass);
        if (antibioticClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, antibioticClass.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!antibioticClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AntibioticClass> result = antibioticClassService.partialUpdate(antibioticClass);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antibioticClass.getId().toString())
        );
    }

    /**
     * {@code GET  /antibiotic-classes} : get all the antibioticClasses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antibioticClasses in body.
     */
    @GetMapping("/antibiotic-classes")
    public ResponseEntity<List<AntibioticClass>> getAllAntibioticClasses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AntibioticClasses");
        Page<AntibioticClass> page = antibioticClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /antibiotic-classes/:id} : get the "id" antibioticClass.
     *
     * @param id the id of the antibioticClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antibioticClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antibiotic-classes/{id}")
    public ResponseEntity<AntibioticClass> getAntibioticClass(@PathVariable Long id) {
        log.debug("REST request to get AntibioticClass : {}", id);
        Optional<AntibioticClass> antibioticClass = antibioticClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(antibioticClass);
    }

    /**
     * {@code DELETE  /antibiotic-classes/:id} : delete the "id" antibioticClass.
     *
     * @param id the id of the antibioticClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antibiotic-classes/{id}")
    public ResponseEntity<Void> deleteAntibioticClass(@PathVariable Long id) {
        log.debug("REST request to delete AntibioticClass : {}", id);
        antibioticClassService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
