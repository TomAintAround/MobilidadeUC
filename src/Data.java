import java.io.Serializable;

/**
 * A classe Data representa uma data completa composta por dia, mês, ano,
 * hora e minuto. Inclui funcionalidades para validação, comparação,
 * cálculo de diferenças e conversão para valores numéricos equivalentes
 */

public class Data implements Serializable {
	private int dia;
	private int mes;
	private int ano;
	private int hora;
	private int minuto;

	/**
	 *
	 * @param dia    Dia do mes
	 * @param mes    Mes do ano
	 * @param ano    Ano
	 * @param hora   Hora do dia
	 * @param minuto Minuto da hora
	 */

	public Data(int dia, int mes, int ano, int hora, int minuto) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.hora = hora;
		this.minuto = minuto;
	}

	private boolean anoEBissexto(int ano) {
		return ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0;
	}

	/**
	 * @return o dia do mes.
	 */

	public int getDia() {
		return dia;
	}

	/**
	 *
	 * @return o mes do ano
	 */

	public int getMes() {
		return mes;
	}

	/**
	 *
	 * @return o ano
	 */

	public int getAno() {
		return ano;
	}

	/**
	 *
	 * @return a hora do dia
	 */

	public int getHora() {
		return hora;
	}

	/**
	 *
	 * @return o minuto da hora
	 */

	public int getMinuto() {
		return minuto;
	}

	/**
	 * Verifica se a data armazenada é inválida, considerando dias, meses,
	 * anos, horas, minutos e regras de ano bissexto.
	 * 
	 * @return verdadeiro se a data for invalida, falso caso contrario
	 */
	public boolean dataEInvalida() {
		if (dia < 1) {
			System.out.println("Não se aceitam dias menores que 1.");
			return true;
		}
		if (mes < 1 || mes > 12) {
			System.out.println("Só se aceitam meses entre 1 e 12.");
			return true;
		}
		if (ano < 0) {
			System.out.println("Não se aceitam anos negativos.");
			return true;
		}
		if (hora < 0 || hora > 23) {
			System.out.println("Só se aceitam horas entre 0 e 23.");
			return true;
		}
		if (minuto < 0 || minuto > 59) {
			System.out.println("Só se aceitam minutos entre 0 e 59.");
			return true;
		}

		boolean anoBissexto = anoEBissexto(ano);
		if (mes == 2) {
			if (anoBissexto && dia > 29) {
				System.out.println("Quando o ano é bissexto, em fevereiro, não se aceitam dias superiores a 29.");
				return true;
			} else if (!anoBissexto && dia > 28) {
				System.out.println("Quando o ano não é bissexto, em fevereiro, não se aceitam dias superiores a 28.");
				return true;
			}
			return false;
		}

		int[] diasDosMeses = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (dia > diasDosMeses[mes - 1]) {
			System.out.println("No mês " + mes + ", não se aceitam dias superiores a " + diasDosMeses[mes - 1] + ".");
			return true;
		}

		return false;
	}

	/**
	 * Calcula o total de dias decorridos desde o ano 0 até esta data.
	 * 
	 * @return número total de dias desde o ano 0.
	 */

	public int totalDias() {
		int dias = 0;

		for (int anoAtual = 0; anoAtual < this.getAno(); anoAtual++) {
			dias += anoEBissexto(anoAtual) ? 366 : 365;
		}

		int[] diasMeses = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		boolean anoBissexto = anoEBissexto(this.getAno());
		for (int mes = 1; mes < this.mes; mes++) {
			if (mes == 2 && anoBissexto)
				dias += 29;
			else
				dias += diasMeses[mes - 1];
		}

		dias += this.dia - 1;
		return dias;
	}

	/**
	 * Calcula o total de minutos desde o ano 0 até esta data.
	 * 
	 * @return total de minutos.
	 */
	public int totalMinutos() {
		return totalDias() * 24 * 60 + this.hora * 60 + this.minuto;
	}

	/**
	 * Calcula a diferença absoluta em dias entre esta data e outra.
	 * 
	 * @param data Outra data para comparar
	 * @return diferença em dias (valor absoluto)
	 */

	public int diferencaDias(Data data) {
		int totalDiasOriginal = this.totalDias();
		int totalDiasOutra = data.totalDias();

		return Math.abs(totalDiasOriginal - totalDiasOutra);
	}

	/**
	 * Calcula a diferença absoluta em horas entre esta data e outra.
	 * 
	 * @param data Outra data para comparar.
	 * @return diferença em horas (valor absoluto).
	 */
	public int diferencaHoras(Data data) {
		int totalMinutosOriginal = this.totalMinutos();
		int totalMinutosOutra = data.totalMinutos();

		return Math.abs(totalMinutosOriginal - totalMinutosOutra) / 60;
	}

	/**
	 * Compara esta data com outra cronologicamente.
	 *
	 * @param data Data a comparar.
	 * @return -1 se esta data for anterior, 1 se posterior, 0 se iguais.
	 */

	public int comparar(Data data) {
		if (this.ano < data.getAno())
			return -1;
		else if (this.ano > data.getAno())
			return 1;

		if (this.mes < data.getMes())
			return -1;
		else if (this.mes > data.getMes())
			return 1;

		if (this.dia < data.getDia())
			return -1;
		else if (this.dia > data.getDia())
			return 1;

		if (this.hora < data.getHora())
			return -1;
		else if (this.hora > data.getHora())
			return 1;

		if (this.minuto < data.getMinuto())
			return -1;
		else if (this.minuto > data.getMinuto())
			return 1;

		return 0;
	}

	public String toString() {
		String minutoString = "";
		if (minuto <= 9)
			minutoString = "0" + minuto;
		else
			minutoString += minuto;
		return dia + "/" + mes + "/" + ano + ", " + hora + ":" + minutoString;
	}
}
