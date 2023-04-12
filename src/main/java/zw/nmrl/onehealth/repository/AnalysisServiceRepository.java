package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.AnalysisService;

/**
 * Spring Data JPA repository for the AnalysisService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisServiceRepository extends JpaRepository<AnalysisService, Long> {}
