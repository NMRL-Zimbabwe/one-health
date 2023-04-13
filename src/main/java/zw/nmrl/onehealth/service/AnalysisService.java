package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.Analysis;

/**
 * Service Interface for managing {@link Analysis}.
 */
public interface AnalysisService {
    /**
     * Save a analysis.
     *
     * @param analysis the entity to save.
     * @return the persisted entity.
     */
    Analysis save(Analysis analysis);

    /**
     * Updates a analysis.
     *
     * @param analysis the entity to update.
     * @return the persisted entity.
     */
    Analysis update(Analysis analysis);

    /**
     * Partially updates a analysis.
     *
     * @param analysis the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Analysis> partialUpdate(Analysis analysis);

    /**
     * Get all the analyses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Analysis> findAll(Pageable pageable);

    /**
     * Get the "id" analysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Analysis> findOne(Long id);

    /**
     * Delete the "id" analysis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
