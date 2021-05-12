package projeto_games;
import java.util.Scanner;
//TODO Implementar uma pausa entre os turnos?



public class BatalhaNaval {
	public static boolean GAME_ON;
	public static int vidaP1 = 3, vidaP2 = 3;
	public static void inicio() {
		Scanner sc = new Scanner(System.in);
		
		String[] nicks = new String[2];
		char[][] posicao = new char[10][10];
		char[][] posicao2 = new char[10][10];
		int[][] memoria = new int[10][10];
		int[][] memoria2 = new int[10][10];
		createBoard(posicao);
		createBoard(posicao2);
		imprimir("Escolha o apelido do Player 1:");
		nicks[0]=sc.nextLine();
		imprimir("Escolha o apelido do Player 2:");
		nicks[1]=sc.nextLine();
		posInput(nicks[0], sc, posicao);
		posInput(nicks[1], sc, posicao2);
		gameStart(nicks, memoria, memoria2, posicao, posicao2,vidaP1, vidaP2, sc);


	}

	private static void gameStart(String[] nicks, int[][] memoria, int[][] memoria2, char[][] posicao, char[][] posicao2, int vidaP1, int vidaP2, Scanner sc) {
		
		GAME_ON = true;
		while(GAME_ON) {
			//turno do player 1
			vidaP2 = gameTurn(nicks[0], memoria, posicao2,vidaP2, sc);
			//turno do player 2
			vidaP1 = gameTurn(nicks[1], memoria2, posicao, vidaP1, sc);
			
		}



	}

	private static void gameEnd(String string) {
		imprimir("Vitória de "+string+"!!!");
		//TODO quando adicionar os outros jogos, não pode sair, precisa voltar para o menu;
		GAME_ON = false;

	}

	private static void posInput(String apelido, Scanner sc, char[][] posicao) {
		// TODO criar switch case para cada navio
		//coloca X nos pontos entre pontoInicial e pontoFinal
		imprimir(apelido+" definia a posiçao do porta aviões.\n"
				+ "Escolha o ponto de inicio, ex: 0 5");
		int[] pontoInicial = checarPontoInicial(sc);

		
		imprimir("Agora, escolha entre os possíveis pontos de fim: ");
		int[] pontoFinal = checarPosicoesValidas(pontoInicial, sc);

		// TODO testar: o que acontece quando pontoFinal<pontoInicial
		int distancia, index0, index1;
		if(pontoInicial[0]!=pontoFinal[0]) {
			//se a diferença for na linha
			distancia = pontoFinal[0]-pontoInicial[0];
			if(distancia>0) {
				index0 = 1;
			}else {
				index0 = -1;
				distancia *= -1;
			}
			index1 = 0;
		}else {
			// se a diferença for na coluna
			distancia = pontoFinal[1]-pontoInicial[1];
			index0 = 0;
			if(distancia>0) {
				index1 = 1;
			}else {
				index1 = -1;
				distancia *= -1;
			}

		}
		//TODO testar i<distancia
		for (int i = 0; i <= distancia; i++) {
			posicao[pontoFinal[0]+(-i*index0)][pontoFinal[1]+(-i*index1)]= 'X';
		}

	}
	private static int[] checarPontoInicial(Scanner sc) {
		int[] pontoInicial = new int[2];
		do {
			pontoInicial[0] = sc.nextInt();
			pontoInicial[1] = sc.nextInt();
		}while(pontoInicial[0]<9&&pontoInicial[0]>0&&pontoInicial[1]<9&&pontoInicial[1]>0);
		
		
		return pontoInicial;
	}

	private static void createBoard(char[][] posicao) {
		for (int i = 0; i < posicao.length; i++) {
			for (int j = 0; j < posicao.length; j++) {
				posicao[i][j]='O';

			}
		}		
	}

	private static int gameTurn(String string, int[][] memoria, char[][] posicao, int vida, Scanner sc) {
		if(GAME_ON==false) return 0;
		
		imprimir("vidaP1="+vidaP1+"vidaP2="+vidaP2);
		//		int[] tiro= {-1,0};
		//		do{
		//			if(tiro[0]!=-1) {
		//				memoria[tiro[0]][tiro[1]]=1;				
		//			}
		//			for (int i = 0; i < 11; i++) {
		//				for (int j = 0; j < 41; j++) {
		//					System.out.print("-");
		//				}
		//				
		//				System.out.println();
		//				if(i<10) {
		//					for (int j = 0; j < 11; j++) {
		//						if(j<10&&memoria[i][j]==1) {
		//							System.out.print("| "+posicao[i][j]+" ");
		//						}else {
		//							System.out.print("|   ");
		//						}
		//						
		//					}
		//					System.out.println();
		//					
		//				}
		//				
		//			}
		//			
		//			tiro[0] = sc.nextInt();
		//			tiro[1] = sc.nextInt();
		//		}while(tiro[0]!=-1);
		imprimir(string + " escolha onde deseja atirar, ex: 5 5");
		drawBoard(memoria, posicao);

		int[] tiro = {sc.nextInt(),sc.nextInt()};
		memoria[tiro[0]][tiro[1]]=1;
		int vidaAtt = drawBoard(tiro, memoria, posicao, vida);
		if(vidaAtt==0) {
			gameEnd(string);
		}else if(vidaAtt<vida) {
			imprimir("Acertou!\n"
					+ "Jogue novamente:");
			
			gameTurn(string, memoria, posicao, vidaAtt, sc);
		}
		return vidaAtt;



	}
	// TODO trocar para checarTiro e não desenhar, somente checar se acertou
	private static int drawBoard(int[] tiro, int[][] memoria, char[][] posicao, int vida) {
		boolean hit = false;
		for (int i = 0; i < 11; i++) {
			
			
			for (int j = 0; j < 41; j++) {
				System.out.print("-");
			}
			System.out.println();
		
		
			
			if(i<10) {
				for (int j = 0; j < 10; j++) {
					if(memoria[i][j]==1) {
						System.out.print("| "+posicao[i][j]+" ");
						if (posicao[i][j]=='X'&i==tiro[0]&&j==tiro[1]) {
							vida--;
							hit = true;
						}
					}else {
						System.out.print("|   ");
						
					}

				}
				System.out.println();

			}
		}
		if(!hit) {
			imprimir("Água!");			
		}
		
		return vida;
		
	}
	private static void drawBoard(int[][] memoria, char[][] posicao) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 41; j++) {
				System.out.print("-");
			}

			System.out.println();
			if(i<10) {
				for (int j = 0; j < 11; j++) {
					if(j<10&&memoria[i][j]==1) {
						System.out.print("| "+posicao[i][j]+" ");
						
					}else {
						System.out.print("|   ");
					}

				}
				System.out.println();

			}
		}
		
	}
	
	
	
	private static int[] checarPosicoesValidas(int[] posicaoInicial, Scanner sc) {
		int tamanhoNavio = 3;
		int paraEsquerda = posicaoInicial[1]-tamanhoNavio+1;
		int paraDireita = posicaoInicial[1]+tamanhoNavio-1;
		int paraCima = posicaoInicial[0]-tamanhoNavio+1;
		int paraBaixo = posicaoInicial[0]+tamanhoNavio-1;
		int posicaoEscolhida, cont = 0;
		int[][] posicoesValidas = new int[4][2];
		do {
			if(cont>0) {
				imprimir("Selecione uma opção válida: ");
				cont = 0;
			}
			if(paraEsquerda>=0) {
				posicoesValidas[cont][0]=posicaoInicial[0];
				posicoesValidas[cont][1]=paraEsquerda;
				cont++;
				imprimir("("+cont+")virado para esquerda:"+posicaoInicial[0] + " " + paraEsquerda);
				//			posicoesValidas[0][0]=paraEsquerda;
				//			posicoesValidas[0][1]=posicaoInicial[1];
			}
			if(paraDireita<=9) {
				posicoesValidas[cont][0]=posicaoInicial[0];
				posicoesValidas[cont][1]=paraDireita;
				cont++;
				imprimir("("+cont+")virado para direita:"+posicaoInicial[0] + " " + paraDireita);
				//			posicoesValidas[1][0]=paraDireita;
				//			posicoesValidas[1][1]=posicaoInicial[1];
			}
			if(paraCima>=0) {
				posicoesValidas[cont][0]=paraCima;
				posicoesValidas[cont][1]=posicaoInicial[1];
				cont++;
				imprimir("("+cont+")virado para cima:"+paraCima + " " + posicaoInicial[1]);
				//			posicoesValidas[2][0]=paraCima;
				//			posicoesValidas[2][1]=posicaoInicial[1];
			}
			if(paraBaixo<=9) {
				posicoesValidas[cont][0]=paraBaixo;
				posicoesValidas[cont][1]=posicaoInicial[1];
				cont++;
				imprimir("("+cont+")virado para baixo:"+paraBaixo + " " + posicaoInicial[1]);
				//			posicoesValidas[3][0]=paraBaixo;
				//			posicoesValidas[3][1]=posicaoInicial[1];
			}
			posicaoEscolhida = sc.nextInt()-1;
		}while(posicaoEscolhida<0||posicaoEscolhida>cont);

		return posicoesValidas[posicaoEscolhida];
	}

	private static void imprimir(String string) {
		System.out.println(string);

	}

}
