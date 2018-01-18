package gamestudio.game.puzzleImage;

import java.util.Random;

import gamestudio.game.puzzles.Coordinate;

public class Field {

	private final int rowCount;
	private final int columnCount;
	private final int[][] tiles;

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getTile(int row, int column) {
		return tiles[row][column];
	}

	public Field(int rowCount, int columnCount) {
		this.columnCount = columnCount;
		this.rowCount = rowCount;
		tiles = new int[rowCount][columnCount];
		generate();
	}

	public void generate() {
		int tile = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = tile;
				tile++;

			}
		}

		shuffle();
	}

	private void shuffle() {
		Random random = new Random();
		for (int i = 0; i < 1; i++) {
			moveTile(random.nextInt(16)+1, random.nextInt(16)+1);
		}

	}

	public boolean isSolved() {
		int tile = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] != tile)
					return false;
				tile++;
			}
		}
		return true;
	}

	public void moveTile(int tile, int tile2) {
		if (tile <= 0 || tile >= rowCount * columnCount)
			return;
		Coordinate tileCoordinate = getTileCoordinate(tile);
		Coordinate tile2Coordinate = getTileCoordinate(tile2);

		tiles[tile2Coordinate.getRow()][tile2Coordinate.getColumn()] = tile;
		tiles[tileCoordinate.getRow()][tileCoordinate.getColumn()] = tile2;

	}

	private Coordinate getTileCoordinate(int tile) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == tile) {
					return new Coordinate(row, column);
				}
			}
		}
		return null;
	}

}
