package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Scanner;

public class Sessao {
    private LocalTime horario;
    private Filme filme;
    private Sala sala;
    private int assentosDisponiveis;
    private Config config;
    private int id;

    public Sessao() {
        config = new Config();
        Scanner sc = config.getScanner();
        int vazio = 1;

        this.filme = Filme.buscarFilmeEmCartaz();
        if (this.filme == null) {
            System.out.println("Nenhum filme selecionado. Operação cancelada.");
            return;
        }

        this.sala = Sala.buscarSalas(sc);
        if (this.sala == null) {
            System.out.println("Nenhuma sala selecionada. Operação cancelada.");
            return;
        }

        sc.nextLine();

        LocalTime horarioTime = null;
        while (horarioTime == null) {
            try {
                System.out.print("Digite o horário da sessão (formato: HH:MM): ");
                String horarioStr = sc.nextLine().trim();
                if (horarioStr.isEmpty()) {
                    System.out.println("Entrada vazia, por favor insira um horário válido.");
                    continue;
                }
                horarioTime = LocalTime.parse(horarioStr);
            } catch (Exception e) {
                System.out.println("Formato de horário inválido. Por favor, use o formato HH:MM.");
            }
        }
        this.horario = horarioTime;
    }

    public Sessao(int vazio) {
        config = new Config();
    }
 
    public void criaSessao() {
        String sql = "INSERT INTO sessao (id_filme, id_sala, hora_inicio, assentos_disponiveis) VALUES (?, ?, ?, ?)";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, filme.getId());
            stmt.setInt(2, sala.getNumeroSala());
            stmt.setObject(3, horario); // Salva o horário no formato TIME
            stmt.setInt(4, this.assentosDisponiveis);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
                System.out.println("Sessão salva no banco de dados com sucesso!");
            } else {
                System.out.println("Falha ao salvar a sessão no banco de dados.");
            }
        } catch (SQLException e) {
            System.out.println("criaSessao(): Erro ao salvar a sessão no banco de dados: " + e.getMessage());
        }
    }

    public int verificaAssento() {
    	Config config = new Config();
    	
        String sql = "SELECT COUNT(*) FROM ingresso WHERE id_sessao = ?";
        int assentosOcupados = 0;

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    assentosOcupados = rs.getInt(1);
          
                    this.assentosDisponiveis = this.sala.getAssentos() - assentosOcupados;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar os assentos ocupados: " + e.getMessage());
        }

        return this.assentosDisponiveis;
    }

    public void atualizaSessao() {
    	Config config = new Config();
        int exit = this.verificaFilme();
        
        if (exit == 1) {
            return;
        }

        this.assentosDisponiveis = verificaAssento();
        
        String sql = "UPDATE sessao SET assentos_disponiveis = ? WHERE id = ?";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.assentosDisponiveis);
            stmt.setInt(2, this.id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sessão atualizada com sucesso! Assentos disponíveis: " + this.assentosDisponiveis);
            } else {
                System.out.println("Falha ao atualizar a sessão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a sessão no banco de dados: " + e.getMessage());
        }
    }

    public int verificaFilme() {
    	Config config = new Config();
    	
        String QverificaFilme = "SELECT em_cartaz FROM filme WHERE id = ?";
        String QdeletarSessao = "DELETE FROM sessao WHERE id = ?";
        int exit = 0;

        try (Connection conn = config.getConnection();
             PreparedStatement verificaFilmeStmt = conn.prepareStatement(QverificaFilme)) {

            verificaFilmeStmt.setInt(1, this.filme.getId());
            try (ResultSet rs = verificaFilmeStmt.executeQuery()) {
                if (rs.next()) {
                    int emCartaz = rs.getInt("em_cartaz");

                    if (emCartaz == 0) {
                        try (PreparedStatement deletarSessaoStmt = conn.prepareStatement(QdeletarSessao)) {
                            deletarSessaoStmt.setInt(1, this.id);
                            int rowsAffected = deletarSessaoStmt.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Sessão excluída, pois o filme não está em cartaz.");
                                exit = 1;
                            } else {
                                System.out.println("Falha ao excluir a sessão.");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar o status do filme ou ao excluir a sessão: " + e.getMessage());
        }
        return exit;
    }

    public static Sessao buscaSessao() {
        Config config = new Config();
        int vazio = 1;

        String sql = """
                SELECT s.id, s.hora_inicio, f.nome AS nome_filme, f.classificacao, c.nome AS categoria
                FROM sessao s
                JOIN filme f ON s.id_filme = f.id
                JOIN categoria c ON f.id_categoria = c.id
                ORDER BY f.nome, s.hora_inicio
                """;

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Sessões disponíveis:");
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalTime horario = rs.getTime("hora_inicio").toLocalTime();
                String nomeFilme = rs.getString("nome_filme");
                String classificacao = rs.getString("classificacao");
                String categoria = rs.getString("categoria");

                System.out.printf("===========================================================================================================\n"
                        + "ID: %d | Horário: %s | Filme: %s | Categoria: %s | Classificação: %s%n",
                        id, horario, nomeFilme, categoria, classificacao);
            }
            System.out.printf("===========================================================================================================\n");

            Scanner sc = config.getScanner();
            System.out.print("Digite o ID da sessão para selecionar ou 0 para cancelar: ");
            int sessaoId = sc.nextInt();

            if (sessaoId == 0) {
                System.out.println("Operação cancelada.");
                return null;
            }

            String sqlDetalhes = """
            	    SELECT s.id, s.hora_inicio, s.id_filme, s.id_sala, s.assentos_disponiveis, sa.numero_assentos
            	    FROM sessao s
            	    JOIN sala sa ON s.id_sala = sa.id
            	    WHERE s.id = ?
            	""";
            try (PreparedStatement stmtDetalhes = conn.prepareStatement(sqlDetalhes)) {
                stmtDetalhes.setInt(1, sessaoId);
                try (ResultSet rsDetalhes = stmtDetalhes.executeQuery()) {
                    if (rsDetalhes.next()) {
                        Sessao sessao = new Sessao(vazio);
                        sessao.id = rsDetalhes.getInt("id");
                        sessao.horario = rsDetalhes.getTime("hora_inicio").toLocalTime();
                        sessao.filme = new Filme(vazio);
                        sessao.filme.setId(rsDetalhes.getInt("id_filme"));
                        sessao.sala = new Sala(vazio);
                        sessao.sala.setNumeroSala(rsDetalhes.getInt("id_sala"));
                        sessao.sala.setAssentos(rsDetalhes.getInt("numero_assentos"));
                        sessao.assentosDisponiveis = rsDetalhes.getInt("assentos_disponiveis");

                        return sessao;
                    } else {
                        System.out.println("Sessão não encontrada.");
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar sessões: " + e.getMessage());
            return null;
        }
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public void setAssentosDisponiveis(int assentosDisponiveis) {
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
