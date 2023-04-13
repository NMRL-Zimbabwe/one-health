package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.LaboratoryRequest;
import zw.nmrl.onehealth.repository.LaboratoryRequestRepository;
import zw.nmrl.onehealth.service.LaboratoryRequestService;

/**
 * Service Implementation for managing {@link LaboratoryRequest}.
 */
@Service
@Transactional
public class LaboratoryRequestServiceImpl implements LaboratoryRequestService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestServiceImpl.class);

    private final LaboratoryRequestRepository laboratoryRequestRepository;

    public LaboratoryRequestServiceImpl(LaboratoryRequestRepository laboratoryRequestRepository) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    @Override
    public LaboratoryRequest save(LaboratoryRequest laboratoryRequest) {
        log.debug("Request to save LaboratoryRequest : {}", laboratoryRequest);
        return laboratoryRequestRepository.save(laboratoryRequest);
    }

    @Override
    public LaboratoryRequest update(LaboratoryRequest laboratoryRequest) {
        log.debug("Request to update LaboratoryRequest : {}", laboratoryRequest);
        return laboratoryRequestRepository.save(laboratoryRequest);
    }

    @Override
    public Optional<LaboratoryRequest> partialUpdate(LaboratoryRequest laboratoryRequest) {
        log.debug("Request to partially update LaboratoryRequest : {}", laboratoryRequest);

        return laboratoryRequestRepository
            .findById(laboratoryRequest.getId())
            .map(existingLaboratoryRequest -> {
                if (laboratoryRequest.getSampleId() != null) {
                    existingLaboratoryRequest.setSampleId(laboratoryRequest.getSampleId());
                }
                if (laboratoryRequest.getSampleTypeId() != null) {
                    existingLaboratoryRequest.setSampleTypeId(laboratoryRequest.getSampleTypeId());
                }
                if (laboratoryRequest.getDateCollected() != null) {
                    existingLaboratoryRequest.setDateCollected(laboratoryRequest.getDateCollected());
                }
                if (laboratoryRequest.getDateReceived() != null) {
                    existingLaboratoryRequest.setDateReceived(laboratoryRequest.getDateReceived());
                }
                if (laboratoryRequest.getSampleCondition() != null) {
                    existingLaboratoryRequest.setSampleCondition(laboratoryRequest.getSampleCondition());
                }
                if (laboratoryRequest.getClientId() != null) {
                    existingLaboratoryRequest.setClientId(laboratoryRequest.getClientId());
                }
                if (laboratoryRequest.getPriority() != null) {
                    existingLaboratoryRequest.setPriority(laboratoryRequest.getPriority());
                }
                if (laboratoryRequest.getStatus() != null) {
                    existingLaboratoryRequest.setStatus(laboratoryRequest.getStatus());
                }
                if (laboratoryRequest.getRemarks() != null) {
                    existingLaboratoryRequest.setRemarks(laboratoryRequest.getRemarks());
                }
                if (laboratoryRequest.getLocationId() != null) {
                    existingLaboratoryRequest.setLocationId(laboratoryRequest.getLocationId());
                }
                if (laboratoryRequest.getSectorId() != null) {
                    existingLaboratoryRequest.setSectorId(laboratoryRequest.getSectorId());
                }
                if (laboratoryRequest.getDistrictId() != null) {
                    existingLaboratoryRequest.setDistrictId(laboratoryRequest.getDistrictId());
                }
                if (laboratoryRequest.getProcinceId() != null) {
                    existingLaboratoryRequest.setProcinceId(laboratoryRequest.getProcinceId());
                }

                return existingLaboratoryRequest;
            })
            .map(laboratoryRequestRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LaboratoryRequest> findAll(Pageable pageable) {
        log.debug("Request to get all LaboratoryRequests");
        return laboratoryRequestRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LaboratoryRequest> findOne(Long id) {
        log.debug("Request to get LaboratoryRequest : {}", id);
        return laboratoryRequestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LaboratoryRequest : {}", id);
        laboratoryRequestRepository.deleteById(id);
    }
}
