package aeroparker;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmailAddress(String emailAddress);
}
