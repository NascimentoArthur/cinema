package gui;

import java.util.Scanner;
import classes.Cliente;
import classes.Categoria;
import classes.Filme;
import classes.Sessao;

public class Cadastro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            // Printando as opções na tela
            System.out.println("Menu de Cadastro:");
            System.out.println("1. Cadastrar Categorias");
            System.out.println("2. Cadastrar Filmes");
            System.out.println("3. Cadastrar Sessões");
            System.out.println("4. Cadastrar Clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            // Lendo a opção do usuário e tratando exceções
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha deixada por nextInt()
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.next(); // Limpa o buffer do scanner
                opcao = -1; // Define uma opção inválida para repetir o loop
            }

            switch (opcao) {
                case 1:
                    // Chamar método para cadastrar categorias
                    System.out.println("Cadastrar Categorias selecionado.");
                    Categoria categoria = new Categoria();
                    categoria.criaCategoria();
                    break;
                case 2:
                    // Chamar método para cadastrar filmes
                    System.out.println("Cadastrar Filmes selecionado.");
                    Filme filme = new Filme();
                    filme.criarFilme();
                    break;
                case 3:
                    // Chamar método para cadastrar sessões
                    System.out.println("Cadastrar Sessões selecionado.");
                    Sessao sessao = new Sessao();
                    sessao.criaSessao();
                    break;
                case 4:
                    // Chamar método para cadastrar clientes
                    System.out.println("Cadastrar Clientes selecionado.");
                    Cliente cliente = new Cliente();
                    cliente.criaCliente();
                    break;
                case 0:
                    // Voltar ao menu principal
                    System.out.println("Voltando ao menu principal.");
                    Inicial.menuPrincipal(); // Chama o método do menu principal
                    return; // Retorna para garantir que o loop principal em Cadastro.java não continue
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println(); // Linha em branco para separar as interações

        } while (opcao != 0);

        scanner.close();
    }
}
