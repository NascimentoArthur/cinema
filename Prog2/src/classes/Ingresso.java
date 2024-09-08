package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Ingresso {
    Cliente cliente;
    Sessao sessao;
    double valor;
    Config config;
    private String tipoIngressoStr = "INTEIRA";
    int id;

    public Ingresso() {
    	
        this.config = new Config();
        Scanner sc = config.getScanner();

        this.cliente = Cliente.buscaCliente();
        if (this.cliente == null) {
            System.out.println("Nenhum cliente selecionado. Operação cancelada.");
            return;
        }

        this.sessao = Sessao.buscaSessao();
        if (this.sessao == null) {
            System.out.println("Nenhuma sessão selecionada. Operação cancelada.");
            return;
        }

        System.out.println("Insira o valor integral do ingresso.");
        this.valor = sc.nextDouble();
    }

    // Coloca o ingresso no banco de dados
    public void vendeIngresso(String tipoIngressoStr) {
        int assento = this.sessao.verificaAssento();

        if (assento <= 0) {
            System.out.println("Nenhum assento disponível. Operação cancelada.");
            return;
        }

        String sql = "INSERT INTO ingresso (id_cliente, id_sessao, valor, assento, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, sessao.getId());
            stmt.setDouble(3, this.valor);
            stmt.setInt(4, assento);
            stmt.setString(5, tipoIngressoStr);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
                System.out.println("Ingresso vendido com sucesso!");
                this.sessao.atualizaSessao();
            } else {
                System.out.println("Falha ao vender o ingresso.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o ingresso no banco de dados:");
            e.printStackTrace();
        }
    }

    public Filme verificarHistorico() {
        String sql = """
                SELECT f.id, f.nome, f.id_classificacao, c.id AS id_categoria, c.nome AS nome_categoria, s.hora_inicio
                FROM ingresso i
                JOIN sessao s ON i.id_sessao = s.id
                JOIN filme f ON s.id_filme = f.id
                JOIN categoria c ON f.id_categoria = c.id
                WHERE i.id_cliente = ?
                ORDER BY i.id DESC
                LIMIT 1
                """;

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idFilme = rs.getInt("id");
                    String nomeFilme = rs.getString("nome");
                    int classificacao = rs.getInt("id_classificacao");
                    int idCategoria = rs.getInt("id_categoria");
                    String nomeCategoria = rs.getString("nome_categoria");

                    // Cria o objeto Categoria com as informações obtidas
                    Categoria categoria = new Categoria();
                    categoria.setId(idCategoria);
                    categoria.setNome(nomeCategoria);

                    // Cria um objeto Filme com as informações obtidas
                    Filme filme = new Filme();
                    filme.setId(idFilme);
                    filme.setNome(nomeFilme);
                    filme.setClassificacao(classificacao);
                    filme.setCategoria(categoria); // Define o objeto Categoria

                    return filme;
                } else {
                    System.out.println("Nenhum histórico encontrado para este cliente.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar o histórico do cliente:");
            e.printStackTrace();
            return null;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }
}
