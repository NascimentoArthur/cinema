package gui;

import classes.*;

public class Inicial {
    public static void menuPrincipal() {
        Config config = new Config();
        int opcao;

        do {
        	System.out.println("=======================================");
            System.out.println("Menu Principal:");
            System.out.println("=======================================");
            System.out.println("1. Cadastro");
            System.out.println("2. Vendas");
            System.out.println("3. Relatório");
            System.out.println("0. Sair");
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
                    Cadastro.main(null);
                    break;
                case 2:
                    Vendas.main(null);
                    break;
                case 3:
                    Relatorio.main(null);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println();

        } while (opcao != 0);
    }
}
