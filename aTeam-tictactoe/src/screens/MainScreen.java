/**
 * 
 */
package screens;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import game.AboutScreen;
import game.Board;
import screens.NameScreen;
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
	GridBagLayout							grid;
	JPanel[]									player_panels;
	JLabel[]									player_names;
	JLabel[]									player_scores;
	JButton[]									buttons;
	JLabel										current_turn;
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

		// Set look-and-feel
		setDefaultLookAndFeelDecorated(true);

		// Set layout
		layout = new BorderLayout(20, 20);
		setLayout(layout);

		// Create temporary players
		player_one = new Player("               ");
		player_two = new Player("               ");

		player_names = new JLabel[2];
		player_scores = new JLabel[2];

		panel = new JPanel();

		// Grid layout
		grid = new GridBagLayout();
		panel.setLayout(grid);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.gridheight = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(50, 50, 50, 50);

		constraints.anchor = GridBagConstraints.NORTHWEST;

		final int[] position =
			{ GridBagConstraints.SOUTHWEST, GridBagConstraints.SOUTH,
				GridBagConstraints.SOUTHEAST, GridBagConstraints.WEST,
				GridBagConstraints.CENTER, GridBagConstraints.EAST,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NORTH,
				GridBagConstraints.NORTHEAST

		};
		add(panel, BorderLayout.CENTER);
		buttons = new JButton[9];

		final int[] order =
			{ 7, 8, 9, 4, 5, 6, 1, 2, 3 };
		// final int[] keys =
		// { KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3,
		// KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
		// KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9 };
		final int[] keys =
			{ KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
				KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8,
				KeyEvent.VK_9 };
		for (int i : order)
		{
			// This subtracts 1 from each i-value
			i = i - 1;
			buttons[i] = new JButton(Integer.toString(i + 1));
			buttons[i].setName(Integer.toString(i + 1));
			buttons[i].setText("");
			buttons[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

			buttons[i].setFocusable(false);

			buttons[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
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
			// buttons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			// .put(KeyStroke.getKeyStroke(keys[i], 0), buttons[i].getAction());
			// buttons[i].getActionMap().put(buttons[i].getAction(),
			// buttons[i].getActionListeners());

			// Disable button
			buttons[i].setEnabled(false);
			buttons[i].setSize(100, 100);
			buttons[i].setPreferredSize(buttons[i].getSize());
			constraints.anchor = position[i];
			panel.add(buttons[i], constraints);
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
		menu_edit.setMnemonic(KeyEvent.VK_E);

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
			KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		// Add mnemonic
		menu_about.setMnemonic(KeyEvent.VK_A);
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

		// Create player panels
		JPanel[] player_panels = new JPanel[2];
		final String[] locations =
			{ BorderLayout.WEST, BorderLayout.EAST };
		final String[] symbols =
			{ "X - ", "O - " };
		Player[] players =
			{ player_one, player_two };
		final Font panel_font = new Font(Font.MONOSPACED, Font.PLAIN, 24);
		for (int i = 0; i < 2; i++)
		{
			// Create panel
			player_panels[i] = new JPanel();
			player_panels[i].setLayout(new BorderLayout(5, 5));

			// Add player name
			JLabel name = new JLabel(symbols[i] + players[i].getName());
			name.setFont(panel_font);
			player_names[i] = name;
			player_panels[i].add(name, BorderLayout.NORTH);

			// Add player name
			JLabel score = new JLabel("Score: " + players[i].getScore());
			score.setFont(panel_font);
			player_scores[i] = score;
			player_panels[i].add(score, BorderLayout.CENTER);

			// Add panel
			add(player_panels[i], locations[i]);

		}

		current_turn = new JLabel("Please start a new game");
		current_turn.setFont(panel_font);
		current_turn.setHorizontalAlignment(SwingConstants.CENTER);
		add(current_turn, BorderLayout.SOUTH);

		// Set default size
		setSize(1280, 720);

		this.setLocationByPlatform(true);
		this.setVisible(true);

		// Creates new game
		newGame(true);
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
			System.out.println("Game was won by " + player);
			// TODO Add win screen
		}
		else
		{
			menu_undo.setEnabled(true);
			buttons[location - 1].setEnabled(false);
		}

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
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 15);

		// Message panel
		JPanel panel = new JPanel();

		JLabel label_one = new JLabel("Player 1: ");
		label_one.setFont(font);
		panel.add(label_one);
		JTextField text_field_one = new JTextField("", 15);
		text_field_one.setFont(font);
		panel.add(text_field_one);

		panel.add(new JSeparator(SwingConstants.HORIZONTAL));

		JLabel label_two = new JLabel("Player 2: ");
		label_two.setFont(font);
		panel.add(label_two);
		JTextField text_field_two = new JTextField("", 15);
		text_field_two.setFont(font);
		panel.add(text_field_two);
		int result = JOptionPane.CANCEL_OPTION;
		String player_names[];
		// Ask for player names
		do
		{
			if (result == JOptionPane.OK_OPTION)
			{
				JOptionPane.showMessageDialog(panel,
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
		// Get player names, if applicable
		if (get_new_players)
		{
			// Call NameScreen
			String[] player_names =
				{ "Player 1", "Player 2" };
			player_names = getPlayerNames();
			if (player_names.length != 2)
			{
				// TODO Handle error
				System.out.println("Error");
			}
			player_one = new Player(player_names[0]);
			player_two = new Player(player_names[1]);
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

			this.player_names[0].setText("X - " + player_one_name);
			this.player_names[1].setText("O - " + player_two_name);

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

	AboutScreen about = new AboutScreen();

	protected void about()
	{
		// System.out.println("About screen will have opened.");
		about.setVisible(true);
	}

	public static void main(String[] args)
	{

		// See
		// https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html#programmatic
		// for how to do this

		// Warning: this is close to example code.
		try
		{
			// Set System Look and Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException error)
		{
			error.printStackTrace();
		}
		catch (ClassNotFoundException error)
		{
			error.printStackTrace();
		}
		catch (InstantiationException error)
		{
			error.printStackTrace();
		}
		catch (IllegalAccessException error)
		{
			error.printStackTrace();
		}

		new MainScreen();
	}

}
