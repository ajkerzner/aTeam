/**
 * 
 */
package game;

/**
 * @author Joe
 *
 */
public class NameScreen extends javax.swing.JFrame
{

	/**
	 * @param args
	 */
	public NameScreen()
	{
		initComponents();
	}
	
	private void initComponents() {

    jTitle = new javax.swing.JLabel(); //The title "Tic-Tac-Toe" on the Name Input Screen
    jLabelP1 = new javax.swing.JLabel(); //The jLabel for "Player 1"
    jTextFieldP1 = new javax.swing.JTextField(); //The jTextField used to input Player 1's name
    jLabelP2 = new javax.swing.JLabel(); //The jLabel for "Player 2"
    jTextFieldP2 = new javax.swing.JTextField(); //The jTextField used to input Player 1's name
    jButtonPlay = new javax.swing.JButton(); //The button pressed to start a game
    jMenuBar1 = new javax.swing.JMenuBar(); //Drop-down bar at top
    jMenuFile = new javax.swing.JMenu(); //Drop-down bar at top
    jNewGame = new javax.swing.JMenuItem(); //New Game option from menu bar
    jExit = new javax.swing.JMenuItem(); //Exit option from menu bar
    jMenuEdit = new javax.swing.JMenu(); //Drop-down bar at top
    jUndo = new javax.swing.JMenuItem(); //Undo option from menu bar
    jMenuHelp = new javax.swing.JMenu(); //Drop-down bar at top
    jAbout = new javax.swing.JMenuItem(); //About option from menu bar

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    //Font stuff for jTitle
    jTitle.setFont(new java.awt.Font("Tahoma", 0, 24));
    jTitle.setText("Tic-Tac-Toe");
    
    
    jLabelP1.setText("Player 1 Name:");
    
    //Default name on startup
    jTextFieldP1.setText("Player 1 Name");    
    
    //Action Listener for Player 1 Name input
    jTextFieldP1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldP1ActionPerformed(evt);
        }
    });

    jLabelP2.setText("Player 2 Name:");
    
    //Default name on startup
    jTextFieldP2.setText("Player 2 Name");
    
    //Action Listener for Player 2 Name Input
    jTextFieldP2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldP2ActionPerformed(evt);
        }
    });

    jButtonPlay.setText("Play");
    
    jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonPlayActionPerformed(evt);
        }
    });
    
    jMenuFile.setText("File");
    
    jMenuFile.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            jMenuFilePropertyChange(evt);
        }
    });

    jNewGame.setText("New Game");
    jNewGame.setEnabled(false);
    
    jMenuFile.add(jNewGame);

    jExit.setText("Exit");
    
    jExit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jExitActionPerformed(evt);
        }
    });
    
    jMenuFile.add(jExit);

    jMenuBar1.add(jMenuFile);

    jMenuEdit.setText("Edit");

    jUndo.setText("Undo Move");
    jUndo.setEnabled(false);
    
    jMenuEdit.add(jUndo);
    jMenuBar1.add(jMenuEdit);

    jMenuHelp.setText("Help");

    jAbout.setText("About");
    
    jAbout.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jAboutActionPerformed(evt);
        }
    });
    
    jMenuHelp.add(jAbout);
    jMenuBar1.add(jMenuHelp);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(117, 117, 117)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabelP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTextFieldP1)
                .addComponent(jLabelP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldP2)
                .addComponent(jButtonPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(138, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabelP1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextFieldP1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabelP2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextFieldP2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(46, Short.MAX_VALUE))
    );

    pack();
}// </editor-fold>                        

private void jNewGameActionPerformed(java.awt.event.ActionEvent evt) {                                         
    // TODO add your handling code here:
}                                        
    String player2Name;
private void jTextFieldP2ActionPerformed(java.awt.event.ActionEvent evt) {                                             
    player2Name = jTextFieldP2.getText();
}                                            

private void jMenuFilePropertyChange(java.beans.PropertyChangeEvent evt) {                                         
    // TODO add your handling code here:
}                                        

private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {                                            
    new MainScreen().setVisible(true);
    dispose();
}                                           

private void jExitActionPerformed(java.awt.event.ActionEvent evt) {                                      
    // TODO add your handling code here:
}                                     
    String player1Name;
private void jTextFieldP1ActionPerformed(java.awt.event.ActionEvent evt) {                                             
    player1Name = jTextFieldP1.getText();
}                                            

private void jAboutActionPerformed(java.awt.event.ActionEvent evt) {                                       
    new AboutScreen().setVisible(true);
}                                      

/**
 * @param args the command line arguments
 */
public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(NameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(NameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(NameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(NameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new NameScreen().setVisible(true);
        }
    });
}

// Variables declaration - do not modify                     
private javax.swing.JMenuItem jAbout;
public javax.swing.JButton jButtonPlay;
private javax.swing.JMenuItem jExit;
private javax.swing.JLabel jLabelP1;
private javax.swing.JLabel jLabelP2;
private javax.swing.JMenuBar jMenuBar1;
private javax.swing.JMenu jMenuEdit;
private javax.swing.JMenu jMenuFile;
private javax.swing.JMenu jMenuHelp;
private javax.swing.JMenuItem jNewGame;
public javax.swing.JTextField jTextFieldP1;
public javax.swing.JTextField jTextFieldP2;
private javax.swing.JLabel jTitle;
private javax.swing.JMenuItem jUndo;
	
}
