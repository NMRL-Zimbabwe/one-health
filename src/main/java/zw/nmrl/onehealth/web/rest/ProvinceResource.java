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
import zw.nmrl.onehealth.domain.Province;
import zw.nmrl.onehealth.repository.ProvinceRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.Province}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProvinceResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);

    private static final String ENTITY_NAME = "province";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvinceRepository provinceRepository;

    public ProvinceResource(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    /**
     * {@code POST  /provinces} : Create a new province.
     *
     * @param province the province to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new province, or with status {@code 400 (Bad Request)} if the province has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provinces")
    public ResponseEntity<Province> createProvince(@Valid @RequestBody Province province) throws URISyntaxException {
        log.debug("REST request to save Province : {}", province);
        if (province.getId() != null) {
            throw new BadRequestAlertException("A new province cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Province result = provinceRepository.save(province);
        return ResponseEntity
            .created(new URI("/api/provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provinces/:id} : Updates an existing province.
     *
     * @param id the id of the province to save.
     * @param province the province to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated province,
     * or with status {@code 400 (Bad Request)} if the province is not valid,
     * or with status {@code 500 (Internal Server Error)} if the province couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provinces/{id}")
    public ResponseEntity<Province> updateProvince(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Province province
    ) throws URISyntaxException {
        log.debug("REST request to update Province : {}, {}", id, province);
        if (province.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, province.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Province result = provinceRepository.save(province);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, province.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /provinces/:id} : Partial updates given fields of an existing province, field will ignore if it is null
     *
     * @param id the id of the province to save.
     * @param province the province to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated province,
     * or with status {@code 400 (Bad Request)} if the province is not valid,
     * or with status {@code 404 (Not Found)} if the province is not found,
     * or with status {@code 500 (Internal Server Error)} if the province couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/provinces/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Province> partialUpdateProvince(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Province province
    ) throws URISyntaxException {
        log.debug("REST request to partial update Province partially : {}, {}", id, province);
        if (province.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, province.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Province> result = provinceRepository
            .findById(province.getId())
            .map(existingProvince -> {
                if (province.getName() != null) {
                    existingProvince.setName(province.getName());
                }
                if (province.getCountryId() != null) {
                    existingProvince.setCountryId(province.getCountryId());
                }
                if (province.getLongitude() != null) {
                    existingProvince.setLongitude(province.getLongitude());
                }
                if (province.getLatitude() != null) {
                    existingProvince.setLatitude(province.getLatitude());
                }

                return existingProvince;
            })
            .map(provinceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, province.getId().toString())
        );
    }

    /**
     * {@code GET  /provinces} : get all the provinces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provinces in body.
     */
    @GetMapping("/provinces")
    public List<Province> getAllProvinces() {
        log.debug("REST request to get all Provinces");
        return provinceRepository.findAll();
    }

    /**
     * {@code GET  /provinces/:id} : get the "id" province.
     *
     * @param id the id of the province to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the province, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provinces/{id}")
    public ResponseEntity<Province> getProvince(@PathVariable Long id) {
        log.debug("REST request to get Province : {}", id);
        Optional<Province> province = provinceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(province);
    }

    /**
     * {@code DELETE  /provinces/:id} : delete the "id" province.
     *
     * @param id the id of the province to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        log.debug("REST request to delete Province : {}", id);
        provinceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
