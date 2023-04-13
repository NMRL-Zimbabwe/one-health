package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.SampleType;
import zw.nmrl.onehealth.repository.SampleTypeRepository;
import zw.nmrl.onehealth.service.SampleTypeService;

/**
 * Service Implementation for managing {@link SampleType}.
 */
@Service
@Transactional
public class SampleTypeServiceImpl implements SampleTypeService {

    private final Logger log = LoggerFactory.getLogger(SampleTypeServiceImpl.class);

    private final SampleTypeRepository sampleTypeRepository;

    public SampleTypeServiceImpl(SampleTypeRepository sampleTypeRepository) {
        this.sampleTypeRepository = sampleTypeRepository;
    }

    @Override
    public SampleType save(SampleType sampleType) {
        log.debug("Request to save SampleType : {}", sampleType);
        return sampleTypeRepository.save(sampleType);
    }

    @Override
    public SampleType update(SampleType sampleType) {
        log.debug("Request to update SampleType : {}", sampleType);
        return sampleTypeRepository.save(sampleType);
    }

    @Override
    public Optional<SampleType> partialUpdate(SampleType sampleType) {
        log.debug("Request to partially update SampleType : {}", sampleType);

        return sampleTypeRepository
            .findById(sampleType.getId())
            .map(existingSampleType -> {
                if (sampleType.getName() != null) {
                    existingSampleType.setName(sampleType.getName());
                }
                if (sampleType.getCode() != null) {
                    existingSampleType.setCode(sampleType.getCode());
                }

                return existingSampleType;
            })
            .map(sampleTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SampleType> findAll(Pageable pageable) {
        log.debug("Request to get all SampleTypes");
        return sampleTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SampleType> findOne(Long id) {
        log.debug("Request to get SampleType : {}", id);
        return sampleTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SampleType : {}", id);
        sampleTypeRepository.deleteById(id);
    }
}
