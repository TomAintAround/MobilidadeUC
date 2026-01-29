import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A classe MobilidadeUC contém o metodo principal da aplicação de gestão de
 * mobilidade da Universidade de Coimbra.
 */

public class MobilidadeUC {
	/**
	 * @param args Argumentos passados pela linha de comandos (nao utilizados).
	 */
	public static void main(String[] args) {
		GestaoAplicacao gestaoAplicacao = new GestaoAplicacao();
		ArrayList<Aluguer> alugueres;
		ArrayList<Utilizador> utilizadores = gestaoAplicacao.carregarUtilizadores();
		ArrayList<Veiculo> veiculos = gestaoAplicacao.carregarVeiculos();

		String nomeFichAlugueres = "alugueres.obj";
		File fichAlugueres = new File("../input/" + nomeFichAlugueres);
		if (fichAlugueres.exists()) {
			System.out.println(
					"O ficheiro \"" + nomeFichAlugueres + "\" foi encontrado. Lendo alugueres escritos no ficheiro.");
			alugueres = gestaoAplicacao.carregarAlugueres(fichAlugueres, nomeFichAlugueres, utilizadores, veiculos);
		} else {
			System.out.println("O ficheiro \"" + nomeFichAlugueres + "\" não foi encontrado.");
			alugueres = new ArrayList<>();
		}

		Scanner scanner = new Scanner(System.in);
		boolean terminarPrograma = false;
		while (!terminarPrograma) {
			System.out.println("\n----------------------\n");
			System.out.println("-- Lista de ações possíveis --");
			System.out.println("1 - Criar aluguer(es)");
			System.out.println("2 - Listar aluguer(es)");
			System.out.println("3 - Imprimir aluguer com maior preço");
			System.out.println("4 - Terminar programa");

			System.out.print("Insire a sua escolha: ");
			int escolha = 0;
			try {
				escolha = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException E) {
				System.out.println("Tipo lido não corresponde ao tipo esperado.");
				System.exit(1);
			}
			switch (escolha) {
				case 1:
					alugueres.addAll(gestaoAplicacao.criarAlugueres(utilizadores, veiculos, scanner));
					break;
				case 2:
					gestaoAplicacao.listarAlugueres(alugueres);
					break;
				case 3:
					gestaoAplicacao.aluguerMaiorPreco(alugueres);
					break;
				case 4:
					terminarPrograma = true;
					break;
				default:
					System.out.println("A sua escolha é inválida.");
			}
		}

		scanner.close();

		gestaoAplicacao.terminar(alugueres, fichAlugueres, nomeFichAlugueres);
	}
}
