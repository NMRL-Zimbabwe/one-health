package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.District;

/**
 * Service Interface for managing {@link District}.
 */
public interface DistrictService {
    /**
     * Save a district.
     *
     * @param district the entity to save.
     * @return the persisted entity.
     */
    District save(District district);

    /**
     * Updates a district.
     *
     * @param district the entity to update.
     * @return the persisted entity.
     */
    District update(District district);

    /**
     * Partially updates a district.
     *
     * @param district the entity to update partially.
     * @return the persisted entity.
     */
    Optional<District> partialUpdate(District district);

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<District> findAll(Pageable pageable);

    /**
     * Get the "id" district.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<District> findOne(Long id);

    /**
     * Delete the "id" district.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
