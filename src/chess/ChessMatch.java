package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {//partida de xadrez

	private int turn;//turno
	private Color currentPlayer;//player atual
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();//lista de peças no tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>();//lista de peças capturadas
	
	public ChessMatch() {
		board = new Board(8, 8); //tamanho do tabuleiro
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece[][] getPieces() {//colocar a peça na posição de xadrez
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];//Uma matriz auxiliar de peças de xadrez
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);;// fazendo o down casting (transformando peça em peça de xadrez)
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {//imprimir possível movimentos
		Position position = sourcePosition.toPosition();// converter a posição de xadrez em posição de matriz
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();//transformando em posição de matriz (origem)
		Position target = targetPosition.toPosition();//transformando em posição de matriz (destino)
		validateSourcePosition(source); // validar a posição
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);// mover a peça da posição de origem para a de destino
		
		if (testCheck(currentPlayer)) {//se o jogador atual se colocou em check
			undoMove(source, target, capturedPiece);//desfazendo o movimento
			throw new ChessException("Você não pode se colocar em check");
		}
		
		//verificando se o oponente está em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {//se o oponente ficou em checkmate acabou o jogo
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {//fazer um movimento 
		ChessPiece p = (ChessPiece) board.removePiece(source);//retirar a peça da posição de origem
		p.increaseMoveCount();//contando o numero de movimento (+1)
		Piece capturedPiece = board.removePiece(target);//remover a possível peça da posição de destino
		board.placePiece(p, target); // colocar a peça na posição de destino
		
		if (capturedPiece != null) {//capturou uma peça 
			piecesOnTheBoard.remove(capturedPiece);//remover a peça do tabuleiro
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {//desfazer um movimento
		ChessPiece p = (ChessPiece) board.removePiece(target);//pegar a peça que foi colocada no destino
		p.decreaseMoveCount();//diminuindo o numero de movimentos -1
		board.placePiece(p, source);// devolver para a posição de origem
		
		if (capturedPiece != null) {// se ele capturou uma peça vamos ter que devolver para a posição que ela estava
			board.placePiece(capturedPiece, target);//devolvendo a peça para a posição que ela eá no caso destino
			capturedPieces.remove(capturedPiece);//tirando a peça da lista de capturadas
			piecesOnTheBoard.add(capturedPiece);//devolvendo a peça para a lista de peças no tabulerio
		}
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {//se não existir uma peça nessa posição
			throw new ChessException("Não existe peça na posição de origem");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("A peça escolhida não é sua");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {//se não tiver nenhum movimento possivel
			throw new ChessException("Não existe movimentos possíveis para peça escolhida");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {//se pra peça de origem a posição de destino não é um movimento possível 
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void nextTurn() {//proximo turno
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {//varer a lista para achar o Rei
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Não existe o Rei da cor \" + color + \" no tabuleiro");
	}
	
	private boolean testCheck(Color color) {//testar se o rei da cor está em check
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {//se o rei está em check mate
		if (!testCheck(color)) {//se ele não estiver em check não vai está em checkmate
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();//pegando a posição da peça
						Position target = new Position(i, j);//nova posição de destino da peça 
						Piece capturedPiece = makeMove(source, target);//movendo a peça da origem para o destino e capturando alguma peça
						boolean testCheck = testCheck(color);//testando se a rei da cor ainda está em check
						undoMove(source, target, capturedPiece);// desfazendo o movimento do teste
						if (!testCheck) {//se ele não etá em check no teste significa que o movimento testado tirou ele do check
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {//para instanciar a peça com as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);//sempre que uma peça for instanciada ela tem que ser adicionado na lista de peças do tabuleiro
	}
	
	private void initialSetup() {//iniciar a partida de xadrez colocando as peças no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
