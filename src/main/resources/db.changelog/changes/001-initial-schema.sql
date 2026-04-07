-- =============================================
-- 001-initial-schema.sql
-- Initial database schema for Charger Monitor
-- =============================================

-- Table: app_user
CREATE TABLE app_user (
    id          BIGSERIAL       PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    blocked     BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table: charger_type
CREATE TABLE charger_type (
    id      BIGSERIAL       PRIMARY KEY,
    name    VARCHAR(255)    NOT NULL
);

-- Table: charger
CREATE TABLE charger (
    id          BIGSERIAL       PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    type_id_fk  BIGINT          NOT NULL,
    CONSTRAINT fk_charger_type FOREIGN KEY (type_id_fk) REFERENCES charger_type (id)
);

-- Table: user_charger
CREATE TABLE user_charger (
    user_id_fk      BIGINT  NOT NULL,
    charger_id_fk   BIGINT  NOT NULL,
    CONSTRAINT fk_user_charger_user    FOREIGN KEY (user_id_fk)    REFERENCES app_user (id),
    CONSTRAINT fk_user_charger_charger FOREIGN KEY (charger_id_fk) REFERENCES charger  (id),
    PRIMARY KEY (user_id_fk, charger_id_fk)
);

-- Table: charger_shm_data
CREATE TABLE charger_shm_data (
    charger_id_fk       BIGINT      NOT NULL,
    data_shm_file_id_fk BIGINT      NOT NULL,
    data                INTEGER     NOT NULL,
    timestamp           BIGINT      NOT NULL,
    received_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_charger_shm_data_charger FOREIGN KEY (charger_id_fk) REFERENCES charger (id),
    PRIMARY KEY (charger_id_fk, data_shm_file_id_fk, timestamp)
);

