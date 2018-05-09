package basedados;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import beans.CD;
import beans.Emprestimo;
import beans.Item;
import beans.Livro;
import beans.Usuario;
import utilidades.Log;

public class EmprestimoDaoJDBC  extends ConectorJDBC implements EmprestimoDAO {
	
	protected EmprestimoDaoJDBC(DB db) throws BaseDadosException {
		super(db);
		// TODO Auto-generated constructor stub
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


	public Emprestimo buscaEmprestimo(int codigoEmprestimo)
			throws BaseDadosException {
		return null;
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

	@Override
	public void alteraEmprestimo(Emprestimo emprestimoAlterado) throws BaseDadosException {
		// TODO Auto-generated method stub
		
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
	protected String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}
