package classes;

import java.util.Scanner;

public class IngressoMeia extends Ingresso{
	private String tipoIngressoStr = "MEIA_ENTRADA";
	public IngressoMeia(){
		this.config = new Config();
        Scanner sc = config.getScanner();

        this.cliente = Cliente.buscaCliente();
        if (this.cliente == null) {
            System.out.println("Nenhum cliente selecionado. Operação cancelada.");
            return;
        }

        this.sessao = Sessao.buscaSessao();
        if (this.sessao == null) {
            System.out.println("Nenhuma sessão selecionada. Operação cancelada.");
            return;
        }

        System.out.println("Insira o valor integral do ingresso.");
        this.valor = sc.nextDouble();
        
        this.valor = this.valor/2;
	}
}
