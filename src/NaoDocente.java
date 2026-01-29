
/**
 * O NaoDocente tem, diferente de um Funcionario, um Servico onde trabalha
 */
public class NaoDocente extends Funcionario {

    private String servico;

    /**
     * Construtor recebe dados sobre o NaoDocente
     * 
     * @param numero          Numero Mecanografico do NaoDocente
     * @param metodoPagamento Metodo de Pagamento do NaoDocente
     * @param servico         Serviço que o NaoDocente trabalha
     */

    public NaoDocente(int numero, MetodoPagamento metodoPagamento, String servico) {
        super(numero, metodoPagamento);
        this.servico = servico;
    }

    @Override
    public double desconto() {
        return 0.5;
    }

    @Override
    public String toString() {
        return "Não Docente:" + "\n\tNúmero: " + getNumero() + "\n\tMétodo de Pagamento: " + getMetodoPagamento()
                + "\n\tServico: " + servico;
    }
}
