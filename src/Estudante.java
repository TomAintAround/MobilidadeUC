
/**
 * O Estudante, diferente do Utilizador, possui um curso onde estuda e um polo
 * que frequenta e o Estudante possui a 1ªcoluna na coluna de precos
 */
public class Estudante extends Utilizador {

    /**
     * Diferentes pólos que o estudante estuda
     */

    public enum Polo {
        PoloI, PoloII, PoloIII
    }

    private String curso;

    private Polo polo;

    /**
     * Construtor recebe dados sobre o Estudante
     * 
     * @param numero          Número Mecanográfico do Estudante
     * @param metodoPagamento Metodo de Pagamento do Estudante
     * @param curso           Curso que o Estudante está
     * @param polo            Polo que o Estudante frequenta
     */

    public Estudante(int numero, Utilizador.MetodoPagamento metodoPagamento, String curso, Polo polo) {
        super(numero, metodoPagamento);
        this.curso = curso;
        this.polo = polo;
    }

    @Override
    public int getColunaPrecos() {
        return 0;
    }

    @Override
    public String toString() {
        return "Estudante:" + "\n\tNúmero: " + getNumero() + "\n\tMétodo de Pagamento: " + getMetodoPagamento()
                + "\n\tCurso: " + curso + "\n\tPolo: " + polo;
    }
}
