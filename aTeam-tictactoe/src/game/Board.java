/**
 * 
 */

package game;

import java.util.Arrays;

import errors.GameOverException;
import errors.SquareNotEmpty;
import errors.SquareOutOfBounds;
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
	private int			move_count;

	/**
	 * A boolean value that indicates the possibility of undo.
	 * 
	 * @see Board
	 */
	private boolean	undo_is_possible;

	/**
	 * A boolean value indicating that the game is over.
	 * 
	 * @see Board
	 */
	private boolean	game_over;

	/**
	 * A {@link Square} representing the winner of the game, if any. This value is
	 * only used when {@link Board#game_over} is true.
	 * 
	 * @see Board
	 */
	private Square	game_winner;

	/**
	 * A value representing the last move in location coordinates. This value is
	 * not guaranteed to be initialized if {@link Board#undo_is_possible} is
	 * false.
	 * 
	 * @see Board
	 */
	private int			last_move;

	/**
	 * Constructor for <code>Board</code> class.
	 */
	public Board()
	{
		// Clear board
		for (int i = 0; i < 3; i++)
		{
			Arrays.fill(board[i], Square.EMPTY);
		}

		// Set move count
		move_count = 0;

		// Disable undo
		undo_is_possible = false;

		// Set game_over
		game_over = false;

		// Set game_winner to default
		game_winner = Square.EMPTY;
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
	 * @see Board#getColumn(int location)
	 * @see Board#getLocation(int row, int column)
	 * 
	 * @see Board
	 */
	public static int getRow(int location)
	{
		return (3 - ((location - 1) / 3));
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
	 * @see Board#getRow(int location)
	 * @see Board#getLocation(int row, int column)
	 * 
	 * @see Board
	 */
	public static int getColumn(int location)
	{
		return ((location - 1) % 3) + 1;
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
	 * @see Board#getRow(int location)
	 * @see Board#getColumn(int location)
	 * 
	 * @see Board
	 */
	public static int getLocation(int row, int column)
	{
		return (3 * (3 - row) + column);
	}

	/**
	 * Checks that the Square pointed to by (row, column) exists.
	 * 
	 * @param row
	 *          the row (1 being the top row, and 3 being the bottom) the square
	 *          is in
	 * @param column
	 *          the column (1 being the left column, 3 being the right column) the
	 *          square is in
	 * 
	 * @return true, if either row or column is not 1, 2, or 3.
	 * 
	 * @see Board
	 */
	public static boolean isOutOfBounds(int row, int column)
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
	public Square getSquare(int location) throws SquareOutOfBounds
	{
		// Calls the equivalent method
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
	public Square getSquare(int row, int column) throws SquareOutOfBounds
	{
		if (isOutOfBounds(row, column))
		{
			throw new SquareOutOfBounds("Row: " + row + ", Column: " + column);
		}
		return board[row - 1][column - 1];
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
	private void setSquare(int location, Square square)
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
	private void setSquare(int row, int column, Square square)
	{
		if (getSquare(row, column).isEmpty() || square.isEmpty())
		{
			// Square is empty
			board[row - 1][column - 1] = square;
		}
		else
		{
			// Square is not empty
			// Throw error
			throw new SquareNotEmpty();
		}

		return;

	}

	/**
	 * @see {@link Board#isGameOver(boolean force_check)
	 */
	public boolean isGameOver()
	{
		return isGameOver(false);
	}

	/**
	 * <h1>Normal usage</h1> Returns the value of {@link Board#game_over}.<br>
	 * 
	 * If the optional parameter <code>force_check</code> is present and is
	 * <code>true</code>, checks for a winner to the game. If the game is over,
	 * changes {@link Board#game_over} to true.<br>
	 * <h1>Checking for a winner</h1> Checks only the row, column, and diagonal
	 * (if along a diagonal) sets on the board. Only checks either two, three, or
	 * four sets of three values.<br>
	 * 
	 * Only checks for a winner if {@link Board#move_count} is between 5 and 9.
	 * <br>
	 * Sets {@link Board#game_winner} to {@link Square#X} or {@link Square#O}
	 * indicating the winner of the game, or {@link Square#EMPTY} if a cats (tie)
	 * game or the game is not over
	 * 
	 * @param force_check
	 *          a boolean value indicating whether or not to force a check for
	 *          game-over
	 * 
	 * @return true, if the game is over according to {@link Board#game_over}
	 * 
	 * @author AlexKerzner
	 */
	public boolean isGameOver(boolean force_check)
	{
		// If force_check is false
		if (!force_check)
		{
			return game_over;
		}

		/*
		 * Short-circuit: do not check for a win if {@link Board#move_count} is less
		 * than 5.
		 */
		if (move_count < 5)
		{
			// Each player has no more than two moves on the board.
			// A player must have at least three moves to win.
			return false;
		}

		// If the game is over because all squares are filled, set game_over to
		// true.
		if (move_count == 9)
		{
			game_over = true;

			// Disable undo
			undo_is_possible = false;
		}

		// Create temporary Square
		Square temp = game_winner;

		// Check rows
		int row = getRow(last_move);
		if (row == 1 && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(7), getSquare(8), getSquare(9));
		}
		if (row == 2 && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(4), getSquare(5), getSquare(6));
		}
		if (row == 3 && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(1), getSquare(2), getSquare(3));
		}

		// Check columns
		int column = getColumn(last_move);
		if (column == 1 && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(1), getSquare(4), getSquare(7));
		}
		if (column == 2 && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(2), getSquare(5), getSquare(8));
		}
		if (column == 3 && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(3), getSquare(6), getSquare(9));
		}

		// Check diagonal from top-left to bottom right
		if (column == row && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(3), getSquare(5), getSquare(7));
		}

		// Check diagonal from top-right to bottom left
		if ((4 - column) == row && temp.isEmpty())
		{
			temp =
				Square.areEqualAndNotEmpty(getSquare(1), getSquare(5), getSquare(9));
		}

		// If temp is not empty, the game is over
		if (!temp.isEmpty())
		{
			game_over = true;

			// Disable undo
			undo_is_possible = false;
		}

		// Transfer the value of temp back to game_winner
		game_winner = temp;

		return game_over;
	}

	/**
	 * Return true if undo is possible
	 * 
	 * @return true, if undo is possible
	 * 
	 * @see Board
	 */
	public boolean isUndoPossible()
	{
		return undo_is_possible;
	}

	/**
	 * Undo a move, if possible.
	 * 
	 * @return the location of the move undone, or -1 if not undone.
	 * 
	 * @see Board
	 */
	public int undo()
	{
		if (undo_is_possible)
		{
			// Disable undo
			undo_is_possible = false;

			// Undo the last move
			setSquare(last_move, Square.EMPTY);

			// Sets a temporary value
			int temp = last_move;

			// Reset the last move indicator
			last_move = 0;

			// Decrement move counter
			move_count = move_count - 1;

			// Last move undone
			return temp;
		}
		// No move undone
		return -1;
	}

	/**
	 * Returns the winner of the game, if the game is over. If the game is not
	 * over, returns the last player to move.
	 * 
	 * @return a {@link Square}
	 * 
	 * @see Board
	 */
	public Square getLastPlayer()
	{
		return game_winner;
	}

	/**
	 * Makes the next move. Checks if the game is over. Returns true if the move
	 * ended the game.
	 * 
	 * @param location
	 *          the location (1 through 9: 1 being bottom left, 4 being middle
	 *          left, 9 being top right, etc.) of a square
	 * @param square
	 *          a {@link Square}
	 * 
	 * @return true, if game is over
	 * 
	 * @throws GameOverException
	 *           the game is over, and can no longer be modified
	 * @throws SquareNotEmpty
	 *           the square at location is not empty
	 * @throws SquareOutOfBounds
	 *           there is no square at location
	 * 
	 * @see Board
	 */
	public boolean next(int location, Square square)
		throws GameOverException, SquareNotEmpty, SquareOutOfBounds
	{
		// The game is over
		if (game_over)
		{
			throw new GameOverException();
		}

		// Set square
		setSquare(location, square);

		// Another move was made
		move_count = move_count + 1;

		// Set last_move
		last_move = location;

		// Check if the game has ended, and enable undo if game is not over
		if (!isGameOver(true))
		{
			undo_is_possible = true;
		}

		// Return game_over
		return isGameOver(false);
	}

}
