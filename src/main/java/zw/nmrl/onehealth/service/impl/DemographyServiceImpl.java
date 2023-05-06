package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.Demography;
import zw.nmrl.onehealth.repository.DemographyRepository;
import zw.nmrl.onehealth.service.DemographyService;
import zw.nmrl.onehealth.service.dto.AnimalHealthDTO;

/**
 * Service Implementation for managing {@link Demography}.
 */
@Service
@Transactional
public class DemographyServiceImpl implements DemographyService {

    private final Logger log = LoggerFactory.getLogger(DemographyServiceImpl.class);

    private final DemographyRepository demographyRepository;

    public DemographyServiceImpl(DemographyRepository demographyRepository) {
        this.demographyRepository = demographyRepository;
    }

    @Override
    public Demography save(Demography demography) {
        log.debug("Request to save Demography : {}", demography);
        return demographyRepository.save(demography);
    }

    @Override
    public Demography update(Demography demography) {
        log.debug("Request to update Demography : {}", demography);
        return demographyRepository.save(demography);
    }

    private AnimalHealthDTO saveDemography(AnimalHealthDTO dto) {
        log.debug("Request to update Demography : {}", dto);

        String uuid = UUID.randomUUID().toString();
        Demography demo = new Demography();
        demo.setRecordId(uuid);
        demo.setValue(dto.getDemographic().getFarmName());
        demo.setDemographicCodingId(uuid);

        demo.setDemographicCodingId(uuid);

        return null; //demographyRepository.save(demography);
    }

    @Override
    public Optional<Demography> partialUpdate(Demography demography) {
        log.debug("Request to partially update Demography : {}", demography);

        return demographyRepository
            .findById(demography.getId())
            .map(existingDemography -> {
                if (demography.getRecordId() != null) {
                    existingDemography.setRecordId(demography.getRecordId());
                }
                if (demography.getDemographicCodingId() != null) {
                    existingDemography.setDemographicCodingId(demography.getDemographicCodingId());
                }
                if (demography.getValue() != null) {
                    existingDemography.setValue(demography.getValue());
                }

                return existingDemography;
            })
            .map(demographyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Demography> findAll(Pageable pageable) {
        log.debug("Request to get all Demographies");
        return demographyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Demography> findOne(Long id) {
        log.debug("Request to get Demography : {}", id);
        return demographyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demography : {}", id);
        demographyRepository.deleteById(id);
    }
}
