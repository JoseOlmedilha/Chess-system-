package chess;

import boardgame.Position;

public class ChessPosition {//posição de xadrez

	private char column;
	private int row;
	
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {//testando se existe a posição de xadrez
			throw new ChessException("Erro instanciando a ChessPosition (Posição do xadrez). Valores validos são de a1 à h8");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}


	public int getRow() {
		return row;
	}
	
	protected Position toPosition() {//transformar a posição de xadrez em posição de matriz 
		return new Position(8 - row, column - 'a') ;
	}
	
	protected static ChessPosition fromPosition(Position position) {//transformar a posição de matriz em posição de xadrez
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}
}
