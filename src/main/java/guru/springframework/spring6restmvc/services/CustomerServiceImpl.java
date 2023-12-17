package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        Customer joe = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("Joe")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer carl = Customer.builder()
                .id(UUID.randomUUID())
                .version(2)
                .customerName("Carl")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer jake = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("Jake")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        this.customerMap = new HashMap<>();
        customerMap.put(joe.getId(), joe);
        customerMap.put(carl.getId(), carl);
        customerMap.put(jake.getId(), jake);
    }
    
	@Override
	public void deleteCustomerById(UUID customerId) {
		customerMap.remove(customerId);
	}
    
	@Override
	public void updateCustomerById(UUID customerId, Customer customer) {
		Customer existing = customerMap.get(customerId);
		existing.setCustomerName(customer.getCustomerName());
		existing.setVersion(customer.getVersion());
		existing.setUpdateDate(LocalDateTime.now());
		
		customerMap.put(customerId, existing);
	}

    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
        .id(UUID.randomUUID())
        .version(customer.getVersion())
        .customerName(customer.getCustomerName())
        .createdDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public List<Customer> listCustomers() {
        log.debug("listCustomers Service");
        return new ArrayList<>(this.customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.debug("getCustomerById Service. ID: " + id.toString());
        return this.customerMap.get(id);
    }
}
