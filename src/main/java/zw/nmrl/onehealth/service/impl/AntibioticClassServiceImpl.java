package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.AntibioticClass;
import zw.nmrl.onehealth.repository.AntibioticClassRepository;
import zw.nmrl.onehealth.service.AntibioticClassService;

/**
 * Service Implementation for managing {@link AntibioticClass}.
 */
@Service
@Transactional
public class AntibioticClassServiceImpl implements AntibioticClassService {

    private final Logger log = LoggerFactory.getLogger(AntibioticClassServiceImpl.class);

    private final AntibioticClassRepository antibioticClassRepository;

    public AntibioticClassServiceImpl(AntibioticClassRepository antibioticClassRepository) {
        this.antibioticClassRepository = antibioticClassRepository;
    }

    @Override
    public AntibioticClass save(AntibioticClass antibioticClass) {
        log.debug("Request to save AntibioticClass : {}", antibioticClass);
        return antibioticClassRepository.save(antibioticClass);
    }

    @Override
    public AntibioticClass update(AntibioticClass antibioticClass) {
        log.debug("Request to update AntibioticClass : {}", antibioticClass);
        return antibioticClassRepository.save(antibioticClass);
    }

    @Override
    public Optional<AntibioticClass> partialUpdate(AntibioticClass antibioticClass) {
        log.debug("Request to partially update AntibioticClass : {}", antibioticClass);

        return antibioticClassRepository
            .findById(antibioticClass.getId())
            .map(existingAntibioticClass -> {
                if (antibioticClass.getName() != null) {
                    existingAntibioticClass.setName(antibioticClass.getName());
                }
                if (antibioticClass.getDescription() != null) {
                    existingAntibioticClass.setDescription(antibioticClass.getDescription());
                }

                return existingAntibioticClass;
            })
            .map(antibioticClassRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AntibioticClass> findAll(Pageable pageable) {
        log.debug("Request to get all AntibioticClasses");
        return antibioticClassRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AntibioticClass> findOne(Long id) {
        log.debug("Request to get AntibioticClass : {}", id);
        return antibioticClassRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AntibioticClass : {}", id);
        antibioticClassRepository.deleteById(id);
    }
}
