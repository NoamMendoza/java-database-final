package com.project.code.Repo;

import com.project.code.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 2. Add custom query methods:
    
    // findByEmail: find a customer by their email address.
    Customer findByEmail(String email);

    // findById: Spring Data JPA already provides Optional<Customer> findById(Long id)
    // If you need it to return Customer directly, you can define it here.
    Customer findById(long id);

    // 3. Additional methods:
    
    // findByName: finding customers by name.
    List<Customer> findByName(String name);

    // findByPhone: finding customers by phone number.
    Customer findByPhone(String phone);
}