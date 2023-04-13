package zw.nmrl.onehealth.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.nmrl.onehealth.domain.ClientAddress;

/**
 * Spring Data JPA repository for the ClientAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {}
