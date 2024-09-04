package classes;

import java.util.List;

public class Catalogo {
    List<Filme> filme;
    Config c = new Config();

    public Catalogo() {
        int view = 1;
        filme = Filme.buscarFilmeEmCartaz(view);
    }

    @Override
    public String toString() {
        // StringBuilder para construir a string com todos os filmes
        StringBuilder sb = new StringBuilder();
        sb.append("Catalogo de Filmes:\n");
        
        // Itera sobre cada filme na lista e adiciona ao StringBuilder
        for (Filme f : filme) {
            sb.append("=================\n");
            sb.append("ID: ").append(f.getId()).append("\n");
            sb.append("Nome: ").append(f.getNome()).append("\n");
            sb.append("Sinopse: ").append(f.getSinopse()).append("\n");
            sb.append("Categoria: ").append(f.getCategoria().getNome()).append("\n");
            sb.append("Classificação: ").append(f.getClassificacao()).append("\n");
            sb.append("Em Cartaz: ").append(f.getEmCartaz() == 1 ? "Sim" : "Não").append("\n");
            sb.append("=================\n");
        }

        return sb.toString();
    }

    public List<Filme> getFilme() {
        return filme;
    }

    public void setFilme(List<Filme> filme) {
        this.filme = filme;
    }

    public Config getC() {
        return c;
    }

    public void setC(Config c) {
        this.c = c;
    }
}
