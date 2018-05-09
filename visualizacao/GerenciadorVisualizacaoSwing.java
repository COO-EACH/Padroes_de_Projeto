package visualizacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import negocio.GerenciadorRegrasNegocio;
import negocio.NegocioException;
import beans.CD;
import beans.Livro;
import beans.Usuario;

public class GerenciadorVisualizacaoSwing extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<JPanel> paineis = new LinkedList<JPanel>();
	private GerenciadorRegrasNegocio gerenciadorRegrasNegocio;
	private Validador validador = new Validador();

	// painel fundo
	private JLabel lblBibliotecaCOO2018 = new JLabel();
	private Container painelFundo;

	// painel emprestar livro
	private JPanel painelEmprestarLivro = new JPanel();
	private JComboBox<String> comboLivros = new JComboBox<String>();
	private JComboBox<String> comboUsuarios = new JComboBox<String>();
	private HashMap<String, Integer> comboLivrosMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> comboUsuariosMap = new HashMap<String, Integer>();

	// painel emprestar CD
	private JPanel painelEmprestarCD = new JPanel();

	// painel devolver livro
	private JPanel painelDevolverLivro = new JPanel();

	// painel devolver CD
	private JPanel painelDevolverCD = new JPanel();

	// painel cadastrar usuário
	private JPanel painelCadastrarUsuario = new JPanel();
	private JTextField fieldNomeUsuario = new JTextField();
	private JTextField fieldCodigoUsuario = new JTextField();

	// painel cadastra CD
	private JPanel painelCadastrarCD = new JPanel();
	private JTextField fieldAlbumCD = new JTextField();
	private JTextField fieldArtistaCD = new JTextField();
	private JTextField fieldCodigoCD = new JTextField();
	private JTextField fieldQtdDeExemplaresTotaisCD = new JTextField();

	// painel cadastrar livro
	private JPanel painelCadastrarLivro = new JPanel();
	private JTextField fieldTituloLivro = new JTextField();
	private JTextField fieldCodigoLivro = new JTextField();
	private JTextField fieldAutoresLivro = new JTextField();
	private JTextField fieldQtdDeExemplaresTotaisLivro = new JTextField();

	// painel lista livros
	private JPanel painelListaLivros = new JPanel();
	private JTable tabelaLivros = new JTable(new DefaultTableModel(
			new Object[] { "Código", "Autores", "Título", "Disponíveis",
					"Emprestados" }, 0));

	// painel lista usuários
	private JPanel painelListaUsuarios = new JPanel();
	private JTable tabelaUsuarios = new JTable(new DefaultTableModel(
			new Object[] { "Código", "Nome" }, 0));

	// painel lista cd
	private JPanel painelListaCD = new JPanel();
	private JTable tabelaCD = new JTable(new DefaultTableModel(new Object[] {
			"Código", "Album", "Artista", "Disponíveis", "Emprestados" }, 0));

	public GerenciadorVisualizacaoSwing() {
		try {
			gerenciadorRegrasNegocio = new GerenciadorRegrasNegocio(
					GerenciadorRegrasNegocio.BaseDados.JDBC);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n"
					+ "A aplicação será encerrada.");
			System.exit(0);
		}

		inicializaComponentes();
	}

	private void inicializaComponentes() {

		// frame
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 500);
		setResizable(false);
		setTitle("Biblioteca COO 2018");

		// --- painel de fundo ---
		painelFundo = getContentPane();
		painelFundo.setBounds(0, 0, 800, 500);
		painelFundo.setLayout(null);
		painelFundo.setBackground(new Color(234, 255, 255));

		lblBibliotecaCOO2018.setFont(new Font("Luminari", 3, 36));
		lblBibliotecaCOO2018.setForeground(new Color(0, 153, 153));
		lblBibliotecaCOO2018.setText(" Biblioteca COO 2018 ");
		adicionaNoCentroDaLinha(painelFundo, lblBibliotecaCOO2018, 20);

		criaMenu();
		// ------------------------

		// --- painel emprestar livro ---
		adicionaPainel(painelEmprestarLivro, "Emprestar Livro", new Color(154,
				95, 35));
		//montaComboPainelEmprestaLivro();
		adicionaCamposComRotulos(painelEmprestarLivro, new String[] {
				"Livro: ", "Usuário: " }, new JComboBox[] { comboLivros,
				comboUsuarios });
		adicionaBotao(painelEmprestarLivro, "Emprestar", 3,
				"emprestarLivroActionPerformed");
		// ------------------------------

		// --- painel emprestar CD ---
		adicionaPainel(painelEmprestarCD, "Emprestar CD",
				new Color(154, 14, 35));
		// ---------------------------

		// --- painel devolver livro ---
		adicionaPainel(painelDevolverLivro, "Devolver Livro", new Color(154,
				95, 200));
		// -----------------------------

		// --- painel devolver CD ---
		adicionaPainel(painelDevolverCD, "Devolver CD", new Color(12, 95, 35));
		// --------------------------

		// --- painel cadastrar usuário ---
		adicionaPainel(painelCadastrarUsuario, "Cadastrar Usuário", new Color(
				154, 255, 255));
		adicionaCamposComRotulos(painelCadastrarUsuario, new String[] {
				"Nome: ", "Código: " }, new JTextField[] { fieldNomeUsuario,
				fieldCodigoUsuario });
		adicionaBotao(painelCadastrarUsuario, "Cadastrar", 3,
				"cadastrarUsuarioActionPerformed");
		// --------------------------------

		// --- painel cadastrar livro ---
		adicionaPainel(painelCadastrarLivro, "Cadastrar Livro", new Color(255,
				154, 255));
		adicionaCamposComRotulos(painelCadastrarLivro, new String[] {
				"Título: ", "Autores: ", "Código: ", "Qtd de Exemplares: " },
				new JTextField[] { fieldTituloLivro, fieldAutoresLivro,
						fieldCodigoLivro, fieldQtdDeExemplaresTotaisLivro });
		adicionaBotao(painelCadastrarLivro, "Cadastrar", 5,
				"cadastrarLivroActionPerformed");
		// ------------------------------

		// --- painel cadastrar CD ---
		adicionaPainel(painelCadastrarCD, "Cadastrar CD",
				new Color(255, 0, 255));
		adicionaCamposComRotulos(painelCadastrarCD, new String[] { "Album: ",
				"Artistas: ", "Código: ", "Qtd de Exemplares: " },
				new JTextField[] { fieldAlbumCD, fieldArtistaCD, fieldCodigoCD,
						fieldQtdDeExemplaresTotaisCD });
		adicionaBotao(painelCadastrarCD, "Cadastrar", 5,
				"cadastrarCDActionPerformed");
		// ------------------------------

		// --- painel lista livros ---
		adicionaPainel(painelListaLivros, "Livros Cadastrados", new Color(255,
				255, 154));
		adicionaTabela(painelListaLivros, tabelaLivros);
		// ---------------------------

		// --- painel lista usuarios ---
		adicionaPainel(painelListaUsuarios, "Usuários Cadastrados", new Color(
				255, 154, 154));
		adicionaTabela(painelListaUsuarios, tabelaUsuarios);
		// ---------------------------

		// --- painel lista usuarios ---
		adicionaPainel(painelListaCD, "CDs Cadastrados", new Color(255, 154,
				154));
		adicionaTabela(painelListaCD, tabelaCD);
		// ---------------------------

		mostraPainel(null);
		setVisible(true);
	}

	private void montaComboPainelEmprestaLivro() {
		comboLivros.removeAllItems();
		comboLivrosMap.clear();
		List<Livro> livros;
		List<Usuario> usuarios;

		try {
			livros = gerenciadorRegrasNegocio.listaLivrosDisponiveis();
			usuarios = gerenciadorRegrasNegocio.listaUsuarios();

			for (Livro livro : livros) {
				String str = livro.getTitulo() + " - " + livro.getAutores();
				comboLivros.addItem(str);
				comboLivrosMap.put(str, livro.getCodigo());
			}

			for (Usuario usuario : usuarios) {
				String str = "(" + usuario.getCodigo() + ") "
						+ usuario.getNome();
				comboUsuarios.addItem(str);
				comboUsuariosMap.put(str, usuario.getCodigo());
			}
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(this,
					"Problemas no acesso à base de dados. \n" + e.getMessage());
		}
	}

	// ---------------- métodos auxiliares ---------------------
	private void adicionaNoCentroDaLinha(Container painel,
			Component componente, int descolamentoTopo) {
		Insets insetsPainel = painel.getInsets();
		painel.add(componente);
		Dimension d = componente.getPreferredSize();
		int deslocamento = (painel.getWidth() - d.width) / 2;
		componente.setBounds(insetsPainel.left + deslocamento,
				insetsPainel.right + descolamentoTopo, d.width, d.height);
	}

	private int calcRowPosition(int row) {
		return 50 + 30 * row;
	}

	private void adicionaCampoComRotulo(JPanel painel, String lblText,
			Component field, int row) {
		JLabel lbl = new JLabel();
		lbl.setFont(new Font("Microsoft Sans Serif", 0, 13));
		lbl.setText(lblText);
		Insets insetsPainel = painel.getInsets();
		painel.add(lbl);
		painel.add(field);
		int fimLabel = 300;
		Dimension dimLbl = lbl.getPreferredSize();
		int deslocamentoEsq = fimLabel - dimLbl.width;
		int descolamentoTopo = calcRowPosition(row);
		int tamanhoField = 230;
		lbl.setBounds(insetsPainel.left + deslocamentoEsq, insetsPainel.top
				+ descolamentoTopo, dimLbl.width, dimLbl.height);

		field.setBounds(insetsPainel.left + fimLabel + 5, insetsPainel.top
				+ descolamentoTopo - 4, tamanhoField, dimLbl.height + 10);
	}

	private void adicionaPainel(JPanel painel, String titulo, Color cor) {
		painel.setLayout(null);
		painel.setBounds(0, 90, 800, 410);
		painel.setBackground(cor);
		painelFundo.add(painel);
		paineis.add(painel);
		JLabel lbl = new JLabel();
		lbl.setFont(new Font("Microsoft Sans Serif", 2, 18));
		lbl.setText(titulo);
		adicionaNoCentroDaLinha(painel, lbl, 30);
	}

	private void adicionaCamposComRotulos(JPanel painel, String[] lblText,
			Component[] field) {
		for (int row = 0; row < lblText.length; row++) {
			adicionaCampoComRotulo(painel, lblText[row], field[row], row + 1);
		}
	}

	void adicionaBotao(JPanel painel, String btnText, int row,
			String actionListenerName) {
		JButton btn = new JButton();
		painel.add(btn);
		btn.setText(btnText);
		adicionaNoCentroDaLinha(painel, btn, calcRowPosition(row));
		addActionListener(btn, actionListenerName);
	}

	private void addActionListener(AbstractButton btn, String actionListenerName) {
		Object autoRef = this;
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Method method = autoRef.getClass().getMethod(
							actionListenerName, ActionEvent.class);
					method.invoke(autoRef, evt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void adicionaTabela(JPanel painel, JTable tabela) {
		JScrollPane scrollPane = new JScrollPane(tabela);
		tabela.setBounds(0, 90, 700, 0);
		scrollPane.setBounds(50, 90, 700, 200);
		painel.add(scrollPane);
	}

	private void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	private void montaTabelaLivros(List<Livro> livros) {
		DefaultTableModel model = (DefaultTableModel) tabelaLivros.getModel();
		clearTable(model);

		for (Livro livro : livros) {
			model.addRow(new Object[] { livro.getCodigo(), livro.getAutores(),
					livro.getTitulo(), livro.getQtdExemplaresDisponiveis(),
					livro.getQtdExemplaresEmprestados() });
		}
	}

	private void montaTabelaUsuarios(List<Usuario> usuarios) {
		DefaultTableModel model = (DefaultTableModel) tabelaUsuarios.getModel();
		clearTable(model);

		for (Usuario usuario : usuarios) {
			model.addRow(new Object[] { usuario.getCodigo(), usuario.getNome() });
		}
	}

	private void mostraPainel(JPanel painel) {
		for (JPanel p : paineis) {
			if (p == painel) {
				p.setVisible(true);
			} else {
				p.setVisible(false);
			}
		}
	}

	private void insereItemNoMenu(JMenuItem item, JMenu menu, String texto,
			String actionListenerName) {
		menu.add(item);
		item.setText(texto);
		addActionListener(item, actionListenerName);
	}

	private void criaMenu() {
		// menu principal
		JMenuBar menuRaiz = new JMenuBar();
		setJMenuBar(menuRaiz);

		// submenu cadastrar
		JMenu menuInserir = new JMenu();
		menuInserir.setText("Cadastrar");
		insereItemNoMenu(new JMenuItem(), menuInserir, "Livro",
				"inserirLivroActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuInserir, "CD",
				"inserirCDActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuInserir, "Usuário",
				"inserirUsuarioActionPerformed");
		menuRaiz.add(menuInserir);

		// submenu listar
		JMenu menuListar = new JMenu();
		menuListar.setText("Listar");
		insereItemNoMenu(new JMenuItem(), menuListar, "Livro",
				"listarLivroActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuListar, "CD",
				"listarCDActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuListar, "Usuário",
				"listarUsuarioActionPerformed");
		menuRaiz.add(menuListar);

		// submenu empréstimos
		JMenu menuEmprestimos = new JMenu();
		menuEmprestimos.setText("Empréstimos");
		insereItemNoMenu(new JMenuItem(), menuEmprestimos, "Emprestar Livro",
				"EmprestarLivroActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuEmprestimos, "Emprestar CD",
				"EmprestarCDActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuEmprestimos, "Devolver Livro",
				"DevolverLivroActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuEmprestimos, "Devolver CD",
				"DevolverCDActionPerformed");
		menuRaiz.add(menuEmprestimos);

	}

	// ---------------------------------------------------------

	// ------------- tratamento dos eventos --------------------
	public void EmprestarLivroActionPerformed(ActionEvent evt) {
		montaComboPainelEmprestaLivro();
		mostraPainel(painelEmprestarLivro);
	}

	public void EmprestarCDActionPerformed(ActionEvent evt) {
		mostraPainel(painelEmprestarCD);
	}

	public void DevolverLivroActionPerformed(ActionEvent evt) {
		mostraPainel(painelDevolverLivro);
	}

	public void DevolverCDActionPerformed(ActionEvent evt) {
		mostraPainel(painelDevolverCD);
	}

	public void inserirLivroActionPerformed(ActionEvent evt) {
		mostraPainel(painelCadastrarLivro);
	}

	public void inserirUsuarioActionPerformed(ActionEvent evt) {
		mostraPainel(painelCadastrarUsuario);
	}

	public void inserirCDActionPerformed(ActionEvent evt) {
		mostraPainel(painelCadastrarCD);
	}

	public void listarUsuarioActionPerformed(ActionEvent evt) {
		mostraPainel(painelListaUsuarios);
		List<Usuario> usuarios;

		try {
			usuarios = gerenciadorRegrasNegocio.listaUsuarios();
			Collections.sort(usuarios, new Comparator<Usuario>() {

				@Override
				public int compare(Usuario u1, Usuario u2) {
					if (u1.getCodigo() < u2.getCodigo()) {
						return -1;
					}

					if (u1.getCodigo() > u2.getCodigo()) {
						return 1;
					}

					return 0;
				}
			});
			montaTabelaUsuarios(usuarios);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível listar os usuários. \n" + e.getMessage());

		}
	}

	public void listarLivroActionPerformed(ActionEvent evt) {
		mostraPainel(painelListaLivros);
		List<Livro> livros;

		try {
			livros = gerenciadorRegrasNegocio.listaLivros();
			montaTabelaLivros(livros);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível listar os livros. \n" + e.getMessage());
		}
	}

	public void listarCDActionPerformed(ActionEvent evt) {
		mostraPainel(painelListaCD);
		List<CD> cds;

		try {
			cds = gerenciadorRegrasNegocio.listaCDs();
			montaTabelaCDs(cds);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível listar os livros. \n" + e.getMessage());
		}

	}

	private void montaTabelaCDs(List<CD> cds) {
		DefaultTableModel model = (DefaultTableModel) tabelaCD.getModel();
		clearTable(model);

		for (CD cd : cds) {
			model.addRow(new Object[] { cd.getCodigo(), cd.getAlbum(),
					cd.getArtista(), cd.getQtdExemplaresDisponiveis(),
					cd.getQtdExemplaresEmprestados() });
		}
	}

	public void emprestarLivroActionPerformed(ActionEvent evt) {
		String str = (String) comboLivros.getSelectedItem();
		int codigoLivro = comboLivrosMap.get(str);
		str = (String) comboUsuarios.getSelectedItem();
		int codigoUsuario = comboUsuariosMap.get(str);

		try {
			gerenciadorRegrasNegocio.emprestaLivro(codigoLivro, codigoUsuario);
			JOptionPane.showMessageDialog(this,
					"Empréstimo realizado com sucesso!");

		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(
					this,
					"Não foi possível realizar o empréstimo. \n"
							+ e.getMessage());

		}
	}

	public void cadastrarUsuarioActionPerformed(ActionEvent evt) {
		int codigo;
		String nome;

		try {
			codigo = validador.validaFormataInteiro(
					fieldCodigoUsuario.getText(), "Código");
			nome = validador.validaFormataCaixaAlta(fieldNomeUsuario.getText(),
					50, "Nome");
			gerenciadorRegrasNegocio.cadastraUsuario(nome, codigo);
			JOptionPane.showMessageDialog(this,
					"Usuário cadastrado com sucesso.");
			fieldCodigoUsuario.setText("");
			fieldNomeUsuario.setText("");
		} catch (ValidacaoException | NegocioException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Não foi possível cadastrar o usuário. \n"
									+ e.getMessage());
		}
	}

	public void cadastrarLivroActionPerformed(ActionEvent evt) {
		String titulo;
		String autores;
		int codigo;
		int qtdExemplares;

		try {
			titulo = validador.validaFormataCaixaAlta(
					fieldTituloLivro.getText(), 50, "Título");
			autores = validador.validaFormataCaixaAlta(
					fieldAutoresLivro.getText(), 50, "Autores");
			codigo = validador.validaFormataInteiro(fieldCodigoLivro.getText(),
					"Código");
			qtdExemplares = validador.validaFormataInteiro(
					fieldQtdDeExemplaresTotaisLivro.getText(),
					"Quantidade de Exemplares");

			gerenciadorRegrasNegocio.cadastraLivro(titulo, autores, codigo,
					qtdExemplares);

			JOptionPane
					.showMessageDialog(this, "Livro cadastrado com sucesso.");
			fieldCodigoLivro.setText("");
			fieldTituloLivro.setText("");
			fieldAutoresLivro.setText("");
			fieldQtdDeExemplaresTotaisLivro.setText("");
		} catch (ValidacaoException | NegocioException e) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível cadastrar o livro. \n" + e.getMessage());
		}
	}

	public void cadastrarCDActionPerformed(ActionEvent evt) {
		int codigo;
		String album;
		String artista;
		int qntExemplares;

		try {
			codigo = validador.validaFormataInteiro(fieldCodigoCD.getText(),
					"Código");
			qntExemplares = validador.validaFormataInteiro(
					fieldQtdDeExemplaresTotaisCD.getText(),
					"Quantidade de Exemplares");
			album = validador.validaFormataCaixaAlta(fieldAlbumCD.getText(),
					50, "Album");
			artista = validador.validaFormataCaixaAlta(
					fieldArtistaCD.getText(), 50, "Artista");
			gerenciadorRegrasNegocio.cadastraCD(album, artista, codigo,
					qntExemplares);
			JOptionPane.showMessageDialog(this, "CD cadastrado com sucesso.");
			fieldCodigoCD.setText("");
			fieldAlbumCD.setText("");
			fieldArtistaCD.setText("");
			fieldQtdDeExemplaresTotaisCD.setText("");
		} catch (ValidacaoException | NegocioException e) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível cadastrar o CD. \n" + e.getMessage());
		}
	}

	// ----------------------------------------------------------

	public static void main(String args[]) throws Exception {
		// configura look-and-feel
		for (UIManager.LookAndFeelInfo info : UIManager
				.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}

		// mostra a tela em uma outra thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GerenciadorVisualizacaoSwing().setVisible(true);
			}
		});
	}
}
