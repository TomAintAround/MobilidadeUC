
/**
 * A classe Eletrico, diferente do Veiculo, possui um valor de bateria restante
 * entre 0 e 100%
 */

public abstract class Eletrico extends Veiculo {
    private int nivelBateria;

    /**
     * O Construtor recebe dados acerca do Eletrico
     * 
     * @param id           ID do Eletrico
     * @param localAtual   Onde esta atualmente o Eletrico
     * @param nivelBateria Qual o nivel de bateria do Eletrico
     */

    public Eletrico(int id, String localAtual, int nivelBateria) {
        super(id, localAtual);
        try {
            if (nivelBateria < 0 || nivelBateria > 100)
                throw new Exception("Só se aceitam níveis de bateria entre 0 a 100%.");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        this.nivelBateria = nivelBateria;
    }

    /**
     * @return Nivel de Bateria restante
     */

    public int getNivelBateria() {
        return nivelBateria;
    }
}
