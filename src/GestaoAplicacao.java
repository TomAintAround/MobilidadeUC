import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe responsável pela gestão global da aplicação: carregamento de dados,
 * criação de alugueres, validações e gravação de ficheiros binários.
 */

public class GestaoAplicacao {
	private int procurarIndice(String[] array, String element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(element))
				return i;
		}
		return -1;
	}

	private boolean linhaEVazia(String[] linhaSplit) {
		return linhaSplit.length == 1 && linhaSplit[0].equals("");
	}

	private Utilizador procurarUtilizador(ArrayList<Utilizador> utilizadores, int num) {
		for (Utilizador utilizador : utilizadores) {
			if (utilizador.getNumero() == num)
				return utilizador;
		}
		return null;
	}

	private Veiculo procurarVeiculo(ArrayList<Veiculo> veiculos, int id) {
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getId() == id)
				return veiculo;
		}
		return null;
	}

	/**
	 * Carrega os utilizadores a partir do ficheiro de texto "utilizadores.txt".
	 * Efetua validações sobre:
	 * - quantidade de campos por linha
	 * - tipos válidos de utilizador
	 * - métodos de pagamento
	 * - valores numéricos
	 *
	 * @return ArrayList com todos os utilizadores carregados.
	 */

	public ArrayList<Utilizador> carregarUtilizadores() {
		ArrayList<Utilizador> utilizadores = new ArrayList<>();

		String nomeFich = "utilizadores.txt";
		File input = new File("../input/" + nomeFich);
		try {
			FileReader fileReader = new FileReader(input);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			int linha = 1;
			while ((line = bufferedReader.readLine()) != null) {
				String erroLinha = "\n\"" + nomeFich + "\", linha " + linha + ":\n\t";

				String[] linhaSplit = line.split(",");
				if (linhaEVazia(linhaSplit)) {
					linha++;
					continue;
				}
				if (linhaSplit.length != 4 && linhaSplit.length != 5) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Cada linha de \"" + nomeFich
							+ "\" deve ter entre 4 a 5 elementos separados por vírgulas.");
				}
				// Estes 2 arrays estão associados
				// (Se for um estudante, a linha terá de ter 5 elementos)
				// Ex.: Estudante,NUMERO,METODOPAGAMENTO,CURSO,POLO
				String[] nomesPossiveis = { "Estudante", "Docente", "NaoDocente" };
				int[] numElementosPossiveis = { 5, 4, 4 };
				String nome = linhaSplit[0].strip();
				int indice = procurarIndice(nomesPossiveis, nome); // indice do array numElementosPossiveis
																	// de acordo com o nome
				if (indice == -1) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Não existem utilizadores \"" + nome + "\".");
				}
				if (numElementosPossiveis[indice] != linhaSplit.length) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Os utilizadores " + nome + " precisam de "
							+ numElementosPossiveis[indice] + " elementos na sua linha.");
				}

				int num = Integer.parseInt(linhaSplit[1].strip());
				if (procurarUtilizador(utilizadores, num) != null) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Este número mecanográfico já existe.");
				}

				String metodoPagamentoString = linhaSplit[2].strip();
				Utilizador.MetodoPagamento metodoPagamento = Utilizador.MetodoPagamento.CartaoCredito;
				if (metodoPagamentoString.equals(Utilizador.MetodoPagamento.CartaoCredito.toString()))
					metodoPagamento = Utilizador.MetodoPagamento.CartaoCredito;
				else if (metodoPagamentoString.equals(Utilizador.MetodoPagamento.ReferenciaMultibanco.toString()))
					metodoPagamento = Utilizador.MetodoPagamento.ReferenciaMultibanco;
				else {
					bufferedReader.close();
					throw new Exception(
							erroLinha + "O tipo de método de pagamento \"" + metodoPagamentoString + "\" não existe.");
				}

				if (nome.equals("Estudante")) {
					String curso = linhaSplit[3].strip();
					String poloString = linhaSplit[4].strip();
					Estudante.Polo polo = Estudante.Polo.PoloI;
					if (poloString.equals(Estudante.Polo.PoloI.toString()))
						polo = Estudante.Polo.PoloI;
					else if (poloString.equals(Estudante.Polo.PoloII.toString()))
						polo = Estudante.Polo.PoloII;
					else if (poloString.equals(Estudante.Polo.PoloIII.toString()))
						polo = Estudante.Polo.PoloIII;
					else {
						bufferedReader.close();
						throw new Exception(erroLinha + "O polo \"" + poloString + "\" não existe.");
					}

					utilizadores.add(new Estudante(num, metodoPagamento, curso, polo));
				} else if (nome.equals("Docente")) {
					String[] faculdadesStringSplit = linhaSplit[3].strip().split(";");
					ArrayList<String> faculdades = new ArrayList<>();
					for (String faculdade : faculdadesStringSplit) {
						faculdade = faculdade.strip();
						faculdades.add(faculdade);
					}

					utilizadores.add(new Docente(num, metodoPagamento, faculdades));
				} else if (nome.equals("NaoDocente")) {
					String servico = linhaSplit[3].strip();
					utilizadores.add(new NaoDocente(num, metodoPagamento, servico));
				}

				linha++;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro \"" + nomeFich + "\".");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao ler ficheiro de texto \"" + nomeFich + "\".");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Não foi possível ler número. Verifique se colocou letras onde devia haver números.");
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		return utilizadores;
	}

	/**
	 * Carrega os veículos a partir de "veículos.txt", efetuando validações sobre:
	 * - tipos de veículos
	 * - tipos de bateria
	 * - tipos de bicicleta
	 * - duplicação de ID
	 *
	 * @return Lista de veículos carregados.
	 */

	public ArrayList<Veiculo> carregarVeiculos() {
		ArrayList<Veiculo> veiculos = new ArrayList<>();

		String nomeFich = "veículos.txt";
		File input = new File("../input/" + nomeFich);
		try {
			FileReader fileReader = new FileReader(input);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			int linha = 1;
			while ((line = bufferedReader.readLine()) != null) {
				String erroLinha = "\n\"" + nomeFich + "\", linha " + linha + ":\n\t";

				String[] linhaSplit = line.split(",");
				if (linhaSplit.length == 1 && linhaSplit[0].equals("")) { // Caso a linha esteja vazia
					linha++;
					continue;
				}
				if (linhaSplit.length != 4 && linhaSplit.length != 5) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Cada linha de \"" + nomeFich
							+ "\" deve ter entre 4 a 5 elementos separados por vírgulas.");
				}

				// Estes 2 arrays estão associados
				// (Se for uma bicicleta, a linha terá de ter 4 elementos)
				// Ex.: Bicicleta,ID,LOCALATUAL,TIPOBICICLETA
				String[] nomesPossiveis = { "Bicicleta", "Trotinete", "EBike" };
				int[] numElementosPossiveis = { 4, 5, 5 };
				String nome = linhaSplit[0].strip();
				int indice = procurarIndice(nomesPossiveis, nome); // indice to array numElementosPossiveis,
																	// de acordo com o nome
				if (indice == -1) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Não existem veículos \"" + nome + "\".");
				}
				if (numElementosPossiveis[indice] != linhaSplit.length) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Os veículos " + nome + " precisam de "
							+ numElementosPossiveis[indice] + " elementos na sua linha.");
				}

				int id = Integer.parseInt(linhaSplit[1].strip());
				if (procurarVeiculo(veiculos, id) != null) {
					bufferedReader.close();
					throw new Exception(erroLinha + "Este ID já existe.");
				}

				String localAtual = linhaSplit[2].strip();

				if (nome.equals("Bicicleta")) {
					String tipoBicicletaString = linhaSplit[3].strip();
					Bicicleta.TipoBicicleta tipoBicicleta = Bicicleta.TipoBicicleta.Individual;
					if (tipoBicicletaString.equals(Bicicleta.TipoBicicleta.Individual.toString()))
						tipoBicicleta = Bicicleta.TipoBicicleta.Individual;
					else if (tipoBicicletaString.equals(Bicicleta.TipoBicicleta.Duplo.toString()))
						tipoBicicleta = Bicicleta.TipoBicicleta.Duplo;
					else {
						bufferedReader.close();
						throw new Exception(
								erroLinha + "O tipo de bicicleta \"" + tipoBicicletaString + "\" não existe.");
					}

					veiculos.add(new Bicicleta(id, localAtual, tipoBicicleta));
				} else if (nome.equals("Trotinete") || nome.equals("EBike")) {
					int nivelBateria = Integer.parseInt(linhaSplit[3].strip());

					if (nome.equals("Trotinete")) {
						String temMapaString = linhaSplit[4].strip();
						if (!temMapaString.equals("false") && !temMapaString.equals("true")) {
							bufferedReader.close();
							throw new Exception(erroLinha
									+ "Para dizer se a trotinete dispõe ecrâ LCD com mapa, tem de inserir \"false\" ou \"true\" no final.");
						}
						boolean temMapa = Boolean.parseBoolean(temMapaString);

						veiculos.add(new Trotinete(id, localAtual, nivelBateria, temMapa));
					} else if (nome.equals("EBike")) {
						String tipoBateriaString = linhaSplit[4].strip();
						EBike.TipoBateria tipoBateria = EBike.TipoBateria.Fixa;
						if (tipoBateriaString.equals(EBike.TipoBateria.Fixa.toString()))
							tipoBateria = EBike.TipoBateria.Fixa;
						else if (tipoBateriaString.equals(EBike.TipoBateria.Removivel.toString()))
							tipoBateria = EBike.TipoBateria.Removivel;
						else {
							bufferedReader.close();
							throw new Exception(
									erroLinha + "O tipo de bateria \"" + tipoBateriaString + "\" não existe.");
						}

						veiculos.add(new EBike(id, localAtual, nivelBateria, tipoBateria));
					}
				}

				linha++;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir o ficheiro \"" + nomeFich + "\".");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao ler ficheiro de texto \"" + nomeFich + "\".");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Não foi possível ler número. Verifique se colocou letras onde devia haver números.");
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		return veiculos;

	}

	private Aluguer criarAluguer(ArrayList<Utilizador> utilizadores, ArrayList<Veiculo> veiculos,
			Scanner scanner) {
		Aluguer aluguer = null;
		try {
			Utilizador utilizador;
			while (true) {
				System.out.print("Introduza o número mecanográfico do utilizador: ");
				int num = scanner.nextInt();
				scanner.nextLine();
				utilizador = procurarUtilizador(utilizadores, num);
				if (utilizador == null) {
					System.out.println("Utilizador com número mecanográfico " + num + " não foi encontrado.");
					continue;
				}

				break;
			}

			Veiculo veiculo;
			while (true) {
				System.out.print("Introduza o ID do veículo: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				veiculo = procurarVeiculo(veiculos, id);
				if (veiculo == null) {
					System.out.println("Veículo com ID " + id + " não foi encontrado.");
					continue;
				}

				break;
			}

			boolean temCapacete;
			while (true) {
				System.out.print("Deseja também alugar um capacete? (S/N): ");
				String temCapaceteLetra = scanner.nextLine();
				temCapacete = false;
				if (temCapaceteLetra.equalsIgnoreCase("S"))
					temCapacete = true;
				else if (temCapaceteLetra.equalsIgnoreCase("N"))
					temCapacete = false;
				else {
					System.out.println("Só se aceita S/N como resposta.");
					continue;
				}

				break;
			}

			boolean temLuz;
			while (true) {
				System.out.print("Deseja também alugar uma luz? (S/N): ");
				String temLuzLetra = scanner.nextLine();
				temLuz = false;
				if (temLuzLetra.equalsIgnoreCase("S"))
					temLuz = true;
				else if (temLuzLetra.equalsIgnoreCase("N"))
					temLuz = false;
				else {
					System.out.println("Só se aceita S/N como resposta.");
					continue;
				}

				break;
			}

			boolean diario;
			while (true) {
				System.out.print("Deseja que o aluguer seja diário? (S/N): ");
				String diarioLetra = scanner.nextLine();
				diario = false;
				if (diarioLetra.equalsIgnoreCase("S"))
					diario = true;
				else if (diarioLetra.equalsIgnoreCase("N"))
					diario = false;
				else {
					System.out.println("Só se aceita S/N como resposta.");
					continue;
				}

				break;
			}

			int dia, mes, ano, hora, minuto;
			while (true) {
				Data dataInicio;
				while (true) {
					System.out.print("Introduza o dia (ex.: 31 de ...) do início do aluguer: ");
					dia = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza o número do mês do início do aluguer: ");
					mes = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza o ano do início do aluguer: ");
					ano = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza a hora (formato 24 horas) do início do aluguer: ");
					hora = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza o minuto do início do aluguer: ");
					minuto = scanner.nextInt();
					scanner.nextLine();

					dataInicio = new Data(dia, mes, ano, hora, minuto);
					if (dataInicio.dataEInvalida())
						continue;

					break;
				}

				Data dataFim;
				while (true) {
					System.out.print("Introduza o dia (ex.: 31 de ...) do fim do aluguer: ");
					dia = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza o número do mês do fim do aluguer: ");
					mes = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza o ano do fim do aluguer: ");
					ano = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza a hora (formato 24 horas) do fim do aluguer: ");
					hora = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Introduza o minuto do fim do aluguer: ");
					minuto = scanner.nextInt();
					scanner.nextLine();

					dataFim = new Data(dia, mes, ano, hora, minuto);
					if (dataFim.dataEInvalida())
						continue;

					break;
				}

				aluguer = new Aluguer(utilizador, veiculo, temCapacete, temLuz, diario, dataInicio, dataFim);
				if (aluguer.datasSaoInvalidas())
					continue;
				if (veiculo.existemConflitosDatas(aluguer))
					continue;

				break;
			}

			utilizador.adicionarAluguer(aluguer);
			veiculo.adicionarAluguer(aluguer);
		} catch (InputMismatchException e) {
			System.out.println("Tipo lido não corresponde ao tipo esperado.");
			System.exit(1);
		}

		System.out.println("Aluguer lido:\n" + aluguer);
		return aluguer;
	}

	/**
	 * Permite criar múltiplos alugueres interativamente.
	 *
	 * @param utilizadores Lista carregada de utilizadores.
	 * @param veiculos     Lista carregada de veículos.
	 * @param scanner      Scanner de input do utilizador.
	 * @return Lista de alugueres criados.
	 */

	public ArrayList<Aluguer> criarAlugueres(ArrayList<Utilizador> utilizadores, ArrayList<Veiculo> veiculos,
			Scanner scanner) {
		System.out.println("\n----------------------\n");
		System.out.println("-- Utilizadores carregados --");
		for (Utilizador utilizador : utilizadores) {
			System.out.println(utilizador);
		}
		System.out.println("\n----------------------\n");
		System.out.println("-- Veículos carregados --");
		for (Veiculo veiculo : veiculos) {
			System.out.println(veiculo);
		}

		System.out.println("\n----------------------\n");
		int numAlugueres = 0;
		while (true) {
			try {
				System.out.print("Introduza o número de alugueres que deseja criar: ");
				numAlugueres = scanner.nextInt();
				scanner.nextLine();
				if (numAlugueres < 0) {
					System.out.println("Não pode inserir um número de alugueres negativos.");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("Tipo lido não corresponde ao tipo esperado.");
				System.exit(1);
			}
			break;
		}

		ArrayList<Aluguer> alugueres = new ArrayList<>();
		for (int i = 0; i < numAlugueres; i++) {
			System.out.println("\n-- Criando agora o aluguer número " + (i + 1) + "--");
			alugueres.add(criarAluguer(utilizadores, veiculos, scanner));
		}

		return alugueres;
	}

	/**
	 * Carrega os alugueres guardados num ficheiro binário.
	 *
	 * @param fichAlugueres     Ficheiro binário.
	 * @param nomeFichAlugueres Nome do ficheiro.
	 * @param utilizadores      Lista de utilizadores (atualizada com estes).
	 * @param veiculos          Lista de veículos (atualizada com estes).
	 * @return Lista de alugueres carregados.
	 */

	@SuppressWarnings("unchecked")
	public ArrayList<Aluguer> carregarAlugueres(File fichAlugueres, String nomeFichAlugueres,
			ArrayList<Utilizador> utilizadores, ArrayList<Veiculo> veiculos) {
		ArrayList<Aluguer> alugueres = new ArrayList<>();
		try {
			FileInputStream fileInputStream = new FileInputStream(fichAlugueres);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			alugueres = (ArrayList<Aluguer>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException e) {
			System.out.println("Erro ao ler o ficheiro \"" + nomeFichAlugueres + "\".");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out.println("Erro ao ler alugueres no ficheiro \"" + nomeFichAlugueres + "\".");
			System.exit(1);
		}

		// Os veículos e alugueres carregados com ficheiros não vêm com os alugueres que
		// estão inscritos. Este código adiciona os alugueres aos utilizadores e
		// veículos lidos no ficheiro de texto com os novos lidos no ficheiro de
		// alugueres. Caso estes não existiem nos ficheiros de textos, são incrementados
		// os ArrayList<>
		for (Aluguer aluguer : alugueres) {
			Utilizador utilizador = procurarUtilizador(utilizadores, aluguer.getUtilizador().getNumero());
			Veiculo veiculo = procurarVeiculo(veiculos, aluguer.getVeiculo().getId());

			if (utilizador != null)
				utilizador.adicionarAluguer(aluguer);
			else
				utilizadores.add(aluguer.getUtilizador());

			if (veiculo != null)
				veiculo.adicionarAluguer(aluguer);
			else
				veiculos.add(aluguer.getVeiculo());
		}

		return alugueres;

	}

	/**
	 * Lista todos os alugueres na consola, indicando o preço total final.
	 *
	 * @param alugueres Lista de alugueres.
	 */

	public void listarAlugueres(ArrayList<Aluguer> alugueres) {
		int aluguerNum = 1;
		double precoFinal = 0;

		System.out.println("\n----------------------\n");
		System.out.println("-- Lista de todos os alugueres --");
		for (Aluguer aluguer : alugueres) {
			System.out.println("Aluguer N.º " + aluguerNum + ":");
			System.out.println(aluguer);
			System.out.println();
			precoFinal += aluguer.precoFinal();
			aluguerNum++;
		}

		// Para arredondar o resultado final
		precoFinal = Math.round(precoFinal * 100);
		precoFinal /= 100;

		System.out.println("Preço total dos alugueres: " + precoFinal + "€");
	}

	public void aluguerMaiorPreco(ArrayList<Aluguer> alugueres) {
		Aluguer aluguerMaiorPreco = null;
		for (Aluguer aluguer : alugueres) {
			double preco = aluguer.precoFinal();
			if (aluguerMaiorPreco == null || aluguerMaiorPreco.precoFinal() < preco)
				aluguerMaiorPreco = aluguer;
		}
		System.out.println("\n----------------------\n");
		System.out.println("-- Aluguer com maior preço --");
		System.out.println(aluguerMaiorPreco);
	}

	/**
	 * Guarda os alugueres num ficheiro binário.
	 *
	 * @param alugueres         Lista de alugueres.
	 * @param fichAlugueres     Ficheiro destino.
	 * @param nomeFichAlugueres Nome do ficheiro.
	 */

	public void terminar(ArrayList<Aluguer> alugueres, File fichAlugueres, String nomeFichAlugueres) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fichAlugueres);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(alugueres);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao criar o ficheiro \"" + nomeFichAlugueres + "\".");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Erro ao escrever no ficheiro \"" + nomeFichAlugueres + "\".");
			System.exit(1);
		}
	}
}
