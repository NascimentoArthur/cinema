package gui;

import classes.Ingresso;
import classes.IngressoMeia;

import java.util.Scanner;

public class Vendas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            // Printando as opções na tela
            System.out.println("Menu de Vendas:");
            System.out.println("1. Vender Ingresso Inteira");
            System.out.println("2. Vender Ingresso Meia Entrada");
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
                    // Vender ingresso inteiro
                    System.out.println("Selecionado: Vender Ingresso Inteiro");
                    Ingresso ingressoInteiro = new Ingresso();
                    ingressoInteiro.vendeIngresso("INTEIRA");
                    break;
                case 2:
                    // Vender ingresso meia entrada
                    System.out.println("Selecionado: Vender Ingresso Meia Entrada");
                    IngressoMeia ingressoMeia = new IngressoMeia();
                    ingressoMeia.vendeIngresso("MEIA_ENTRADA");
                    break;
                case 0:
                    // Voltar ao menu principal
                    Inicial.menuPrincipal();
                    return; // Retorna para garantir que o loop principal em Vendas.java não continue
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println(); // Linha em branco para separar as interações

        } while (opcao != 0);

        scanner.close();
    }
}
