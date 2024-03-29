/**
 * @author AlexKerzner
 */

package errors;

/**
 * <code>SquareNotEmpty</code> is thrown when attempting to overwrite the value
 * of a square, if the square is not empty.
 * 
 * @author AlexKerzner
 * 
 * @version 1.0
 * 
 * @see SquareOutOfBounds
 * @see GameOverException
 * 
 * @see game.Board
 */
public class SquareNotEmpty extends RuntimeException
{

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with the default message: "Square not empty".
	 * 
	 */
	public SquareNotEmpty()
	{
		this("Square not empty");
	}

	/**
	 * Constructor with a custom message.
	 * 
	 * @param message
	 *          the error message
	 */
	public SquareNotEmpty(String message)
	{
		super(message);
	}

}
