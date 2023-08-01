package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	private ChessMatch chessMatch; 
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {//se for a peça branca
			//pra peça branca andar pra cima tenho que tirar uma linha 
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se exisitr e tiver vazia
				mat[p.getRow()][p.getColumn()] = true;
			}
			//o primeiro movimento do pião ele pode andar duas casas pra frente
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());//criando uma posição para ferificar se tem uma peça na frente do pião quando ele for andar 2 casas
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				//posição de p exisitr E não tiver peça na posição p E a posição p2 existir E Não tiver peça na p2 E o numero de movimentos for 0(vais er o 1 movimento)
				mat[p.getRow()][p.getColumn()] = true;
			}
			//possivel movimento para a diagonal direita
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existir e tiver um oponente  
				mat[p.getRow()][p.getColumn()] = true;
			}	
			//posivel movimenti para esquerda
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}		
			
			//#specialmove en passant white
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);//pegar a peça do lado
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() ) {
					   // se a posição existe E se a peça que esta na posição é oponente E se essa peça que esá lá ta vuneralvel a tomar o en passant
				mat[left.getRow() -1][left.getColumn()] = true;
				}
			
				Position right = new Position(position.getRow(), position.getColumn() + 1);//pegar a peça do lado
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable() ) {
					   // se a posição existe E se a peça que esta na posição é oponente E se essa peça que esá lá ta vuneralvel a tomar o en passant
				mat[right.getRow() -1][right.getColumn()] = true;
				}
				
			}
			
			
		}
		else {//Possivel movimentos para a peça preta, igual o da branca só que a agora é linha +1 pq ele anda pra baixo
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}			
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}	
			
			//#specialmove en passant black
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);//pegar a peça do lado
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() ) {
					   // se a posição existe E se a peça que esta na posição é oponente E se essa peça que esá lá ta vuneralvel a tomar o en passant
				mat[left.getRow() + 1][left.getColumn()] = true;
				}
			
				Position right = new Position(position.getRow(), position.getColumn() + 1);//pegar a peça do lado
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable() ) {
					   // se a posição existe E se a peça que esta na posição é oponente E se essa peça que esá lá ta vuneralvel a tomar o en passant
				mat[right.getRow() + 1][right.getColumn()] = true;
				}
				
			}
			
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
	
	
	

