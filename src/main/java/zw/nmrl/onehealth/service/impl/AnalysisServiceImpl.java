package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.Analysis;
import zw.nmrl.onehealth.repository.AnalysisRepository;
import zw.nmrl.onehealth.service.AnalysisService;

/**
 * Service Implementation for managing {@link Analysis}.
 */
@Service
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository analysisRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public Analysis save(Analysis analysis) {
        log.debug("Request to save Analysis : {}", analysis);
        return analysisRepository.save(analysis);
    }

    @Override
    public Analysis update(Analysis analysis) {
        log.debug("Request to update Analysis : {}", analysis);
        return analysisRepository.save(analysis);
    }

    @Override
    public Optional<Analysis> partialUpdate(Analysis analysis) {
        log.debug("Request to partially update Analysis : {}", analysis);

        return analysisRepository
            .findById(analysis.getId())
            .map(existingAnalysis -> {
                if (analysis.getSampleId() != null) {
                    existingAnalysis.setSampleId(analysis.getSampleId());
                }
                if (analysis.getAnalysisServiceId() != null) {
                    existingAnalysis.setAnalysisServiceId(analysis.getAnalysisServiceId());
                }
                if (analysis.getResult() != null) {
                    existingAnalysis.setResult(analysis.getResult());
                }
                if (analysis.getDateResulted() != null) {
                    existingAnalysis.setDateResulted(analysis.getDateResulted());
                }

                return existingAnalysis;
            })
            .map(analysisRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Analysis> findAll(Pageable pageable) {
        log.debug("Request to get all Analyses");
        return analysisRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Analysis> findOne(Long id) {
        log.debug("Request to get Analysis : {}", id);
        return analysisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Analysis : {}", id);
        analysisRepository.deleteById(id);
    }
}
