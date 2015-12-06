/**
 * 
 */
package screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import game.Board;
import game.Player;
import game.Square;

/**
 * 
 * PROOF OF CONCEPT
 * 
 * @author AlexKerzner
 * 
 * @author Joe (original)
 * 
 * 
 */
public class MainScreen extends JFrame
{
	/**
	 * An enumerated value representing the players
	 * 
	 * @author AlexKerzner
	 *
	 */
	protected enum Turn
	{
		PLAYER_ONE,
		PLAYER_TWO,
		NO_PLAYERS
	}

	Turn											turn							= Turn.NO_PLAYERS;

	protected Player					player_one;
	protected Player					player_two;
	protected Board						board							= new Board();

	protected Square					game_winner				= Square.EMPTY;

	/**
	 * Default serial version UID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 
	 */
	public MainScreen()
	{

		// Set title
		super("aTeam Tic-Tac-Toe");

		// Creates board

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set default size
		setSize(640, 480);

		// Set layout
		BorderLayout layout = new BorderLayout();
		setLayout(layout);

		GridLayout grid = new GridLayout(3, 3);
		JPanel panel = new JPanel();
		panel.setLayout(grid);
		add(panel, BorderLayout.CENTER);
		JButton[] buttons = new JButton[9];
		// GridLayout.;
		final int[] order =
			{ 7, 8, 9, 4, 5, 6, 1, 2, 3 };
		final int[] keys =
			{ KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3,
				KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
				KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9 };
		for (int i : order)
		{
			i = i - 1;
			buttons[i] = new JButton(Integer.toString(i + 1));
			buttons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent event)
				{

					if (turn == Turn.NO_PLAYERS)
					{
						// Do nothing
						// Game not in progress.
					}
					else if (turn == Turn.PLAYER_ONE)
					{
						// Moves for player two
						board.next(
							Integer.valueOf(((JButton) event.getSource()).getName()) + 1,
							Square.X);
					}
					else if (turn == Turn.PLAYER_TWO)
					{
						// Moves for player two
						board.next(
							Integer.valueOf(((JButton) event.getSource()).getName()) + 1,
							Square.O);
					}

				}
			});
			buttons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(keys[i], 0), buttons[i].getAction());

			panel.add(buttons[i]);
		}

		// Create Menu Bar
		JMenuBar menu_bar = new JMenuBar();

		// Create File menu
		JMenu menu_file = new JMenu("File");
		menu_file.setMnemonic(KeyEvent.VK_F);
		// KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));

		/**
		 * Create File->New_Game menu item
		 */
		JMenuItem menu_new_game = new JMenuItem("New Game");
		// Add keyboard shortcut
		menu_new_game.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_new_game.setMnemonic(KeyEvent.VK_N);
		// Add action to call newGame()
		menu_new_game.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event)
			{
				newGame();
			}
		});
		// Add to File menu
		menu_file.add(menu_new_game);

		/**
		 * Create File->Exit menu item
		 */
		JMenuItem menu_exit = new JMenuItem("Exit");
		// Add keyboard shortcut
		menu_exit.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		// Add mnemonic
		menu_exit.setMnemonic(KeyEvent.VK_E);
		// Add action to call exitGame(0)
		menu_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// Exit normally
				exitGame(0);
			}
		});
		// Add to File menu
		menu_file.add(menu_exit);

		menu_bar.add(menu_file);
		this.setJMenuBar(menu_bar);

		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	/**
	 * Exits the game
	 * 
	 * @param error_code
	 *          the error code (default is 0)
	 */
	protected void exitGame(int error_code)
	{
		System.exit(error_code);
	}

	protected void newGame()
	{
		newGame(false);
	}

	/**
	 * Start a new game
	 * 
	 * @param get_new_players
	 *          true, if new players are to play
	 */
	protected void newGame(boolean get_new_players)
	{
		board = new Board();
		// Get player names, if applicable
		if (get_new_players)
		{
			// Call NameScreen
			player_one = new Player();
			player_two = new Player();
		}

		turn = Turn.PLAYER_ONE;

	}

	public static void main(String[] args)
	{
		// Suppressed because constructor does all the work
		@SuppressWarnings("unused")
		MainScreen screen = new MainScreen();
	}

}
