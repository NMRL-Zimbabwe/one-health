package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.DemographicCoding;

/**
 * Service Interface for managing {@link DemographicCoding}.
 */
public interface DemographicCodingService {
    /**
     * Save a demographicCoding.
     *
     * @param demographicCoding the entity to save.
     * @return the persisted entity.
     */
    DemographicCoding save(DemographicCoding demographicCoding);

    /**
     * Updates a demographicCoding.
     *
     * @param demographicCoding the entity to update.
     * @return the persisted entity.
     */
    DemographicCoding update(DemographicCoding demographicCoding);

    /**
     * Partially updates a demographicCoding.
     *
     * @param demographicCoding the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemographicCoding> partialUpdate(DemographicCoding demographicCoding);

    /**
     * Get all the demographicCodings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemographicCoding> findAll(Pageable pageable);

    /**
     * Get the "id" demographicCoding.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemographicCoding> findOne(Long id);

    /**
     * Delete the "id" demographicCoding.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
