package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Organism;

/**
 * Spring Data JPA repository for the Organism entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganismRepository extends JpaRepository<Organism, Long> {}
