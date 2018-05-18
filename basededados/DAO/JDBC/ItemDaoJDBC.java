package basededados.DAO.JDBC;

import java.sql.SQLException;

import basedados.BaseDadosException;
import basedados.ConectorJDBC;
import basedados.ConectorJDBC.DB;
import basededados.DAO.ItemDAO;
import beans.Item;
import utilidades.Log;

public class ItemDaoJDBC extends ConectorDaoJDBC implements ItemDAO {

	protected ItemDaoJDBC(DB db) throws BaseDadosException {
		super(db);
		// TODO Auto-generated constructor stub
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

	
}
