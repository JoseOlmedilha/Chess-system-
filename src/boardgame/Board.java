package boardgame;

public class Board { //tabuleiro

	private int rows;     //Quantidade de linhas do tabuleiro
	private int columns;  //Quantidade de colunas do tabuleiro
	private Piece[][] pieces; //Uma matriz com as peças do tabuleiro
	
	
	public Board(int rows, int columns) { //construtor recebendo a quantidade de linhas e colunas 
		if(rows < 1 || columns < 1 ) { //se a linha e coluna (tamanho do tabuleiro) for menor que 1 ele lança um erro
			throw new BoardException("Erro criando tabuleiro: é necessário que tenha pelo menos 1 linha e 1 coluna  ");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //Minha matriz de peças va ter o mesmo tamanho que o tabuleiro 
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) { //pegar a peça do tabuleiro na linha e coluna
		if(!positionExists(row, column)) {//se essa posição não existir 
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pieces[row][column]; // retirna a peça da linha e coluna da matriz pieces
	}
	
	public Piece piece(Position position) {// vai retorna a peça pela position
		if(!positionExists(position)) {//se essa posição não existir 
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { // coloca a peça no lugar 
		if(thereIsAPiece(position)) {//se já tiver uam peça na coordenada que eu for colocar a peça vai me lançar um erro
			throw new BoardException("Já existe uma peça na posição " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece; // coloca na posição a peça que eu informei no método 
		piece.position = position; //passando pra peça o lugar que ela está
	}
	
	private boolean positionExists(int row, int column) { // verificar se existe a posição pela linha e coluna 
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {//verificar se existe a posição
		return positionExists(position.getRow(), position.getColumn());
	//chama o método de cima pansando a linha e coluna que está dentro do objeto posição e retorna true se a posição existir e false se não
	}
	
	public boolean thereIsAPiece(Position position) {//verificar se tem uma peça nessa posição
		if(!positionExists(position)) {//se essa posição não existir 
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return piece(position) != null ;//o método piece vai retorna a peça que está na posição, se for igual a null é pq não tem peça
	}
	
	
	
	
}
