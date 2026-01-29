import java.io.Serializable;

/**
 * Esta classe verifica qual é o custo total do aluguer para cada utilizador
 */

public class Aluguer implements Serializable {
    private double[][] tabelaPrecos = {
            { 1.00, 2.00 },
            { 2.00, 3.00 },
            { 1.00, 2.50 },
            { 1.10, 2.60 },
            { 1.25, 2.75 },
            { 1.50, 3.00 },
    };
    private Utilizador utilizador;
    private Veiculo veiculo;
    private boolean temCapacete;
    private boolean temLuz;
    private boolean diario;
    private Data dataInicio;
    private Data dataFim;

    /**
     * Construtor recebe dados sobre cada Aluguer
     * 
     * @param utilizador  Que tipo de utilizador
     * @param veiculo     Que tipo de veiculo vai alugar
     * @param temCapacete Se leva capacete
     * @param temLuz      Se tem uma luz
     * @param diario      Se o aluguer é diario
     * @param dataInicio  Quando inicia o aluguer
     * @param dataFim     Quando termina o aluguer
     */

    public Aluguer(Utilizador utilizador, Veiculo veiculo,
            boolean temCapacete, boolean temLuz, boolean diario, Data dataInicio, Data dataFim) {
        this.utilizador = utilizador;
        this.veiculo = veiculo;
        this.temCapacete = temCapacete;
        this.temLuz = temLuz;
        this.diario = diario;

        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    /**
     * @return tipo de Utilizador
     */

    public Utilizador getUtilizador() {
        return utilizador;
    }

    /**
     * @return tipo de Veiculo
     */

    public Veiculo getVeiculo() {
        return veiculo;
    }

    /**
     * @return DataInicio do Aluguer
     */

    public Data getDataInicio() {
        return dataInicio;
    }

    /**
     * @return DataFim do Aluguer
     */

    public Data getDataFim() {
        return dataFim;
    }

    /**
     *
     * @return Verdadeiro se a data de inicio do aluguer for anterior à data do fim
     *         do aluguer
     */

    public boolean datasSaoInvalidas() {
        if (dataInicio.totalMinutos() >= dataFim.totalMinutos()) {
            System.out.println("A data de início do aluguer deve ser anterior à data final.");
            return true;
        }

        return false;
    }

    /**
     * @return custo final do Aluguer com 2 casas decimais
     */

    public double precoFinal() {
        double preco = tabelaPrecos[veiculo.getLinhaPrecos()][utilizador.getColunaPrecos()];
        int diferencaDias = dataInicio.diferencaDias(dataFim);
        int diferencaHoras = dataInicio.diferencaHoras(dataFim);
        if (diario) {
            preco *= 8 * (diferencaDias + 1);
        } else {
            preco *= diferencaHoras + 1;
        }
        if (temCapacete)
            preco += 5.00 * (diferencaDias + 1);
        if (temLuz)
            preco += 2.50 * (diferencaDias + 1);
        preco *= 1 - utilizador.desconto();

        // Para arredondar os números para 2 casas decimais
        preco = Math.round(preco * 100);
        preco /= 100;

        return preco;
    }

    public String toString() {
        String resultado = "Número mecanográfico do utilizador: " + utilizador.getNumero() + "\nID do veículo: "
                + veiculo.getId() + "\nTem capacete: ";

        if (temCapacete)
            resultado += "sim";
        else
            resultado += "não";

        resultado += "\nTem luz: ";
        if (temLuz)
            resultado += "sim";
        else
            resultado += "não";

        resultado += "\nÉ diário: ";
        if (diario)
            resultado += "sim";
        else
            resultado += "não";

        resultado += "\nData inicial: " + dataInicio + "\nData final: " + dataFim + "\nPreço: " + precoFinal() + "€";

        return resultado;
    }
}
