package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.AnalysisService;
import zw.nmrl.onehealth.repository.AnalysisServiceRepository;
import zw.nmrl.onehealth.service.AnalysisServiceService;

/**
 * Service Implementation for managing {@link AnalysisService}.
 */
@Service
@Transactional
public class AnalysisServiceServiceImpl implements AnalysisServiceService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceServiceImpl.class);

    private final AnalysisServiceRepository analysisServiceRepository;

    public AnalysisServiceServiceImpl(AnalysisServiceRepository analysisServiceRepository) {
        this.analysisServiceRepository = analysisServiceRepository;
    }

    @Override
    public AnalysisService save(AnalysisService analysisService) {
        log.debug("Request to save AnalysisService : {}", analysisService);
        return analysisServiceRepository.save(analysisService);
    }

    @Override
    public AnalysisService update(AnalysisService analysisService) {
        log.debug("Request to update AnalysisService : {}", analysisService);
        return analysisServiceRepository.save(analysisService);
    }

    @Override
    public Optional<AnalysisService> partialUpdate(AnalysisService analysisService) {
        log.debug("Request to partially update AnalysisService : {}", analysisService);

        return analysisServiceRepository
            .findById(analysisService.getId())
            .map(existingAnalysisService -> {
                if (analysisService.getName() != null) {
                    existingAnalysisService.setName(analysisService.getName());
                }
                if (analysisService.getCode() != null) {
                    existingAnalysisService.setCode(analysisService.getCode());
                }

                return existingAnalysisService;
            })
            .map(analysisServiceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalysisService> findAll(Pageable pageable) {
        log.debug("Request to get all AnalysisServices");
        return analysisServiceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalysisService> findOne(Long id) {
        log.debug("Request to get AnalysisService : {}", id);
        return analysisServiceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalysisService : {}", id);
        analysisServiceRepository.deleteById(id);
    }
}
