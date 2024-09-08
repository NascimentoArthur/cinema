package classes;


public class IngressoMeia extends Ingresso{
	private String tipoIngressoStr = "MEIA_ENTRADA";
	
	public IngressoMeia() {
        this.config = new Config();
       
        this.valor = this.valor/2;
        
        if (this.valor > 0) {
        	System.out.println("Valor Meia-Entrada: R$" + this.valor);
        }
        
    }
}
