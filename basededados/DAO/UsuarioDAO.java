package basededados.DAO;

import java.util.List;

import basedados.BaseDadosException;
import beans.Usuario;

public interface UsuarioDAO {
	
	void insere(Usuario usuario) throws BaseDadosException;
	
	public Usuario busca(int codigoUsuario) throws BaseDadosException;
	
	public List<Usuario> lista() throws BaseDadosException;
	
	/* EXEMPLO DE AULA
	 * 
	 * public abstract void insere(Usuario u);
	 * 
	 * public abstract Usuario busca(int id);
	 * 
	 * */
	
}
