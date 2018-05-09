package beans;
public class Item {
	private int qtdTotalExemplares;
	private int qtdExemplaresDisponiveis;
	private int qtdExemplaresEmprestados;
	private int codigo;

	public Item(int qtdTotalExemplares, int qtdExemplaresDisponiveis,
			int qtdExemplaresEmprestados, int codigo) {
		this.qtdTotalExemplares = qtdTotalExemplares;
		this.qtdExemplaresDisponiveis = qtdExemplaresDisponiveis;
		this.qtdExemplaresEmprestados = qtdExemplaresEmprestados;
		this.codigo = codigo;
	}

	public int getQtdTotalExemplares() {
		return qtdTotalExemplares;
	}

	public void setQtdTotalExemplares(int qtdTotalExemplares) {
		this.qtdTotalExemplares = qtdTotalExemplares;
	}

	public int getQtdExemplaresDisponiveis() {
		return qtdExemplaresDisponiveis;
	}

	public void setQtdExemplaresDisponiveis(int qtdExemplaresDisponiveis) {
		this.qtdExemplaresDisponiveis = qtdExemplaresDisponiveis;
	}

	public int getQtdExemplaresEmprestados() {
		return qtdExemplaresEmprestados;
	}

	public void setQtdExemplaresEmprestados(int qtdExemplaresEmprestados) {
		this.qtdExemplaresEmprestados = qtdExemplaresEmprestados;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
