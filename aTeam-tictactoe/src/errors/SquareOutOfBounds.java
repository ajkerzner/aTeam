/**
 * @author AlexKerzner
 */

package errors;

/**
 * <code>SquareOutOfBounds</code> is thrown when attempting to reference a
 * square not on the tic-tac-toe board.
 * 
 * @author AlexKerzner
 * 
 * @version 1.0
 * 
 * @see SquareNotEmpty
 * @see GameOverException
 * 
 * @see game.Board
 */
public class SquareOutOfBounds extends RuntimeException
{

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with the default message: "Square out of bounds".
	 * 
	 */
	public SquareOutOfBounds()
	{
		this("Square out of bounds");
	}

	/**
	 * Constructor with a custom message.
	 * 
	 * @param message
	 *          the error message
	 */
	public SquareOutOfBounds(String message)
	{
		super(message);
	}

}
