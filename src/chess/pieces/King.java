package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;//uam dependencia para posibilitar a jogada Roque

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean canMove(Position position) {//verificar se o rei pode mover para uma posição
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();	
	}
	
	private boolean testRookCastling(Position position){//testar se a torre está apta para o Roque 
		ChessPiece p = (ChessPiece)getBoard().piece(position);	
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//#Movimento de especial de Roque (castling)
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			
			//Roque Pequeno(lado do rei)
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3 ); //posição onde deve está a torre (A ultima da direita)
			if(testRookCastling(posT1)) {//usando o metodo de teste 
				Position p1 = new Position(position.getRow(), position.getColumn() + 1 );//testando se as duas casas no meio deles está livre
				Position p2 = new Position(position.getRow(), position.getColumn() + 2 );
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;	
				}
			}
			//Roque Grande(lado da rainha)
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4 ); //posição onde deve está a torre (A ultima da esquerda)
			if(testRookCastling(posT2)) {//usando o metodo de teste 
				Position p1 = new Position(position.getRow(), position.getColumn() - 1 );//testando se as duas casas no meio deles está livre
				Position p2 = new Position(position.getRow(), position.getColumn() - 2 );
				Position p3 = new Position(position.getRow(), position.getColumn() - 3 );
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;	
				}
			}
		}
		
		
		
		return mat;
	}
}
