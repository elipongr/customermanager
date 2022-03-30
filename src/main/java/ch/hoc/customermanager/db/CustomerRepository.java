package ch.hoc.customermanager.db;

import ch.hoc.customermanager.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCostumerById(Long id);
}
