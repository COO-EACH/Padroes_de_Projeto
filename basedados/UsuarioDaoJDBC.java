package basedados;

import java.sql.SQLException;
import java.util.LinkedList;

import beans.Usuario;
import utilidades.Log;

public class UsuarioDaoJDBC extends ConectorJDBC implements UsuarioDAO { 
	//CRIOU CONSTRUTOR AUTOMATICO
	protected UsuarioDaoJDBC(DB db) throws BaseDadosException {
		super(db);
		// TODO Auto-generated constructor stub
	}

	private static final String USER = "root";
	
	protected String getUser() {
		return USER;
	}
	
	public void insereUsuario(Usuario usuario) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Usuario (nome, codigo) values (?, ?)");

		try {
			pstmt.setString(1, usuario.getNome());
			pstmt.setInt(2, usuario.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}
	
	public Usuario buscaUsuario(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select nome from Usuario where codigo=" + codigo);
		Usuario usuario = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String nome = rs.getString(1);
				usuario = new Usuario(nome, codigo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return usuario;
	}
	
	public LinkedList<Usuario> listaUsuarios() throws BaseDadosException {
		LinkedList<Usuario> usuarios = new LinkedList<Usuario>();
		abreConexao();
		preparaComandoSQL("select codigo, nome from Usuario");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				Usuario usuario = new Usuario(nome, codigo);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();
		return usuarios;
	}

	@Override
	protected String getDbHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDbName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}
