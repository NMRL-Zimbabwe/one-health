package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.Antibiotic;

/**
 * Service Interface for managing {@link Antibiotic}.
 */
public interface AntibioticService {
    /**
     * Save a antibiotic.
     *
     * @param antibiotic the entity to save.
     * @return the persisted entity.
     */
    Antibiotic save(Antibiotic antibiotic);

    /**
     * Updates a antibiotic.
     *
     * @param antibiotic the entity to update.
     * @return the persisted entity.
     */
    Antibiotic update(Antibiotic antibiotic);

    /**
     * Partially updates a antibiotic.
     *
     * @param antibiotic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Antibiotic> partialUpdate(Antibiotic antibiotic);

    /**
     * Get all the antibiotics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Antibiotic> findAll(Pageable pageable);

    /**
     * Get the "id" antibiotic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Antibiotic> findOne(Long id);

    /**
     * Delete the "id" antibiotic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
