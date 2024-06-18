package ml.backspace.finance.Accounts.repository;

import ml.backspace.finance.Accounts.dto.CustomerDto;
import ml.backspace.finance.Accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
