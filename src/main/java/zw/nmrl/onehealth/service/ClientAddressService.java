package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.ClientAddress;

/**
 * Service Interface for managing {@link ClientAddress}.
 */
public interface ClientAddressService {
    /**
     * Save a clientAddress.
     *
     * @param clientAddress the entity to save.
     * @return the persisted entity.
     */
    ClientAddress save(ClientAddress clientAddress);

    /**
     * Updates a clientAddress.
     *
     * @param clientAddress the entity to update.
     * @return the persisted entity.
     */
    ClientAddress update(ClientAddress clientAddress);

    /**
     * Partially updates a clientAddress.
     *
     * @param clientAddress the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClientAddress> partialUpdate(ClientAddress clientAddress);

    /**
     * Get all the clientAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientAddress> findAll(Pageable pageable);

    /**
     * Get the "id" clientAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientAddress> findOne(Long id);

    /**
     * Delete the "id" clientAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
