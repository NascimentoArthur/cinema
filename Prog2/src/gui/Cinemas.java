package gui;

import classes.*;

public class Cinemas {
    public static void main(String[] args) {
        
        
        
    }
    
    public static Cinema telaCinema() {
    	Config config = new Config();
    	int opcao;
    	System.out.println("=======================================");
        System.out.println("Menu de Cinema:");
        System.out.println("=======================================");
        System.out.println("1. Criar Cinema");
        System.out.println("2. Acessar Cinema");
        System.out.print("Escolha uma opção: ");

        try {
            opcao = config.sc.nextInt();
            config.sc.nextLine();
        } catch (Exception e) {
            System.out.println("Entrada inválida! Por favor, insira um número.");
            config.sc.next();
            opcao = -1;
        }

        switch (opcao) {
            case 1:
                Cinema novoCinema = new Cinema();
                novoCinema.criaCinema();
                return novoCinema;
            case 2:
                Cinema cinemaSelecionado = Cinema.buscaCinema();
                if (cinemaSelecionado != null) {
                    System.out.printf("Você selecionou o cinema com ID %d e faturamento %.2f%n",
                            cinemaSelecionado.getId(), cinemaSelecionado.getFaturamento());
                }
                return cinemaSelecionado;
            default:
                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                return null;
        }
}
  
}
