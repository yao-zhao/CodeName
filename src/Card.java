import java.awt.Color;

public class Card {
	private final String word;
	private final int color;
	private boolean visibility;

	//
	public final static int Red  = 0;
	public final static int Blue = 1;
	public final static int Yellow = 2;
	public final static int Green = 3;
	public final static int White = 4;
	public final static int Black = 5;
	public final static boolean hidden = false;
	public final static boolean visible = true;
	public final static Color[] colors = {Color.red,Color.blue,Color.yellow,Color.green,Color.gray,Color.black};
	public final static String[] names = {"red","blue","yellow","green","white","black"};


	// constructor
	public Card (String str, int color) {
		this.word = str;
		this.color = color;
		this.visibility = Card.hidden;
	}

	// public choose card
	public int getColor () {
		return this.color;
	}

	public boolean flipCard () {
		if (this.visibility == Card.hidden) {
			this.visibility = Card.visible;
			return true;
		} else {
			return false;
		}

	}

	// get score
	public int getScore (int player) {
		if (player == this.color && this.visibility == Card.visible){
			return 1;
		} else {
			return 0;
		}
	}

	// get word
	public String getWord () {
		return this.word;
	}

	
	public boolean getVisibility () {
		return this.visibility;
	}
}
