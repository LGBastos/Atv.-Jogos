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
		vidaP1 = posInput(nicks[0], sc, posicao, vidaP1);
		vidaP2 = posInput(nicks[1], sc, posicao2, vidaP2);
		GAME_ON = true;
		while(GAME_ON) {
			//turno do player 1
			vidaP2 = gameTurn(nicks[0], memoria, posicao2,nicks[1],vidaP2, vidaP1, sc);
			//turno do player 2
			vidaP1 = gameTurn(nicks[1], memoria2, posicao,nicks[0], vidaP1, vidaP2, sc);
			
		}



	}

	private static void gameEnd(String string) {
		imprimir("Vit�ria de "+string+"!!!");
		GAME_ON = false;
		vidaP1 = 0;
		vidaP2 = 0;
		

	}

	private static int posInput(String apelido, Scanner sc, char[][] posicao, int vida) {
//		(1) porta-avi�es (cinco quadrados), (2) navios-tanque (quatro quadrados), 
//		(3) contratorpedeiros (tr�s quadrados) e (4) submarinos (dois quadrados)
		int naviosCont = 4;
		int input = -1;
		int navios = 4;
		int[] cont = {1, 2, 3, 4};
		//cuida para que 2 navios n�o ocupem um mesmo ponto
		int[][]memoriaTemporariaDePosicao = new int[10][10];
		
		while(naviosCont!=0||input==-1) {
			imprimir(apelido +" Escolha qual navio deseja posicionar:");
			for(int j = 1 ; j<=naviosCont ; j++) {
					
				switch (cont[j-1]) {
				case (1):
					imprimir("("+j+") porta-avi�es (cinco quadrados)" );
					break;
				case (2):
					imprimir("("+j+") navios-tanque (quatro quadrados)");
					break;
				case(3):
					imprimir("("+j+") contratorpedeiros (tr�s quadrados)");
					break;
				case(4):
					imprimir("("+j+")  submarinos (dois quadrados)");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + j);
				}
			}
			input = sc.nextInt();
			if(input<=0||input>naviosCont) {
				imprimir("Op��o inv�lida!");
				continue;
			}
			int escolha = cont[input-1];
			
			for(int i=input-1 ; i< navios-1 ; i++) {
				cont[i]=cont[i+1];
			}
				
			
			
			switch (escolha) {
			case (1):
				vida = definirPosicao(apelido, "porta-avi�es", 5, sc, posicao, vida,memoriaTemporariaDePosicao);
				System.out.println(vida);
				naviosCont--;
				break;
			case (2):
				vida = definirPosicao(apelido, "navios-tanque", 4, sc, posicao, vida,memoriaTemporariaDePosicao);
				System.out.println(vida);
				naviosCont--;
				break;
			case(3):
				vida = definirPosicao(apelido, "contratorpedeiros", 3, sc, posicao, vida,memoriaTemporariaDePosicao);
				System.out.println(vida);
				naviosCont--;
				break;
			case(4):
				vida = definirPosicao(apelido, "submarinos ", 2, sc, posicao, vida,memoriaTemporariaDePosicao);
				System.out.println(vida);
				naviosCont--;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + input);
			}
		}
		
		return vida;
		
		

	}
	
	//coloca 'X' nos pontos entre pontoInicial e pontoFinal
	//incrementa a vida passada como parametro em 1, para cada 'X' marcado no array
	//retorna o valor final da vida
	private static int definirPosicao(String apelido, String tipoNavio, int tamanho, Scanner sc, char[][] posicao, int vida, int[][] memoriaTemporariaDePosicao) {
				imprimir(apelido+" definia a posi�ao do "+tipoNavio+".\n"
						+ "Escolha o ponto de inicio, ex: 0 5");
				int[] pontoInicial = checarPontoInicial(sc, memoriaTemporariaDePosicao);

				
				
				int[] pontoFinal = checarPosicoesValidas(pontoInicial, sc, tamanho, memoriaTemporariaDePosicao);
				while(pontoFinal[0]==-1) {
					imprimir("Escolha um novo ponto de inicio:");
					pontoInicial = checarPontoInicial(sc, memoriaTemporariaDePosicao);
					pontoFinal = checarPosicoesValidas(pontoInicial, sc, tamanho, memoriaTemporariaDePosicao);
				}
				
				int distancia, index0, index1;
				//index = 1 quando pontoFinal for maior que inicial e -1 quando for menor.
				if(pontoInicial[0]!=pontoFinal[0]) {
					//se a diferen�a for na linha
					distancia = pontoFinal[0]-pontoInicial[0];
					if(distancia>0) {
						index0 = 1;
					}else {
						index0 = -1;
						distancia *= -1;
					}
					index1 = 0;
				}else {
					// se a diferen�a for na coluna
					distancia = pontoFinal[1]-pontoInicial[1];
					if(distancia>0) {
						index1 = 1;
					}else {
						index1 = -1;
						distancia *= -1;
					}
					index0 = 0;

				}
				//Quando index = 0 n�o altera a linha/coluna, quando index=1 come�a no ponto 
				//Final e diminui at� o Inicial, quando index=-1 come�a no ponto Final e aumenta at� o Inicial
				for (int i = 0; i <= distancia; i++) {
					posicao[pontoFinal[0]+(-i*index0)][pontoFinal[1]+(-i*index1)]= 'X';
					memoriaTemporariaDePosicao[pontoFinal[0]+(-i*index0)][pontoFinal[1]+(-i*index1)]=1;
					drawBoard(memoriaTemporariaDePosicao, posicao);
					vida++;
				}
				return vida;
		
	}

	private static int[] checarPontoInicial(Scanner sc, int[][] memoriaTemporariaDePosicao) {
		int[] pontoInicial = new int[2];
		pontoInicial[0] = sc.nextInt();
		pontoInicial[1] = sc.nextInt();
		//dentro do while � usado apenas 1 simbolo "||" , pois caso um dos valores estiver fora do range, ele passa no primeiro teste e nao
		//entra no segundo(o que causaria  java.lang.ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 10) 
		while(!(pontoInicial[0]<=9&&pontoInicial[0]>=0&&pontoInicial[1]<=9&&pontoInicial[1]>=0)||memoriaTemporariaDePosicao[pontoInicial[0]][pontoInicial[1]]==1){
			imprimir("Valor inv�lido!");
			if(!(pontoInicial[0]<=9&&pontoInicial[0]>=0&&pontoInicial[1]<=9&&pontoInicial[1]>=0)) {
				imprimir("Digite um valor entre 0 e 9:");
				pontoInicial[0] = sc.nextInt();
				pontoInicial[1] = sc.nextInt();
			}else {
				imprimir("Existe um navio posicionado nesse ponto.\n"
						+ "Escolha outro ponto inicial: ");
				pontoInicial[0] = sc.nextInt();
				pontoInicial[1] = sc.nextInt();
			}
		}
		
		
		return pontoInicial;
	}

	private static void createBoard(char[][] posicao) {
		for (int i = 0; i < posicao.length; i++) {
			for (int j = 0; j < posicao.length; j++) {
				posicao[i][j]='O';

			}
		}		
	}

	private static int gameTurn(String apelido, int[][] memoria, char[][] posicao,String apelidoOponente, int vidaOponente, int vida, Scanner sc) {
		if(GAME_ON==false) return 0;
		
		imprimir(apelido+"  HP:"+vida+"\n"
				+ apelidoOponente +" HP:"+vidaOponente );
		
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
		imprimir(apelido + " escolha onde deseja atirar, ex: 5 5");
		drawBoard(memoria, posicao);

		int[] tiro = {sc.nextInt(),sc.nextInt()};
		while(memoria[tiro[0]][tiro[1]]==1) {
			imprimir("J� atirou em : "+tiro[0]+" "+tiro[1]+"\n"
					+ "escolha outro alvo:");
			drawBoard(memoria, posicao);
			tiro[0]=sc.nextInt();
			tiro[1]=sc.nextInt();
		}
		memoria[tiro[0]][tiro[1]]=1;
		int vidaAtt = drawBoard(tiro, memoria, posicao, vidaOponente);
		if(vidaAtt==0) {
			gameEnd(apelido);
		}else if(vidaAtt<vidaOponente) {
			imprimir("Acertou!\n"
					+ "Jogue novamente:");
			
			vidaAtt = gameTurn(apelido, memoria, posicao,apelidoOponente, vidaAtt, vida, sc);
		}
		return vidaAtt;



	}
	// TODO trocar para checarTiro e n�o desenhar, somente checar se acertou
	private static int drawBoard(int[] tiro, int[][] memoria, char[][] posicao, int vida) {
		boolean hit = false;
		for (int i = 0; i < 11; i++) {
			
			
//			for (int j = 0; j < 41; j++) {
//				System.out.print("-");
//			}
//			System.out.println();
		
		
			
			if(i<10) {
				for (int j = 0; j < 10; j++) {
					if(memoria[i][j]==1) {
//						System.out.print("| "+posicao[i][j]+" ");
						if (posicao[i][j]=='X'&i==tiro[0]&&j==tiro[1]) {
							vida--;
							hit = true;
						}
					}else {
//						System.out.print("|   ");
						
					}

				}
//				System.out.println();

			}
		}
		if(!hit) {
			imprimir("�gua!");			
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
	
	
	//imprime as posi�oes poss�veis
	//recebe o input e avalia se � uma op��o v�lida
	//retorna um array[2] com o ponto final do navio ou um array={-1,0} caso n�o existam op��es v�lidas.
	private static int[] checarPosicoesValidas(int[] posicaoInicial, Scanner sc, int tamanhoNavio, int[][] memoriaTemporariaDePosicao) {

		int paraEsquerda = posicaoInicial[1]-tamanhoNavio+1;
		int paraDireita = posicaoInicial[1]+tamanhoNavio-1;
		int paraCima = posicaoInicial[0]-tamanhoNavio+1;
		int paraBaixo = posicaoInicial[0]+tamanhoNavio-1;
		int posicaoEscolhida, cont = 0;
		int[][] posicoesValidas = new int[4][2];
		boolean[] check= {false, false, false, false};
		
		do {
			if(cont>0) {
				imprimir("Op��o inv�lida!");
				cont = 0;
			}
			imprimir("Agora, escolha entre os poss�veis pontos de fim: ");
			if(paraEsquerda>=0) {
				for(int i = posicaoInicial[1]; i>=paraEsquerda ; i--) {
					if(memoriaTemporariaDePosicao[posicaoInicial[0]][i]==1) {
						check[0]= false;
						break;
					}else{
						check[0]= true;
					}
				}
				if(check[0]) {
					posicoesValidas[cont][0]=posicaoInicial[0];
					posicoesValidas[cont][1]=paraEsquerda;
					cont++;
					imprimir("("+cont+")virado para esquerda:"+posicaoInicial[0] + " " + paraEsquerda);
					//			posicoesValidas[0][0]=paraEsquerda;
					//			posicoesValidas[0][1]=posicaoInicial[1];
				}
			}
			if(paraDireita<=9) {
				for(int i = posicaoInicial[1]; i<=paraDireita ; i++) {
					if(memoriaTemporariaDePosicao[posicaoInicial[0]][i]==1) {
						check[1] = false;
						break;
					}else{
						check[1]= true;
					}
				}
				if(check[1]) {
					posicoesValidas[cont][0]=posicaoInicial[0];
					posicoesValidas[cont][1]=paraDireita;
					cont++;
					imprimir("("+cont+")virado para direita:"+posicaoInicial[0] + " " + paraDireita);
					//			posicoesValidas[1][0]=paraDireita;
					//			posicoesValidas[1][1]=posicaoInicial[1];
					
				}
			}
			if(paraCima>=0) {
				for(int i = posicaoInicial[0]; i>=paraCima; i--) {
					if(memoriaTemporariaDePosicao[i][posicaoInicial[1]]==1) {
						check[2] = false;
						break;
					}else{
						check[2]= true;
					}
				}
				if(check[2]) {
					posicoesValidas[cont][0]=paraCima;
					posicoesValidas[cont][1]=posicaoInicial[1];
					cont++;
					imprimir("("+cont+")virado para cima:"+paraCima + " " + posicaoInicial[1]);
					//			posicoesValidas[2][0]=paraCima;
					//			posicoesValidas[2][1]=posicaoInicial[1];
					
				}
			}
			if(paraBaixo<=9) {
				for(int i = posicaoInicial[0]; i<=paraBaixo; i++) {
					if(memoriaTemporariaDePosicao[i][posicaoInicial[1]]==1) {
						check[3] = false;
						break;
					}else{
						check[3]= true;
					}
				}
				if(check[3]) {
					posicoesValidas[cont][0]=paraBaixo;
					posicoesValidas[cont][1]=posicaoInicial[1];
					cont++;
					imprimir("("+cont+")virado para baixo:"+paraBaixo + " " + posicaoInicial[1]);
					//			posicoesValidas[3][0]=paraBaixo;
					//			posicoesValidas[3][1]=posicaoInicial[1];
					
					
				}
			}
			if(!(check[0]||check[1]||check[2]||check[3])) {
				imprimir("N�o existem posi��es v�lidas!\n");
				int[] checkout = {-1,0};
				
				return checkout;
			}
			
			posicaoEscolhida = sc.nextInt();
		}while(posicaoEscolhida<=0||posicaoEscolhida>cont);

		return posicoesValidas[posicaoEscolhida-1];
	}

	private static void imprimir(String string) {
		System.out.println(string);

	}

}
