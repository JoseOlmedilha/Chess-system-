package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece{

	private Color color; //Uma peça de xadrez tem todas a caracteristicas de uma peça normal, só que agora ela vai ter que ter uma cor

	public ChessPiece(Board board, Color color) {//construtor com tabuleiro e cor 
		super(board); // chamando o construtor da classe mãe e passando o tabuleiro
		this.color = color; 
	}

	public Color getColor() {
		return color;
	}
	
	
}
