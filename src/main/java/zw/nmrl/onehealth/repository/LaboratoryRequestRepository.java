package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.LaboratoryRequest;

/**
 * Spring Data JPA repository for the LaboratoryRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratoryRequestRepository extends JpaRepository<LaboratoryRequest, Long> {}
