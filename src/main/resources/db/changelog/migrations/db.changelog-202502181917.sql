--liquibase formatted sql
--changeset emanuel:202502181844
--comment: tb_cards table create

CREATE TABLE IF NOT EXISTS tb_cards(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT boards_columns_cards_fk
    FOREIGN KEY (board_column_id) REFERENCES tb_boards_columns(id)
    ON DELETE CASCADE


) ENGINE=InnoDB;


--rollback DROP TABLE IF EXISTS tb_cards;