package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.LaboratoryRequest;

/**
 * Service Interface for managing {@link LaboratoryRequest}.
 */
public interface LaboratoryRequestService {
    /**
     * Save a laboratoryRequest.
     *
     * @param laboratoryRequest the entity to save.
     * @return the persisted entity.
     */
    LaboratoryRequest save(LaboratoryRequest laboratoryRequest);

    /**
     * Updates a laboratoryRequest.
     *
     * @param laboratoryRequest the entity to update.
     * @return the persisted entity.
     */
    LaboratoryRequest update(LaboratoryRequest laboratoryRequest);

    /**
     * Partially updates a laboratoryRequest.
     *
     * @param laboratoryRequest the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LaboratoryRequest> partialUpdate(LaboratoryRequest laboratoryRequest);

    /**
     * Get all the laboratoryRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LaboratoryRequest> findAll(Pageable pageable);

    /**
     * Get the "id" laboratoryRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LaboratoryRequest> findOne(Long id);

    /**
     * Delete the "id" laboratoryRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
