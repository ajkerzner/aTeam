/**
 * 
 */

package game;

/**
 * An enumerated value representing the contents of a square on the tic-tac-toe
 * board. The value may be {@link Square#X}, {@link Square#O}, or {@link EMPTY}.
 * 
 * @author AlexKerzner
 * 
 * @see Board
 *
 */
public enum Square
{
	/**
	 * <code>X</code> represents an 'X'.
	 * 
	 * @see Square
	 */
	X,
	/**
	 * <code>O</code> represents an 'O'.
	 * 
	 * @see Square
	 */
	O,
	/**
	 * <code>EMPTY</code> represents an empty square.
	 * 
	 * @see Square
	 */
	EMPTY,
}
