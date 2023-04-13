package zw.nmrl.onehealth.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.nmrl.onehealth.domain.AnalysisService;

/**
 * Service Interface for managing {@link AnalysisService}.
 */
public interface AnalysisServiceService {
    /**
     * Save a analysisService.
     *
     * @param analysisService the entity to save.
     * @return the persisted entity.
     */
    AnalysisService save(AnalysisService analysisService);

    /**
     * Updates a analysisService.
     *
     * @param analysisService the entity to update.
     * @return the persisted entity.
     */
    AnalysisService update(AnalysisService analysisService);

    /**
     * Partially updates a analysisService.
     *
     * @param analysisService the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalysisService> partialUpdate(AnalysisService analysisService);

    /**
     * Get all the analysisServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnalysisService> findAll(Pageable pageable);

    /**
     * Get the "id" analysisService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalysisService> findOne(Long id);

    /**
     * Delete the "id" analysisService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
