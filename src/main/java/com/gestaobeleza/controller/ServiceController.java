
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

import com.gestaobeleza.model.Service;
import com.gestaobeleza.repository.ServiceRepository;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public List<Service> listarServicos() {
        return serviceRepository.findAll();
    }

    @PostMapping
    public Service cadastrarServico(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarServicoPorId(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(servico -> ResponseEntity.ok((Object) servico))
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body("Serviço não encontrado!"));
    }

@PutMapping("/{id}")
public ResponseEntity<?> atualizarServico(
        @PathVariable Long id,
        @RequestBody Service dadosAtualizados) {

    return serviceRepository.findById(id)
            .map(servico -> {
                servico.setName(dadosAtualizados.getName());
                servico.setDescription(dadosAtualizados.getDescription());
                servico.setPrice(dadosAtualizados.getPrice());
                servico.setDurationMinutes(dadosAtualizados.getDurationMinutes());

                if (dadosAtualizados.getActive() != null) {
                    servico.setActive(dadosAtualizados.getActive());
                }

                Service servicoSalvo = serviceRepository.save(servico);

                return ResponseEntity.ok((Object) servicoSalvo);
            })
            .orElseGet(() -> ResponseEntity
                    .status(404)
                    .body("Serviço não encontrado!"));
}

@DeleteMapping("/{id}")
public ResponseEntity<String> excluirServico(@PathVariable Long id) {

    if (!serviceRepository.existsById(id)) {
        return ResponseEntity
                .status(404)
                .body("Serviço não encontrado!");
    }

    serviceRepository.deleteById(id);

    return ResponseEntity.ok("Serviço excluído com sucesso!");
}
}
