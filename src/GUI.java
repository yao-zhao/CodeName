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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI implements ActionListener{
	Game game;
	boolean isMaster=false;

	JFrame frame;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem viewSwitch;
	JMenuItem startNewGame;
	JPanel contentPanel;
	GridBagLayout contentPanelLayout;
	JPanel buttonPanel;
	JPanel timerPanel;
	JPanel scorePanel;
	JLabel scorelabel;
	JLabel[] scoreLabels;
	List<JButton> buttons;
	Font font = new Font("Serif",Font.BOLD,40);
	Font font_reverse = new Font("Serif",Font.BOLD,40);
	
	JPanelSelector<Integer>[] textPanels;
	JPanelSelector<String> databasePanel;


	public GUI () {
//		Volcabulary vol = new Volcabulary();
//		game = new Game(3,6,6,3,vol);
		isMaster = true;
	}

	public void setFrame () {
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

		frame.setJMenuBar(menuBar);
		
		// add contentpanel
		contentPanel = new JPanel();
		contentPanelLayout = new GridBagLayout();
		contentPanel.setLayout(contentPanelLayout);
		frame.add(contentPanel);
	}

	
	public void run () {
		// set base frame
		setFrame();
		// add switch button
		if (isMaster) {
			viewSwitch = new JMenuItem("switch to team view");
		}else {
			viewSwitch = new JMenuItem("switch to master view");
		}
		viewSwitch.setActionCommand("switch window");
		viewSwitch.addActionListener(this);
		viewSwitch.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		viewSwitch.getAccessibleContext().setAccessibleDescription(
				"switch between team view and master view");
		menu.add(viewSwitch);
		// startNewGame
		startNewGame = new JMenuItem("start a new game");
		startNewGame.setActionCommand("start a new game");
		startNewGame.addActionListener(this);
		menu.add(startNewGame);

		buttonPanel = new JPanel(new BorderLayout(50,50));
		buttonPanel.setBorder( BorderFactory.createLineBorder(Card.colors[game.getCurrentPlayer()], 10));


		timerPanel = new JPanel(new BorderLayout());
		timerPanel.setBackground(Color.black);

		scorePanel = new JPanel(new BorderLayout());
		scorePanel.setBackground(Color.white);

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
		buttonLayout.setHgap(5);
		buttonLayout.setVgap(5);
		buttonPanel.setLayout(buttonLayout);
		buttons = new ArrayList<JButton>();
		for (int i=0;i<game.getNumberofCards();i++) {
			buttons.add(new JButton());
			//			JLabel label1 = new JLabel(flipString.flip(game.getWord(i)),SwingConstants.CENTER);
			//			JLabel label1 = new JLabel(new StringBuilder(game.getWord(i)).reverse().toString(),SwingConstants.CENTER);
			JLabel label1 = new JLabel(game.getWord(i),SwingConstants.CENTER);
			label1.setOpaque(false);
			JLabel label2 = new JLabel(game.getWord(i),SwingConstants.CENTER);
			JPanelReversed panel1 = new JPanelReversed();
			//JPanel panel1 = new JPanel();
			JPanel panel2 = new JPanel();
			panel1.add(label1);
			panel2.add(label2);
			label1.setFont(font);
			label2.setFont(font);
			buttons.get(i).setLayout(new BorderLayout());
			buttons.get(i).add(BorderLayout.NORTH,panel1);
			buttons.get(i).add(BorderLayout.SOUTH,panel2);
			panel1.setOpaque(false);
			panel2.setOpaque(false);
			label1.setForeground(new Color((float)0.4,(float)0.4,(float)0.4));

			//			buttons.get(i).setText(game.getWord(i));
			buttons.get(i).setActionCommand(Integer.toString(i));
			buttons.get(i).addActionListener(this);
			buttonPanel.add(buttons.get(i));
		}
		drawScores();
		drawButtons();
		//		frame.pack();
		frame.setVisible(true);
		//		splitPane.setDividerLocation(0.2);

	}
	
	@SuppressWarnings("unchecked")
	public void startNewGame() {
		setFrame();

		GridBagConstraints c = new GridBagConstraints();

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		JPanel selectors = new JPanel();
		contentPanelLayout.setConstraints(selectors, c);
		contentPanel.add(selectors);
		
	    textPanels = (JPanelSelector<Integer>[])new JPanelSelector[4];
		Integer[] playeroptions = {2, 3, 4};
		textPanels[0] =new JPanelSelector<>("number of players",playeroptions );
		selectors.add(textPanels[0]);
		Integer[] rowoptions = {5, 6, 7};
		textPanels[1] = new JPanelSelector<>("number of rows",rowoptions );
		selectors.add(textPanels[1]);
		Integer[] columnoptions = {5,6,7};
		textPanels[2] = new JPanelSelector<>("number of columns",columnoptions );
		selectors.add(textPanels[2]);
		Integer[] bomboptions = {0,1,2,3,4,5,6,7,8,9,10};
		textPanels[3] = new JPanelSelector<>("number of bombs",bomboptions);
		textPanels[3].setSelectorIndex(1);;
		selectors.add(textPanels[3]);
		for (JPanelSelector<Integer> tp:textPanels) {
			tp.setTextFont(font);
		}
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		String[] names = new String[1];
		this.databasePanel = new JPanelSelector<>("choose a database",Volcabulary.getTitles() );
		databasePanel.setTextFont(font);
		contentPanelLayout.setConstraints(databasePanel, c);
		contentPanel.add(databasePanel);
		
		c.weightx = 1;
		c.weighty = 5;
		c.gridx = 0;
		c.gridy = 3;		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		JPanel showpanel = new JPanel();
		contentPanelLayout.setConstraints(showpanel, c);
		contentPanel.add(showpanel);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 4;		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		JButton startbutton = new JButton();
		startbutton.setActionCommand("load data and start");
		startbutton.addActionListener(this);
		contentPanelLayout.setConstraints(startbutton, c);
		contentPanel.add(startbutton);
		JLabel newlabel = new JLabel("Start!");
		newlabel.setFont(font);
		startbutton.add(newlabel,BorderLayout.CENTER);
	

	//	textPanels[0].setLabel(JPanelTextEditor.INPUT, "number of players");
		
		frame.setVisible(true);

	}

	public void switchWindow () {
		clearWindow();
		this.isMaster=!this.isMaster;
		this.run();
	}

	public void clearWindow() {
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame.removeAll();
	}
	
	public void loadData () {
		Volcabulary vol = new Volcabulary(databasePanel.getSelectedIndex());
		game = new Game(textPanels[0].getSelectedValue(),textPanels[1].getSelectedValue(),
				textPanels[2].getSelectedValue(),textPanels[3].getSelectedValue(),vol);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String eventCommand = e.getActionCommand();
		switch (eventCommand) {
		case "load data and start":
			loadData();
			clearWindow();
			run();
			break;
		case "swtich window":
			switchWindow();
			break;
		case "start a new game":
			clearWindow();
			startNewGame();
			break;
		default:
			step(eventCommand);
		}
	}

	public void step(String command) {
		if (!this.game.isFinsihed()) {
			if (isMaster) {
				switchWindow();
				//		JOptionPane.showMessageDialog(frame, "Please give to the team");
				return;
			}
			int index = Integer.parseInt(command);

			// decide result
			int result = this.game.pickedCard(index);
			drawScores();
			drawButtons();
			drawCurrentPlayerBackground();

			//
			switch (result) {
			case Game.NOCHANGE:
				return;
			case Game.CONTINUE: 

				break;
			case Game.CHANGEPLAYER:
			case Game.LOSE:
				//	JOptionPane optionPane = new JOptionPane("Please give to the master");
				//optionPane.setMessage(new Message("Please give to the master"));
				//option.showMessageDialog(frame, "Please give to the master");
				if (!this.game.isFinsihed()) {
					JOptionPane.showMessageDialog(frame, "Please give to the master");
					switchWindow();
					break;
				}
			case Game.WIN:
				switchWindow();
				break;
			default:
			}
		}
	}

	public void drawCurrentPlayerBackground() {
		buttonPanel.setBorder( BorderFactory.createLineBorder(Card.colors[game.getCurrentPlayer()], 10));
	}

	public void drawScores () {
		if (this.game.isFinsihed()) {
			scorelabel.setText(Card.names[game.getWinner()]+" win!");
			scorelabel.setForeground(Card.colors[game.getWinner()]);
		}
		for (int colorindex=0; colorindex<game.getNumberofPlayers();colorindex++) {
			if (this.game.isBusted(colorindex)) {
				scoreLabels[colorindex].setText(" Dead");

			} else {
				scoreLabels[colorindex].setText(Integer.toString(game.getScore(colorindex)) +
						"/" + Integer.toString(game.getPlayerGoal(colorindex)));
			}
		}
	}

	public void drawButtons () {
		Color color;
		for (int i=0;i<this.game.getNumberofCards();i++){
			if (!this.game.getVisiblility(i)) {
				if (isMaster) {
					color = Card.colors[this.game.getColor(i)];
					//					buttons.get(i).setBackground(new Color(color.getRed(),color.getGreen(),color.getBlue(),128));
					buttons.get(i).setBorder(BorderFactory.createLineBorder(color,10));
				} else {
					buttons.get(i).setBorder(BorderFactory.createLineBorder(Color.white,10));
				}
			} else {
				buttons.get(i).setBackground(Card.colors[this.game.getColor(i)]);
			}
		}

	} 

	public void popDialog () {
		final JOptionPane optionPane = new JOptionPane(
				"The only way to close this dialog is by\n"
						+ "pressing one of the following buttons.\n"
						+ "Do you understand?",
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.YES_NO_OPTION);

	}

}


