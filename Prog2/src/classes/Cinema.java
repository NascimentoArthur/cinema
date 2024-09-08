package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cinema {
    int id;
    double faturamento;
    Catalogo catalogo;

    public Cinema() {
    }

    public void atualizaCinema() {
        String sqlSum = "SELECT SUM(valor) AS total_faturamento FROM ingresso";
        String sqlUpdate = "UPDATE cinema SET faturamento = ? WHERE id = ?";

        Config config = new Config();

        try (Connection conn = config.getConnection();
             PreparedStatement stmtSum = conn.prepareStatement(sqlSum);
             ResultSet rs = stmtSum.executeQuery()) {

            if (rs.next()) {
                double totalFaturamento = rs.getDouble("total_faturamento");
                this.faturamento = totalFaturamento;

                try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                    stmtUpdate.setDouble(1, this.faturamento);
                    stmtUpdate.setInt(2, this.id);

                    int rowsAffected = stmtUpdate.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Faturamento atualizado com sucesso para o cinema com ID: " + this.id);
                    } else {
                        System.out.println("Nenhuma atualização realizada para o cinema com ID: " + this.id);
                    }
                }
            } else {
                System.out.println("Nenhum ingresso encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o faturamento do cinema:");
            e.printStackTrace();
        }
    }

    public void criaCinema() {
        String sqlInsert = "INSERT INTO cinema (faturamento) VALUES (0)";

        Config config = new Config();

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                        this.faturamento = 0;
                        System.out.println("Cinema criado com sucesso com ID: " + this.id);
                    }
                }
            } else {
                System.out.println("Falha ao criar o cinema.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar o cinema:");
            e.printStackTrace();
        }
    }

    public static Cinema buscaCinema() {
        String sql = "SELECT * FROM cinema";
        Config config = new Config();
        List<Cinema> cinemas = new ArrayList<>();

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Cinemas disponíveis:");
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setId(rs.getInt("id"));
                cinema.setFaturamento(rs.getDouble("faturamento"));
                cinemas.add(cinema);

                System.out.printf("ID: %d | Faturamento: %.2f%n", cinema.getId(), cinema.getFaturamento());
            }

            if (!cinemas.isEmpty()) {
                System.out.print("Digite o ID do cinema para selecionar ou 0 para cancelar: ");
                int selectedId = config.sc.nextInt();

                if (selectedId == 0) {
                    System.out.println("Operação cancelada.");
                    return null;
                }

                for (Cinema cinema : cinemas) {
                    if (cinema.getId() == selectedId) {
                        return cinema;
                    }
                }
                System.out.println("Cinema com ID " + selectedId + " não encontrado.");
            } else {
                System.out.println("Nenhum cinema encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar os cinemas:");
            e.printStackTrace();
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }
}
