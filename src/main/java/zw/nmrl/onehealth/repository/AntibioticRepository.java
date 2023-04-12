package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Antibiotic;

/**
 * Spring Data JPA repository for the Antibiotic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntibioticRepository extends JpaRepository<Antibiotic, Long> {}
