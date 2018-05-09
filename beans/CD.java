package beans;

public class CD extends Item {
	private String album;
	private String artista;

	public CD(String album, String artista, int qtdTotalExemplares, int codigo) {
		super(qtdTotalExemplares, qtdTotalExemplares, 0, codigo);
		this.album = album;
		this.artista = artista;
	}

	public CD(String album, String artista, Item item) {
		this(album, artista, item.getQtdTotalExemplares(), item.getCodigo());
		setQtdExemplaresDisponiveis(item.getQtdExemplaresDisponiveis());
		setQtdExemplaresEmprestados(item.getQtdExemplaresEmprestados());
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}
}
