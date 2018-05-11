package basededados.DAO;

import java.util.LinkedList;

import basedados.BaseDadosException;
import beans.Emprestimo;
import beans.Usuario;

public interface EmprestimoDAO {
	
	public void insere(Emprestimo emprestimo) throws BaseDadosException;

	public void altera(Emprestimo emprestimoAlterado) throws BaseDadosException;
	
	public Emprestimo busca(int codigoEmprestimo) throws BaseDadosException;
	
	public LinkedList<Emprestimo> listaEmprestimosEmAbertoDoUsuario(
			Usuario usuario) throws BaseDadosException;
	
}
