import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

	private int numRows = 5;
	private int numColumns = 5;
	private int numCards = numRows * numColumns;
	private int numPlayers = 2;
	private int numBombs = 1;
	private int[] start_sequence;
	private int[] player_goals;
	private List<Card> cards = new ArrayList<Card>();


	/**
	 * @param numDecks how many decks you want to play
	 */
	public Deck(Volcabulary volcabulary) {
		// draw vocabulary from database
		this.initialize (volcabulary);
	}

	public Deck(int numplayers, int rows, int columns, int bombs, Volcabulary volcabulary) {
		this.numPlayers = numplayers;
		this.numRows = rows;
		this.numColumns = columns;
		this.numCards = this.numRows * this.numColumns;
		this.numBombs = bombs;
		this.initialize (volcabulary);
	}


	private void initialize (Volcabulary volcabulary) {
		// draw vocabulary from database
		List<String> words = volcabulary.drawWords(this.numCards);		
		// draw start sequence
		this.start_sequence = new int [this.numPlayers];
		for (int i=0;i<this.numPlayers;i++) {
			this.start_sequence[i] = i;
		}


		// Implementing Fisher–Yates shuffle
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = ThreadLocalRandom.current();
		for (int i = this.start_sequence.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = this.start_sequence[index];
			this.start_sequence[index] = this.start_sequence[i];
			this.start_sequence[i] = a;
		}


		// calculate player goals
		int deduct = (this.numPlayers-1)*this.numPlayers/2 + this.numBombs -1 ;
		int base = (this.numCards - deduct)/(this.numPlayers+1);
		this.player_goals = new int [this.numPlayers];
		for (int i=0;i<this.numPlayers;i++) {
			this.player_goals[this.start_sequence[i]] = base - i + this.getNumberofPlayers()-1;
		}

		// assign labels
		int[] labels = new int[this.numCards];
		for (int i=0;i<this.numCards;i++) {

			if (i<base*this.numPlayers+deduct) {
				int sum = 0;
				for (int j=0; j<this.numPlayers;j++){
					sum += this.player_goals[j];
					if (i<sum) {
						labels[i]= j;
						break;
					} 
				}
			} else if (i<this.numCards - this.numBombs ) {
				labels[i]= Card.White;
			} else {
				labels[i]= Card.Black;
			}
			this.cards.add(new Card(words.get(i),labels[i]));
		}

		// shuffle cards
		Collections.shuffle(cards);
		
	}


	//get start player
	public int[] getStartSequence() {
		return this.start_sequence;
	}
	
	//get start player
	public int getStartPlayer() {
		return this.start_sequence[0];
	}
	
	public int getNumberofPlayers() {
		return this.numPlayers;
	}

	// get player goal
	public int getPlayerGoal (int player) {
		return this.player_goals[player];
	}

	// get score
	public int getScore(int player){
		int score = 0;
		for (Card card:this.cards) {
			score += card.getScore(player);
		}
		return score;
	}

	// choose card
	public boolean flipCard(int index ){
		return this.cards.get(index).flipCard();
	}
	
	public int getColor(int index) {
		return this.cards.get(index).getColor(); 
	}

	public int getNumberofCards () {
		return this.numCards;
	}
	
	public int getNumberofRows () {
		return this.numRows;
	}
	public int getNumberofColumns () {
		return this.numColumns;
	}
	
	public String getWord (int ind) {
		return this.cards.get(ind).getWord();
	}

	//    // print deck
	//    public void printDeck () {
	//    	for (int i=0;i<Deck.numRows;i++) {
	//    		
	//    	}
	//    	
	//    }

}