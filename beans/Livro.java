package beans;
public class Livro extends Item {
	private String autores;
	private String titulo;

	public Livro(String autores, String titulo, int qtdTotalExemplares,
			int qtdExemplaresDisponiveis, int qtdExemplaresEmprestados,
			int codigo) {
		super(qtdTotalExemplares, qtdExemplaresDisponiveis,
				qtdExemplaresEmprestados, codigo);
		this.autores = autores;
		this.titulo = titulo;
	}

	public Livro(String autores, String titulo, Item item) {
		this(autores, titulo, item.getQtdTotalExemplares(), item
				.getQtdExemplaresDisponiveis(), item
				.getQtdExemplaresEmprestados(), item.getCodigo());
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
