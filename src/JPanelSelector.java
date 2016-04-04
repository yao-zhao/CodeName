import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelSelector<T> extends JPanel implements ActionListener {
	
	public final static int INPUT=0;
	public final static int OUTPUT=1;
	
	private JPanel labelPanel;
	private JPanel selectorPanel;
//	private JLabel inputLabel;
	private JLabel labelLabelPanel;
	private JComboBox<T> inputbox;
//	private String inputString="";
//	private String outputString="";
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public JPanelSelector (String label, T[] array) {
		this();
		this.setLabel(label);
		this.setSelector(array);
		if (array.length>0) {
			this.inputbox.setSelectedIndex(0);
		}
	}
	
	public JPanelSelector() {
		super();
		
		this.labelPanel = new JPanel();
		this.selectorPanel = new JPanel();
		this.add(labelPanel, BorderLayout.WEST);
		this.add(selectorPanel, BorderLayout.EAST);
		
		this.inputbox = new JComboBox<T>();		
		this.inputbox.addActionListener(this);
		this.selectorPanel.add(this.inputbox);
		
		this.labelLabelPanel = new JLabel("");
		this.labelPanel.add(labelLabelPanel);
	}
	
	public void setSelector (T[] array) {
		inputbox.removeAllItems();
		for (int i=0;i<array.length;i++) {
			inputbox.addItem(array[i]);
		}
	}
	
	public void setSelectorIndex(int index){
		inputbox.setSelectedIndex(index);
	}
	
	
	public T getSelectedValue() {
		return inputbox.getItemAt(inputbox.getSelectedIndex());
	}
	
	public int getSelectedIndex() {
		return inputbox.getSelectedIndex();
	}
	
	public void setLabel(String str){
		this.labelLabelPanel.setText(str);
	}
	
//	public void setLabel(int i, String str){
//		switch (i) {
////		case JPanelTextEditor.INPUT:
////			this.inputLabel.setText(str);
////			break;
//		case JPanelSelector.OUTPUT:
//			this.labelLabelPanel.setText(str);
//			break;
//		default:
//		}
//	}
	
	@Override 
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
	}

//	@Override
//	public void setFont(Font font) {
//		this.setFont(font);
//	//	this.labelLabelPanel.setFont(font);
//	//	this.inputbox.setFont(font);
//	}
	
	public void setTextFont(Font font) {
		this.labelLabelPanel.setFont(font);
		this.inputbox.setFont(font);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
