package application;

import chess.ChessPiece;

public class UI {

	public static void printBoard(ChessPiece[][] pieces) {
		for(int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]) ;
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece) {//imprimir uma peça
		if(piece == null) {//se for igual a null é pq não tinha peça na coordenada
			System.out.print("-");
		}
		else {//se tiver peça vai imprimir a peça
			System.out.print(piece);
		}
		System.out.print(" ");
	}
	
	
	
	
}