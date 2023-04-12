package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.SampleType;

/**
 * Spring Data JPA repository for the SampleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleTypeRepository extends JpaRepository<SampleType, Long> {}
