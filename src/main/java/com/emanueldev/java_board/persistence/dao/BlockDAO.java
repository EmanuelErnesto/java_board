package com.emanueldev.java_board.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static com.emanueldev.java_board.persistence.converter.OffsetDateTimeConverter.toTimestamp;



public class BlockDAO {

    private final Connection connection;

    public BlockDAO(Connection connection) {
        this.connection = connection;
    }

    public void block(final String reason, final Long cardId) throws SQLException {
        String query = "INSERT INTO tb_blocks (blocked_at, block_reason, card_id) VALUES (?, ?, ?);";
        try(var statement = connection.prepareStatement(query)){
            int i = 1;
            statement.setTimestamp(i ++, toTimestamp(OffsetDateTime.now()));
            statement.setString(i ++, reason);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public void unblock(final String reason, final Long cardId) throws SQLException{
        String query = "UPDATE tb_blocks SET unblocked_at = ?, unblock_reason = ? WHERE card_id = ? AND unblock_reason IS NULL;";
        try(var statement = connection.prepareStatement(query)){
            int i = 1;
            statement.setTimestamp(i ++, toTimestamp(OffsetDateTime.now()));
            statement.setString(i ++, reason);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

}
