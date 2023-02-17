CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS account (
  id          VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4()::varchar,
  balance     DECIMAL(16,2) NOT NULL DEFAULT 0 CHECK (balance >= 0),
  "limit"     DECIMAL(16,2) CHECK ("limit" >= balance)
);