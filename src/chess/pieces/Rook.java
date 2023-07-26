package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{//Torre

	public Rook(Board board, Color color) {
		super(board, color);	
	}
	
	@Override
	public String toString() {
		return "T";
	}
	
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//Verificar a cima da minha peça
		p.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//enquanto a posição p existir e não tiver peça lá
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//testando se a proxima peça é um oponente, se a posição existir e for oponente 
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Verificar a esquerda da minha peça
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//enquanto a posição p existir e não tiver peça lá			
			mat[p.getRow()][p.getColumn()] = true;		
			p.setColumn(p.getColumn() -1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//testando se a proxima peça é um oponente, se a posição existir e for oponente 
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Verificar a direita da minha peça
		p.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//enquanto a posição p existir e não tiver peça lá			
			mat[p.getRow()][p.getColumn()] = true;		
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//testando se a proxima peça é um oponente, se a posição existir e for oponente 
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Verificar a baixo da minha peça
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//enquanto a posição p existir e não tiver peça lá
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//testando se a proxima peça é um oponente, se a posição existir e for oponente 
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}
	
}
