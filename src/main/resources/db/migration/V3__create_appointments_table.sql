
CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,

    establishment_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    professional_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,

    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',

    notes VARCHAR(500),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_appointment_establishment
        FOREIGN KEY (establishment_id)
        REFERENCES establishments(id),

    CONSTRAINT fk_appointment_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id),

    CONSTRAINT fk_appointment_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id),

    CONSTRAINT fk_appointment_service
        FOREIGN KEY (service_id)
        REFERENCES services(id),

    CONSTRAINT chk_appointment_times
        CHECK (end_time > start_time),

    CONSTRAINT chk_appointment_status
        CHECK (status IN (
            'SCHEDULED',
            'CONFIRMED',
            'COMPLETED',
            'CANCELLED'
        ))
);

CREATE INDEX idx_appointments_establishment
ON appointments(establishment_id);

CREATE INDEX idx_appointments_professional_time
ON appointments(professional_id, start_time);
