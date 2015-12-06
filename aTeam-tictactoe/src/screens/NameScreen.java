/**
 * 
 */
package screens;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * @author AlexKerzner
 *
 */
public class NameScreen
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Prompts the players for their names.
	 * 
	 * @return a string array containing each player's name
	 */
	public static String[] getPlayerNames()
	{
		// Containing panel
		JPanel panel = new JPanel();

		JLabel label_one = new JLabel("Player 1: ");
		panel.add(label_one);
		JTextField text_field_one = new JTextField("Player 1", 15);
		panel.add(text_field_one);

		panel.add(new JSeparator());

		JLabel label_two = new JLabel("Player 2: ");
		panel.add(label_two);
		JTextField text_field_two = new JTextField("Player 2", 15);
		panel.add(text_field_two);
		int result;
		// Ask for player names
		// TODO do.. while loop -> repeat prompt if a player name is the wrong size.
		result = JOptionPane.showConfirmDialog(null, panel,
			"Player Selection Screen", JOptionPane.OK_CANCEL_OPTION);

		String[] player_names = new String[2];

		if (result == JOptionPane.OK_OPTION)
		{
			player_names[0] = text_field_one.getText();
			player_names[1] = text_field_two.getText();
		}
		else
		{
			System.exit(0);
		}
		return player_names;

	}

}
