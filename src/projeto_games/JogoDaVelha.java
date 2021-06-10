package projeto_games;
import java.util.Scanner;

import static java.lang.System.out;

public class JogoDaVelha {
	private final char[][] tabuleiro = new char[3][3];
	private final static Scanner sc = new Scanner(System.in);
	public JogoDaVelha() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tabuleiro[i][j] = ' ';
			}
		}
	}

	public void mostrar() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println();
		}
	}
	private void drawBoard(char[][] posicao) {
//		out.print("  ");
//		for (int j = 0; j <posicao.length; j++) {
//			out.print("   "+j+"  "); imprimiria o numero das colunas
//		}
		out.println();
		for (int i = 0; i < posicao.length+1; i++) {
			out.print("  ");
			for (int j = 0; j < posicao.length*6+1; j++) {
				out.print("-");
			}

			out.println();
			if(i<posicao.length) {
//				out.print(i+" "); imprimiria os numeros das linhas
				for (int j = 0; j < posicao.length+1; j++) {
					if(j<posicao.length) {
						out.print("|  "+posicao[i][j]+"  ");

					}

				}
				out.println("|");

			}
		}
		out.println("\n");

	}
	public char verificar() {
		for (int i = 0; i < 3; i++) {
			if ((tabuleiro[i][0] == tabuleiro[i][1]) &&
				(tabuleiro[i][0] == tabuleiro[i][2])) {
				if (tabuleiro[i][0] != 0) {
					return tabuleiro[i][0];
				}
			}
		}
		for (int j = 0; j < 3; j++) {
			if ((tabuleiro[0][j] == tabuleiro[1][j]) &&
				(tabuleiro[0][j] == tabuleiro[2][j]) &&
				(tabuleiro[0][j] != 0)) {
				return tabuleiro[0][j];
			}
		}
		if ((tabuleiro[0][0] == tabuleiro[1][1]) &&
			(tabuleiro[0][0] == tabuleiro[2][2]) &&
			(tabuleiro[0][0] != 0)) {
			return tabuleiro[0][0];
		}
		if ((tabuleiro[0][2] == tabuleiro[1][1]) &&
			(tabuleiro[0][2] == tabuleiro[2][0]) &&
			(tabuleiro[0][2] != 0)) {
			return tabuleiro[0][2];
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tabuleiro[i][j] == ' ') {
					return ' ';
				}
			}
		}
		return 'E';
	}
	
	 boolean efetuarJogada(char jogador, int linha, int coluna) {
		if ((linha < 0) || (linha > 2)) {
			return false;
		}
		if ((coluna < 0) || (coluna > 2)) {
			return false;
		}
		if (tabuleiro[linha][coluna] != ' ') {
			return false;
		}
		tabuleiro[linha][coluna] = jogador;
		return true;
	}

	public static void inicio() {
		JogoDaVelha jogo = new JogoDaVelha();
		char jogador = 'X';
		char vencedor = jogo.verificar();
		jogo.drawBoard(jogo.tabuleiro);
		while (vencedor == ' ') {
			System.out.println("Jogador " + jogador);
			System.out.print("Linha: ");
			int linha = sc.nextInt();
			System.out.print("Coluna: ");
			int coluna = sc.nextInt();
			if (!jogo.efetuarJogada(jogador, linha-1, coluna-1)) {
				System.out.println("Jogada inválida...");
			} else {
				jogador = (jogador == 'X') ? 'O' : 'X';
			}
			jogo.drawBoard(jogo.tabuleiro);
			vencedor = jogo.verificar();
		}
		switch (vencedor) {
		case 'X' :
			System.out.println("Vencedor jogador 'X'");
			break;
		case 'O':
			System.out.println("Vencedor jogador 'O'");
			break;
		case 'E':
			System.out.println("Empate");
			break;
		}
		jogarNovamente();
	}
	public static void jogarNovamente() {
		// Método para repetição do método inicio
		System.out.println("\n-------------------------\nJogar novamente?\n(1)Sim\t(2)Não");
		int op = sc.nextInt();
		if (op == 1) {
			JogoDaVelha.inicio();
		}

	}
}