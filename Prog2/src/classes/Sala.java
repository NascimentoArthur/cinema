package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Sala {
    private int numeroSala;
    private int numeroAssentos;

    // Construtor vazio para uso em Sessao
    public Sala(int vazio) {
    }

    public Sala(int numeroSala, int numeroAssentos) {
        this.numeroSala = numeroSala;
        this.numeroAssentos = numeroAssentos;
    }

    public static Sala buscarSalas(Scanner sc) {
        Config config = new Config();
        String sql = "SELECT id, numero_assentos FROM sala";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Salas disponíveis:");
            while (rs.next()) {
                int numeroSala = rs.getInt("id");
                int numeroAssentos = rs.getInt("numero_assentos");

                System.out.printf("Número da Sala: %d | Assentos: %d%n", numeroSala, numeroAssentos);
            }

            System.out.print("Digite o número da sala para selecionar ou 0 para cancelar: ");
            int salaNumero = sc.nextInt();

            if (salaNumero == 0) {
                System.out.println("Operação cancelada.");
                return null;
            }

            String sqlDetalhes = "SELECT id, numero_assentos FROM sala WHERE id = ?";
            try (PreparedStatement stmtDetalhes = conn.prepareStatement(sqlDetalhes)) {
                stmtDetalhes.setInt(1, salaNumero);
                try (ResultSet rsDetalhes = stmtDetalhes.executeQuery()) {
                    if (rsDetalhes.next()) {
                        return new Sala(rsDetalhes.getInt("id"), rsDetalhes.getInt("numero_assentos"));
                    } else {
                        System.out.println("Sala não encontrada.");
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar salas: " + e.getMessage());
            return null;
        }
    }

    public int getAssentos() {
        return numeroAssentos;
    }

    public void setAssentos(int numeroAssentos) {
        this.numeroAssentos = numeroAssentos;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }
}
