-- =============================================
-- 003-data-shm-file.sql
-- Add data_shm_file table and FK constraint on charger_shm_data
-- =============================================

-- Table: data_shm_file
CREATE TABLE data_shm_file (
    id          BIGSERIAL       PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add FK constraint from charger_shm_data.data_shm_file_id_fk -> data_shm_file.id
ALTER TABLE charger_shm_data
    ADD CONSTRAINT fk_charger_shm_data_data_shm_file
        FOREIGN KEY (data_shm_file_id_fk) REFERENCES data_shm_file (id);

