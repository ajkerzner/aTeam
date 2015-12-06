/**
 * 
 */
package screens;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	protected Board						board;

	protected Square					game_winner				= Square.EMPTY;

	/**
	 * Default serial version UID
	 */
	private static final long	serialVersionUID	= 1L;

	BorderLayout							layout;
	JPanel										panel;
	GridLayout								grid;
	JButton[]									buttons;
	JLabel[]									player_names;
	JMenuBar									menu_bar;
	JMenu											menu_file;
	JMenuItem									menu_new_game;
	JMenuItem									menu_exit;
	JMenu											menu_edit;
	JMenuItem									menu_undo;
	JMenu											menu_help;
	JMenuItem									menu_about;

	// Here's a video on java programming that may be helpful:
	// https://youtu.be/dQw4w9WgXcQ

	/**
	 * 
	 */
	public MainScreen()
	{

		// Set title
		super("aTeam Tic-Tac-Toe");

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set default size
		setSize(640, 480);

		// Set layout
		layout = new BorderLayout();
		setLayout(layout);

		grid = new GridLayout(3, 3);
		panel = new JPanel();
		panel.setLayout(grid);
		add(panel, BorderLayout.CENTER);
		buttons = new JButton[9];
		// GridLayout.;
		final int[] order =
			{ 7, 8, 9, 4, 5, 6, 1, 2, 3 };
		final int[] keys =
			{ KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3,
				KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
				KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9 };
		for (int i : order)
		{
			// This subtracts 1 from each i-value
			i = i - 1;
			buttons[i] = new JButton(Integer.toString(i + 1));
			buttons[i].setName(Integer.toString(i + 1));
			buttons[i].setText("");
			buttons[i].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 42));
			buttons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent event)
				{
					// Calls move(button_number).
					move(Integer.parseInt(((JButton) event.getSource()).getName()));
				}
			});
			// Add keyboard shortcut
			buttons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(keys[i], 0), buttons[i].getAction());

			// Disable button
			buttons[i].setEnabled(false);
			panel.add(buttons[i]);
		}

		// Create Menu Bar
		menu_bar = new JMenuBar();

		// Create File menu
		menu_file = new JMenu("File");
		menu_file.setMnemonic(KeyEvent.VK_F);
		// KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));

		/**
		 * Create File->New_Game menu item
		 */
		menu_new_game = new JMenuItem("New Game");
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
				newGame(true);
			}
		});
		// Add to File menu
		menu_file.add(menu_new_game);

		/**
		 * Create File->Exit menu item
		 */
		menu_exit = new JMenuItem("Exit");
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

		// Create Edit menu
		menu_edit = new JMenu("Edit");
		menu_file.setMnemonic(KeyEvent.VK_E);

		/**
		 * Create Edit->Undo menu item
		 */
		menu_undo = new JMenuItem("Undo");
		// Add keyboard shortcut
		menu_undo.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_undo.setMnemonic(KeyEvent.VK_U);
		// Add action to call undo()
		menu_undo.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event)
			{
				undo();
			}
		});
		// Add to Edit menu
		menu_edit.add(menu_undo);

		menu_bar.add(menu_edit);

		// Create Help menu
		menu_help = new JMenu("Help");
		menu_help.setMnemonic(KeyEvent.VK_H);

		/**
		 * Create Help->About menu item
		 */
		menu_about = new JMenuItem("About");
		// Add keyboard shortcut
		menu_about.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_about.setMnemonic(KeyEvent.VK_H);
		// Add action to call about()
		menu_about.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event)
			{
				about();
			}
		});
		// Add to Help menu
		menu_help.add(menu_about);

		// Add Help to menu bar
		menu_bar.add(menu_help);

		// Sets the menu bar
		this.setJMenuBar(menu_bar);

		// Creates new game
		newGame(true);
		// Create labels
		player_names = new JLabel[2];

		player_names[0] = new JLabel();
		player_names[0].setText("X - " + player_one.getName());
		player_names[0].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 38));
		add(player_names[0], BorderLayout.WEST);

		player_names[1] = new JLabel();
		player_names[1].setText("O - " + player_two.getName());
		player_names[1].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 38));
		add(player_names[1], BorderLayout.EAST);

		// https://youtu.be/dQw4w9WgXcQ

		// Prevents evil location placement.
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	public boolean move(int location)
	{
		Square this_move = Square.EMPTY;
		String player = "";
		switch (turn)
		{
			case PLAYER_ONE:
				player = "X";
				this_move = Square.X;
				turn = Turn.PLAYER_TWO;
				break;
			case PLAYER_TWO:
				player = "O";
				this_move = Square.O;
				turn = Turn.PLAYER_ONE;
				break;
			case NO_PLAYERS:
			default:
				player = "";
				this_move = Square.EMPTY;
				turn = Turn.NO_PLAYERS;
				break;
		}
		// Update button
		buttons[location - 1].setText(player);

		if (board.next(location, this_move))
		{
			// Game is over
			game_winner = board.getLastPlayer();
			turn = Turn.NO_PLAYERS;
			for (JButton button : buttons)
			{
				button.setEnabled(false);
			}
			if (game_winner.isEmpty())
			{
				player = "no one";
			}
			System.out.println("Game was won by " + player);
			return true;
		}
		else
		{
			menu_undo.setEnabled(true);
			buttons[location - 1].setEnabled(false);
		}

		return true;
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
		menu_undo.setEnabled(false);

		for (JButton button : buttons)
		{
			button.setEnabled(true);
			button.setText("");
		}

	}

	protected void undo()
	{
		// System.out.println("Undo");

		if (board.isUndoPossible())
		{
			// Undo is possible
			int result = board.undo();
			if (result <= 0)
			{
				// ERROR
				// This shouldn't be reached. The isUndoPossible() should prevent this
				// from happening.
			}
			else
			{
				buttons[result - 1].setEnabled(true);
				buttons[result - 1].setText("");
				switch (turn)
				{
					case PLAYER_ONE:
						turn = Turn.PLAYER_TWO;
						break;
					case PLAYER_TWO:
						turn = Turn.PLAYER_ONE;
						break;
					case NO_PLAYERS:
					default:
						break;
				}

			}
			menu_undo.setEnabled(false);
			;
			return;
		}
	}

	protected void about()
	{
		System.out.println("About screen will have opened.");
	}

	public static void main(String[] args)
	{
		// Suppressed because constructor does all the work
		@SuppressWarnings("unused")
		MainScreen screen = new MainScreen();
	}

}
