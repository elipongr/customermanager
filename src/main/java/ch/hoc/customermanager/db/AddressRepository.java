package ch.hoc.customermanager.db;

import ch.hoc.customermanager.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
