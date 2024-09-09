package classes;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class Config {
    Connection connection;
    public Scanner sc;
    DateTimeFormatter formatoData;

    public Config() {
        this.sc = new Scanner(System.in);
        this.connection = createConnection();
    }

    public Connection createConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/db_cinema";
        String username = "root";
        String password = "user123";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados:");
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Scanner getScanner() {
        return this.sc;
    }

    public DateTimeFormatter getFormatoData() {
        return this.formatoData;
    }

    // Método para fechar a conexão com o banco de dados
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Conexão fechada");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão:");
                e.printStackTrace();
            }
        }
    }
}
