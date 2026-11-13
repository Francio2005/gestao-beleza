
-- Tabela de estabelecimentos
CREATE TABLE establishments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de clientes
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    establishment_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_customer_establishment
        FOREIGN KEY (establishment_id)
        REFERENCES establishments(id)
);

-- Tabela de profissionais
CREATE TABLE professionals (
    id BIGSERIAL PRIMARY KEY,
    establishment_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(20),
    specialty VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_professional_establishment
        FOREIGN KEY (establishment_id)
        REFERENCES establishments(id)
);

-- Índices para facilitar consultas
CREATE INDEX idx_customers_establishment
ON customers(establishment_id);

CREATE INDEX idx_professionals_establishment
ON professionals(establishment_id);
