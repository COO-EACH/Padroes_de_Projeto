package basedados;

import java.util.LinkedList;

import beans.Emprestimo;
import beans.Usuario;

public interface EmprestimoDAO {
	
	public void insereEmprestimo(Emprestimo emprestimo) throws BaseDadosException;

	public void alteraEmprestimo(Emprestimo emprestimoAlterado) throws BaseDadosException;
	
	public Emprestimo buscaEmprestimo(int codigoEmprestimo) throws BaseDadosException;
	
	public LinkedList<Emprestimo> listaEmprestimosEmAbertoDoUsuario(
			Usuario usuario) throws BaseDadosException;
	
}
