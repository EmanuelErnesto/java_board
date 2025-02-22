--liquibase formatted sql
--changeset emanuel:202502181844
--comment: tb_blocks table create

CREATE TABLE IF NOT EXISTS tb_blocks(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(255) NOT NULL,
    unblocked_at TIMESTAMP NULL,
    unblock_reason VARCHAR(255) NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk
    FOREIGN KEY (card_id) REFERENCES tb_cards(id)
    ON DELETE CASCADE


) ENGINE=InnoDB;


--rollback DROP TABLE IF EXISTS tb_blocks;