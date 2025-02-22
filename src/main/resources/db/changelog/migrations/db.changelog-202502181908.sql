--liquibase formatted sql
--changeset emanuel:202502181844
--comment: tb_boards_columns table create

CREATE TABLE IF NOT EXISTS tb_boards_columns(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    `order` int NOT NULL,
    kind VARCHAR(7) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_columns_fk FOREIGN KEY (board_id) REFERENCES tb_boards(id) ON DELETE CASCADE,
    CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board_id, `order`)

) ENGINE=InnoDB;


--rollback DROP TABLE IF EXISTS tb_boards_columns;