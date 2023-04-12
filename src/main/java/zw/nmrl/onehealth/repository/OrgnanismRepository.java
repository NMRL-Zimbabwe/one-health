package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.Orgnanism;

/**
 * Spring Data JPA repository for the Orgnanism entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgnanismRepository extends JpaRepository<Orgnanism, Long> {}
