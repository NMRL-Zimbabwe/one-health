package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.nmrl.onehealth.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
