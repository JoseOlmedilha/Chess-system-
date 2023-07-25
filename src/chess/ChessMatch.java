package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {//Partida de Xadrez

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);//Quando eu começar a partida ele monta um tabuleiro 8,8
		initialSetup();
	}

	//colocar peça com a posição do xadrez
	public ChessPiece[][] getPieces(){// Retornar a matriz com as peças de xadrez
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; //Uma matriz auxiliar de peças de xadrez
		for(int i=0; i<board.getRows(); i++){   //Percorrendo a matriz 
			for(int j=0; j<board.getColumns(); j++){
				mat[i][j] = (ChessPiece) board.piece(i, j);// fazendo o down casting (transformando peça em peça de xadrez)  
			}
		}
		return mat;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {//para instanciar a peça com as coordenadas do xadrez 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	private void initialSetup() {//iniciar a partida de xadrez colocando as peças no tabuleiro
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}

}
