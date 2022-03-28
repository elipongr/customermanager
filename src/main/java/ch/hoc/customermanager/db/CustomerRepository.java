package ch.hoc.customermanager.db;

import ch.hoc.customermanager.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c left join fetch c.addresses where c.id =:id")
    Customer findEagleById(@Param("id") Long id);
}
