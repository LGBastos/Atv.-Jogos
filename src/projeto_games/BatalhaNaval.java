package projeto_games;
import java.util.Scanner;
//TODO Implementar uma pausa entre os turnos?



public class BatalhaNaval {
	public static boolean GAME_ON;
	public static int vidaP1, vidaP2;
	public static void inicio() {
		Scanner sc = new Scanner(System.in);
		
		String[] nicks = new String[2];
		char[][] posicao = new char[10][10];
		char[][] posicao2 = new char[10][10];
		int[][] memoria = new int[10][10];
		int[][] memoria2 = new int[10][10];
		
		gameStart(nicks, memoria, memoria2, posicao, posicao2,sc);
		
	}

	private static void gameStart(String[] nicks, int[][] memoria, int[][] memoria2, char[][] posicao, char[][] posicao2, Scanner sc) {
		createBoard(posicao);
		createBoard(posicao2);
		imprimir("Escolha o apelido do Player 1:");
		nicks[0]=sc.nextLine();
		imprimir("Escolha o apelido do Player 2:");
		nicks[1]=sc.nextLine();
		posInput(nicks[0], sc, posicao, vidaP1);
		posInput(nicks[1], sc, posicao2, vidaP2);
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
		GAME_ON = false;
		vidaP1 = 0;
		vidaP2 = 0;
		

	}

	private static void posInput(String apelido, Scanner sc, char[][] posicao, int vida) {
//		(1) porta-aviões (cinco quadrados), (2) navios-tanque (quatro quadrados), 
//		(3) contratorpedeiros (três quadrados) e (4) submarinos (dois quadrados)
		int naviosCont = 4;
		int navios = 4;
		int[] cont = {1, 2, 3, 4};
		
		while(naviosCont!=0) {
			imprimir(apelido +" escolha qual navio deseja posicionar:");
			for(int j = 1 ; j<=naviosCont ; j++) {
					
				switch (cont[j-1]) {
				case (1):
					imprimir("("+j+") porta-aviões (cinco quadrados)" );
					break;
				case (2):
					imprimir("("+j+") navios-tanque (quatro quadrados)");
					break;
				case(3):
					imprimir("("+j+") contratorpedeiros (três quadrados)");
					break;
				case(4):
					imprimir("("+j+")  submarinos (dois quadrados)");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + j);
				}
			}
			int input = sc.nextInt();
			int escolha = cont[input-1];
			
			for(int i=input-1 ; i< navios-1 ; i++) {
				cont[i]=cont[i+1];
			}
				
			
			
			switch (escolha) {
			case (1):
				definirPosicao(apelido, "porta-aviões", 5, sc, posicao, vida);
				System.out.println(vida);
				naviosCont--;
				break;
			case (2):
				definirPosicao(apelido, "navios-tanque", 4, sc, posicao, vida);
				System.out.println(vida);
				naviosCont--;
				break;
			case(3):
				definirPosicao(apelido, "contratorpedeiros", 3, sc, posicao, vida);
				System.out.println(vida);
				naviosCont--;
				break;
			case(4):
				definirPosicao(apelido, "submarinos ", 2, sc, posicao, vida);
				System.out.println(vida);
				naviosCont--;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}
		
		

	}
	
	private static void definirPosicao(String apelido, String tipoNavio, int tamanho, Scanner sc, char[][] posicao, int vida) {
		//coloca X nos pontos entre pontoInicial e pontoFinal
				imprimir(apelido+" definia a posiçao do "+tipoNavio+".\n"
						+ "Escolha o ponto de inicio, ex: 0 5");
				int[] pontoInicial = checarPontoInicial(sc);

				
				imprimir("Agora, escolha entre os possíveis pontos de fim: ");
				int[] pontoFinal = checarPosicoesValidas(pontoInicial, sc, tamanho);

				
				int distancia, index0, index1;
				//index = 1 quando pontoFinal for maior que inicial e -1 quando for menor.
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
				//Quando index = 0 não altera a linha/coluna, quando index=1 começa no ponto 
				//Final e diminui até o Inicial, quando index=-1 começa no ponto Final e aumenta até o Inicial
				for (int i = 0; i <= distancia; i++) {
					posicao[pontoFinal[0]+(-i*index0)][pontoFinal[1]+(-i*index1)]= 'X';
					vida++;
				}
		
	}

	private static int[] checarPontoInicial(Scanner sc) {
		int[] pontoInicial = new int[2];
		do {
			pontoInicial[0] = sc.nextInt();
			pontoInicial[1] = sc.nextInt();
		}while(!(pontoInicial[0]<=9&&pontoInicial[0]>=0&&pontoInicial[1]<=9&&pontoInicial[1]>=0));
		
		
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
	
	
	
	private static int[] checarPosicoesValidas(int[] posicaoInicial, Scanner sc, int tamanhoNavio) {

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
