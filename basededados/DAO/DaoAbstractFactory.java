package basededados.DAO;

public interface DaoAbstractFactory {
	public UsuariaDAO createEmprestimoDao(UsuarioDAO usuario LivroDAO livro) throws BaseDadosException;
	public CdDAO createCdDao(ItemDao item) throws BaseDadosException;
	public ItemDAO createItemDao() throws BaseDadosException;
	public LivroDAO createLivroDao() throws BaseDadosException;
}
