package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void criarCliente() {
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
}
