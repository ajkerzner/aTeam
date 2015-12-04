/**
 * 
 */

package game;

import errors.SquareNotEmpty;
// Enumerated value representing each square
import game.Square;

/**
 * The <code>Board</code> class represents the tic-tac-toe board for a game.
 * 
 * @author AlexKerzner
 *
 */
public class Board
{
	/**
	 * The game's board.
	 */
	private Square board[][] = new Square[3][3];

	/**
	 * Constructor for <code>Board</code> class.
	 */
	public Board()
	{
		// Clear board
		for (short i = 1; i <= 9; i++)
		{
			setSquare(i, Square.EMPTY);
		}
	}

	/**
	 * Get the column of a location value.
	 * 
	 * @param location
	 *          the location (1 through 9: 1 being bottom left, 4 being middle
	 *          left, 9 being top right, etc.) of a square
	 * 
	 * @return the row (1 being the top row, and 3 being the bottom) the square is
	 *         in
	 * 
	 * @see Board#getColumn(short location)
	 * @see Board#getLocation(short row, short column)
	 * 
	 * @see Board
	 */
	public static short getRow(short location)
	{
		return (short) (3 - (location / 3));
	}

	/**
	 * Get the row of a location value.
	 * 
	 * @param location
	 *          the location (1 through 9: 1 being bottom left, 4 being middle
	 *          left, 9 being top right, etc.) of a square
	 * 
	 * @return the column (1 being the left column, 3 being the right column) the
	 *         square is in
	 * 
	 * @see Board#getRow(short location)
	 * @see Board#getLocation(short row, short column)
	 * 
	 * @see Board
	 */
	public static short getColumn(short location)
	{
		return (short) (location % 3);
	}

	/**
	 * Get the location given the row and the column.
	 * 
	 * @param row
	 *          the row (1 being the top row, and 3 being the bottom) the square
	 *          is in
	 * @param column
	 *          the column (1 being the left column, 3 being the right column) the
	 *          square is in
	 * 
	 * @return the location (1 through 9: 1 being bottom left, 4 being middle
	 *         left, 9 being top right, etc.) of a square
	 * 
	 * @see Board#getRow(short location)
	 * @see Board#getColumn(short location)
	 * 
	 * @see Board
	 */
	public static short getLocation(short row, short column)
	{
		return (short) (3 * (3 - row) + column);
	}

	/**
	 * Gets the square at location.
	 * 
	 * @param location
	 *          the location (1 through 9: 1 being bottom left, 4 being middle
	 *          left, 9 being top right, etc.) of a square
	 * 
	 * @return a {@link Square}
	 * 
	 * @see Square
	 * 
	 * @see Board
	 */
	private Square getSquare(short location)
	{
		// Calls the equivalent method with alternate coordinates
		return getSquare(getRow(location), getColumn(location));
	}

	/**
	 * Gets the square at (row, column).
	 * 
	 * @param row
	 *          the row (1 being the top row, and 3 being the bottom) the square
	 *          is in
	 * @param column
	 *          the column (1 being the left column, 3 being the right column) the
	 *          square is in
	 * 
	 * @return a {@link Square}
	 * 
	 * @see Square
	 * 
	 * @see Board
	 */
	private Square getSquare(short row, short column)
	{
		// TODO Handle Out-of-bounds errors
		return board[row][column];
	}

	/**
	 * Sets square at location to square.
	 * 
	 * @param location
	 *          the location (1 through 9: 1 being bottom left, 4 being middle
	 *          left, 9 being top right, etc.) of a square
	 * @param square
	 *          a {@link Square}.
	 * 
	 * @see Square
	 * 
	 * @see Board
	 */
	private void setSquare(short location, Square square)
	{
		// Calls the equivalent method with alternate coordinates
		setSquare(getRow(location), getColumn(location), square);

		return;
	}

	/**
	 * Sets square at (row, column) to value.
	 * 
	 * @param row
	 *          the row (1 being the top row, and 3 being the bottom) the square
	 *          is in
	 * @param column
	 *          the column (1 being the left column, 3 being the right column) the
	 *          square is in
	 * 
	 * @param square
	 *          a {@link Square}
	 * 
	 * @see Square
	 * 
	 * @see Board
	 */
	private void setSquare(short row, short column, Square square)
	{
		if (board[row][column] == Square.EMPTY)
		{
			// Square is empty
			board[row][column] = square;
		}
		else
		{
			// Square is not empty
			throw new SquareNotEmpty();
		}

		return;
	}

}
