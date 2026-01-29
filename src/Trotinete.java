
/**
 * A classe Trotinete, diferente do Veiculo, possui um valor de bateria restante
 */

public class Trotinete extends Eletrico {
	private boolean temMapa;

	/**
	 * O Construtor recebe dados acerca da Trotinete
	 * 
	 * @param id           ID da Trotinete
	 * @param localAtual   Onde esta atualmente a Trotinete
	 * @param nivelBateria Qual o nivel de bateria da Trotinete
	 * @param temMapa      Se a trotinete tem Mapa
	 */

	public Trotinete(int id, String localAtual, int nivelBateria, boolean temMapa) {
		super(id, localAtual, nivelBateria);
		this.temMapa = temMapa;
	}

	/**
	 * Indica se a trotinete possui um mapa integrado.
	 * Este valor pode influenciar o preço do aluguer,
	 * 
	 * @return verdadeiro se o veículo tiver mapa integrado ,falso caso contrário.
	 */

	public boolean isTemMapa() {
		return temMapa;
	}

	@Override
	public int getLinhaPrecos() {
		if (temMapa)
			return 3;
		return 2;
	}

	@Override
	public String toString() {
		return "Trotinete:" + "\n\tID: " + getId() + "\n\tLocal Atual: " + getLocalAtual() + "\n\tLinha de preços: "
				+ getLinhaPrecos() + "\n\tNível de bateria: " + getNivelBateria() + "%\n\tTem mapa: " + temMapa;
	}
}
