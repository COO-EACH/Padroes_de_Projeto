package basedados;

public class ConectorDaoJDBC extends ConectorJDBC{
	
	public static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String HOST = "localhost";
	private static final String DB_NAME = "coo2018";
	private boolean jaCriouBD;
	
	protected ConectorDaoJDBC(DB db) throws BaseDadosException {
		super(DB.MYSQL);
		// TODO Auto-generated constructor stub
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
}
