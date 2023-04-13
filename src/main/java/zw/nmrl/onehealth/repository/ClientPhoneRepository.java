package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.ClientPhone;

/**
 * Spring Data JPA repository for the ClientPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientPhoneRepository extends JpaRepository<ClientPhone, Long> {}
