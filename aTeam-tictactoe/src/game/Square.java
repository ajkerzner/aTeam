/**
 * @author AlexKerzner
 */

package game;

/**
 * An enumerated value representing the contents of a square on the tic-tac-toe
 * board. The value may be {@link Square#X}, {@link Square#O}, or
 * {@link Square#EMPTY}.
 * 
 * @author AlexKerzner
 * 
 * @version 1.0
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
	EMPTY;

	/**
	 * Checks if this Square is empty.
	 * 
	 * @return true, if this is equal to {@link Square#EMPTY}
	 */
	public boolean isEmpty()
	{
		return (this == EMPTY);
	}

	/**
	 * Checks if A, B, and C are equal. If they are equal, the value of A is
	 * returned. If they are not all equal, or if one is empty, then
	 * {@link Square#EMPTY} is returned.
	 * 
	 * @param A
	 *          the first value
	 * @param B
	 *          the second value
	 * @param C
	 *          the third value
	 * 
	 * @return a {@link Square}
	 */
	public static Square areEqualAndNotEmpty(Square A, Square B, Square C)
	{
		// If A is not empty
		if (!A.isEmpty())
		{
			// If A equals B
			if (A.equals(B))
			{
				// If B equals C
				if (B.equals(C))
				{
					// Return A, which is also B and C.
					return A;
				}
			}
		}
		// Return EMPTY
		return Square.EMPTY;
	}
}
