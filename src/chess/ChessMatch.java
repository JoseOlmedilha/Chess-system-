package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {//Partida de Xadrez

	private int turn;//turno
	private Color currentPlayer;//player atual
	private Board board;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();//lista de peças no tabuleiro
	private List<Piece> CapturedPieces = new ArrayList<>();  //lista de peças capturadas
	
	
	public ChessMatch() {
		board = new Board(8, 8);//Quando eu começar a partida ele monta um tabuleiro 8,8
		turn = 1; //turno comela com 1 
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){//imprimir possível movimentos
		Position position = sourcePosition.toPosition(); // converter a posição de xadrez em posição de matriz
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
		
		
	}
	
	//performace de moviemnto da peça de xadrex
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();//transformando em posição de matriz (origem)
		Position target = targetPosition.toPosition();//transformando em posição de matriz (destino)
		validateSourcePosition(source); // validar a posição de origem
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target); // mover a peça da posição de origem para a de destino
		
		if(testCheck(currentPlayer)) {//se o jogador atual se colocou em check
			undoMove(source, target, capturedPiece);//desfazendo o movimento
			throw new ChessException("Você não pode se colocar em check");
		}
		//verificando se o oponente está em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {//fazer um movimento 
		Piece p = board.removePiece(source);//retirar a peça da posição de origem
		Piece capturedPiece = board.removePiece(target); //remover a possível peça da posição de destino
		board.placePiece(p, target); // colocar a peça na posição de destino

		if(capturedPiece != null) {//capturou uma peça 
			piecesOnTheBoard.remove(capturedPiece);//remover a peça do tabuleiro
			CapturedPieces.add(capturedPiece);
		}
		
		return capturedPiece; //retorna a peça capturada 
	}
	
	private void undoMove(Position source, Position target, Piece capturadPiece) {//desfazer um movimento(ex: se o rei se colocar em check)
		Piece p = board.removePiece(target); //pegar a peça que foi colocada no destino
		board.placePiece(p, target); // devolver para a posição de origem
		
		if(capturadPiece != null) {// se ele capturou uma peça vamos ter que devolver para a posição wue ela estava
			board.placePiece(capturadPiece, target);//devolvendo a peça para a posição que ela eá no caso destino
			CapturedPieces.remove(capturadPiece);//tirando a peça da lista de capturadas
			piecesOnTheBoard.add(capturadPiece);//devolvendo a peça para a lista de peças no tabulerio
		}
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {//se não existir uam peça nessa posição
			throw new ChessException("Não existe peça na posição de origem");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("A peça escolhida não é sua");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {//se não tiver nenhum movimento possivel
			throw new ChessException("Não existe movimentos possíveis para peça escolhida");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {//se pra peça de origem a posição de destino não é um movimento possível 
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void nextTurn() {//Proximo turno
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {//para instanciar a peça com as coordenadas do xadrez 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); //sempre que uma peça for instanciada ela tem que ser adicionado na losta de peças do tabuleiro
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {//varer a lista para achar o Rei
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Não existe o Rei da cor " + color + " no tabuleiro");//nunca vai ocorrer se ocorrer ta com problema na logica toda
	}

	private boolean testCheck(Color color) {//testar se o rei da cor está em check
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList()); // lista com as peças oponentes 
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();//matriz de possiveis moviemntos da peça (acaomo está dentro do for ele vai mudando as peças, então cada laço do for a matriz é outra)
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {//se a posição do rei na matrix estiver em check
				return true;//o rei está em check
			}
		}
		return false;
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
