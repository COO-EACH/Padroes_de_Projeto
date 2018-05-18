package basededados.DAO;

import java.util.List;

import basedados.BaseDadosException;
import beans.CD;

public interface CdDAO{
	
	public void insereCD(CD cd) throws BaseDadosException;

	public CD buscaCD(int codigo) throws BaseDadosException;

	public List<CD> listaCD() throws BaseDadosException;
	
}
