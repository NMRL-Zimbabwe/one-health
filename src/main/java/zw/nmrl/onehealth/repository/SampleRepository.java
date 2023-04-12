package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Sample;

/**
 * Spring Data JPA repository for the Sample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {}
