/**
 * @author AlexKerzner
 */

package errors;

/**
 * <code>GameOverException</code> is thrown when attempting to set the value of
 * a square after the game has ended.
 * 
 * @author AlexKerzner
 * 
 * @version 1.0
 * 
 * @see SquareOutOfBounds
 * @see SquareNotEmpty
 * 
 * @see game.Board
 */
public class GameOverException extends RuntimeException
{

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with the default message: "Game is over".
	 * 
	 */
	public GameOverException()
	{
		this("Game is over");
	}

	/**
	 * Constructor with a custom message.
	 * 
	 * @param message
	 *          the error message
	 */
	public GameOverException(String message)
	{
		super(message);
	}

}
