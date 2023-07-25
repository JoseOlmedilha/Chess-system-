package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	//performace de moviemnto da peça de xadrex
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();//transformando em posição de matriz (origem)
		Position target = targetPosition.toPosition();//transformando em posição de matriz (destino)
		validateSourcePosition(source); // validar a posição de origem
		Piece capturedPiece = makeMove(source, target); // mover a peça da posição de origem para a de destino
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {//fazer um movimento 
		Piece p = board.removePiece(source);//retirar a peça da posição de origem
		Piece capturedPiece = board.removePiece(target); //remover a possível peça da posição de destino
		board.placePiece(p, target); // colocar a peça na posição de destino

		return capturedPiece; //retorna a peça capturada 
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {//se não existir uam peça nessa posição
			throw new ChessException("Não existe peça na posição de origem");
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {//para instanciar a peça com as coordenadas do xadrez 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	private void initialSetup() {//iniciar a partida de xadrez colocando as peças no tabuleiro
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}

}
