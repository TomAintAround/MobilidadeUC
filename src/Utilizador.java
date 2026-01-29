import java.io.Serializable;
import java.util.ArrayList;

/**
 * Os Utilizadores são caracterizados pelo seu Numero Mecanografico e pelo seu
 * Metodo de Pagamento
 */

public abstract class Utilizador implements Serializable {
    /**
     * Diferentes metodos de Pagamento para cada Utilizador
     */
    public enum MetodoPagamento {
        CartaoCredito, ReferenciaMultibanco
    }

    private int numero;
    private MetodoPagamento metodoPagamento;
    private ArrayList<Aluguer> alugueres = new ArrayList<>();

    /**
     * Construtor recebe dados sobre o Utilizador
     * 
     * @param numero          Número Mecanográfico do Utilizador
     * @param metodoPagamento Metodo Pagamento do Utilizador
     */

    public Utilizador(int numero, MetodoPagamento metodoPagamento) {
        this.numero = numero;
        this.metodoPagamento = metodoPagamento;
        alugueres = new ArrayList<>();
    }

    /**
     * @return numero do Utilizador
     */

    public int getNumero() {
        return numero;
    }

    /**
     * @return metodo pagamento do Utilizador
     */

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    /**
     * @param aluguer adiciona alugueres à lista
     */

    public void adicionarAluguer(Aluguer aluguer) {
        alugueres.add(aluguer);
    }

    /**
     * @return desconto para cada Utilizador
     */

    public double desconto() {
        return 0;
    }

    /**
     * @return Coluna de Precos para cada tipo de Utilizador
     */

    public abstract int getColunaPrecos();
}
