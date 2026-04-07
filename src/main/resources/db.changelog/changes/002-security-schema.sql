-- =============================================
-- 002-security-schema.sql
-- Security schema: authority, role, role_authority, user_role
-- =============================================

-- Table: authority
CREATE TABLE authority (
    id      BIGSERIAL       PRIMARY KEY,
    name    VARCHAR(255)    NOT NULL UNIQUE
);

-- Table: role
CREATE TABLE role (
    id      BIGSERIAL       PRIMARY KEY,
    name    VARCHAR(255)    NOT NULL UNIQUE
);

-- Table: role_authority
CREATE TABLE role_authority (
    role_id_fk          BIGINT  NOT NULL,
    authority_id_fk     BIGINT  NOT NULL,
    CONSTRAINT fk_role_authority_role       FOREIGN KEY (role_id_fk)        REFERENCES role      (id),
    CONSTRAINT fk_role_authority_authority  FOREIGN KEY (authority_id_fk)   REFERENCES authority (id),
    PRIMARY KEY (role_id_fk, authority_id_fk)
);

-- Table: user_role
CREATE TABLE user_role (
    user_id_fk  BIGINT  NOT NULL,
    role_id_fk  BIGINT  NOT NULL,
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id_fk) REFERENCES app_user (id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id_fk) REFERENCES role     (id),
    PRIMARY KEY (user_id_fk, role_id_fk)
);

