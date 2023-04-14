package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.AntibioticClass;

/**
 * Service Interface for managing {@link AntibioticClass}.
 */
public interface AntibioticClassService {
    /**
     * Save a antibioticClass.
     *
     * @param antibioticClass the entity to save.
     * @return the persisted entity.
     */
    AntibioticClass save(AntibioticClass antibioticClass);

    /**
     * Updates a antibioticClass.
     *
     * @param antibioticClass the entity to update.
     * @return the persisted entity.
     */
    AntibioticClass update(AntibioticClass antibioticClass);

    /**
     * Partially updates a antibioticClass.
     *
     * @param antibioticClass the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AntibioticClass> partialUpdate(AntibioticClass antibioticClass);

    /**
     * Get all the antibioticClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AntibioticClass> findAll(Pageable pageable);

    /**
     * Get the "id" antibioticClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AntibioticClass> findOne(Long id);

    /**
     * Delete the "id" antibioticClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
