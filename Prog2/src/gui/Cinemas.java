package gui;

import classes.Cinema;
import java.util.Scanner;

public class Cinemas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            // Printando as opções na tela
            System.out.println("Menu de Cinema:");
            System.out.println("1. Criar Cinema");
            System.out.println("2. Acessar Cinema");
            System.out.println("0. Voltar ao Menu Principal");
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
                    // Criar novo cinema
                    Cinema novoCinema = new Cinema();
                    novoCinema.criaCinema();
                    break;
                case 2:
                    // Acessar um cinema existente
                    Cinema cinemaSelecionado = Cinema.buscaCinema();
                    if (cinemaSelecionado != null) {
                        // Exibir informações do cinema selecionado
                        System.out.printf("Você selecionou o cinema com ID %d e faturamento %.2f%n",
                                cinemaSelecionado.getId(), cinemaSelecionado.getFaturamento());
                    }
                    break;
                case 0:
                    // Voltar ao menu principal
                    System.out.println("Voltando ao menu principal.");
                    Inicial.menuPrincipal(); // Chama o método do menu principal
                    return; // Retorna para garantir que o loop principal em CinemaApp.java não continue
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println(); // Linha em branco para separar as interações

        } while (opcao != 0);

        scanner.close();
    }
}
