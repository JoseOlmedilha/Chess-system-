package boardgame;

public class Board { //tabuleiro

	private int rows;     //Quantidade de linhas do tabuleiro
	private int columns;  //Quantidade de colunas do tabuleiro
	private Piece[][] pieces; //Uma matriz com as peças do tabuleiro
	
	
	public Board(int rows, int columns) { //construtor recebendo a quantidade de linhas e colunas 
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //Minha matriz de peças va ter o mesmo tamanho que o tabuleiro 
	}


	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public int getColumns() {
		return columns;
	}


	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) { //pegar a peça do tabuleiro na linha e coluna
		return pieces[row][column]; // retirna a peça da linha e coluna da matriz pieces
	}
	
	public Piece piece(Position position) {// vai retorna a peça pela position
		return pieces[position.getRow()][position.getColumn()];
	}
	
	
	
	
	
	
	
	
	
}
