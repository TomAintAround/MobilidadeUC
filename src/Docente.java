import java.util.ArrayList;

/**
 * O Docente, diferente do Funcionario, tem uma lista de Faculdades em que
 * leciona
 */

public class Docente extends Funcionario {

    private ArrayList<String> faculdades;

    /**
     * Construtor recebe dados sobre o Docente
     * 
     * @param numero          Número Mecanográfico do Docente
     * @param metodoPagamento Metodo de Pagamento do Docente
     * @param faculdades      Lista de faculdades em que leciona
     */

    public Docente(int numero, MetodoPagamento metodoPagamento, ArrayList<String> faculdades) {
        super(numero, metodoPagamento);
        this.faculdades = faculdades;
    }

    @Override
    public String toString() {
        return "Docente:" + "\n\tNúmero: " + getNumero() + "\n\tMétodo de Pagamento: " + getMetodoPagamento()
                + "\n\tFaculdades: " + faculdades;
    }
}
