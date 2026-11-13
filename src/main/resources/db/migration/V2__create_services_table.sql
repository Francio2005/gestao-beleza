
CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    establishment_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    price NUMERIC(10, 2) NOT NULL,
    duration_minutes INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_service_establishment
        FOREIGN KEY (establishment_id)
        REFERENCES establishments(id),

    CONSTRAINT chk_service_price
        CHECK (price >= 0),

    CONSTRAINT chk_service_duration
        CHECK (duration_minutes > 0)
);

CREATE INDEX idx_services_establishment
ON services(establishment_id);
