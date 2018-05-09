package basedados;
import java.util.LinkedList;
import java.util.List;

import beans.CD;
import beans.Emprestimo;
import beans.Livro;
import beans.Usuario;

public interface GerenciadorBaseDados {

	void insereUsuario(Usuario usuario) throws BaseDadosException;

	public void insereLivro(Livro livro) throws BaseDadosException;

	public void insereCD(CD cd) throws BaseDadosException;

	public void insereEmprestimo(Emprestimo emprestimo) throws BaseDadosException;

	public void alteraEmprestimo(Emprestimo emprestimoAlterado) throws BaseDadosException;

	public CD buscaCD(int codigo) throws BaseDadosException;

	public Livro buscaLivro(int codigo) throws BaseDadosException;

	public Usuario buscaUsuario(int codigoUsuario) throws BaseDadosException;

	public Emprestimo buscaEmprestimo(int codigoEmprestimo) throws BaseDadosException;

	public List<Usuario> listaUsuarios() throws BaseDadosException;

	public List<Livro> listaLivros() throws BaseDadosException;

	public List<CD> listaCD() throws BaseDadosException;
	
	public LinkedList<Emprestimo> listaEmprestimosEmAbertoDoUsuario(
			Usuario usuario) throws BaseDadosException;

}
