
/**
 * A classe Bicicleta, diferente do Veiculo, possui um tipo que pode ser
 * Individual ou Dupla
 */

public class Bicicleta extends Veiculo {
	/**
	 * Diferentes tipos de Bicicleta
	 */
	public enum TipoBicicleta {
		Individual, Duplo
	}

	private TipoBicicleta tipo;

	/**
	 * O Construtor recebe dados acerca da Bicicleta
	 * 
	 * @param id         ID da Bicicleta
	 * @param localAtual Onde esta atualmente a Bicicleta
	 * @param tipo       Tipo da Bicicleta
	 */

	public Bicicleta(int id, String localAtual, TipoBicicleta tipo) {
		super(id, localAtual);
		this.tipo = tipo;
	}

	@Override
	public int getLinhaPrecos() {
		if (tipo.equals(TipoBicicleta.Individual))
			return 0;
		return 1;
	}

	@Override
	public String toString() {
		return "Bicicleta:" + "\n\tID: " + getId() + "\n\tLocal Atual: " + getLocalAtual() + "\n\tLinha de preços: "
				+ getLinhaPrecos() + "\n\tTipo de bicicleta: " + tipo;
	}
}
