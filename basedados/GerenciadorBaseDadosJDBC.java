package basedados;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import utilidades.Log;
import beans.CD;
import beans.Emprestimo;
import beans.Item;
import beans.Livro;
import beans.Usuario;

public class GerenciadorBaseDadosJDBC extends ConectorJDBC implements
		GerenciadorBaseDados {

	private static final String PASSWORD = "";
	private static final String USER = "root";
	private static final String HOST = "localhost";
	private static final String DB_NAME = "coo2018";
	private boolean jaCriouBD;

	public GerenciadorBaseDadosJDBC() throws BaseDadosException {
		super(DB.MYSQL);

		try {
			criaBancoDeDados();
			criaTabelaItem();
			criaTabelaLivro();
			criaTabelaCD();
			criaTabelaUsuario();
			criaTabelaEmprestimo();
			populaTabelas();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao tentar criar o banco de dados.");
		}
	}

	protected String getUser() {
		return USER;
	}

	@Override
	protected String getPassword() {
		return PASSWORD;
	}

	@Override
	protected String getDbHost() {
		return HOST;
	}

	@Override
	protected String getDbName() {
		return jaCriouBD ? DB_NAME : "";
	}

	private void insereItem(Item item) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Item (qtdTotalExemplares, qtdExemplaresDisponiveis, qtdExemplaresEmprestados, codigo) values (?, ?, ?, ?)");

		try {
			pstmt.setInt(1, item.getQtdTotalExemplares());
			pstmt.setInt(2, item.getQtdExemplaresDisponiveis());
			pstmt.setInt(3, item.getQtdExemplaresEmprestados());
			pstmt.setInt(4, item.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
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

	public void insereLivro(Livro livro) throws BaseDadosException {
		insereItem(livro);
		abreConexao();
		preparaComandoSQL("insert into Livro (autores, titulo, codigo) values (?, ?, ?)");

		try {
			pstmt.setString(1, livro.getAutores());
			pstmt.setString(2, livro.getTitulo());
			pstmt.setInt(3, livro.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public void insereCD(CD cd) throws BaseDadosException {
		insereItem(cd);
		abreConexao();
		preparaComandoSQL("insert into CD (artista, album, codigo) values (?, ?, ?)");

		try {
			pstmt.setString(1, cd.getArtista());
			pstmt.setString(2, cd.getAlbum());
			pstmt.setInt(3, cd.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public void insereEmprestimo(Emprestimo emprestimo)
			throws BaseDadosException {
		abreConexao();

		try {
			preparaComandoSQL("insert into Emprestimo (codigoItem, codigoUsuario, finalizado) values (?, ?, ?)");
			pstmt.setInt(1, emprestimo.getItem().getCodigo());
			pstmt.setInt(2, emprestimo.getUsuario().getCodigo());
			pstmt.setBoolean(3, false);
			pstmt.execute();
			preparaComandoSQL("update Item set qtdExemplaresDisponiveis = qtdExemplaresDisponiveis - 1, qtdExemplaresEmprestados = qtdExemplaresEmprestados + 1 where codigo = ?");
			pstmt.setInt(1, emprestimo.getItem().getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public Item buscaItem(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select qtdTotalExemplares, qtdExemplaresDisponiveis, qtdExemplaresEmprestados from Item where codigo="
				+ codigo);
		Item item = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int qtdTotalExemplares = rs.getInt(1);
				int qtdExemplaresDisponiveis = rs.getInt(2);
				int qtdExemplaresEmprestados = rs.getInt(3);
				item = new Item(qtdTotalExemplares, qtdExemplaresDisponiveis,
						qtdExemplaresEmprestados, codigo);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return item;
	}

	public CD buscaCD(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select artista, album from CD where codigo="
				+ codigo);
		CD cd = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String artista = rs.getString(1);
				String album = rs.getString(2);
				cd = new CD(album, artista, buscaItem(codigo));
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return cd;
	}

	public Livro buscaLivro(int codigo) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("select autores, titulo from Livro where codigo="
				+ codigo);
		Livro livro = null;

		try {
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String autores = rs.getString(1);
				String titulo = rs.getString(2);
				livro = new Livro(autores, titulo, buscaItem(codigo));
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return livro;
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

	private LinkedList<Item> listaItens() throws BaseDadosException {
		LinkedList<Item> itens = new LinkedList<Item>();
		abreConexao();
		preparaComandoSQL("select codigo, " + "qtdExemplaresDisponiveis, "
				+ "qtdExemplaresEmprestados, " + "qtdTotalExemplares "
				+ "from Item");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int qtdExemplaresDisponiveis = rs.getInt(2);
				int qtdExemplaresEmprestados = rs.getInt(3);
				int qtdTotalExemplares = rs.getInt(4);
				Item item = new Item(qtdTotalExemplares,
						qtdExemplaresDisponiveis, qtdExemplaresEmprestados,
						codigo);
				itens.add(item);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return itens;
	}

	public LinkedList<Livro> listaLivros() throws BaseDadosException {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		LinkedList<Item> itens = listaItens();
		abreConexao();
		preparaComandoSQL("select autores, titulo from Livro where codigo = ?");

		for (Item item : itens) {
			try {
				pstmt.setInt(1, item.getCodigo());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					String autores = rs.getString(1);
					String titulo = rs.getString(2);
					Livro livro = new Livro(autores, titulo, item);
					livros.add(livro);
				}
			} catch (SQLException e) {
				Log.gravaLog(e);
				throw new BaseDadosException(
						"Problemas ao ler o resultado da consulta.");
			}

		}

		fechaConexao();
		return livros;
	}

	@Override
	public List<CD> listaCD() throws BaseDadosException {
		LinkedList<CD> cds = new LinkedList<CD>();
		LinkedList<Item> itens = listaItens();
		abreConexao();
		preparaComandoSQL("select artista, album from CD where codigo = ?");

		for (Item item : itens) {
			try {
				pstmt.setInt(1, item.getCodigo());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					String artista = rs.getString(1);
					String album = rs.getString(2);
					CD cd = new CD(album, artista, item);
					cds.add(cd);
				}
			} catch (SQLException e) {
				Log.gravaLog(e);
				throw new BaseDadosException(
						"Problemas ao ler o resultado da consulta.");
			}
		}

		fechaConexao();
		return cds;
	}

	@Override
	public LinkedList<Emprestimo> listaEmprestimosEmAbertoDoUsuario(
			Usuario usuario) throws BaseDadosException {
		LinkedList<Emprestimo> emprestimos = new LinkedList<Emprestimo>();
		abreConexao();

		try {
			preparaComandoSQL("select codigoEmprestimo, codigoItem from Emprestimo where finalizado=? and codigoUsuario=?");
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, usuario.getCodigo());
			rs = pstmt.executeQuery();
			ArrayList<Integer> codigoItens = new ArrayList<Integer>();

			while (rs.next()) {
				int codigoEmprestimo = rs.getInt(1);
				int codigoItem = rs.getInt(2);
				Emprestimo emprestimo = new Emprestimo(null, usuario,
						codigoEmprestimo);
				emprestimos.add(emprestimo);
				codigoItens.add(codigoItem);
			}

			int index = 0;

			for (Emprestimo emprestimo : emprestimos) {
				CD cd = buscaCD(codigoItens.get(index));
				if (cd != null) {
					emprestimo.setItem(cd);
				} else {
					Livro livro = buscaLivro(codigoItens.get(index));
					emprestimo.setItem(livro);
				}
				index++;
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}
		fechaConexao();
		return emprestimos;
	}

	public void alteraEmprestimo(Emprestimo emprestimoAlterado)
			throws BaseDadosException {
	}

	public Emprestimo buscaEmprestimo(int codigoEmprestimo)
			throws BaseDadosException {
		return null;
	}

	private void criaTabelaEmprestimo() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Emprestimo ("
				+ "codigoEmprestimo  int unsigned not null auto_increment primary key,"
				+ "codigoItem int unsigned not null,"
				+ "codigoUsuario int unsigned not null,"
				+ "finalizado boolean not null,"
				+ "constraint fk_Emprestimo_Item FOREIGN KEY (codigoItem) REFERENCES Item (codigo),"
				+ "constraint fk_Emprestimo_Usuario FOREIGN KEY (codigoUsuario) REFERENCES Usuario (codigo))");
		pstmt.execute();
		fechaConexao();
	}

	private void criaTabelaUsuario() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Usuario ("
				+ "codigo int unsigned not null primary key,"
				+ "nome varchar(50) not null)");
		pstmt.execute();
		fechaConexao();
	}

	private void criaTabelaCD() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists CD ("
				+ "artista varchar(100) not null,"
				+ "album varchar(50) not null,"
				+ "codigo int unsigned not null,"
				+ "constraint fk_CD_Item FOREIGN KEY (codigo) REFERENCES Item (codigo))");
		pstmt.execute();
		fechaConexao();
	}

	private void criaTabelaLivro() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Livro ("
				+ "autores varchar(100) not null,"
				+ "titulo varchar(50) not null,"
				+ "codigo int unsigned not null,"
				+ "constraint fk_Livro_Item FOREIGN KEY (codigo) REFERENCES Item (codigo))");
		pstmt.execute();
		fechaConexao();
	}

	private void criaTabelaItem() throws SQLException, BaseDadosException {
		abreConexao();
		preparaComandoSQL("create table if not exists Item ("
				+ "codigo  int unsigned not null primary key,"
				+ "qtdTotalExemplares int unsigned not null,"
				+ "qtdExemplaresDisponiveis int unsigned not null,"
				+ "qtdExemplaresEmprestados int unsigned not null)");
		pstmt.execute();
		fechaConexao();
	}

	private void criaBancoDeDados() throws SQLException, BaseDadosException {
		abreConexao();
		jaCriouBD = true;
		preparaComandoSQL("create database if not exists " + getDbName());
		pstmt.execute();
		fechaConexao();
	}

	private void populaTabelas() throws BaseDadosException {
		if(buscaUsuario(1) != null) {
			return;
		}

		Usuario u1, u2;
		Livro l1, l2;
		CD cd1, cd2;
		
		insereUsuario(u1 = new Usuario("Alexandre", 1));
		insereUsuario(u2 = new Usuario("Fernando", 2));
		insereUsuario(new Usuario("Adriano", 3));
		insereLivro(l1 = new Livro("Allan Kardec", "O Livro dos Espíritos", 5,
				5, 0, 1));
		insereLivro(l2 = new Livro("Allan Kardec", "O Livro dos Médiuns", 5, 5,
				0, 2));
		insereLivro(new Livro("André Luiz", "Nosso Lar", 5, 5, 0, 3));
		insereCD(cd1 = new CD("Em Canto", "Elizabete Lacerda", 5, 4));
		insereCD(cd2 = new CD("Tic Tic Tati", "Fortuna", 5, 5));
		insereCD(new CD("A Casa é Sua", "Arnaldo Antunes", 5, 6));
		insereEmprestimo(new Emprestimo(l1, u1));
		insereEmprestimo(new Emprestimo(cd1, u1));
		insereEmprestimo(new Emprestimo(l2, u2));
		insereEmprestimo(new Emprestimo(cd2, u2));
	}

}
