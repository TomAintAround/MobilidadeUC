import java.io.Serializable;
import java.util.ArrayList;

/**
 * Os Veiculos são caracterizados pelo seu ID e pelo seu Local Atual
 */

public abstract class Veiculo implements Serializable {
	private int id;
	private String localAtual;
	private ArrayList<Aluguer> alugueres = new ArrayList<>();

	/**
	 * O Construtor recebe dados acerca do Veiculo
	 * 
	 * @param id         ID do Veiculo
	 * @param localAtual Onde se localiza o Veiculo
	 */

	public Veiculo(int id, String localAtual) {
		verificarId(id);
		this.id = id;
		this.localAtual = localAtual;
	}

	private void verificarId(int id) {
		try {
			if (id < 0 || id > Math.pow(10, 4) - 1) {
				System.out.println("O ID deve ser um número de 4 dígitos.");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	/**
	 * @return ID do Veiculo
	 */

	public int getId() {
		return id;
	}

	/**
	 * @return Local Atual do Veiculo
	 */

	public String getLocalAtual() {
		return localAtual;
	}

	/**
	 *
	 * @param novoAluguer Verifica se este novo entra em conflito com algum dos
	 *                    alugueres ja existentes
	 * @return verdadeiro se existe conflito , falso caso contrário
	 */
	public boolean existemConflitosDatas(Aluguer novoAluguer) {
		System.out.println(alugueres);
		for (Aluguer aluguerAntigo : alugueres) {
			Data dataIniNovo = novoAluguer.getDataInicio();
			Data dataIniAntigo = aluguerAntigo.getDataInicio();
			Data dataFimNovo = novoAluguer.getDataFim();
			Data dataFimAntigo = aluguerAntigo.getDataFim();

			if ((dataIniNovo.comparar(dataIniAntigo) >= 0 && dataIniNovo.comparar(dataFimAntigo) <= 0)
					|| (dataFimNovo.comparar(dataIniAntigo) >= 0 && dataFimNovo.comparar(dataFimAntigo) <= 0)) {
				System.out.println("Há conflito! Este veículo já está alugado entre as datas fornecidas.");
				return true;
			}
		}

		return false;

	}

	/**
	 * @param aluguer Adiciona alugueres à lista de alugueres e verifica se ha
	 *                conflito entre datas
	 */

	public void adicionarAluguer(Aluguer aluguer) {
		alugueres.add(aluguer);
	}

	/**
	 * @return indice da linha correspondente a cada Veículo
	 */

	public abstract int getLinhaPrecos();
}
