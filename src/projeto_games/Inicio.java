package projeto_games;

import java.util.Scanner;

public class Inicio {
	private static Scanner sc = new Scanner (System.in);
	public static void main(String[] args) {

		int option = 0;
		
		do {
			impressao("--------------------------------------");
			impressao("Qual jogo você deseja jogar?");
			impressao("(1) Jogo da Forca\n(2) Batalha Naval\n(3) Jogo da Velha\n(4) Sair");
			option = sc.nextInt();
			switch (option) {
			case 1:
				System.out.println(" \n"
						+ " ");
				System.out.println("JOGO DA FORCA INICIADO\n"
						+ "-----------------------------"); 
				Forca.inicio();
				break;

			case 2:
				BatalhaNaval.inicio();
				break;

			case 3:
				JogoDaVelha.inicio();
				break;

			case 4:
				System.out.println("Você saiu do jogo.");
				break;

			default:
				System.out.println("Número inválido");
				break;
			}
		} while (option != 4);
		

	}

	public static void impressao(String txt) {
		System.out.println(txt);

	}
}