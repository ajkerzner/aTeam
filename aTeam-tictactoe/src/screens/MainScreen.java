/**
 * 
 */
package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

import game.AboutScreen;
import game.Board;
import game.Player;
import game.Square;

/**
 * 
 * PROOF OF CONCEPT
 * 
 * @author AlexKerzner
 * @author Joe
 * @author Ben
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

	/**
	 * Default serial version UID
	 */
	private static final long	serialVersionUID	= 1L;

	Turn											turn							= Turn.NO_PLAYERS;
	protected Square					game_winner				= Square.EMPTY;
	protected Player					player_one;
	protected Player					player_two;
	protected Board						board;

	private BorderLayout			layout;
	private JPanel						panel;
	private JPanel						game_panel;
	private GridBagLayout			grid;
	private JPanel[]					player_panels;
	private JLabel[]					player_names;
	private JLabel[]					player_scores;
	private JButton[]					buttons;
	private JLabel						current_turn;
	private JMenuBar					menu_bar;
	private JMenu							menu_file;
	private JMenuItem					menu_new_game;
	private JMenuItem					menu_new_players;
	private JMenuItem					menu_exit;
	private JMenu							menu_edit;
	private JMenuItem					menu_undo;
	private JMenu							menu_help;
	private JMenuItem					menu_about;

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
		player_one = new Player("               ");
		player_two = new Player("               ");

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
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(5, 5, 5, 5);

		constraints.anchor = 0;

		final int[] position =
			{ GridBagConstraints.SOUTHWEST, GridBagConstraints.SOUTH,
				GridBagConstraints.SOUTHEAST, GridBagConstraints.WEST,
				GridBagConstraints.CENTER, GridBagConstraints.EAST,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NORTH,
				GridBagConstraints.NORTHEAST

		};
		panel.add(game_panel, BorderLayout.CENTER);
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
			buttons[i].setName(Integer.toString(i + 1));
			buttons[i].setText("");
			buttons[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

			buttons[i].setFocusable(false);

			buttons[i].setFont(button_font);
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

			// Disable button
			buttons[i].setEnabled(false);
			buttons[i].setSize(100, 100);
			buttons[i].setPreferredSize(buttons[i].getSize());
			constraints.anchor = position[i];
			game_panel.add(buttons[i], constraints);
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
		menu_edit.setText("Edit");
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
		menu_help.setText("About");
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
		final String[] symbols =
			{ " X - ", " O - " };
		Player[] players =
			{ player_one, player_two };
		final Font panel_font = new Font(Font.MONOSPACED, Font.PLAIN, 24);
		for (int i = 0; i < 2; i++)
		{
			// Create panel
			player_panels[i] = new JPanel();
			player_panels[i].setLayout(new BorderLayout(10, 10));

			// Add player name
			JLabel name = new JLabel(symbols[i] + players[i].getName());
			name.setFont(panel_font);
			player_names[i] = name;
			player_names[i].setBorder(BorderFactory.createLineBorder(new Color(0)));
			player_panels[i].add(name, BorderLayout.NORTH);

			// Add player name
			JLabel score = new JLabel("Score: " + players[i].getScore());
			score.setFont(panel_font);
			player_scores[i] = score;
			player_panels[i].add(score, BorderLayout.CENTER);

			// Add panel
			panel.add(player_panels[i], locations[i]);

		}

		current_turn = new JLabel("Please start a new game");
		current_turn.setFont(panel_font);
		current_turn.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(current_turn, BorderLayout.SOUTH);

		add(panel);

		// Set default size
		setSize(1280, 720);

		// Center MainScreen on screen
		this.setLocationRelativeTo(null);

		this.setVisible(true);

		// Creates new game
		newGame(true);
	}

	protected boolean updatePlayerNames()
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
		return true;
	}

	protected boolean updateScore()
	{
		player_scores[0].setText(" Score: " + player_one.getScore());
		player_scores[1].setText(" Score: " + player_two.getScore());
		return true;
	}

	protected boolean updateTurnIndicator()
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
		return true;
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
			menu_undo.setEnabled(false);
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

			int result = 0;
			String winner;
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
				{ "Play again", "Switch players", "Exit" };
			result = JOptionPane.showOptionDialog(getParent(),
				winner + " won the game.", "Game Over", JOptionPane.CLOSED_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (result == 0)
			{
				// Default
				newGame(false);
				switch (game_winner)
				{
					case X:
						turn = Turn.PLAYER_ONE;
						break;
					case O:
						turn = Turn.PLAYER_TWO;
						break;
					case EMPTY:
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
								// Do nothing
								break;
						}
				}

			}
			else if (result == 1)
			{
				// New players
				newGame(true);
			}
			else
			{
				exitGame(0);
			}

		}
		else
		{
			menu_undo.setEnabled(true);
			buttons[location - 1].setEnabled(false);
		}

		updateTurnIndicator();
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

	protected String[] getPlayerNames()
	{
		final String player_name_tooltip = "Names must be between 1 and 15 letters";
		final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 15);

		// Message panel
		JPanel panel = new JPanel(new GridLayout(0, 2));

		JLabel label_one = new JLabel("Player 1 Name: ");
		label_one.setFont(font);
		panel.add(label_one);
		JTextField text_field_one = new JTextField("Player 1", 15);
		text_field_one.setFont(font);
		text_field_one.setToolTipText(player_name_tooltip);
		panel.add(text_field_one);

		for (int i = 0; i < 2; i++)
		{
			JLabel separator = new JLabel("---------------");
			separator.setFont(font);
			panel.add(separator);
		}

		JLabel label_two = new JLabel("Player 2 Name: ");
		label_two.setFont(font);
		panel.add(label_two);
		JTextField text_field_two = new JTextField("Player 2", 15);
		text_field_two.setFont(font);
		text_field_one.setToolTipText(player_name_tooltip);
		panel.add(text_field_two);

		int result = JOptionPane.CANCEL_OPTION;
		String player_names[];
		// Ask for player names
		do
		{
			if (result == JOptionPane.OK_OPTION)
			{
				JOptionPane.showMessageDialog(getParent(),
					"Please type in a name for both players, 1 to 15 characters",
					"Name Error", JOptionPane.ERROR_MESSAGE);
			}
			result = JOptionPane.showConfirmDialog(getParent(), panel,
				"Player Selection Screen", JOptionPane.OK_CANCEL_OPTION);

			player_names = new String[2];

			if (result == JOptionPane.OK_OPTION)
			{
				player_names[0] = text_field_one.getText();
				player_names[1] = text_field_two.getText();
			}
			else
			{
				System.exit(0);
			}
		}
		while ((player_names[0].length() < 1) || (player_names[0].length() > 15)
			|| (player_names[1].length() < 1) || (player_names[1].length() > 15));
		return player_names;

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
		}

		turn = Turn.PLAYER_ONE;
		menu_undo.setEnabled(false);

		for (JButton button : buttons)
		{
			button.setEnabled(true);
			button.setText("");
		}

		updateTurnIndicator();
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
			updateTurnIndicator();
			return;
		}
	}

	AboutScreen about = new AboutScreen();

	protected void about()
	{
		// System.out.println("About screen will have opened.");

		about.setVisible(true);
	}

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
