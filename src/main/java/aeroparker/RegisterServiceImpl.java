package aeroparker;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private CustomerRepository repository;

    private final Pattern EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");

    private final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

    @Override
    public boolean register(Customer customer) {
        // Validate existence and length
        if(customer.getEmailAddress() == null || customer.getEmailAddress().length() > 255) {
            return false;
        }
        if(customer.getTitle() == null || customer.getTitle().length() > 5) {
            return false;
        }
        if(customer.getFirstName() == null || customer.getFirstName().length() > 50) {
            return false;
        }
        if(customer.getLastName() == null || customer.getLastName().length() > 50) {
            return false;
        }
        if(customer.getAddressLine1() == null || customer.getAddressLine1().length() > 255) {
            return false;
        }
        if(customer.getAddressLine2() != null && customer.getAddressLine2().length() > 255) {
            return false;
        }
        if(customer.getPostcode() == null || customer.getPostcode().length() > 255) {
            return false;
        }
        if(customer.getCity() != null && customer.getCity().length() > 255) {
            return false;
        }
        if(customer.getPhoneNumber() != null && customer.getPhoneNumber().length() > 10) {
            return false;
        }
        
        // Convert email to lowercase
        customer.setEmailAddress(customer.getEmailAddress().toLowerCase());

        // Check email is unique
        if(repository.findByEmailAddress(customer.getEmailAddress()).isPresent()) {
            return false;
        }

        // Validate format
        if(!EMAIL_ADDRESS_REGEX.matcher(customer.getEmailAddress()).matches()) {
            return false;
        }
        if(customer.getPhoneNumber() != null && !PHONE_NUMBER_REGEX.matcher(customer.getPhoneNumber()).matches()) {
            return false;
        }

        // Set registered date
        customer.setRegistered(LocalDateTime.now());

        return repository.save(customer) != null;
    }
    
}
