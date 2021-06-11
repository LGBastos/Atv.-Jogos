package projeto_games;
import java.util.Scanner;

import static java.lang.System.*;



public class BatalhaNaval {
	public static boolean GAME_ON;
	public static int vidaP1, vidaP2;
	public static  final String[] nicks = new String[2];
	public static void inicio() {


		char[][] posicao1 = new char[10][10];
		char[][] posicao2 = new char[10][10];
		//cuida os pontos nos quais foram atirados
		int[][] memoria1 = new int[10][10];
		int[][] memoria2 = new int[10][10];

		gameStart(memoria1, memoria2, posicao1, posicao2);

	}

	private static void gameStart(int[][] memoria1, int[][] memoria2, char[][] posicao1, char[][] posicao2) {
		getNicks();
		createBoard(posicao1);
		createBoard(posicao2);
		vidaP1 = posicionamentoDeNavios(nicks[0], posicao1, vidaP1);
		vidaP2 = posicionamentoDeNavios(nicks[1], posicao2, vidaP2);
		GAME_ON = true;
		while(GAME_ON) {
			//turno do player 1
			gameTurn(nicks[0], memoria1, posicao2,nicks[1]);
			//turno do player 2
			gameTurn(nicks[1], memoria2, posicao1,nicks[0]);

		}



	}

	private static void getNicks() {
		Scanner sc = new Scanner(in);
		imprimir("Escolha o apelido para o Player 1:");
		nicks[0]= sc.nextLine();
		imprimir("Escolha o apelido para o Player 2:");
		nicks[1]= sc.nextLine();
	}

	private static void createBoard(char[][] posicao) {

		for (int i = 0; i < posicao.length; i++) {
			for (int j = 0; j < posicao.length; j++) {
				posicao[i][j]='O';

			}
		}
	}

	/*inicia o turno do jogador "apelido", caso o jogo ainda não tenha acabado*/
	private static void gameTurn(String apelido, int[][] memoria, char[][] posicao, String apelidoOponente) {
		if (GAME_ON) {
			shot(apelido, apelidoOponente, memoria, posicao);
		}


	}


	//	recebe o ponto atirado e avalia se acertou navio ou água
//	se acertou um navio, atualiza a vida e repete
	private static void shot(String apelido, String apelidoOponente, int[][] memoria, char[][] posicao) {

		boolean playAgain = false;
		do {
			displayHP();
			drawBoard(memoria, posicao);
			imprimir(apelido + " escolha onde deseja atirar, ex: 5 5");

			int[] tiro = new int[2];
			lineSelect(tiro);
			columSelect(tiro);


//		cuida para que o tiro ocorra num ponto que ainda não foi alvo (memoria[tiro[0]][tiro[1]] == 0)
			while(memoria[tiro[0]][tiro[1]]==1) {
				imprimir("Já atirou em : "+tiro[0]+" "+tiro[1]);
				drawBoard(memoria, posicao);
				imprimir("escolha outro alvo:");
				lineSelect(tiro);
				columSelect(tiro);
			}
//		persiste a informação que esse ponto ja foi atirado.
			memoria[tiro[0]][tiro[1]]=1;

//		depois de dado o tiro, checa se existia um navio no ponto e decrementa a vida caso sim e imprime "Água!" caso não.
			if(posicao[tiro[0]][tiro[1]]=='X') {
				playAgain = attVida(apelidoOponente, apelido, posicao);

			}else {
				imprimir("Água!");
				playAgain = false;
			}
		} while (playAgain);


	}
	private static void displayHP() 	{
		for (int i=0 ; i<55 ; i++){
			out.print(" ");
		}
		imprimir(nicks[0] +"  HP:"+vidaP1);
		for (int i=0 ; i<55 ; i++){
			out.print(" ");
		}
		imprimir(nicks[1] +"  HP:"+vidaP2);
	}
	//	desenha o tabuleiro
	private static void drawBoard(int[][] memoria, char[][] posicao) {
		out.print("  ");
		for (int j = 0; j <posicao.length; j++) {
			out.print("   "+j+"  ");
		}
		out.println();
		for (int i = 0; i < posicao.length+1; i++) {
			out.print("  ");
			for (int j = 0; j < 61; j++) {
				out.print("-");
			}

			out.println();
			if(i<posicao.length) {
				out.print(i+" ");
				for (int j = 0; j < posicao.length+1; j++) {
					if(j<10&&memoria[i][j]==1) {
						out.print("|  "+posicao[i][j]+"  ");

					}else {
						out.print("|     ");
					}

				}
				out.println("");

			}
		}
		out.println("\n");

	}


	/*desenha o tabuleiro com o que esta atualmente nas posicoes('O' ou 'X')*/
	private static void drawBoard(char[][] posicao) {
		out.print("  ");
		for (int j = 0; j <posicao.length; j++) {
			out.print("   "+j+"  ");
		}
		out.println();
		for (int i = 0; i < posicao.length+1; i++) {
			out.print("  ");
			for (int j = 0; j < 61; j++) {
				out.print("-");
			}

			out.println();
			if(i<posicao.length) {
				out.print(i+" ");
				for (int j = 0; j < posicao.length+1; j++) {
					if(j<10) {
						out.print("|  "+posicao[i][j]+"  ");

					}

				}
				out.println("");

			}
		}
		out.println("\n");

	}


	/*retorna false, caso o jogo deva acabar*/
	private static boolean attVida(String apelidoOponente, String apelido, char[][] posicao) {
		if(apelidoOponente ==nicks[0]) vidaP1 --;
		else vidaP2 --;
		if(vidaP1==0||vidaP2==0) {
			drawBoard(posicao);
			gameEnd(apelido);
			return false;
		}
		imprimir("Acertou!\n"
				+ "Jogue novamente:");
		return true;

	}

	private static void gameEnd(String string) {
		imprimir("Vitória de "+string+"!!!");
		GAME_ON = false;
		vidaP1 = 0;
		vidaP2 = 0;
		jogarNovamente();


	}

//	 cuida do posicionamento dos navios e retorna a vida, baseado no numero de pontos preenxidos.
	private static int posicionamentoDeNavios(String apelido, char[][] posicao, int vida) {
//		(1) porta-aviões (cinco quadrados), (2) navios-tanque (quatro quadrados),
//		(3) contratorpedeiros (três quadrados) e (4) submarinos (dois quadrados)
		//Conta quantos navios faltam ser posicionados	if
		if(!(nicks[0].equalsIgnoreCase("teste1")&&nicks[1].equalsIgnoreCase("teste2"))){
			int naviosCont = 4;

			int input;
			//		numero total de naios
			final int NAVIOS = 4;
			//controla quais navios são mostrados na tela, independente da ordem de escolha.
			int[] cont = {1, 2, 3, 4};
			//cuida para que 2 navios não ocupem um mesmo ponto
			while(naviosCont != 0) {
				imprimir(apelido +" Escolha qual navio deseja posicionar:");
				for(int j = 1 ; j<=naviosCont ; j++) {

					switch (cont[j - 1]) {
						case (1) :
							imprimir("(" + j + ") porta-aviões (cinco quadrados)");
							break;
						case (2) :
							imprimir("(" + j + ") navios-tanque (quatro quadrados)");
							break;
						case (3) :
							imprimir("(" + j + ") contratorpedeiros (três quadrados)");
							break;
						case (4) :
							imprimir("(" + j + ")  submarinos (dois quadrados)");
							break;
					}
				}
				input = inputInt(1,naviosCont);
				if(input<=0||input>naviosCont) {
					imprimir("Opção inválida!");
					continue;
				}
//			o input é baseado no que é mostrado em tela sendo assim diferente da escolha real, que é o numero no array cont, no index input-1
				int escolha = cont[input-1];
//			remove o numero referente ao navio escolhido do array cont e 'recua' os valores seguintes ex:
//			se input = 2 quando cont={1,2,3,4}, o for resultaria em cont={1,3,4,4};
				arraycopy(cont, input, cont, input - 1, NAVIOS - input);


//			por conta da maneira que o array cont funciona, o input do usuario faz com que 'escolha' seja um numero de 1 a 4
//			equivalente ao navio escolhido, que, no switch/case abaixo, chama o metodo de acordo.
				switch (escolha) {
					case (1) : {
						vida = definirExtremidades(apelido, "porta-aviões", 5, posicao, vida);
						naviosCont--;
						break;
					}
					case (2) : {
						vida = definirExtremidades(apelido, "navios-tanque", 4, posicao, vida);
						naviosCont--;
						break;
					}
					case (3) : {
						vida = definirExtremidades(apelido, "contratorpedeiros", 3, posicao, vida);
						naviosCont--;
						break;
					}
					case (4) : {
						vida = definirExtremidades(apelido, "submarinos ", 2, posicao, vida);
						naviosCont--;
						break;
					}
				}
			}

		}else{
			vida = definirExtremidades(apelido, "porta-aviões", 5, posicao, vida);
			vida = definirExtremidades(apelido, "navios-tanque", 4, posicao, vida);
			vida = definirExtremidades(apelido, "contratorpedeiros", 3, posicao, vida);
			vida = definirExtremidades(apelido, "submarinos ", 2, posicao, vida);
		}

		return vida;



	}

	// chama os métodos que definem os pontos inicial e final e depois o que posiciona os navios
	// retorna o valor final da vida
	private static int definirExtremidades(String apelido, String tipoNavio, int tamanho, char[][] posicao, int vida) {
	    /*Caso os apelidos tenhão sido iniciados com "teste1" e teste2"(ignorando caixa alta) esta etapa é pulada e os navios
	    * são setados em uma posição padrão de testes*/
		int[] pontoInicial;
		int[] pontoFinal;
		if (!(nicks[0].equalsIgnoreCase("teste1")&&nicks[1].equalsIgnoreCase("teste2"))) {
			imprimir(apelido+" definia a posiçao do "+tipoNavio+"\n" +
							"Informe o ponto onde ficará a primeira extremidade:");
			pontoInicial = checarPontoInicial(posicao);
			pontoFinal = checarPosicoesValidas(pontoInicial, tamanho, posicao);
			while(pontoFinal[0]==-1) {
				imprimir("Escolha um novo ponto de inicio:");
				pontoInicial = checarPontoInicial(posicao);
				pontoFinal = checarPosicoesValidas(pontoInicial, tamanho, posicao);
			}

			return colocarNavio(posicao, vida, pontoInicial, pontoFinal);
		} else {
			pontoInicial = new int[]{0, tamanho};
			pontoFinal = new int[]{tamanho - 1, tamanho};
			return colocarNavio(posicao, vida, pontoInicial, pontoFinal);
		}

	}

	/*recebe do usuario uma linha e uma coluna e checa para que a posição esteja vazia,
	* retorna um array com as cordenadas da posição*/
	private static int[] checarPontoInicial( char[][] posicao) {
		int[] pontoInicial = new int[2];
		lineSelect(pontoInicial);
		columSelect(pontoInicial);
		while(posicao[pontoInicial[0]][pontoInicial[1]]=='X'){
				imprimir("Outro navio já ocupa esse espaço.\n"
						+ "Escolha outro ponto inicial: ");
				lineSelect(pontoInicial);
				columSelect(pontoInicial);
		}


		return pontoInicial;
	}


	//avalia se existem opções válidas
	//imprime as posiçoes possíveis
	//recebe o input e avalia se é uma das opções
	//retorna um array[2] com a extremidade final do navio ou um array={-1,0} caso não existam opções válidas.
	private static int[] checarPosicoesValidas(int[] posicaoInicial, int tamanhoNavio, char[][] posicao) {
//		soma ou subtrai 1 para contar com a posicao inicial ex: 3-2 = 1, porem um navio de tamanho 2 seria posicionado inicialmente em 3 e depois em 2
		int paraEsquerda = posicaoInicial[1]-tamanhoNavio+1;
		int paraDireita = posicaoInicial[1]+tamanhoNavio-1;
		int paraCima = posicaoInicial[0]-tamanhoNavio+1;
		int paraBaixo = posicaoInicial[0]+tamanhoNavio-1;
		int posicaoEscolhida, cont = 0;
		int[][] posicoesValidas = new int[4][2];
		boolean[] check= {false, false, false, false};

		do {
			if(cont>0) {
				imprimir("Opção inválida!");
				cont = 0;
			}

			if(paraEsquerda>=0) {
				for(int i = posicaoInicial[1]; i>=paraEsquerda ; i--) {
					if(posicao[posicaoInicial[0]][i]=='X') {
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
					imprimir("Agora, escolha entre os possíveis pontos de fim: ");
					imprimir("("+cont+")virado para esquerda:"+posicaoInicial[0] + " " + paraEsquerda);
					//			posicoesValidas[0][0]=paraEsquerda;
					//			posicoesValidas[0][1]=posicaoInicial[1];
				}
			}
			if(paraDireita<=9) {
				for(int i = posicaoInicial[1]; i<=paraDireita ; i++) {
					if(posicao[posicaoInicial[0]][i]=='X') {
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
					if(cont==1) {
						imprimir("Agora, escolha entre os possíveis pontos de fim: ");
					}
					imprimir("("+cont+")virado para direita:"+posicaoInicial[0] + " " + paraDireita);
					//			posicoesValidas[1][0]=paraDireita;
					//			posicoesValidas[1][1]=posicaoInicial[1];

				}
			}
			if(paraCima>=0) {
				for(int i = posicaoInicial[0]; i>=paraCima; i--) {
					if(posicao[i][posicaoInicial[1]]=='X') {
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
					if(cont==1) {
						imprimir("Agora, escolha entre os possíveis pontos de fim: ");
					}
					imprimir("("+cont+")virado para cima:"+paraCima + " " + posicaoInicial[1]);
					//			posicoesValidas[2][0]=paraCima;
					//			posicoesValidas[2][1]=posicaoInicial[1];

				}
			}
			if(paraBaixo<=9) {
				for(int i = posicaoInicial[0]; i<=paraBaixo; i++) {
					if(posicao[i][posicaoInicial[1]]=='X') {
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
					if(cont==1) {
						imprimir("Agora, escolha entre os possíveis pontos de fim: ");
					}
					imprimir("("+cont+")virado para baixo:"+paraBaixo + " " + posicaoInicial[1]);
					//			posicoesValidas[3][0]=paraBaixo;
					//			posicoesValidas[3][1]=posicaoInicial[1];


				}
			}
			if(!(check[0]||check[1]||check[2]||check[3])) {
				imprimir("Não existem posições válidas!\n");

				return new int[]{-1,0};
			}

			posicaoEscolhida = inputInt(1,cont);
		}while(posicaoEscolhida<=0||posicaoEscolhida>cont);

		return posicoesValidas[posicaoEscolhida-1];
	}

	//coloca 'X' nos pontos entre pontoInicial e pontoFinal
	//incrementa a vida passada como parametro em 1, para cada 'X' marcado no array
	private static int colocarNavio(char[][] posicao, int vida, int[] pontoInicial, int[] pontoFinal) {
		int distancia, index0, index1;
		//index = 1 quando pontoFinal for maior que inicial e -1 quando for menor.
		if (pontoInicial[0] != pontoFinal[0]) {
			//se a diferença for na linha
			distancia = pontoFinal[0] - pontoInicial[0];
			if (distancia > 0) {
				index0 = 1;
			} else {
				index0 = -1;
				distancia *= -1;
			}
			index1 = 0;
		} else {
			// se a diferença for na coluna
			distancia = pontoFinal[1] - pontoInicial[1];
			if (distancia > 0) {
				index1 = 1;
			} else {
				index1 = -1;
				distancia *= -1;
			}
			index0 = 0;

		}
		//Quando index = 0 não altera a linha/coluna, quando index=1 começa no ponto
		//Final e diminui até o Inicial, quando index=-1 começa no ponto Final e aumenta até o Inicial

		for (int i = 0; i <= distancia; i++) {
			posicao[pontoFinal[0] + (-i * index0)][pontoFinal[1] + (-i * index1)] = 'X';

			//a linha seguinte mostraria os 'X' sendo desenhados


			vida++;
		}
		drawBoard(posicao);
		return vida;
	}


	private static void lineSelect(int[] arrayPonto) {
		imprimir("Informe a linha(de 0 à 9):");
		arrayPonto[0] = inputInt(0,9);
	}
	public static void columSelect(int[] arrayPonto){
		imprimir("Informe a coluna(de 0 à 9):");
		arrayPonto[1]=inputInt(0,9);
	}



	public static void jogarNovamente() {
		// Método para repetição do método inicio
		Scanner sc = new Scanner(in);
		System.out.println("\n-------------------------\nJogar novamente?\n" + "(1)Sim\t(2)Não");
		int op = inputInt(1,2);
		if (op == 1) {
			BatalhaNaval.inicio();
		}

	}

	private static int inputInt(int first, int last){
		int i=-1;
		Scanner scan = new Scanner(in);
		try{
			i = scan.nextInt();
			if (!(i <= last && i >= first)){
				throw new Error();
			}
		} catch (Exception ie){
			imprimir("Por favor, inserir apenas números\n" +
					"Informe novamente:");
			 i = inputInt(first, last);
		}catch (Error e){
			imprimir("Valor inválido, valores aceitos no intervalo ["+first+", "+last+"]\n" +
					"Informne novamente:");
			i = inputInt(first,last);
		}

		return i;

	}

	private static void imprimir(String string) {
		out.println(string);
	}

}
