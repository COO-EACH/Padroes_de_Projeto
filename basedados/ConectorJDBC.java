package basedados;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Log;

public abstract class ConectorJDBC {

	protected enum DB {
		MYSQL, POSTGRES;
	}

	// MySQL
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static final String MYSQL_URL = "jdbc:mysql";

	// Postgres
	private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
	private static final String POSTGRES_URL = "jdbc:postgresql";

	private DB db;
	protected Connection con;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	
	protected abstract String getDbHost();

	protected abstract String getDbName();

	protected abstract String getUser();

	protected abstract String getPassword();

	protected ConectorJDBC(DB db) throws BaseDadosException {
		this.db = db;
		String dbDriver = this.db == DB.MYSQL ? MYSQL_DRIVER : POSTGRES_DRIVER;

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas no acesso ao banco de dados.");
		}
	}

	protected void abreConexao() throws BaseDadosException {
		String dbURL = this.db == DB.MYSQL ? MYSQL_URL : POSTGRES_URL;
		try {
			con = DriverManager.getConnection(dbURL + "://" + getDbHost() + "/"
					+ getDbName(), getUser(), getPassword());
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas no acesso ao banco de dados.");
		}
	}

	protected void fechaConexao() throws BaseDadosException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}

			if (con != null) {
				con.close();
				con = null;
			}

		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas no acesso ao banco de dados.");
		}

	}

	protected void preparaComandoSQL(String sql) throws BaseDadosException {
		try {
			pstmt = con.prepareStatement(sql);
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas no acesso ao banco de dados.");
		}
	}
}
