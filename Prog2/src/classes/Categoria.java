package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Categoria {
    private int id;
    private String nome;

    // Construtor padrão
    public Categoria() {
    }

    // Construtor com id e nome
    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Método para buscar categorias com Scanner e Config
    public static Categoria buscarCategoria(Scanner sc, Config config) {
        List<Categoria> categorias = new ArrayList<>();
        String query = "SELECT id, nome FROM categoria";

        try (Connection connection = config.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Categoria categoria = new Categoria(id, nome);
                categorias.add(categoria);
            }

            System.out.println("Selecione uma categoria:");
            System.out.println("0. Nova categoria");
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i + 1) + ". " + categorias.get(i).getNome());
            }

            int escolha;
            System.out.print("Digite o número da categoria desejada: ");
            escolha = sc.nextInt();
            sc.nextLine(); // Limpa o buffer após a leitura de um número

            if (escolha == 0) {
                // Caso a categoria não esteja na lista, cria uma nova categoria
                Categoria novaCategoria = new Categoria();
                novaCategoria.criaCategoria(sc, config);
                return novaCategoria;
            } else if (escolha > 0 && escolha <= categorias.size()) {
                return categorias.get(escolha - 1);
            } else {
                System.out.println("Seleção inválida.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar categorias do banco de dados:");
            e.printStackTrace();
            return null;
        }
    }

    // Método para criar uma nova categoria (simplificado para exemplo)
    private void criaCategoria(Scanner sc, Config config) {
        // Captura o nome da nova categoria
        System.out.print("Digite o nome da nova categoria: ");
        this.nome = sc.nextLine();

        String query = "INSERT INTO categoria (nome) VALUES (?)";

        try (Connection connection = config.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.nome);
            stmt.executeUpdate();

            // Obtém o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                }
            }
            System.out.println("Categoria criada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar categoria no banco de dados:");
            e.printStackTrace();
        }
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
