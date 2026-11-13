
package com.gestaobeleza.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestaobeleza.model.Appointment;
import com.gestaobeleza.repository.AppointmentRepository;
import com.gestaobeleza.repository.CustomerRepository;
import com.gestaobeleza.repository.ProfessionalRepository;
import com.gestaobeleza.repository.ServiceRepository;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final ProfessionalRepository professionalRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentController(
            AppointmentRepository appointmentRepository,
            CustomerRepository customerRepository,
            ProfessionalRepository professionalRepository,
            ServiceRepository serviceRepository) {

        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.professionalRepository = professionalRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public List<Appointment> listarAgendamentos() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAgendamentoPorId(
            @PathVariable Long id) {

        return appointmentRepository.findById(id)
                .map(agendamento ->
                        ResponseEntity.ok((Object) agendamento))
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body("Agendamento não encontrado!"));
    }

    @PostMapping
    public ResponseEntity<?> cadastrarAgendamento(
            @RequestBody Appointment appointment) {

        if (appointment.getEstablishmentId() == null
                || appointment.getCustomerId() == null
                || appointment.getProfessionalId() == null
                || appointment.getServiceId() == null
                || appointment.getStartTime() == null
                || appointment.getEndTime() == null) {

            return ResponseEntity.badRequest()
                    .body("Preencha todos os campos obrigatórios!");
        }

        if (!appointment.getEndTime()
                .isAfter(appointment.getStartTime())) {

            return ResponseEntity.badRequest()
                    .body("O horário final deve ser após o inicial!");
        }

        var cliente = customerRepository
                .findById(appointment.getCustomerId());

        var profissional = professionalRepository
                .findById(appointment.getProfessionalId());

        var servico = serviceRepository
                .findById(appointment.getServiceId());

        if (cliente.isEmpty()
                || profissional.isEmpty()
                || servico.isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("Cliente, profissional ou serviço não encontrado!");
        }

        Long estabelecimentoId = appointment.getEstablishmentId();

        if (!estabelecimentoId.equals(cliente.get().getEstablishmentId())
                || !estabelecimentoId.equals(profissional.get().getEstablishmentId())
                || !estabelecimentoId.equals(servico.get().getEstablishmentId())) {

            return ResponseEntity.badRequest()
                    .body("Os cadastros devem pertencer ao mesmo estabelecimento!");
        }

        if (!Boolean.TRUE.equals(profissional.get().getActive())
                || !Boolean.TRUE.equals(servico.get().getActive())) {

            return ResponseEntity.badRequest()
                    .body("O profissional e o serviço precisam estar ativos!");
        }

        if (appointmentRepository.existeConflito(
        appointment.getProfessionalId(),
        appointment.getStartTime(),
        appointment.getEndTime())) {

            return ResponseEntity.badRequest()
                .body("Este profissional já possui um agendamento nesse horário!");
        }

        appointment.setStatus("SCHEDULED");

        Appointment salvo = appointmentRepository.save(appointment);

        return ResponseEntity.ok(salvo);
    }

@PatchMapping("/{id}/cancel")
public ResponseEntity<?> cancelarAgendamento(@PathVariable Long id) {

    return appointmentRepository.findById(id)
            .map(agendamento -> {

                if ("CANCELLED".equals(agendamento.getStatus())) {
                    return ResponseEntity.badRequest()
                            .body((Object) "Este agendamento já está cancelado!");
                }

                if ("COMPLETED".equals(agendamento.getStatus())) {
                    return ResponseEntity.badRequest()
                            .body((Object) "Não é possível cancelar um atendimento concluído!");
                }

                agendamento.setStatus("CANCELLED");

                Appointment salvo = appointmentRepository.save(agendamento);

                return ResponseEntity.ok((Object) salvo);
            })
            .orElseGet(() -> ResponseEntity
                    .status(404)
                    .body("Agendamento não encontrado!"));
}

@PatchMapping("/{id}/confirm")
public ResponseEntity<?> confirmarAgendamento(@PathVariable Long id) {

    return appointmentRepository.findById(id)
            .map(agendamento -> {

                if ("CONFIRMED".equals(agendamento.getStatus())) {
                    return ResponseEntity.badRequest()
                            .body((Object) "Este agendamento já está confirmado!");
                }

                if ("CANCELLED".equals(agendamento.getStatus())) {
                    return ResponseEntity.badRequest()
                            .body((Object) "Não é possível confirmar um agendamento cancelado!");
                }

                if ("COMPLETED".equals(agendamento.getStatus())) {
                    return ResponseEntity.badRequest()
                            .body((Object) "Este atendimento já foi concluído!");
                }

                agendamento.setStatus("CONFIRMED");

                Appointment salvo = appointmentRepository.save(agendamento);

                return ResponseEntity.ok((Object) salvo);
            })
            .orElseGet(() -> ResponseEntity
                    .status(404)
                    .body("Agendamento não encontrado!"));
}
}
