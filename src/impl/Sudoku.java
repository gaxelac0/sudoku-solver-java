package impl;

import interfaces.SudokuTDA;

public class Sudoku implements SudokuTDA {
	Celda [][] matrix = new Celda[9][9];
	
	/**
	 * Inicializa el sudoku colocando una celda nueva en cada posición de la matriz.
	 */
	public void inicializarSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				matrix[i][j] = new Celda();
			}
		}
	}
	
	/**
	 * Metodo para permite agregar un numero entre 1 y 9 en la posicion indicada por los parametros siguientes. 
	 * Si y hay un número en la posición, el nuevo número reemplazará al anterior.
	 */
	public void agregar(int value, int row, int column) {
		if(value >= 1 && value <= 9) {
			matrix[row][column].value = value;
		}
	}
	
	/**
	 * Método que permite remover un valor siempre en cuando no sea uno fijo y no sea cero.
	 */
	public void sacar(int row, int column, int value) {
		if (matrix[row][column].value != 0 && matrix[row][column].state == 0) {
			matrix[row][column].value = 0;
		}
	}
	
	/**
	 * Metodo que verifica que el valor a insertar en la posición sea valido
	 * - No se puede repetir en la fila
	 * - No se puede repetir en la columna
	 * - No se puede repetir en la subgrilla
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 */
	public boolean isValid(int row, int column, int value) {
		
		// Valido que el numero no se encuentre en la columna
		for (int act_row = 0; act_row < 9; act_row++) {
			if (matrix[act_row][column].value == value) {
				return false;
			}
		}
		
		// Valido que el numero no se encuentre en la fila
		for (int act_column = 0; act_column < 9; act_column++) {
			if (matrix[row][act_column].value == value) {
				return false;
			}
		}
		
		// Valido que el numero no se encuentre en la sub-grilla
		int row_start = row - (row % 3);
		int col_start = column - (column % 3);
		for (int i = row_start; i < row_start+3; i++) {
			for (int j = col_start; j < col_start+3; j++) {
				if (matrix[i][j].value == value) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/*
	 * Metodo principal de la clase, el cual aplica backtracking para la resolución
	 * del sudoku. Se explica en la Estrategia del Informe.
	 */
	public boolean resolver() {
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				
				if (matrix[r][c].value == 0) {
					for (int v = 1; v <= 9; v++) {
						
						if (isValid(r, c, v) ) {
							
							// Si no es un valor fijo
							if (matrix[r][c].state == 0) {
								agregar(v, r, c);
							}
							
							if (resolver()) {
								return true;
							} else {
								revertir(r, c);
							}
						}
					}
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Revierte a cero el valor de la posicion indicada
	 */
	public void revertir(int row, int column) {
		matrix[row][column].value = 0;
	}
	
	/**
	 * Limpia la matriz dejando todos los valores en cero
	 */
	public void limpiar() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (matrix[i][j].state == 0) {
					matrix[i][j].value = 0;
				}
			}
		}
	}
	
	
	/**
	 * Agrega valores fijos que son parte del sudoku a ingresar. (state==1)
	 * @param row
	 * @param column
	 * @param value
	 */
	private void agregarFijo(int row, int column, int value) {
		matrix[row][column].value = value;
		matrix[row][column].state = 1;
		
	}
	
	/**
	 * Muestra el sudoku
	 */
	public void mostrar() {
		int filas = 9;
		int columnas = 9;
		
		for (int i=0;i<filas;i++) {
			if (i == 0) {
				System.out.println(" _________________________________________ ");				
			}
			if (i == 3 || i == 6) {
				System.out.println("|_____________|_____________|_____________|");
			}
			System.out.print("| ");
			for (int j=0;j<columnas;j++) {
				if (matrix[i][j].value == 0) {
					System.out.print(" - ");
				} else if (matrix[i][j].state == 1){
					System.out.print("[" + matrix[i][j].value + "]");
				} else {
					System.out.print(" " + matrix[i][j].value + " ");
				}
				System.out.print(" ");
				if (j == 2 || j == 5) {
					System.out.print("| ");	
				} if (j == 8) {
					System.out.println("|");
				}
			}
			if (i == 8) {
				System.out.println("|_____________|_____________|_____________|");
			}
		}
		System.out.println("\n\n");
	}
	
	/**
	 * Metodo que se encarga de cargar el sudoku según el enunciado dado. 
	 * @param enunciado si es valido1 o valido2 se cargarán sudokus validos, si es invalido se cargará uno invalido.
	 */
	public void cargarEnunciado(String enunciado) {
		
		if ("valido1".equalsIgnoreCase(enunciado)) {
			/*
			 * 	   - SuDoKu Propuesto -
			 *    _______________________
			 *   | - - 2 | - 9 - | 6 - - |
			 *   | - - - | - 4 - | - - 3 |
			 *   | 1 - - | - - 8 | - - - |
			 *   |_______|_______|_______|
			 *   | 7 3 - | - - - | - - 2 |
			 *   | - 8 - | - - - | 4 - - |
			 *   | - - - | - - - | - - 8 |
			 *   |_______|_______|_______|
			 *   | 9 - - | - - - | - - 5 |
			 *   | - 5 - | - 3 4 | - 2 - |
			 *   | - - - | 6 2 - | - - 1 |
			 *   |_______|_______|_______|
			 */  
			
			agregarFijo(0, 2, 2);
			agregarFijo(0, 4, 9);
			agregarFijo(0, 6, 6);
			agregarFijo(1, 4, 4);
			agregarFijo(1, 8, 3);
			agregarFijo(2, 0, 1);
			agregarFijo(2, 5, 8);
			agregarFijo(3, 0, 7);
			agregarFijo(3, 1, 3);
			agregarFijo(3, 8, 2);
			agregarFijo(4, 1, 8);
			agregarFijo(4, 6, 4);
			agregarFijo(5, 8, 8);
			agregarFijo(6, 0, 9);
			agregarFijo(6, 8, 5);
			agregarFijo(7, 1, 5);
			agregarFijo(7, 4, 3);
			agregarFijo(7, 5, 4);
			agregarFijo(7, 7, 2);
			agregarFijo(8, 3, 6);
			agregarFijo(8, 4, 2);
			agregarFijo(8, 8, 1);
			
		} else if ("valido2".equalsIgnoreCase(enunciado)) {
			
			/*
			 * 	   - SuDoKu Propuesto -
			 *    _______________________
			 *   | - - - | 4 - - | 1 8 6 |
			 *   | - 8 6 | - 1 3 | - 7 2 |
			 *   | 2 1 - | 9 - - | - - - |
			 *   |_______|_______|_______|
			 *   | 7 - 8 | - - - | - 1 3 |
			 *   | - - - | - - - | - - - |
			 *   | - - - | 7 - 9 | - - - |
			 *   |_______|_______|_______|
			 *   | - 4 1 | - - 7 | 6 - - |
			 *   | - 5 - | - - - | 8 - 7 |
			 *   | - 7 9 | - 3 - | 2 - 1 |
			 *   |_______|_______|_______|
			 */
			agregarFijo(0, 3, 4);
			agregarFijo(0, 6, 1);
			agregarFijo(0, 7, 8);
			agregarFijo(0, 8, 6);
			agregarFijo(1, 1, 8);
			agregarFijo(1, 2, 6);
			agregarFijo(1, 4, 1);
			agregarFijo(1, 5, 3);
			agregarFijo(1, 7, 7);
			agregarFijo(1, 8, 2);
			agregarFijo(2, 0, 2);
			agregarFijo(2, 1, 1);
			agregarFijo(2, 3, 8);
			agregarFijo(3, 0, 7);
			agregarFijo(3, 2, 8);
			agregarFijo(3, 7, 1);
			agregarFijo(3, 8, 3);
			agregarFijo(5, 3, 7);
			agregarFijo(5, 5, 9);
			agregarFijo(6, 1, 4);
			agregarFijo(6, 2, 1);
			agregarFijo(6, 5, 7);
			agregarFijo(6, 6, 6);
			agregarFijo(7, 1, 5);
			agregarFijo(7, 6, 8);
			agregarFijo(7, 8, 7);
			agregarFijo(8, 1, 7);
			agregarFijo(8, 2, 9);
			agregarFijo(8, 4, 3);
			agregarFijo(8, 6, 2);
			agregarFijo(8, 8, 1);
			
		} else if ("invalido".equalsIgnoreCase(enunciado)) {
			/*
			 * 	   - SuDoKu Propuesto -
			 *    _______________________
			 *   | - - - | 1 - - | 1 8 6 |
			 *   | - 8 6 | - 4 3 | - 7 2 |
			 *   | 2 1 - | 9 - - | - - - |
			 *   |_______|_______|_______|
			 *   | 7 - 8 | - - - | - 1 3 |
			 *   | - - - | - - - | - - - |
			 *   | - - - | 7 - 9 | - - - |
			 *   |_______|_______|_______|
			 *   | - 4 1 | - - 7 | 6 - - |
			 *   | - 5 - | - - - | 8 - 7 |
			 *   | - 7 9 | - 3 - | 2 - 1 |
			 *   |_______|_______|_______|
			 */
			agregarFijo(0, 3, 1);
			agregarFijo(0, 6, 1);
			agregarFijo(0, 7, 8);
			agregarFijo(0, 8, 6);
			agregarFijo(1, 1, 8);
			agregarFijo(1, 2, 6);
			agregarFijo(1, 4, 4);
			agregarFijo(1, 5, 3);
			agregarFijo(1, 7, 7);
			agregarFijo(1, 8, 2);
			agregarFijo(2, 0, 2);
			agregarFijo(2, 1, 1);
			agregarFijo(2, 3, 8);
			agregarFijo(3, 0, 7);
			agregarFijo(3, 2, 8);
			agregarFijo(3, 7, 1);
			agregarFijo(3, 8, 3);
			agregarFijo(5, 3, 7);
			agregarFijo(5, 5, 9);
			agregarFijo(6, 1, 4);
			agregarFijo(6, 2, 1);
			agregarFijo(6, 5, 7);
			agregarFijo(6, 6, 6);
			agregarFijo(7, 1, 5);
			agregarFijo(7, 6, 8);
			agregarFijo(7, 8, 7);
			agregarFijo(8, 1, 7);
			agregarFijo(8, 2, 9);
			agregarFijo(8, 4, 3);
			agregarFijo(8, 6, 2);
			agregarFijo(8, 8, 1);
			
		}
	}
}
