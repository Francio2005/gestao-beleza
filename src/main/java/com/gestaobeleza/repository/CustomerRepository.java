
package com.gestaobeleza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaobeleza.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
