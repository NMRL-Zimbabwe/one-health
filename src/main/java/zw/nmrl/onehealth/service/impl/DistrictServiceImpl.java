package zw.nmrl.onehealth.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.nmrl.onehealth.domain.District;
import zw.nmrl.onehealth.repository.DistrictRepository;
import zw.nmrl.onehealth.service.DistrictService;

/**
 * Service Implementation for managing {@link District}.
 */
@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

    private final Logger log = LoggerFactory.getLogger(DistrictServiceImpl.class);

    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public District save(District district) {
        log.debug("Request to save District : {}", district);
        return districtRepository.save(district);
    }

    @Override
    public District update(District district) {
        log.debug("Request to update District : {}", district);
        return districtRepository.save(district);
    }

    @Override
    public Optional<District> partialUpdate(District district) {
        log.debug("Request to partially update District : {}", district);

        return districtRepository
            .findById(district.getId())
            .map(existingDistrict -> {
                if (district.getName() != null) {
                    existingDistrict.setName(district.getName());
                }
                if (district.getProvinceId() != null) {
                    existingDistrict.setProvinceId(district.getProvinceId());
                }
                if (district.getLongitude() != null) {
                    existingDistrict.setLongitude(district.getLongitude());
                }
                if (district.getLatitude() != null) {
                    existingDistrict.setLatitude(district.getLatitude());
                }

                return existingDistrict;
            })
            .map(districtRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<District> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<District> findOne(Long id) {
        log.debug("Request to get District : {}", id);
        return districtRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete District : {}", id);
        districtRepository.deleteById(id);
    }
}
