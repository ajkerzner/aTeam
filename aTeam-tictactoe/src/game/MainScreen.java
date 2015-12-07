/**
 * @author AlexKerzner
 * @author BenjaminDodson
 * @author RonaldDrescher
 * @author WalterGoerling
 * @author JosephMiller
 */

package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;

/**
 * 
 * The Main Screen.
 * 
 * @author AlexKerzner
 * @author BenjaminDodson
 * @author RonaldDrescher
 * @author WalterGoerling
 * @author JosephMiller
 * 
 * @version 1.1
 * 
 * @see Board
 */
public class MainScreen extends JFrame
{
	/**
	 * An enumerated value representing the players
	 * 
	 * @see Square
	 */
	protected enum Turn
	{
		PLAYER_ONE,
		PLAYER_TWO,
		NO_PLAYERS;

		/**
		 * Returns a {@link Square} equivalent of a Turn.
		 * 
		 * @return a square
		 * 
		 * @see Turn
		 */
		protected Square getPlayer()
		{
			switch (this)
			{
				case PLAYER_ONE:
					return Square.X;
				case PLAYER_TWO:
					return Square.O;
				case NO_PLAYERS:
				default:
					return Square.EMPTY;
			}
		}

		/**
		 * Gets the Turn equivalent of a {@link Square}.
		 * 
		 * @return a Turn
		 * 
		 * @see Turn
		 */
		protected static Turn getTurn(Square square)
		{
			switch (square)
			{
				case X:
					return Turn.PLAYER_ONE;
				case O:
					return Turn.PLAYER_TWO;
				case EMPTY:
				default:
					return Turn.NO_PLAYERS;
			}
		}

		/**
		 * Switch to other player unless {@link Turn#NO_PLAYERS}.
		 * 
		 * @return the opposite Turn
		 */
		protected Turn switchPlayer()
		{
			switch (this)
			{
				case PLAYER_ONE:
					return PLAYER_TWO;
				case PLAYER_TWO:
					return PLAYER_ONE;
				case NO_PLAYERS:
				default:
					return NO_PLAYERS;
			}
		}
	}

	/**
	 * Default serial version UID
	 */
	private static final long		serialVersionUID	= 1L;

	/**
	 * Release version
	 */
	private static final double	VERSION						= 1.2;

	/**
	 * Fixed width of the Main Screen
	 */
	private static final int		WIDTH							= 1000;

	/**
	 * Fixed height of the Main Screen
	 */
	private static final int		HEIGHT						= 500;

	// Variable initialization
	Turn												turn							= Turn.NO_PLAYERS;
	protected Square						game_winner				= Square.EMPTY;

	protected Turn							first_turn				= Turn.PLAYER_ONE;
	protected Player						player_one;
	protected Player						player_two;
	protected Board							board							= new Board();

	private BorderLayout				layout;
	private JPanel							panel;
	private JPanel							game_panel;
	private GridBagLayout				grid;
	private JPanel[]						player_panels;
	private JLabel[]						player_names;
	private JLabel[]						player_scores;
	private JPanel[]						panels;
	private JButton[]						buttons;
	private JLabel							current_turn;
	private JMenuBar						menu_bar;
	private JMenu								menu_file;
	private JMenuItem						menu_new_game;
	private JMenuItem						menu_new_players;
	private JMenuItem						menu_exit;
	private JMenu								menu_edit;
	private JMenuItem						menu_undo;
	private JMenu								menu_help;
	private JMenuItem						menu_about;

	/**
	 * 
	 */
	public MainScreen()
	{

		// Set title
		super("aTeam Tic-Tac-Toe");

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set look-and-feel
		setDefaultLookAndFeelDecorated(true);

		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(panel);

		// Set layout
		layout = new BorderLayout(20, 20);
		panel.setLayout(layout);

		// Create temporary players
		player_one = new Player("Player 1");
		player_two = new Player("Player 2");

		player_names = new JLabel[2];
		player_scores = new JLabel[2];

		game_panel = new JPanel();

		// Grid layout
		grid = new GridBagLayout();
		game_panel.setLayout(grid);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = GridBagConstraints.CENTER;
		constraints.gridy = GridBagConstraints.CENTER;
		constraints.gridwidth = 3;
		constraints.gridheight = 3;
		constraints.weightx = .5;
		constraints.weighty = .5;
		// constraints.insets = new Insets(5, 5, 5, 5);

		constraints.anchor = 0;

		final int[] position =
			{ GridBagConstraints.SOUTHWEST, GridBagConstraints.SOUTH,
				GridBagConstraints.SOUTHEAST, GridBagConstraints.WEST,
				GridBagConstraints.CENTER, GridBagConstraints.EAST,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NORTH,
				GridBagConstraints.NORTHEAST

		};
		game_panel.setBackground(Color.GRAY);
		panel.add(game_panel, BorderLayout.CENTER);

		panels = new JPanel[9];
		buttons = new JButton[9];

		final Font button_font = new Font(Font.MONOSPACED, Font.PLAIN, 42);
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

			// Set name
			buttons[i].setName(Integer.toString(i + 1));

			// Adds action
			buttons[i].setAction(new AbstractAction()
			{
				/**
				 * Default serial version UID
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent event)
				{
					// Calls move(button_number).
					if (((JButton) event.getSource()).isEnabled())
					{
						move(Integer.parseInt(((JButton) event.getSource()).getName()));
					}
				}
			});

			buttons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(keys[i], 0), String.valueOf(i));
			buttons[i].getActionMap().put(String.valueOf(i), buttons[i].getAction());
			buttons[i].setText("");
			buttons[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

			buttons[i].setFocusable(false);

			buttons[i].setFont(button_font);

			// Disable button
			buttons[i].setEnabled(false);
			buttons[i].setSize(100, 100);

			panels[i] = new JPanel();

			panels[i].setSize(105, 105);
			buttons[i].setPreferredSize(buttons[i].getSize());
			constraints.anchor = position[i];
			panels[i].add(buttons[i]);
			game_panel.add(panels[i], constraints);
		}

		// Create Menu Bar
		menu_bar = new JMenuBar();

		// Create File menu
		menu_file = new JMenu("File");
		menu_file.setMnemonic(KeyEvent.VK_F);

		/**
		 * Create File->New_Game menu item
		 */
		menu_new_game = new JMenuItem("New Game");
		// Add action to call newGame(false)
		menu_new_game.setAction(new AbstractAction()
		{

			/**
			 * Default serial version UID
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event)
			{
				// Start new game, keep existing players
				newGame(false);
			}
		});
		// Add text
		menu_new_game.setText("New Game");
		// Add keyboard shortcut
		menu_new_game.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_new_game.setMnemonic(KeyEvent.VK_N);

		// Add to File menu
		menu_file.add(menu_new_game);

		/**
		 * Create File->New_Players menu item
		 */
		menu_new_players = new JMenuItem("New Players");
		// Add action to call newGame(true)
		menu_new_players.setAction(new AbstractAction()
		{
			/**
			 * Default serial version UID
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event)
			{
				// Start new game with new players
				newGame(true);
			}
		});
		// Add text
		menu_new_players.setText("New Players");
		// Add keyboard shortcut
		menu_new_players.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
			ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_new_players.setMnemonic(KeyEvent.VK_P);

		// Add to File menu
		menu_file.add(menu_new_players);

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
		menu_exit.setAction(new AbstractAction()
		{
			/**
			 * Default serial version UID
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event)
			{
				// Exit normally
				exitGame(0);
			}
		});
		// Add text
		menu_exit.setText("Exit");
		// Add keyboard shortcut
		menu_exit.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		// Add mnemonic
		menu_exit.setMnemonic(KeyEvent.VK_E);
		// Add to File menu
		menu_file.add(menu_exit);

		menu_bar.add(menu_file);

		// Create Edit menu
		menu_edit = new JMenu("Edit");
		menu_edit.setMnemonic(KeyEvent.VK_E);

		/**
		 * Create Edit->Undo menu item
		 */
		menu_undo = new JMenuItem("Undo");
		// Add action to call undo()
		menu_undo.setAction(new AbstractAction()
		{
			/**
			 * Default serial version UID
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event)
			{
				// Undo
				undo();
			}
		});
		// Add text
		menu_undo.setText("Undo");
		// Add keyboard shortcut
		menu_undo.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_undo.setMnemonic(KeyEvent.VK_U);

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
		// Add action to call about()
		menu_about.setAction(new AbstractAction()
		{
			/**
			 * Default serial version UID
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event)
			{
				// About screen
				about();
			}
		});
		// Add text
		menu_about.setText("About");
		// Add keyboard shortcut
		menu_about.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_about.setMnemonic(KeyEvent.VK_A);
		// Add to Help menu
		menu_help.add(menu_about);

		// Add Help to menu bar
		menu_bar.add(menu_help);

		// Sets the menu bar
		this.setJMenuBar(menu_bar);

		// Create player panels
		player_panels = new JPanel[2];
		final String[] locations =
			{ BorderLayout.WEST, BorderLayout.EAST };
		final Font panel_font = new Font(Font.MONOSPACED, Font.PLAIN, 24);
		for (int i = 0; i < 2; i++)
		{
			// Create panel
			player_panels[i] = new JPanel();
			player_panels[i].setLayout(new BorderLayout(10, 10));

			// Add player name
			JLabel name = new JLabel();
			name.setFont(panel_font);
			player_names[i] = name;
			player_names[i].setBorder(BorderFactory.createLineBorder(new Color(0)));
			player_panels[i].add(name, BorderLayout.NORTH);

			// Add player name
			JLabel score = new JLabel();
			score.setFont(panel_font);
			player_scores[i] = score;
			player_panels[i].add(score, BorderLayout.CENTER);

			// Add panel
			panel.add(player_panels[i], locations[i]);

		}
		updatePlayerNames();
		updateScore();

		current_turn = new JLabel("Please start a new game");
		current_turn.setFont(panel_font);
		current_turn.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(current_turn, BorderLayout.SOUTH);

		add(panel);

		// Set default size
		setSize(WIDTH, HEIGHT);
		setResizable(false);

		// Center MainScreen on screen
		this.setLocationRelativeTo(null);

		this.setVisible(true);

		// Creates new game
		newGame(true);
	}

	/**
	 * Switch turns
	 * 
	 * @see MainScreen
	 * 
	 * @since 1.1
	 */
	private void switchTurns()
	{
		if (board.isGameOver())
		{
			// Game is over
			// Switch players if the player that won went first, or if a tie
			if (first_turn == Turn.getTurn(game_winner)
				|| Turn.NO_PLAYERS == Turn.getTurn(game_winner))
			{
				first_turn = first_turn.switchPlayer();
				turn = first_turn;
			}
			else
			{
				// Otherwise, set the turn to first_turn
				turn = first_turn;
			}
		}
		else

		{
			// Game is not over
			turn = turn.switchPlayer();
		}

	}

	/**
	 * Updates the player names.
	 */
	protected void updatePlayerNames()
	{
		String player_one_name = player_one.getName();
		String player_two_name = player_two.getName();
		while (player_one_name.length() < 15)
		{
			// append " " to name
			player_one_name = player_one_name + " ";
		}
		while (player_two_name.length() < 15)
		{
			// append " " to name
			player_two_name = player_two_name + " ";
		}

		this.player_names[0].setText(" X - " + player_one_name);
		this.player_names[1].setText(" O - " + player_two_name);
	}

	/**
	 * Updates the score indicator.
	 */
	protected void updateScore()
	{
		player_scores[0].setText(" Score: " + player_one.getScore());
		player_scores[1].setText(" Score: " + player_two.getScore());
	}

	/**
	 * Updates the turn indicator.
	 */
	protected void updateTurnIndicator()
	{
		switch (turn)
		{
			case PLAYER_ONE:
				current_turn.setText("" + player_one.getName() + "'s turn");
				break;
			case PLAYER_TWO:
				current_turn.setText("" + player_two.getName() + "'s turn");
				break;
			case NO_PLAYERS:
			default:
				current_turn.setText("Please start a new game");
				break;

		}
	}

	/**
	 * Plays a move.
	 * 
	 * @param location
	 *          the location of the move
	 */
	public void move(int location)
	{
		Square player = turn.getPlayer();

		// Update button
		buttons[location - 1].setText(player.toString());

		if (board.next(location, player))
		{
			// Game is over

			Turn last_turn = turn;
			turn = Turn.NO_PLAYERS;
			updateTurnIndicator();
			turn = last_turn;

			menu_undo.setEnabled(false);
			game_winner = board.getLastPlayer();

			switchTurns();

			for (JButton button : buttons)
			{
				button.setEnabled(false);
			}

			int result = 0;
			String winner;
			// Update scores and get winner's name
			switch (game_winner)
			{
				case X:
					winner = player_one.getName();
					player_one.addGame(true);
					player_two.addGame(false);
					break;
				case O:
					winner = player_two.getName();
					player_one.addGame(false);
					player_two.addGame(true);
					break;
				case EMPTY:
				default:
					winner = "Nobody";
					player_one.addGame(false);
					player_two.addGame(false);
			}

			updateScore();

			String[] options =
				{ "Play again", "New players", "Cancel" };
			result = JOptionPane.showOptionDialog(getContentPane(),
				winner + " won the game.", "Game Over", JOptionPane.CLOSED_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (result == 0)
			{
				// Default
				newGame(false);
			}
			else if (result == 1)
			{
				// New players
				newGame(true);
			}
			else
			{
				// Do nothing
			}

		}
		else
		{
			// Continue game

			menu_undo.setEnabled(true);
			buttons[location - 1].setEnabled(false);

			switchTurns();
			updateTurnIndicator();
		}

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

	/**
	 * Prompts for new players.
	 * 
	 * @return a String array containing two strings - the names of player one and
	 *         player two
	 */
	protected String[] getPlayerNames()
	{
		final String player_name_tooltip = "Names must be between 1 and 15 letters";
		final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 15);

		// Message panel
		JPanel panel = new JPanel(new GridLayout(0, 2));

		JLabel info_label_one = new JLabel("Enter player names");
		info_label_one.setFont(font);
		panel.add(info_label_one);

		JLabel info_label_two = new JLabel(" up to 15 letters.");
		info_label_two.setFont(font);
		panel.add(info_label_two);

		JLabel info_label_three = new JLabel("Leave blank to use");
		info_label_three.setFont(font);
		panel.add(info_label_three);

		JLabel info_label_four = new JLabel(" existing names.");
		info_label_four.setFont(font);
		panel.add(info_label_four);

		for (int i = 0; i < 2; i++)
		{
			JLabel separator = new JLabel("                  ");
			separator.setFont(font);
			panel.add(separator);
		}

		JLabel label_one = new JLabel(" Player 1's Name: ");
		label_one.setFont(font);
		panel.add(label_one);
		String name_one = player_one.getName().trim();
		if (name_one.equals("Player 1"))
		{
			// Clear Player 1 default name
			name_one = "";
		}
		JTextField text_field_one = new JTextField(name_one, 15);
		text_field_one.setFont(font);
		text_field_one.setToolTipText(player_name_tooltip);

		((AbstractDocument) text_field_one.getDocument())
			.setDocumentFilter(new NameFilter());
		panel.add(text_field_one);

		for (int i = 0; i < 2; i++)
		{
			JLabel separator = new JLabel("                  ");
			separator.setFont(font);
			panel.add(separator);
		}

		JLabel label_two = new JLabel(" Player 2's Name: ");
		label_two.setFont(font);
		panel.add(label_two);
		String name_two = player_two.getName().trim();
		if (name_two.equals("Player 2"))
		{
			// Clear Player 2 default name
			name_two = "";
		}
		JTextField text_field_two = new JTextField(name_two, 15);
		text_field_two.setPreferredSize(text_field_two.getPreferredSize());
		text_field_two.setFont(font);
		text_field_one.setToolTipText(player_name_tooltip);
		((AbstractDocument) text_field_two.getDocument())
			.setDocumentFilter(new NameFilter());
		panel.add(text_field_two);

		for (int i = 0; i < 2; i++)
		{
			JLabel separator = new JLabel("                  ");
			separator.setFont(font);
			panel.add(separator);
		}

		int result = JOptionPane.CANCEL_OPTION;
		String player_names[];
		// Ask for player names
		result = JOptionPane.showConfirmDialog(getContentPane(), panel,
			"Player Creation Screen", JOptionPane.OK_CANCEL_OPTION);

		player_names = new String[2];

		if (result == JOptionPane.OK_OPTION)
		{
			// Change players

			// Player 1
			name_one = text_field_one.getText().trim();
			if (name_one.isEmpty())
			{
				// Set default name for player 1
				name_one = "Player 1";
			}
			player_names[0] = name_one;

			// Player 2
			name_two = text_field_two.getText().trim();
			if (name_two.isEmpty())
			{
				// Set default name for player 2
				name_two = "Player 2";
			}
			player_names[1] = name_two;
		}
		else
		{
			// Do not change players
			player_names[0] = player_one.getName();
			player_names[1] = player_two.getName();
		}
		return player_names;

	}

	/**
	 * Start a new game with existing players
	 */
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

		// Get player names, if applicable
		if (get_new_players)
		{
			// Call NameScreen
			String[] player_names = new String[2];
			player_names = getPlayerNames();
			if (player_names.length != 2)
			{
				player_names[0] = "Player 1";
				player_names[1] = "Player 2";
			}
			player_one = new Player(player_names[0]);
			player_two = new Player(player_names[1]);
			updateScore();
			updatePlayerNames();

			first_turn = Turn.PLAYER_ONE;
		}
		else
		{
			if (!board.isGameOver())
			{
				// If game did not finish
				turn = first_turn;
			}
		}

		board = new Board();
		turn = first_turn;

		menu_undo.setEnabled(false);

		for (JButton button : buttons)
		{
			button.setEnabled(true);
			button.setText("");
		}

		updateTurnIndicator();
	}

	/**
	 * Undo the last move, if possible.
	 */
	protected void undo()
	{
		if (board.isUndoPossible())
		{
			// Undo is possible
			int result = board.undo();
			if (result <= 0)
			{
				// Unknown error
				System.err.println("Error - board.undo() failed. Contact aTeam.");
				System.exit(1);
			}
			else
			{
				buttons[result - 1].setEnabled(true);
				buttons[result - 1].setText("");
				turn = turn.switchPlayer();

			}
			menu_undo.setEnabled(false);
			updateTurnIndicator();
			return;
		}
	}

	/**
	 * Shows the About Screen.
	 * 
	 */
	protected void about()
	{
		String about_text = "<html><center>" + "<H2>Written by aTeam</H2>"
			+ "<H3>Benjamin Dodson<br>Ronnie Drescher<br>"
			+ "Walter Goerling<br>Alexander Kerzner<br>Joseph Miller</H3>"
			+ "<br><b>Tic-Tac-Toe version " + VERSION + "</b></center></html>";
		JOptionPane.showMessageDialog(getContentPane(), about_text,
			"About Tic-Tac-Toe", JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * Main method
	 * 
	 * @param args
	 *          the command line arguments
	 */
	public static void main(String[] args)
	{
		try
		{
			// Set System Look and Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException error)
		{
			error.printStackTrace();
		}
		catch (IllegalAccessException error)
		{
			error.printStackTrace();
		}
		catch (InstantiationException error)
		{
			error.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException error)
		{
			error.printStackTrace();
		}

		new MainScreen();
	}

}
