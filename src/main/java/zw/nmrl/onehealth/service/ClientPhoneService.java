package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.ClientPhone;

/**
 * Service Interface for managing {@link ClientPhone}.
 */
public interface ClientPhoneService {
    /**
     * Save a clientPhone.
     *
     * @param clientPhone the entity to save.
     * @return the persisted entity.
     */
    ClientPhone save(ClientPhone clientPhone);

    /**
     * Updates a clientPhone.
     *
     * @param clientPhone the entity to update.
     * @return the persisted entity.
     */
    ClientPhone update(ClientPhone clientPhone);

    /**
     * Partially updates a clientPhone.
     *
     * @param clientPhone the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClientPhone> partialUpdate(ClientPhone clientPhone);

    /**
     * Get all the clientPhones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientPhone> findAll(Pageable pageable);

    /**
     * Get the "id" clientPhone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientPhone> findOne(Long id);

    /**
     * Delete the "id" clientPhone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
