--liquibase formatted sql
--changeset emanuel:202502181844
--comment: tb_boards table create

CREATE TABLE IF NOT EXISTS tb_boards(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE IF EXISTS tb_boards;


