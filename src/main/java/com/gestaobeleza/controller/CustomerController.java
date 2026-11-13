
package com.gestaobeleza.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestaobeleza.model.Customer;
import com.gestaobeleza.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> listarClientes() {
        return customerRepository.findAll();
    }

    @PostMapping
    public Customer cadastrarCliente(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }


@GetMapping("/{id}")
public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
    return customerRepository.findById(id)
            .map(cliente -> ResponseEntity.ok((Object) cliente))
            .orElseGet(() -> ResponseEntity
                    .status(404)
                    .body("Cliente não encontrado!"));
}



@PutMapping("/{id}")
public ResponseEntity<?> atualizarCliente(
        @PathVariable Long id,
        @RequestBody Customer dadosAtualizados) {

    return customerRepository.findById(id)
            .map(cliente -> {
                cliente.setName(dadosAtualizados.getName());
                cliente.setEmail(dadosAtualizados.getEmail());
                cliente.setPhone(dadosAtualizados.getPhone());

                Customer clienteSalvo = customerRepository.save(cliente);

                return ResponseEntity.ok((Object) clienteSalvo);
            })
            .orElseGet(() -> ResponseEntity
                    .status(404)
                    .body("Cliente não encontrado!"));
}

@DeleteMapping("/{id}")
public ResponseEntity<String> excluirCliente(@PathVariable Long id) {

    if (!customerRepository.existsById(id)) {
        return ResponseEntity
                .status(404)
                .body("Cliente não encontrado!");
    }

    customerRepository.deleteById(id);

    return ResponseEntity.ok("Cliente excluído com sucesso!");
}
}
