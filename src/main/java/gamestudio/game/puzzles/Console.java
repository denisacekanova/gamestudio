package gamestudio.game.puzzles;

import java.util.Scanner;

import gamestudio.consoleUI.ConsoleGameUI;
import gamestudio.game.puzzles.Field;

public class Console implements ConsoleGameUI {
	private Field field;
	private Scanner scanner = new Scanner(System.in);

	public Console(Field field) {
		this.field = field;
	}

	@Override
	public void run() {
		long sTime = System.currentTimeMillis();
		print();
		while (!field.isSolved()) {
			processInput();
			print();

		}
		System.out.println("You won!");
		long eTime = System.currentTimeMillis();
		long time = eTime - sTime;
		System.out.println("hru si hral " + time / 1000 + " sekund");

	}

	private void print() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);
				if (tile == Field.EMPTY_TILE)
					System.out.printf("   ", tile);
				else
					System.out.printf(" %2d", tile);
			}
			System.out.println();
		}
	}

	public void processInput() {
		System.out.println("enter tile number or X to exit : ");
		String input = scanner.nextLine().trim().toUpperCase();

		if ("X".equals(input))
			System.exit(0);
		try {
			int tile = Integer.parseInt(input);
			if (!field.moveTile(tile)) {
				System.out.println("zle zadany vstup");
			}
		} catch (NumberFormatException e) {
			System.out.println("zle zadany vstup");
		}
	}

	@Override
	public String getName() {
		return "puzzle";
	}

}
