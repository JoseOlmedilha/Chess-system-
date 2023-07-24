package boardgame;

public class Piece {//Peça

	
	//Para ter uma peça é preciso ter um tabuleiro e uma posição 
	protected Position position; //Protected pq essa posição do tabuleiro emforma de matriz(0,0) a do Xadrez é dada como coordenadas(a,0)
	private Board board; // Tabuleiro
	
	
	
	public Piece(Board board) { // Para instanciar eu preciso de um tabuleiro a posição posso colocar depois 
		this.board = board;
	}


	protected Board getBoard() { // pode ser acessado pelo pack boardgame e pelas sub-classes de Piece
		return board;
	}
	
	
	
	
}
