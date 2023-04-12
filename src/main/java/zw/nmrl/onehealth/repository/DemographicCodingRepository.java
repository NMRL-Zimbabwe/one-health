package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.DemographicCoding;

/**
 * Spring Data JPA repository for the DemographicCoding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemographicCodingRepository extends JpaRepository<DemographicCoding, Long> {}
