package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Analysis;

/**
 * Spring Data JPA repository for the Analysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {}
