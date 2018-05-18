package negocio;

import java.util.LinkedList;
import java.util.List;

import utilidades.Log;
import basedados.BaseDadosException;
import basedados.GerenciadorBaseDados;
import basedados.GerenciadorBaseDadosJDBC;
import basedados.dao.ItemDao;
import basedados.dao.jdbc.CdDaoJdbc;
import basedados.dao.jdbc.EmprestimoDaoJdbc;
import basedados.dao.jdbc.ItemDaoJdbc;
import basedados.dao.jdbc.LivroDaoJdbc;
import basedados.dao.jdbc.UsuarioDaoJdbc;
import basededados.DAO.DaoAbstractFactory;
import beans.CD;
import beans.Emprestimo;
import beans.Item;
import beans.Livro;
import beans.Usuario;

public class GerenciadorRegrasNegocio {

	private GerenciadorBaseDados gerenciadorBaseDados;

	public enum BaseDados {
		RAM, JDBC;
	}

	private BaseDados bd;

	public GerenciadorRegrasNegocio(BaseDados bd) throws NegocioException {
		this.bd = bd;
		if (this.bd == BaseDados.JDBC) {
			try {
				DaoAbstractFactory daoFactory = new DaoJdbcFactory();
				//ou:
				//DaoAbstractFactory daoFactory =(DaoAbstractFactory) Class.forName("basedados.dao.jdbc.DaoJdbcFactory");
				usuarioDao = daoFactory.createUsuario();
				ItemDao itemDao = daoFactory.createItemDao();
				cdDao = daoFactory.createCdDao();
				livroDao = daoFactory.createLivroDao();
				emprestimoDao = daoFactory.createEmprestimoDao(usuarioDao, cdDao, livroDao);
				
				//instanciar dos DAOs
				usuarioDao = new UsuarioDaoJdbc();
				ItemDao itemDao = new ItemDaoJdbc();
				cdDao = new CdDaoJdbc(itemDao);
				livroDao = new LivroDaoJdbc(itemDao);
				emprestimoDao = new EmprestimoDaoJdbc(usuarioDao, cdDao,
						livroDao);
				
				gerenciadorBaseDados = new GerenciadorBaseDadosJDBC();
			} catch (BaseDadosException e) {
				throw new NegocioException(e);
			}
		} else {
			throw new NegocioException("Problemas no acesso à base de dados");
		}
	}

	public void cadastraUsuario(String nome, int codigo)
			throws NegocioException {
		Usuario usuario = new Usuario(nome, codigo);
		try {
			//usuarioDao.insere(usuario);
			gerenciadorBaseDados.insereUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void cadastraLivro(String titulo, String autores, int codigo,
			int qtdExemplares) throws NegocioException {
		Livro livro = new Livro(autores, titulo, qtdExemplares, qtdExemplares,
				0, codigo);
		try {
			gerenciadorBaseDados.insereLivro(livro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void cadastraCD(String album, String artista, int codigo,
			int qtdExemplares) throws NegocioException {
		CD cd = new CD(album, artista, qtdExemplares, codigo);
		try {
			gerenciadorBaseDados.insereCD(cd);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void devolveLivro(int codigoEmprestimo) throws NegocioException {
		try {
			Emprestimo emprestimo = gerenciadorBaseDados
					.buscaEmprestimo(codigoEmprestimo);
			emprestimo.setFinalizado(true);
			gerenciadorBaseDados.alteraEmprestimo(emprestimo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Usuario> listaUsuarios() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaUsuarios();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Livro> listaLivros() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaLivros();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<CD> listaCDs() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaCD();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Emprestimo> listaEmprestimosEmAbertoDoUsuario(Usuario usuario)
			throws NegocioException {
		return null;
	}

	public List<Livro> listaLivrosDisponiveis() throws NegocioException {
		List<Livro> livros = listaLivros();
		List<Livro> livrosDisponiveis = new LinkedList<Livro>();

		for (Livro livro : livros) {
			if (livro.getQtdExemplaresDisponiveis() > 0) {
				livrosDisponiveis.add(livro);
			}
		}

		return livrosDisponiveis;
	}

	public void emprestaLivro(int codigoLivro, int codigoUsuario)
			throws NegocioException {
		try {
			Livro livro = gerenciadorBaseDados.buscaLivro(codigoLivro);
			Usuario usuario = gerenciadorBaseDados.buscaUsuario(codigoUsuario);
			emprestaItem(livro, usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void emprestaCD(int codigoCD, int codigoUsuario)
			throws NegocioException {
	}

	public void emprestaItem(Item item, Usuario usuario)
			throws NegocioException {
		int maxItens = 6;
		int maxLivros = 3;
		int maxCDs = 5;

		try {
			LinkedList<Emprestimo> listaEmprestimos = gerenciadorBaseDados
					.listaEmprestimosEmAbertoDoUsuario(usuario);
			int nEmprestimos = listaEmprestimos.size();
			int nLivros = 0;
			int nCDs = 0;
			boolean jahEmprestouOutroExemplarDoMesmoItem = false;

			for (Emprestimo emprestimo : listaEmprestimos) {
				if (emprestimo.getItem().getCodigo() == item.getCodigo()) {
					jahEmprestouOutroExemplarDoMesmoItem = true;
				}

				if (emprestimo.getItem() instanceof Livro) {
					nLivros++;
				} else {
					nCDs++;
				}
			}

			// regra de negócio 1
			if (nEmprestimos >= maxItens || nLivros >= maxLivros
					|| nCDs >= maxCDs) {
				throw new NegocioException("Usuário já possui " + nLivros
						+ " livros e " + nCDs + " CDs emprestados!");
			}

			// regra de negócio 2
			if (jahEmprestouOutroExemplarDoMesmoItem) {
				throw new NegocioException(
						"Um exemplar deste item já encontra-se emprestado ao usuário!");
			}

			Emprestimo emprestimo = new Emprestimo(item, usuario);
			gerenciadorBaseDados.insereEmprestimo(emprestimo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}
}
