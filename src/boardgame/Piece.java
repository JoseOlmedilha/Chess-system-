package boardgame;

public abstract class Piece {//Peça

	
	//Para ter uma peça é preciso ter um tabuleiro e uma posição 
	protected Position position; //Protected pq essa posição do tabuleiro emforma de matriz(0,0) a do Xadrez é dada como coordenadas(a,0)
	private Board board; // Tabuleiro
	
	
	
	public Piece(Board board) { // Para instanciar eu preciso de um tabuleiro a posição posso colocar depois 
		this.board = board;
	}


	protected Board getBoard() { // pode ser acessado pelo pack boardgame e pelas sub-classes de Piece
		return board;
	}
	
	public abstract boolean[][] possibleMoves();//Matriz para as possibilidades de movimentos
	
	public boolean possibleMove(Position position) {//testar se a peça pode mover para a posição
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {//se há pelo menos 1 movimento possivel para a peça 
		boolean[][] mat = possibleMoves();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {//se for verdadeiro é pq tem um movimento possivel
					return true;
				}
			}
		}
		return false;
	}
}
