import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class GUI implements ActionListener{
	Game game;

	JFrame frame;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	JPanel contentPanel;
	GridBagLayout contentPanelLayout;
	JPanel buttonPanel;
	JPanel timerPanel;
	JPanel scorePanel;
	JLabel scorelabel;
	JLabel[] scoreLabels;
	List<JButton> buttons;
	Font font = new Font("Serif",Font.BOLD,40);

	public GUI () {
		File file = new File("volcabularies/animals.txt");
		Volcabulary vol = new Volcabulary(file);
		game = new Game(3,6,6,3,vol);
	}


	public void run () {

		// frame
		frame = new JFrame("Code Name");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//		frame.setSize(500, 400);
		// menu
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.white);

		menu = new JMenu("menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);
		menuItem = new JMenuItem("save");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);


		contentPanel = new JPanel();
		frame.add(contentPanel);

		buttonPanel = new JPanel(new BorderLayout());
		//		contentPanel.setSize(500, 400);
		buttonPanel.setBackground(Card.colors[game.getStartPlayer()]);
		//		contentPane.setBorder(someBorder);
		//		contentPane.add(someComponent, BorderLayout.CENTER);
		//		contentPane.add(anotherComponent, BorderLayout.PAGE_END);
		//		frame.setContentPane(contentPanel);

		timerPanel = new JPanel(new BorderLayout());
		timerPanel.setBackground(Color.black);

		scorePanel = new JPanel(new BorderLayout());
		scorePanel.setBackground(Color.white);

		contentPanelLayout = new GridBagLayout();
		contentPanel.setLayout(contentPanelLayout);
		GridBagConstraints c = new GridBagConstraints();

		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;		
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		contentPanelLayout.setConstraints(buttonPanel, c);
		contentPanel.add(buttonPanel);

		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		contentPanelLayout.setConstraints(timerPanel, c);
		contentPanel.add(timerPanel);

		c.weightx = .5;
		c.weighty = 3;
		c.gridx = 1;
		c.gridy = 1;
		contentPanelLayout.setConstraints(scorePanel, c);
		contentPanel.add(scorePanel);


		scorePanel.setLayout(new GridLayout(this.game.getNumberofPlayers()+1,1));
		//	JPanel scoreLabelPanel = new JPanel ();
		//		scorePanel.add(scoreLabelPanel);
		//		JLabel scoreLabel = ;
		//		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		//		scoreLabel.setVerticalAlignment(JLabel.CENTER);
		scorelabel = new JLabel("Scores:",SwingConstants.CENTER);
		scorePanel.add(scorelabel);
		scorelabel.setFont(font);


		scoreLabels = new JLabel[game.getNumberofPlayers()];
		for (int i=0;i<game.getNumberofPlayers();i++) {
			JLabel label = new JLabel( Integer.toString(game.getScore(i)) +
					"/" + Integer.toString(game.getPlayerGoal(i)), SwingConstants.CENTER);
			label.setForeground(Card.colors[i]);
			label.setFont(font);
			scoreLabels[i]=label;
			scorePanel.add(label);
		}
		//	frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);//add("label", BorderLayout.CENTER);

		// buttons
		GridLayout buttonLayout = new GridLayout(this.game.getNumberofRows(),this.game.getNumberofColumns());
		buttonPanel.setLayout(buttonLayout);
		buttons = new ArrayList<JButton>();
		for (int i=0;i<game.getNumberofCards();i++) {
			buttons.add(new JButton());
			buttons.get(i).setText(game.getWord(i));
			buttons.get(i).setActionCommand(Integer.toString(i));
			buttons.get(i).addActionListener(this);
			buttons.get(i).setFont(font);
			buttonPanel.add(buttons.get(i));
		}


		//		frame.pack();
		frame.setVisible(true);
		//		splitPane.setDividerLocation(0.2);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index = Integer.parseInt(e.getActionCommand());
		int colorindex = this.game.getColor(index);

		// decide result
		switch (this.game.pickedCard(index)) {
		case Game.NOCHANGE:
			return;
		case Game.CONTINUE: 
			buttons.get(index).setBackground(Card.colors[colorindex]);
			break;
		case Game.CHANGEPLAYER:
			buttonPanel.setBackground(Card.colors[game.getCurrentPlayer()]);
			buttons.get(index).setBackground(Card.colors[colorindex]);
			break;
		case Game.LOSE:
			buttons.get(index).setBackground(Card.colors[colorindex]);
			// reset the score
			//		if (this.game.isBusted(this.game.getCurrentPlayer())==true){
			colorindex = this.game.getCurrentPlayer();
			scoreLabels[colorindex].setText(Integer.toString(game.getScore(colorindex)) +
					"/" + Integer.toString(game.getPlayerGoal(colorindex))+" Dead");
			if (this.game.isFinsihed()) {
				scorelabel.setText(Card.names[game.getWinner()]+" win!");
			}
			//		}
			break;
		case Game.WIN:
			scorelabel.setText(Card.names[game.getWinner()]+" win!");
			buttons.get(index).setBackground(Card.colors[colorindex]);
			break;
		default:
		}



		if (colorindex<game.getNumberofPlayers()) {
			if (this.game.isBusted(colorindex)) {
				scoreLabels[colorindex].setText(Integer.toString(game.getScore(colorindex)) +
						"/" + Integer.toString(game.getPlayerGoal(colorindex))+ " Dead");

			} else {
				scoreLabels[colorindex].setText(Integer.toString(game.getScore(colorindex)) +
						"/" + Integer.toString(game.getPlayerGoal(colorindex)));
			}
		}

	}

}
