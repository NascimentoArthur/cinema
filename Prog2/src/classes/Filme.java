package classes;

public class Filme {
	String nome;
	String sinopse;
	Categoria categoria1;
	Categoria categoria2;
	int classificacao;
	
	public Filme() {
		System.out.println("Nome do filme: ");
		setNome(c.sc.nextLine());
		System.out.println("Sinopse: ");
		setSinopse(c.sc.nextLine());
		categoria1.setCategoria("1");
		categoria2.setCategoria("2");
	}
	
	
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public Categoria getCategoria1() {
		return categoria1;
	}

	public void setCategoria1(Categoria categoria1) {
		this.categoria1 = categoria1;
	}

	public int getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}
	
	Config c = new Config();
}
