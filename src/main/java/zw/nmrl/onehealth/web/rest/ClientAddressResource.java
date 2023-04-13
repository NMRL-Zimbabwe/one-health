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
import zw.nmrl.onehealth.domain.ClientAddress;
import zw.nmrl.onehealth.repository.ClientAddressRepository;
import zw.nmrl.onehealth.service.ClientAddressService;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.ClientAddress}.
 */
@RestController
@RequestMapping("/api")
public class ClientAddressResource {

    private final Logger log = LoggerFactory.getLogger(ClientAddressResource.class);

    private static final String ENTITY_NAME = "clientAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientAddressService clientAddressService;

    private final ClientAddressRepository clientAddressRepository;

    public ClientAddressResource(ClientAddressService clientAddressService, ClientAddressRepository clientAddressRepository) {
        this.clientAddressService = clientAddressService;
        this.clientAddressRepository = clientAddressRepository;
    }

    /**
     * {@code POST  /client-addresses} : Create a new clientAddress.
     *
     * @param clientAddress the clientAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientAddress, or with status {@code 400 (Bad Request)} if the clientAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-addresses")
    public ResponseEntity<ClientAddress> createClientAddress(@RequestBody ClientAddress clientAddress) throws URISyntaxException {
        log.debug("REST request to save ClientAddress : {}", clientAddress);
        if (clientAddress.getId() != null) {
            throw new BadRequestAlertException("A new clientAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientAddress result = clientAddressService.save(clientAddress);
        return ResponseEntity
            .created(new URI("/api/client-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-addresses/:id} : Updates an existing clientAddress.
     *
     * @param id the id of the clientAddress to save.
     * @param clientAddress the clientAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientAddress,
     * or with status {@code 400 (Bad Request)} if the clientAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-addresses/{id}")
    public ResponseEntity<ClientAddress> updateClientAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientAddress clientAddress
    ) throws URISyntaxException {
        log.debug("REST request to update ClientAddress : {}, {}", id, clientAddress);
        if (clientAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientAddress.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientAddress result = clientAddressService.update(clientAddress);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-addresses/:id} : Partial updates given fields of an existing clientAddress, field will ignore if it is null
     *
     * @param id the id of the clientAddress to save.
     * @param clientAddress the clientAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientAddress,
     * or with status {@code 400 (Bad Request)} if the clientAddress is not valid,
     * or with status {@code 404 (Not Found)} if the clientAddress is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientAddress> partialUpdateClientAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientAddress clientAddress
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientAddress partially : {}, {}", id, clientAddress);
        if (clientAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientAddress.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientAddress> result = clientAddressService.partialUpdate(clientAddress);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientAddress.getId().toString())
        );
    }

    /**
     * {@code GET  /client-addresses} : get all the clientAddresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientAddresses in body.
     */
    @GetMapping("/client-addresses")
    public ResponseEntity<List<ClientAddress>> getAllClientAddresses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ClientAddresses");
        Page<ClientAddress> page = clientAddressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-addresses/:id} : get the "id" clientAddress.
     *
     * @param id the id of the clientAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-addresses/{id}")
    public ResponseEntity<ClientAddress> getClientAddress(@PathVariable Long id) {
        log.debug("REST request to get ClientAddress : {}", id);
        Optional<ClientAddress> clientAddress = clientAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientAddress);
    }

    /**
     * {@code DELETE  /client-addresses/:id} : delete the "id" clientAddress.
     *
     * @param id the id of the clientAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-addresses/{id}")
    public ResponseEntity<Void> deleteClientAddress(@PathVariable Long id) {
        log.debug("REST request to delete ClientAddress : {}", id);
        clientAddressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
