package visualizacao;

public class Validador {

	public String validaFormataCaixaAlta(String txt, int tamanhoMax,
			String rotuloCampo) throws ValidacaoException {
		if (txt == null) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		txt = txt.trim().toUpperCase();

		if (txt.length() == 0) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		if (txt.length() > tamanhoMax) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve conter no máximo " + tamanhoMax + " caracteres.");
		}

		return txt;
	}

	public int validaFormataInteiro(String txt, String rotuloCampo)
			throws ValidacaoException {
		if (txt == null) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		txt = txt.trim().toUpperCase();

		if (txt.length() == 0) {
			throw new ValidacaoException("O campo " + rotuloCampo
					+ " deve ser preenchido.");
		}

		int i;

		try {
			i = Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			throw new ValidacaoException("Erro na digitação do campo " + rotuloCampo
					+ ". \n" + e.getMessage());
		}

		return i;
	}
}
