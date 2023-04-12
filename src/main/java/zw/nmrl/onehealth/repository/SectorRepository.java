package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Sector;

/**
 * Spring Data JPA repository for the Sector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {}
