/**
 * 
 * @author AlexKerzner
 * 
 */

package game;

/**
 * The <code>Player</code> class stores a player's name and score.
 * 
 * To create a new <code>Player</code>, use the constructor <br>
 * <code>Player(String name)</code>.
 * 
 * @author AlexKerzner
 * 
 * @see Player#Player()
 * @see Player#Player(String)
 * 
 * @see Player#getName()
 * @see Player#getScore()
 * @see Player#getGamesPlayed()
 * @see Player#addGame(boolean game_was_won)
 * @see Player#resetScore()
 */
public class Player
{
	/**
	 * Player name
	 */
	private String	name;
	/**
	 * Player score
	 */
	private int			score;
	/**
	 * Player's total number of games played.
	 */
	private int			games_played;

	/**
	 * Calls <code>Player("Guest")</code>.
	 * 
	 * @see Player#Player(String name)
	 * 
	 * @see Player
	 */
	public Player()
	{
		this("Guest");
	}

	/**
	 * Creates the player with name <code>name</code>, and sets their score to 0.
	 * 
	 * @param name
	 *          the player's name
	 * 
	 * @see Player#Player()
	 * 
	 * @see Player
	 */
	public Player(String name)
	{
		// Set name
		this.name = name;

		// Set score
		this.score = 0;

		// Set total games played
		this.games_played = 0;
	}

	/**
	 * Returns the player's name.
	 * 
	 * @return the player's name, as a {@link String}
	 * 
	 * @see Player#getScore()
	 * @see Player#getGamesPlayed()
	 * @see Player#addGame(boolean game_was_won)
	 * @see Player#resetScore()
	 * 
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the player's current score, as an integer.
	 * 
	 * @return the player's score, as an integer
	 * 
	 * @see Player
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * Returns the number of games played.
	 * 
	 * @return the number of games played, as an integer
	 * 
	 * @see Player
	 */
	public int getGamesPlayed()
	{
		return games_played;
	}

	/**
	 * Adds a game to the total games played. If <code>game_was_won</code> is
	 * <code>true</code>, increments score by 1.
	 * 
	 * @param game_was_won
	 *          true, if the player won the game
	 * 
	 * @return player's new score, as an integer
	 * 
	 * @see Player
	 */
	public int addGame(boolean game_was_won)
	{
		// Player played another game.
		games_played = games_played + 1;

		if (game_was_won)
		{
			// Player won that game.
			score = score + 1;
		}

		return score;
	}

	/**
	 * Resets the player's score and total number of games played to 0.
	 * 
	 * @see Player
	 */
	public void resetScore()
	{
		// Reset score
		score = 0;

		// Reset number of games played
		games_played = 0;

		return;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		// Returns a message like: "'Guest' won 4 of 10 games."
		return "'" + name + "' won " + score + " of " + games_played + " games.";
	}

}
