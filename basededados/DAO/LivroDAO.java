package basededados.DAO;

import java.util.List;

import basedados.BaseDadosException;
import beans.Livro;

public interface LivroDAO {
	
	public void insereLivro(Livro livro) throws BaseDadosException;
	
	public Livro buscaLivro(int codigo) throws BaseDadosException;

	public List<Livro> listaLivros() throws BaseDadosException;
}
