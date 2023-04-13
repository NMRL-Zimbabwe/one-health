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
import zw.nmrl.onehealth.domain.ClientPhone;
import zw.nmrl.onehealth.repository.ClientPhoneRepository;
import zw.nmrl.onehealth.service.ClientPhoneService;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.ClientPhone}.
 */
@RestController
@RequestMapping("/api")
public class ClientPhoneResource {

    private final Logger log = LoggerFactory.getLogger(ClientPhoneResource.class);

    private static final String ENTITY_NAME = "clientPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientPhoneService clientPhoneService;

    private final ClientPhoneRepository clientPhoneRepository;

    public ClientPhoneResource(ClientPhoneService clientPhoneService, ClientPhoneRepository clientPhoneRepository) {
        this.clientPhoneService = clientPhoneService;
        this.clientPhoneRepository = clientPhoneRepository;
    }

    /**
     * {@code POST  /client-phones} : Create a new clientPhone.
     *
     * @param clientPhone the clientPhone to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientPhone, or with status {@code 400 (Bad Request)} if the clientPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-phones")
    public ResponseEntity<ClientPhone> createClientPhone(@RequestBody ClientPhone clientPhone) throws URISyntaxException {
        log.debug("REST request to save ClientPhone : {}", clientPhone);
        if (clientPhone.getId() != null) {
            throw new BadRequestAlertException("A new clientPhone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientPhone result = clientPhoneService.save(clientPhone);
        return ResponseEntity
            .created(new URI("/api/client-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-phones/:id} : Updates an existing clientPhone.
     *
     * @param id the id of the clientPhone to save.
     * @param clientPhone the clientPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientPhone,
     * or with status {@code 400 (Bad Request)} if the clientPhone is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-phones/{id}")
    public ResponseEntity<ClientPhone> updateClientPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientPhone clientPhone
    ) throws URISyntaxException {
        log.debug("REST request to update ClientPhone : {}, {}", id, clientPhone);
        if (clientPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientPhone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientPhone result = clientPhoneService.update(clientPhone);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientPhone.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-phones/:id} : Partial updates given fields of an existing clientPhone, field will ignore if it is null
     *
     * @param id the id of the clientPhone to save.
     * @param clientPhone the clientPhone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientPhone,
     * or with status {@code 400 (Bad Request)} if the clientPhone is not valid,
     * or with status {@code 404 (Not Found)} if the clientPhone is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientPhone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-phones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientPhone> partialUpdateClientPhone(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientPhone clientPhone
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientPhone partially : {}, {}", id, clientPhone);
        if (clientPhone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientPhone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientPhoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientPhone> result = clientPhoneService.partialUpdate(clientPhone);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientPhone.getId().toString())
        );
    }

    /**
     * {@code GET  /client-phones} : get all the clientPhones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientPhones in body.
     */
    @GetMapping("/client-phones")
    public ResponseEntity<List<ClientPhone>> getAllClientPhones(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ClientPhones");
        Page<ClientPhone> page = clientPhoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-phones/:id} : get the "id" clientPhone.
     *
     * @param id the id of the clientPhone to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientPhone, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-phones/{id}")
    public ResponseEntity<ClientPhone> getClientPhone(@PathVariable Long id) {
        log.debug("REST request to get ClientPhone : {}", id);
        Optional<ClientPhone> clientPhone = clientPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientPhone);
    }

    /**
     * {@code DELETE  /client-phones/:id} : delete the "id" clientPhone.
     *
     * @param id the id of the clientPhone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-phones/{id}")
    public ResponseEntity<Void> deleteClientPhone(@PathVariable Long id) {
        log.debug("REST request to delete ClientPhone : {}", id);
        clientPhoneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
