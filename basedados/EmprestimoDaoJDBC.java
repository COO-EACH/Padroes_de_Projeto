package basedados;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import beans.CD;
import beans.Emprestimo;
import beans.Livro;
import beans.Usuario;
import utilidades.Log;

public class EmprestimoDaoJDBC  extends ConectorJDBC implements EmprestimoDAO {
	
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
}
