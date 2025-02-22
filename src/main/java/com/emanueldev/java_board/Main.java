package com.emanueldev.java_board;

import com.emanueldev.java_board.persistence.migration.MigrationStrategy;
import com.emanueldev.java_board.ui.MainMenu;

import java.sql.SQLException;

import static com.emanueldev.java_board.persistence.config.ConnectionConfig.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()) {
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();
    }
}
