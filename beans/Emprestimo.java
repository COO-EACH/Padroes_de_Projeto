package beans;
public class Emprestimo {

	private Item item;
	private Usuario usuario;
	private boolean finalizado;
	private int codigo;
	
	public Emprestimo(Item item, Usuario usuario, boolean finalizado) {
		this.item = item;
		this.usuario = usuario;
		this.finalizado = finalizado;
	}

	public Emprestimo(Item item, Usuario usuario) {
		this.item = item;
		this.usuario = usuario;
	}

	public Emprestimo(Item item, Usuario usuario, int codigo) {
		this.item = item;
		this.usuario = usuario;
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
