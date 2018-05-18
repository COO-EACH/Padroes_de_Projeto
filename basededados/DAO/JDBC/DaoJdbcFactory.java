package basededados.DAO.JDBC;

import basededados.DAO.DaoAbstractFactory;

public class DaoJdbcFactory implements DaoAbstractFactory {
	
	public UsuarioDao createUsuarioDao() throws BaseDadosException{
		UsuarioDao usuario = new UsuarioDaoJdbc();
		return usuario;
	}
	
	public EmprestimoDao createEmprestimoDao(UsuarioDao usuario, CdDao cdDao, LivroDao livroDao) throws BaseDadosException{
		return new EmprestimoDaoJdbc(usuarioDao, cdDao, livroDao,);
	}
	
	public CdDao createCdDao(ItemDao itemDao) throws BaseDados{
		return new CdDaoJdbc(cdDao);
	}
	
	public ItemDao createItemDao(ItemDao itemDao) throws {
		return new ItemDaoJdbc(itemDao);
	}
	
	public LivroDao createLivroDao(ItemDao itemDao) throws Base{
		return new LivroDaoJdbc();
	}
}
