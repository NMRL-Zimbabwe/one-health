package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.DemographicCoding;
import zw.nmrl.onehealth.repository.DemographicCodingRepository;
import zw.nmrl.onehealth.service.DemographicCodingService;

/**
 * Service Implementation for managing {@link DemographicCoding}.
 */
@Service
@Transactional
public class DemographicCodingServiceImpl implements DemographicCodingService {

    private final Logger log = LoggerFactory.getLogger(DemographicCodingServiceImpl.class);

    private final DemographicCodingRepository demographicCodingRepository;

    public DemographicCodingServiceImpl(DemographicCodingRepository demographicCodingRepository) {
        this.demographicCodingRepository = demographicCodingRepository;
    }

    @Override
    public DemographicCoding save(DemographicCoding demographicCoding) {
        log.debug("Request to save DemographicCoding : {}", demographicCoding);
        return demographicCodingRepository.save(demographicCoding);
    }

    @Override
    public DemographicCoding update(DemographicCoding demographicCoding) {
        log.debug("Request to update DemographicCoding : {}", demographicCoding);
        return demographicCodingRepository.save(demographicCoding);
    }

    @Override
    public Optional<DemographicCoding> partialUpdate(DemographicCoding demographicCoding) {
        log.debug("Request to partially update DemographicCoding : {}", demographicCoding);

        return demographicCodingRepository
            .findById(demographicCoding.getId())
            .map(existingDemographicCoding -> {
                if (demographicCoding.getName() != null) {
                    existingDemographicCoding.setName(demographicCoding.getName());
                }
                if (demographicCoding.getCode() != null) {
                    existingDemographicCoding.setCode(demographicCoding.getCode());
                }
                if (demographicCoding.getDescription() != null) {
                    existingDemographicCoding.setDescription(demographicCoding.getDescription());
                }

                return existingDemographicCoding;
            })
            .map(demographicCodingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemographicCoding> findAll(Pageable pageable) {
        log.debug("Request to get all DemographicCodings");
        return demographicCodingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemographicCoding> findOne(Long id) {
        log.debug("Request to get DemographicCoding : {}", id);
        return demographicCodingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemographicCoding : {}", id);
        demographicCodingRepository.deleteById(id);
    }
}
