package classes;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class Config {
    private Connection connection;
    private Scanner sc;
    private DateTimeFormatter formatoData;

    public Config() {
        this.sc = new Scanner(System.in); // Inicializa o Scanner aqui
        this.connection = createConnection(); // Inicializa a conexão
    }

    // Método estático para criar a conexão com o banco de dados
    public Connection createConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/db_cinema";
        String username = "root"; // Substitua pelo seu usuário
        String password = "user123"; // Substitua pela sua senha

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado ao banco");
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados:");
            e.printStackTrace();
        }

        return connection;
    }

    // Método para obter a conexão existente
    public Connection getConnection() {
        return this.connection;
    }

    // Método para obter o Scanner
    public Scanner getScanner() {
        return this.sc;
    }

    // Método para obter o formato de data
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
