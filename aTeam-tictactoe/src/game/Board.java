/**
 * 
 */

package game;

import errors.SquareNotEmpty;
import errors.SquareOutOfBounds;
// Enumerated value representing each square
import game.Square;

/**
 * The <code>Board</code> class represents the tic-tac-toe board for a game.
 * 
 * @author AlexKerzner
 *
 * 
 */
public class Board
{
	/**
	 * The game's board.
	 * 
	 * @see Board
	 */
	private Square	board[][]	= new Square[3][3];

	/**
	 * The number of moves, equal to the number of non-empty squares on the board.
	 * 
	 * @see Board
	 */
	private short		move_count;

	/**
	 * A boolean value that indicates the possibility of undo.
	 * 
	 * @see Board
	 */
	private boolean	undo_is_possible;

	/**
	 * A value representing the last move in location coordinates. This value is
	 * not guaranteed to be initialized if {@link Board#undo_is_possible} is
	 * false.
	 * 
	 * @see Board
	 */
	private short		last_move;

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

		// Set move count
		move_count = 0;

		// Disable undo
		undo_is_possible = false;

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

	public static boolean isOutOfBounds(short row, short column)
	{
		// true if either row or column are greater than 3 or less than 1.
		return (row < 1) || (column < 1) || (row > 3) || (column > 3);
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
	 * @throws SquareOutOfBounds
	 *           Square is out of bounds
	 * 
	 * @see Square
	 * 
	 * @see Board
	 */
	public Square getSquare(short location) throws SquareOutOfBounds
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
	 * @throws SquareOutOfBounds
	 *           Square is out of bounds
	 * 
	 * @see Square
	 * 
	 * @see Board
	 */
	public Square getSquare(short row, short column) throws SquareOutOfBounds
	{
		if (isOutOfBounds(row, column))
		{
			throw new SquareOutOfBounds();
		}
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

	/**
	 * Checks for a winner to the game. If the game is over, changes
	 * {@link Board#game_over} to true.
	 * 
	 * Checks only the row, column, and diagonal (if along a diagonal) sets on the
	 * board. Only checks either two or three sets of three values.
	 * 
	 * 
	 * @return {@link Square#X} or {@link Square#O} indicating the winner of the
	 *         game, or {@link Square#EMPTY} if a cats (tie) game or the game is
	 *         not over
	 * 
	 * @author AlexKerzner
	 */
	private Square isGameOver()
	{

		switch (last_move)
		{
			case 1: // corner
			default: // do nothing
				break;
		}
		return Square.EMPTY;
	}

	public Square next(short location, Square square)
		throws SquareNotEmpty, SquareOutOfBounds
	{
		isGameOver();
		return Square.EMPTY;
	}

}
