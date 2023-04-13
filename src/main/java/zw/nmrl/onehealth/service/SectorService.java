package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.Sector;

/**
 * Service Interface for managing {@link Sector}.
 */
public interface SectorService {
    /**
     * Save a sector.
     *
     * @param sector the entity to save.
     * @return the persisted entity.
     */
    Sector save(Sector sector);

    /**
     * Updates a sector.
     *
     * @param sector the entity to update.
     * @return the persisted entity.
     */
    Sector update(Sector sector);

    /**
     * Partially updates a sector.
     *
     * @param sector the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sector> partialUpdate(Sector sector);

    /**
     * Get all the sectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sector> findAll(Pageable pageable);

    /**
     * Get the "id" sector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sector> findOne(Long id);

    /**
     * Delete the "id" sector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
