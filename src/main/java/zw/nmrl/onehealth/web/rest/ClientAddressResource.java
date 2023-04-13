package zw.nmrl.onehealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.nmrl.onehealth.domain.ClientAddress;
import zw.nmrl.onehealth.repository.ClientAddressRepository;
import zw.nmrl.onehealth.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link zw.nmrl.onehealth.domain.ClientAddress}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientAddressResource {

    private final Logger log = LoggerFactory.getLogger(ClientAddressResource.class);

    private static final String ENTITY_NAME = "clientAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientAddressRepository clientAddressRepository;

    public ClientAddressResource(ClientAddressRepository clientAddressRepository) {
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
        ClientAddress result = clientAddressRepository.save(clientAddress);
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

        ClientAddress result = clientAddressRepository.save(clientAddress);
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

        Optional<ClientAddress> result = clientAddressRepository
            .findById(clientAddress.getId())
            .map(existingClientAddress -> {
                if (clientAddress.getName() != null) {
                    existingClientAddress.setName(clientAddress.getName());
                }
                if (clientAddress.getClientId() != null) {
                    existingClientAddress.setClientId(clientAddress.getClientId());
                }

                return existingClientAddress;
            })
            .map(clientAddressRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientAddress.getId().toString())
        );
    }

    /**
     * {@code GET  /client-addresses} : get all the clientAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientAddresses in body.
     */
    @GetMapping("/client-addresses")
    public List<ClientAddress> getAllClientAddresses() {
        log.debug("REST request to get all ClientAddresses");
        return clientAddressRepository.findAll();
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
        Optional<ClientAddress> clientAddress = clientAddressRepository.findById(id);
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
        clientAddressRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
