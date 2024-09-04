package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sala {
    int numeroSala; // Esta variável não é mais usada, mas pode ser mantida para referência futura
    int assentos;
    Config config;

    // Construtor que inicializa Config e usa Scanner para preencher o objeto
    public Sala() {
        this.config = new Config();
        Scanner sc = config.getScanner(); // Certifique-se de que getScanner() está correto

        // Captura o número de assentos da sala
        System.out.print("Digite o número de assentos da sala: ");
        while (!sc.hasNextInt()) {
            System.out.println("Número inválido. Por favor, digite um número inteiro.");
            sc.next(); // Limpa a entrada inválida
            System.out.print("Digite o número de assentos da sala: ");
        }
        this.assentos = sc.nextInt();
    }
    
    public Sala(int vazio) {
    	
    }

    // Método para criar a sala no banco de dados
    public void criarSala() {
        String query = "INSERT INTO sala (numero_assentos) VALUES (?)";
        
        try (Connection connection = config.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.assentos);
            stmt.executeUpdate();
            System.out.println("Sala inserida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir sala no banco de dados:");
            e.printStackTrace();
        }
    }

    // Método para buscar todas as salas e permitir a seleção do usuário
    public Sala buscarSalas(Scanner sc) {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT id, numero_assentos FROM sala";
        int vazio = 1;

        try (Connection connection = new Config().getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int numeroSala = rs.getInt("id");
                int assentos = rs.getInt("numero_assentos");
                Sala sala = new Sala(vazio);
                sala.numeroSala = numeroSala;
                sala.assentos = assentos;
                salas.add(sala);
            }

            System.out.println("Selecione uma sala:");
            for (int i = 0; i < salas.size(); i++) {
                Sala sala = salas.get(i);
                System.out.println("Sala " + sala.numeroSala + " com " + sala.assentos + " assentos");
            }

            int escolha;
            System.out.print("Digite o número da sala desejada: ");
            escolha = sc.nextInt();
            sc.nextLine(); // Limpa o buffer após a leitura de um número

            if (escolha > 0 && escolha <= salas.size()) {
                return salas.get(escolha - 1);
            } else {
                System.out.println("Seleção inválida.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar salas do banco de dados:");
            e.printStackTrace();
            return null;
        }
    }
}
