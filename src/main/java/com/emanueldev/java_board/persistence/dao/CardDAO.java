package com.emanueldev.java_board.persistence.dao;

import com.emanueldev.java_board.dto.CardDetailsDTO;
import com.emanueldev.java_board.persistence.entity.CardEntity;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static com.emanueldev.java_board.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;



import static java.util.Objects.nonNull;

public class CardDAO {

    private final Connection connection;

    public CardDAO(Connection connection) {
        this.connection = connection;
    }

    public CardEntity insert(final CardEntity entity) throws SQLException {
        String query = "INSERT INTO tb_cards (title, description, board_column_id) values (?, ?, ?);";
        try(var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            int i = 1;
            statement.setString(i ++, entity.getTitle());
            statement.setString(i ++, entity.getDescription());
            statement.setLong(i, entity.getBoardColumn().getId());
            statement.executeUpdate();
            try(var rs = statement.getGeneratedKeys()) {
                if(rs.next()){
                    entity.setId(rs.getLong(1));
                }
            }
        }
        return entity;
    }

    public void moveToColumn(final Long columnId, final Long cardId) throws SQLException{
        String query = "UPDATE tb_cards SET board_column_id = ? WHERE id = ?;";
        try(var statement = connection.prepareStatement(query)){
            int i = 1;
            statement.setLong(i ++, columnId);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var sql =
                """
                SELECT c.id,
                       c.title,
                       c.description,
                       b.blocked_at,
                       b.block_reason,
                       c.board_column_id,
                       bc.name,
                       (SELECT COUNT(sub_b.id)
                               FROM tb_blocks sub_b
                              WHERE sub_b.card_id = c.id) blocks_amount
                  FROM tb_cards c
                  LEFT JOIN tb_blocks b
                    ON c.id = b.card_id
                   AND b.unblocked_at IS NULL
                 INNER JOIN tb_boards_columns bc
                    ON bc.id = c.board_column_id
                  WHERE c.id = ?;
                """;
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()){
                var dto = new CardDetailsDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        nonNull(resultSet.getString("b.block_reason")),
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.name")
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
