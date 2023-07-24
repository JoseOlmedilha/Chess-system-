package boardgame; //tabuleiro do jogo

public class Position { //posição

	private int row;//linha
	private int column;//coluna
	
	public Position(int row,int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	@Override
	public String toString() {
		return row + ", " + column;
	}
	
	
	
}
