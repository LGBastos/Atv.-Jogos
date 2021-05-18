package projeto_games;

import java.util.Scanner;

public class Forca {

	static Scanner ed = new Scanner(System.in);

	public static void inicio() {
		// Método para iniciar o jogo
		int erro = 0;
		String patent = "", letras = "";
		String palavra = palavraOculta();
		desenho(erro);
		sublin(palavra);
		while (true) {
			char letra = letra();
			if (letras.indexOf(letra) >= 0) {

				System.out.println("Você já tentou essa letra!");

			} else {
				letras += letra;
				if (palavra.indexOf(letra) >= 0) {
					System.out.println("Você acertou!");
					desenho(erro);
					String vz = "";
					for (int i = 0; i < palavra.length(); i++) {

						vz += letras.indexOf(palavra.charAt(i)) >= 0 ? (palavra.charAt(i)) : "_ ";
						patent = vz;

					}
					System.out.println(patent);

					if (patent.equals(palavra)) {
						System.out.println("Fim de Jogo");
						break;
					}

				} else {
					erro++;
					System.out.println("Você errou!\nRestam apenas " + (6 - erro) + " tentativas.");
					desenho(erro);
					System.out.println(patent);
					if (erro == 6) {
						System.out.println("Fim de Jogo");
						System.out.println("A palavra completa era " + palavra);
						break;
					}
				}
			}
		}
		jogVenc(erro);
	}

	public static void sublin(String palavra) {
		// Método para criar uma quantidade de sublinhas igual a quantidade de letras da
		// palavra oculta,
		// servindo como dica para o JOGADOR2
		for (int i = 0; i < palavra.length(); i++) {
			System.out.print("_ ");
		}
	}

	public static char letra() {
		// Método que captura as letras tentadas pelo JOGADOR2
		System.out.println("\n JOGADOR2\n----------\nInforme a letra:");
		char letra = ed.next().toUpperCase().charAt(0);
		return letra;
	}

	public static void jogVenc(int erro) {
		// Método para definir qual foi o jogador vencedor
		if (erro == 6) {
			System.out.println("O JOGADOR1 venceu!");
		} else {
			System.out.println("O JOGADOR2 venceu!");
		}
	}

	public static String palavraOculta() {
		// Método para obter a palavra oculta do jogador 1
		System.out.println(" JOGADOR1\n----------\nInforme a palavra a ser adivinhada pelo JOGADOR2:");
		String palavra = ed.nextLine();
		palavra = palavra.toUpperCase();
		return palavra;
	}

	public static void desenho(int erro) {
		// Método para desenhar a forca a depender da progressão do erro
		String des = "";
		switch (erro) {
		case 1:
			des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     \r\n" + " |     \r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);
			break;
		case 2:
			des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |      |\r\n" + " |     \r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);
			break;
		case 3:
			des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |      |\\\r\n" + " |     \r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);

			break;
		case 4:
			des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     /|\\\r\n" + " |     \r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);

			break;
		case 5:
			des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     /|\\\r\n" + " |     /\r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);

			break;
		case 6:
			des = ("  ______\r\n" + " |      |\r\n" + " |      O\r\n" + " |     /|\\\r\n" + " |     /\\\r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);

			break;
		default:
			des = ("  ______\r\n" + " |      |\r\n" + " |      \r\n" + " |     \r\n" + " |     \r\n" + " |\r\n"
					+ " |	\r\n" + "_|___");
			System.out.println(des);

			break;
		}
	}

}
