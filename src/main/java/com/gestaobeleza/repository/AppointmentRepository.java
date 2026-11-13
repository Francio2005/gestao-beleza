
package com.gestaobeleza.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestaobeleza.model.Appointment;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT COUNT(a) > 0
        FROM Appointment a
        WHERE a.professionalId = :professionalId
        AND a.status <> 'CANCELLED'
        AND a.startTime < :endTime
        AND a.endTime > :startTime
        """)
    boolean existeConflito(
            @Param("professionalId") Long professionalId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
