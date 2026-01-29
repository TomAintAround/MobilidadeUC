
/**
 * O Funcionario possui a 2ªcoluna na coluna de precos
 */
public abstract class Funcionario extends Utilizador {
    /**
     * Construtor recebe dados sobre o Funcionario
     * 
     * @param numero          Numero Mecanografico do Funcionario
     * @param metodoPagamento Metodo de Pagamento do Funcionario
     */
    public Funcionario(int numero, MetodoPagamento metodoPagamento) {
        super(numero, metodoPagamento);
    }

    @Override
    public int getColunaPrecos() {
        return 1;
    }
}
