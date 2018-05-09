package basedados;

import java.util.List;

import beans.Usuario;

public interface UsuarioDAO {
	
	void insereUsuario(Usuario usuario) throws BaseDadosException;
	
	public Usuario buscaUsuario(int codigoUsuario) throws BaseDadosException;
	
	public List<Usuario> listaUsuarios() throws BaseDadosException;
	
}
