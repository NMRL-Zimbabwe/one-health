package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.AntibioticClass;

/**
 * Spring Data JPA repository for the AntibioticClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntibioticClassRepository extends JpaRepository<AntibioticClass, Long> {}
