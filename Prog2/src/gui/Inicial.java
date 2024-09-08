package gui;


public class Inicial {
    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            // Printando as opções na tela
            System.out.println("Menu Principal:");
            System.out.println("1. Cinema");
            System.out.println("2. Cadastro");
            System.out.println("3. Vendas");
            System.out.println("4. Relatório");
            System.out.println("0. Sair");
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
                    // Chamar a classe Cinema
                    Cinemas.main(null);
                    break;
                case 2:
                    // Chamar a classe Cadastro
                    Cadastro.main(null);
                    break;
                case 3:
                    // Chamar a classe Vendas
                    Vendas.main(null);
                    break;
                case 4:
                    // Chamar a classe Relatório
                    Relatorio.main(null);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println(); // Linha em branco para separar as interações

        } while (opcao != 0);

        scanner.close();
    }
}
