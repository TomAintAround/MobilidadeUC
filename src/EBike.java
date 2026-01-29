
/**
 * A classe E-Bike diferente da classe Eletrico possui um tipo de bateria, se é
 * removível ou fixa
 */

public class EBike extends Eletrico {
    /**
     * Diferentes tipos de Bateria de cada EBike
     */
    public enum TipoBateria {
        Fixa, Removivel
    }

    private TipoBateria tipoBateria;

    /**
     * O Construtor recebe dados acerca da EBike
     * 
     * @param id           ID da EBike
     * @param localAtual   Onde esta atualmente a EBike
     * @param nivelBateria Qual o nivel de bateria da EBike
     * @param tipoBateria  Qual o tipo de bateria da EBike
     */

    public EBike(int id, String localAtual, int nivelBateria, TipoBateria tipoBateria) {
        super(id, localAtual, nivelBateria);
        this.tipoBateria = tipoBateria;
    }

    @Override
    public int getLinhaPrecos() {
        if (tipoBateria.equals(TipoBateria.Fixa))
            return 4;
        return 5;
    }

    @Override
    public String toString() {
        return "EBike:" + "\n\tID: " + getId() + "\n\tLocal Atual: " + getLocalAtual() + "\n\tLinha de preços: "
                + getLinhaPrecos() + "\n\tNível de bateria: " + getNivelBateria() + "%\n\tTipo de bateria: "
                + tipoBateria;
    }
}
