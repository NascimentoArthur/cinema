package gui;

import classes.Cinema;
import java.util.Scanner;

public class Relatorio {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
        	System.out.println("=======================================");
            System.out.println("Menu de Relatórios:");
            System.out.println("=======================================");
            System.out.println("1. Exibir Faturamento do Cinema");
            System.out.println("0. Voltar ao Menu Principal");
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
                    Cinema cinema = Cinema.buscaCinema();
                    if (cinema != null) {
                        System.out.printf("Faturamento do cinema com ID %d: %.2f%n", cinema.getId(), cinema.getFaturamento());
                    }
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
