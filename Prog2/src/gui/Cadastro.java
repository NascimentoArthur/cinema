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
        	System.out.println("=======================================");
            System.out.println("Menu de Cadastro:");
            System.out.println("=======================================");
            System.out.println("1. Cadastrar Categorias");
            System.out.println("2. Cadastrar Filmes");
            System.out.println("3. Cadastrar Sessões");
            System.out.println("4. Cadastrar Clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.next();
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.println("Cadastrar Categorias selecionado.");
                    Categoria categoria = new Categoria();
                    categoria.criaCategoria();
                    break;
                case 2:
                    System.out.println("Cadastrar Filmes selecionado.");
                    Filme filme = new Filme();
                    filme.criarFilme();
                    break;
                case 3:
                    System.out.println("Cadastrar Sessões selecionado.");
                    Sessao sessao = new Sessao();
                    sessao.criaSessao();
                    break;
                case 4:
                    System.out.println("Cadastrar Clientes selecionado.");
                    Cliente cliente = new Cliente();
                    cliente.criaCliente();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal.");
                    Inicial.menuPrincipal(); 
                    return;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
