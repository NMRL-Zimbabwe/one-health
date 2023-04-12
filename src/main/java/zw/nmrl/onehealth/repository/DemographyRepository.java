package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Demography;

/**
 * Spring Data JPA repository for the Demography entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemographyRepository extends JpaRepository<Demography, Long> {}
