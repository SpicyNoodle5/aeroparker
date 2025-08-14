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

    private final Pattern POSTCODE_REGEX = Pattern.compile("([A-Za-z][A-Ha-hJ-Yj-y]?[0-9][A-Za-z0-9]? ?[0-9][A-Za-z]{2}|[Gg][Ii][Rr] ?0[Aa]{2})$");

    private final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

    @Override
    public String register(Customer customer) {
        // Validate existence and length
        if(customer.getEmailAddress() == null || customer.getEmailAddress().isEmpty()) {
            return "Please enter an email";
        }
        if(customer.getTitle() == null || customer.getTitle().isEmpty()) {
            return "Please enter a title";
        }
        if(customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
            return "Please enter your first name";
        }
        if(customer.getLastName() == null || customer.getLastName().isEmpty()) {
            return "Please enter your last name";
        }
        if(customer.getAddressLine1() == null || customer.getAddressLine1().isEmpty()) {
            return "Please enter the first line of your address";
        }
        if(customer.getPostcode() == null || customer.getPostcode().isEmpty()) {
            return "Please enter your postcode";
        }
        if(customer.getEmailAddress().length() > 255) {
            return "Email is too long";
        }
        if(customer.getTitle().length() > 5) {
            return "Title is too long";
        }
        if(customer.getFirstName().length() > 50) {
            return "First name is too long";
        }
        if(customer.getLastName().length() > 50) {
            return "First name is too long";
        }
        if(customer.getAddressLine1().length() > 255) {
            return "Address line 1 is too long";
        }
        if(customer.getAddressLine2() != null && customer.getAddressLine2().length() > 255) {
            return "Address line 2 is too long";
        }
        if(customer.getCity() != null && customer.getCity().length() > 255) {
            return "City is too long";
        }
        if(customer.getPostcode().length() > 10) {
            return "Postcode is too long";
        }
        if(customer.getPhoneNumber() != null && customer.getPhoneNumber().length() > 20) {
            return "Phone number is too long";
        }
        
        // Convert email to lowercase
        customer.setEmailAddress(customer.getEmailAddress().toLowerCase());

        // Check email is unique
        if(repository.findByEmailAddress(customer.getEmailAddress()).isPresent()) {
            return "Email is already in use";
        }

        // Validate format
        if(!EMAIL_ADDRESS_REGEX.matcher(customer.getEmailAddress()).matches()) {
            return "Email is invalid";
        }
        if(!POSTCODE_REGEX.matcher(customer.getPostcode()).matches()) {
            return "Postcode is invalid";
        }
        if(customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty() && !PHONE_NUMBER_REGEX.matcher(customer.getPhoneNumber()).matches()) {
            return "Phone number is invalid";
        }

        // Set registered date and save
        customer.setRegistered(LocalDateTime.now());
        repository.save(customer);

        return "success";
    }
    
}
