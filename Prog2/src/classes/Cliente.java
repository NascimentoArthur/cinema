package classes;

import java.sql.*;

import java.util.*;

public class Cliente {
    private int id;
    private String nome;
    private int idade;
    private Filme[] historico;
    private Config config;

    public Cliente() {
        this.config = new Config();
        Scanner sc = config.getScanner();

        System.out.print("Digite o nome do cliente: ");
        this.nome = sc.nextLine();

        System.out.print("Digite a idade do cliente: ");
        this.idade = sc.nextInt();
    }
    
    public Cliente(int vazio) {
    	
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Filme[] getHistorico() {
        return historico;
    }

    public void setHistorico(Filme[] historico) {
        this.historico = historico;
    }

    public void criaCliente() {
        String query = "INSERT INTO cliente (nome, idade) VALUES (?, ?)";

        try (Connection connection = config.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, this.nome);
            stmt.setInt(2, this.idade);
            stmt.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente no banco de dados:");
            e.printStackTrace();
        }
    }
    
    public static Cliente buscaCliente() {
        Config config = new Config();
        Scanner sc = config.getScanner();
        int vazio = 1;

        String query = "SELECT id, nome, idade FROM cliente ORDER BY nome";

        try (Connection connection = config.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Clientes disponíveis:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                System.out.printf("ID: %d | Nome: %s | Idade: %d%n", id, nome, idade);
            }

            System.out.print("Digite o ID do cliente para selecionar ou 0 para cancelar: ");
            int clienteId = sc.nextInt();
            sc.nextLine(); // Limpa o buffer do Scanner

            if (clienteId == 0) {
                System.out.println("Operação cancelada.");
                return null;
            }

            // Consultar o cliente específico pelo ID selecionado
            String queryDetalhe = "SELECT id, nome, idade FROM cliente WHERE id = ?";
            try (PreparedStatement stmtDetalhe = connection.prepareStatement(queryDetalhe)) {
                stmtDetalhe.setInt(1, clienteId);
                try (ResultSet rsDetalhe = stmtDetalhe.executeQuery()) {
                    if (rsDetalhe.next()) {
                        Cliente cliente = new Cliente(vazio);
                        cliente.id = rsDetalhe.getInt("id");
                        cliente.nome = rsDetalhe.getString("nome");
                        cliente.idade = rsDetalhe.getInt("idade");

                        return cliente;
                    } else {
                        System.out.println("Cliente não encontrado.");
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes no banco de dados:");
            e.printStackTrace();
            return null;
        }
    }
}
