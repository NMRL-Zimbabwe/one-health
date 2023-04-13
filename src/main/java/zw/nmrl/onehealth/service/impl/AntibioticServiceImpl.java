package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.Antibiotic;
import zw.nmrl.onehealth.repository.AntibioticRepository;
import zw.nmrl.onehealth.service.AntibioticService;

/**
 * Service Implementation for managing {@link Antibiotic}.
 */
@Service
@Transactional
public class AntibioticServiceImpl implements AntibioticService {

    private final Logger log = LoggerFactory.getLogger(AntibioticServiceImpl.class);

    private final AntibioticRepository antibioticRepository;

    public AntibioticServiceImpl(AntibioticRepository antibioticRepository) {
        this.antibioticRepository = antibioticRepository;
    }

    @Override
    public Antibiotic save(Antibiotic antibiotic) {
        log.debug("Request to save Antibiotic : {}", antibiotic);
        return antibioticRepository.save(antibiotic);
    }

    @Override
    public Antibiotic update(Antibiotic antibiotic) {
        log.debug("Request to update Antibiotic : {}", antibiotic);
        return antibioticRepository.save(antibiotic);
    }

    @Override
    public Optional<Antibiotic> partialUpdate(Antibiotic antibiotic) {
        log.debug("Request to partially update Antibiotic : {}", antibiotic);

        return antibioticRepository
            .findById(antibiotic.getId())
            .map(existingAntibiotic -> {
                if (antibiotic.getName() != null) {
                    existingAntibiotic.setName(antibiotic.getName());
                }
                if (antibiotic.getCode() != null) {
                    existingAntibiotic.setCode(antibiotic.getCode());
                }
                if (antibiotic.getStatus() != null) {
                    existingAntibiotic.setStatus(antibiotic.getStatus());
                }
                if (antibiotic.getDescription() != null) {
                    existingAntibiotic.setDescription(antibiotic.getDescription());
                }

                return existingAntibiotic;
            })
            .map(antibioticRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Antibiotic> findAll(Pageable pageable) {
        log.debug("Request to get all Antibiotics");
        return antibioticRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Antibiotic> findOne(Long id) {
        log.debug("Request to get Antibiotic : {}", id);
        return antibioticRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Antibiotic : {}", id);
        antibioticRepository.deleteById(id);
    }
}
