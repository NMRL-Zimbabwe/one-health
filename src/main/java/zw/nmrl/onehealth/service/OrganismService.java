package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.Organism;

/**
 * Service Interface for managing {@link Organism}.
 */
public interface OrganismService {
    /**
     * Save a organism.
     *
     * @param organism the entity to save.
     * @return the persisted entity.
     */
    Organism save(Organism organism);

    /**
     * Updates a organism.
     *
     * @param organism the entity to update.
     * @return the persisted entity.
     */
    Organism update(Organism organism);

    /**
     * Partially updates a organism.
     *
     * @param organism the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Organism> partialUpdate(Organism organism);

    /**
     * Get all the organisms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Organism> findAll(Pageable pageable);

    /**
     * Get the "id" organism.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Organism> findOne(Long id);

    /**
     * Delete the "id" organism.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
