
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

import com.gestaobeleza.model.Professional;
import com.gestaobeleza.repository.ProfessionalRepository;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalController(
            ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public List<Professional> listarProfissionais() {
        return professionalRepository.findAll();
    }

    @PostMapping
    public Professional cadastrarProfissional(
            @RequestBody Professional professional) {
        return professionalRepository.save(professional);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProfissionalPorId(
            @PathVariable Long id) {

        return professionalRepository.findById(id)
                .map(profissional ->
                        ResponseEntity.ok((Object) profissional))
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body("Profissional não encontrado!"));
    }

@PutMapping("/{id}")
public ResponseEntity<?> atualizarProfissional(
        @PathVariable Long id,
        @RequestBody Professional dadosAtualizados) {

    return professionalRepository.findById(id)
            .map(profissional -> {
                profissional.setName(dadosAtualizados.getName());
                profissional.setEmail(dadosAtualizados.getEmail());
                profissional.setPhone(dadosAtualizados.getPhone());
                profissional.setSpecialty(dadosAtualizados.getSpecialty());

                if (dadosAtualizados.getActive() != null) {
                    profissional.setActive(dadosAtualizados.getActive());
                }

                Professional profissionalSalvo =
                        professionalRepository.save(profissional);

                return ResponseEntity.ok((Object) profissionalSalvo);
            })
            .orElseGet(() -> ResponseEntity
                    .status(404)
                    .body("Profissional não encontrado!"));
}

@DeleteMapping("/{id}")
public ResponseEntity<String> excluirProfissional(
        @PathVariable Long id) {

    if (!professionalRepository.existsById(id)) {
        return ResponseEntity
                .status(404)
                .body("Profissional não encontrado!");
    }

    professionalRepository.deleteById(id);

    return ResponseEntity.ok(
            "Profissional excluído com sucesso!"
    );
}
}
