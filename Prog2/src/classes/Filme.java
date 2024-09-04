package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Filme {
    static int EM_CARTAZ_SIM = 1;
    static int EM_CARTAZ_NAO = 0;
    int id;
    String nome;
    String sinopse;
    Categoria categoria;
    int classificacao;
    int emCartaz; 
    Config config;

    // Construtor que inicializa Config e usa Scanner para preencher o objeto
    public Filme() {
        this.config = new Config();
        Scanner sc = config.getScanner();

        // Captura as informações do filme
        System.out.print("Digite o nome do filme: ");
        this.nome = sc.nextLine();

        System.out.print("Digite a sinopse do filme: ");
        this.sinopse = sc.nextLine();

        System.out.print("Digite a classificação do filme: ");
        while (!sc.hasNextInt()) {
            System.out.println("Classificação inválida. Por favor, digite um número inteiro.");
            sc.next(); // Limpa a entrada inválida
            System.out.print("Digite a classificação do filme: ");
        }
        this.classificacao = sc.nextInt();         
        sc.nextLine(); // Limpa o buffer após a leitura de um número

        // Busca categorias e preenche o atributo Categoria
        this.categoria = Categoria.buscarCategoria(sc, config);
        
        // Pergunta se o filme está em cartaz
        System.out.print("O filme está em cartaz? (sim/não): ");
        String resposta = sc.nextLine().toLowerCase();
        this.emCartaz = resposta.equals("sim") ? EM_CARTAZ_SIM : EM_CARTAZ_NAO;
    }
    
    public Filme(int vazio) {
        // Construtor vazio para uso em busca de filmes
    }

    // Método para criar o filme no banco de dados
    public void criarFilme() {
        Config c = new Config();
        String query = "INSERT INTO filme (nome, sinopse, id_categoria, classificacao, em_cartaz) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = c.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, this.nome);
            stmt.setString(2, this.sinopse);
            stmt.setInt(3, this.categoria.getId());
            stmt.setInt(4, this.classificacao);
            stmt.setInt(5, this.emCartaz);
            stmt.executeUpdate();
            System.out.println("Filme inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir filme no banco de dados:");
            e.printStackTrace();
        }
    }

    // Método para buscar todos os filmes do banco de dados e oferecer opção de criar novo filme
    public static List<Filme> buscarFilme() {
        int vazio = 1;
        Config config = new Config();
        Scanner sc = config.getScanner();
        List<Filme> filmes = new ArrayList<>();
        // Query ajustada para trazer o nome da categoria usando JOIN
        String query = "SELECT filme.id, filme.nome, filme.sinopse, filme.id_categoria, filme.classificacao, filme.em_cartaz, categoria.nome AS nome_categoria " +
                       "FROM filme " +
                       "JOIN categoria ON filme.id_categoria = categoria.id";

        try (Connection connection = config.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Filme filme = new Filme(vazio);
                filme.id = rs.getInt("id");
                filme.nome = rs.getString("nome");
                filme.sinopse = rs.getString("sinopse");
                filme.categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome_categoria"));
                filme.classificacao = rs.getInt("classificacao");
                filme.emCartaz = rs.getInt("em_cartaz");
                filmes.add(filme);
            }
            
            // Exibindo os filmes no formato solicitado
            for (int i = 0; i < filmes.size(); i++) {
                Filme filme = filmes.get(i);
                System.out.println("=================\n" + (i + 1) + ". " + filme.getNome() +
                    "\nCategoria: " + filme.getCategoria().getNome() +
                    "\nClassificação: " + filme.getClassificacao() +
                    "\nEm cartaz: " + ((filme.getEmCartaz() == 1) ? "Sim" : "Não") +
                    "\n===================="
                );
            }

            // Perguntar ao usuário se deseja criar um novo filme
            System.out.print("Deseja criar um novo filme? (sim/não): ");
            String resposta = sc.nextLine().toLowerCase();
            if (resposta.equals("sim")) {
                Filme novoFilme = new Filme();
                novoFilme.criarFilme();
            } else {
                System.out.println("Operação finalizada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar filmes do banco de dados:");
            e.printStackTrace();
        }

        return filmes;
    }

    // Método para buscar filmes que estão em cartaz e permitir a seleção pelo usuário
    public static Filme buscarFilmeEmCartaz() {
        Config c = new Config();
        List<Filme> filmesEmCartaz = new ArrayList<>();
        String query = "SELECT filme.id, filme.nome, filme.sinopse, filme.id_categoria, filme.classificacao, filme.em_cartaz, categoria.nome AS nome_categoria " +
                       "FROM filme " +
                       "JOIN categoria ON filme.id_categoria = categoria.id " +
                       "WHERE filme.em_cartaz = ?";
        int vazio = 1;

        try (Connection connection = c.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, EM_CARTAZ_SIM);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Filme filme = new Filme(vazio);
                filme.id = rs.getInt("id");
                filme.nome = rs.getString("nome");
                filme.sinopse = rs.getString("sinopse");
                filme.categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome_categoria")); 
                filme.classificacao = rs.getInt("classificacao");
                filme.emCartaz = rs.getInt("em_cartaz");
                filmesEmCartaz.add(filme);
            }

            // Exibindo os filmes em cartaz
            System.out.println("Selecione um filme pelo ID ou digite 0 para cancelar:");
            for (Filme filme : filmesEmCartaz) {
                System.out.println("=================\nID: " + filme.getId() + 
                                   " | Nome: " + filme.getNome() + 
                                   " | Categoria: " + filme.getCategoria().getNome() +
                                   " | Classificação: " + filme.getClassificacao() +
                                   " | Em cartaz: " + ((filme.getEmCartaz() == 1) ? "Sim" : "Não") +
                                   "\n====================");
            }

            // Seleção do filme pelo usuário
            System.out.print("Digite o ID do filme desejado ou 0 para cancelar: ");
            int escolha = c.getScanner().nextInt();
            c.getScanner().nextLine(); // Limpa o buffer após a leitura do número
            
            if (escolha == 0) {
                System.out.println("Operação cancelada.");
                return null;
            }

            for (Filme filme : filmesEmCartaz) {
                if (filme.getId() == escolha) {
                    return filme;
                }
            }

            System.out.println("ID inválido. Nenhum filme selecionado.");
            return null;

        } catch (SQLException e) {
            System.err.println("Erro ao buscar filmes em cartaz do banco de dados:");
            e.printStackTrace();
            return null;
        }
    }

    // Sobrecarga do método buscarFilmeEmCartaz que retorna uma lista de filmes quando passado um int como argumento
    public static List<Filme> buscarFilmeEmCartaz(int view) {
        Config c = new Config();
        List<Filme> filmesEmCartaz = new ArrayList<>();
        String query = "SELECT filme.id, filme.nome, filme.sinopse, filme.id_categoria, filme.classificacao, filme.em_cartaz, categoria.nome AS nome_categoria " +
                       "FROM filme " +
                       "JOIN categoria ON filme.id_categoria = categoria.id " +
                       "WHERE filme.em_cartaz = ?";

        try (Connection connection = c.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, EM_CARTAZ_SIM);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Filme filme = new Filme(1);
                filme.id = rs.getInt("id");
                filme.nome = rs.getString("nome");
                filme.sinopse = rs.getString("sinopse");
                filme.categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome_categoria")); 
                filme.classificacao = rs.getInt("classificacao");
                filme.emCartaz = rs.getInt("em_cartaz");
                filmesEmCartaz.add(filme);
            }

            // Retorna a lista completa de filmes em cartaz
            return filmesEmCartaz;

        } catch (SQLException e) {
            System.err.println("Erro ao buscar filmes em cartaz do banco de dados:");
            e.printStackTrace();
            return null;
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

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public int getEmCartaz() {
        return emCartaz;
    }

    public void setEmCartaz(int emCartaz) {
        this.emCartaz = emCartaz;
    }
}
