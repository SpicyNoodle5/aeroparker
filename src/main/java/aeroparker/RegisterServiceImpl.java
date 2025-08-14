package aeroparker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer register(Customer customer) {
        return repository.save(customer);
    }
    
}
