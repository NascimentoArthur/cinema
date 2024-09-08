package gui;

import classes.*;
import classes.IngressoMeia;

import java.util.Scanner;

public class Vendas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        Cinema cinema = new Cinema();
        cinema = gui.Cinemas.telaCinema();
        
        do {
        	System.out.println("=======================================");
            System.out.println("Menu de Vendas:");
            System.out.println("=======================================");
            System.out.println("1. Vender Ingresso Inteira");
            System.out.println("2. Vender Ingresso Meia Entrada");
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
                    System.out.println("Selecionado: Vender Ingresso Inteiro");
                    Ingresso ingressoInteiro = new Ingresso();
                    if (ingressoInteiro.getCliente() != null && ingressoInteiro.getSessao() != null) {
                    	ingressoInteiro.vendeIngresso(cinema, "INTEIRA");
                    	cinema.atualizaCinema(ingressoInteiro.getValor());
                    }
                    break;
                case 2:
                    System.out.println("Selecionado: Vender Ingresso Meia Entrada");
                    IngressoMeia ingressoMeia = new IngressoMeia();
                    if (ingressoMeia.getCliente() != null && ingressoMeia.getSessao() != null) {
                    	ingressoMeia.vendeIngresso(cinema, "MEIA_ENTRADA");
                    	cinema.atualizaCinema(ingressoMeia.getValor());
                    }
                    break;
                case 0:
                    Inicial.menuPrincipal();
                    return; 
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println();

        } while (opcao != 0);

        scanner.close();
    }
}
