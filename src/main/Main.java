package main;

import impl.Sudoku;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("--          TPO [Grupo 1]          --");
		System.out.println("--     Sudoku por backtracking     --\n\n");
		
		System.out.println("  Se recibe sudoku v�lido #1. ");
		ejecutarEnunciado("valido1");
		System.out.println("  Se recibe sudoku v�lido #2. ");
		ejecutarEnunciado("valido2");
		System.err.println("  Se recibe sudoku inv�lido. ");
		ejecutarEnunciado("invalido");
	}
	
	/**
	 * M�todo que ejecuta la l�gica principal del programa para el enunciado dado.
	 * @param enunciado es la variable que almacena el sudoku a cargar (cargamos por defecto 2 validos y 1 inv�lido).
	 */
	public static void ejecutarEnunciado(String enunciado) {
		
		Sudoku matriz = new Sudoku();
		System.out.println("  Se inicializa la matriz del sudoku...");
		matriz.inicializarSudoku();
		System.out.println("  Se cargan los primeros valores...");
		matriz.cargarEnunciado(enunciado);
		System.out.println("  Sudoku ingresado: ");
		matriz.mostrar();
		System.out.println("  Resolviendo sudoku ...");
		
		if (matriz.resolver()) {
			System.out.println("  Soluci�n:");
		} else {
			System.err.println("  El sudoku ingresado no tiene soluci�n");	
		}
		matriz.mostrar();
	}
}
