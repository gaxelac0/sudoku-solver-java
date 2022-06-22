package interfaces;

public interface SudokuTDA {
	void inicializarSudoku();
	void agregar(int row, int column, int value);
	void sacar(int row, int column, int value);
	void mostrar();
	boolean resolver();
	void revertir(int row, int column);
	void limpiar();
}
