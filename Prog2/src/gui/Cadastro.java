package gui;

import java.util.Scanner;

import classes.Cliente;

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

            // Lendo a opção do usuário
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // Chamar método para cadastrar categorias
                    System.out.println("Cadastrar Categorias selecionado.");
                    // cadastrarCategoria();
                    break;
                case 2:
                    // Chamar método para cadastrar filmes
                    System.out.println("Cadastrar Filmes selecionado.");
                    // cadastrarFilme();
                    break;
                case 3:
                    // Chamar método para cadastrar sessões
                    System.out.println("Cadastrar Sessões selecionado.");
                    // cadastrarSessao();
                    break;
                case 4:
                    // Chamar método para cadastrar clientes
                    System.out.println("Cadastrar Clientes.");
                    Cliente cliente = new Cliente();
                    cliente.criaCliente();
                    
                    break;
                case 0:
                    // Opção para voltar
                    System.out.println("Voltando ao menu anterior.");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println(); // Linha em branco para separar as interações

        } while (opcao != 0);

        scanner.close();
    }
}
