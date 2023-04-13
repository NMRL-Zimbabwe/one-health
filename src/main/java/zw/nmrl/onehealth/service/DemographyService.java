package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.Demography;

/**
 * Service Interface for managing {@link Demography}.
 */
public interface DemographyService {
    /**
     * Save a demography.
     *
     * @param demography the entity to save.
     * @return the persisted entity.
     */
    Demography save(Demography demography);

    /**
     * Updates a demography.
     *
     * @param demography the entity to update.
     * @return the persisted entity.
     */
    Demography update(Demography demography);

    /**
     * Partially updates a demography.
     *
     * @param demography the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Demography> partialUpdate(Demography demography);

    /**
     * Get all the demographies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Demography> findAll(Pageable pageable);

    /**
     * Get the "id" demography.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Demography> findOne(Long id);

    /**
     * Delete the "id" demography.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
