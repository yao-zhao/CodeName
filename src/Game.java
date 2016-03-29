
public class Game extends Deck{
	//	private Deck deck;
	private int current_player;
	private boolean[] busted;

	public static final int NOCHANGE = -1;
	public static final int CONTINUE = 0;
	public static final int CHANGEPLAYER = 1;
	public static final int LOSE = 3;
	public static final int WIN = 4;

	// constructor
	public Game (Volcabulary vol){
		//	deck = new Deck (vol);
		super(vol);
		intiallize() ;
	}
	
	public Game(int numplayers, int rows, int columns, int bombs, Volcabulary volcabulary) {
		super( numplayers,  rows,  columns,  bombs,  volcabulary) ;
		intiallize() ;
	}
	
	private void intiallize() {
		this.current_player = this.getStartPlayer();
		this.busted = new boolean [this.getNumberofPlayers()];
	}


	// next player
	private void nextPlayer() {
		int[] startsequence = this.getStartSequence();
		for (int i=0;i<startsequence.length;i++) {
			if (startsequence[i] == this.current_player) {
				this.current_player = startsequence[(i+1)%startsequence.length];
				break;
			}
		}
		// go to next player if current is busted
		if (this.busted[this.current_player]) {
			this.nextPlayer();
		}
	}

	// pick a card and return if game is ended
	public int pickedCard (int index) {
		//
		boolean wasFinished = this.isFinsihed();
		if (this.flipCard(index) && !wasFinished) {

			// decide if player is busted
			int color = this.getColor(index);
			if ( color == Card.Black) {
				this.busted[this.current_player]=true;
				return Game.LOSE;
			}
			// decide if winner already exist
			if (this.isFinsihed()){
				if (this.getWinner() == this.current_player) {
					return Game.WIN;
				} else {
					return Game.LOSE;
				}
			}	
			// if this player is correct
			if ( color == this.current_player) {
				return Game.CONTINUE;
			} else {
				this.nextPlayer();
				return Game.CHANGEPLAYER;
			}
		} else {
			return Game.NOCHANGE;
		}
	}

	// get winner
	public int getWinner() {
		int sum = 0;
		int survivor = 0;
		// if winner hasnt been decided
		for (int i=0;i<this.getNumberofPlayers();i++) {
			if (this.busted[i] == false) {
				sum ++;
				survivor = i;
				if ( this.getScore(i) == this.getPlayerGoal(i)) {
					return i;
				}
			}
		}
		
		// if only one survivor
		if (sum == 1) {
			return survivor;
		}
		return Card.White;
	}


	// decide if game is ended
	public boolean isFinsihed () {
		return this.getWinner()!=Card.White;
	}

	// get current player
	public int getCurrentPlayer () {
		return this.current_player;
	}
	
	public boolean isBusted (int index) {
		return this.busted[ index];
	}
	//	
	//	public int getNumberofCards () {
	//		return this.deck.getNumberofCards();
	//	}
	//	
	//	public int getNumberofPlayers () {
	//		return this.deck.getNumberofPlayers();
	//	}
	//
	//	public int getNumberofRows () {
	//		return this.deck.getNumberofRows();
	//	}
	//	
	//	public int getNumberofColumns () {
	//		return this.deck.getNumberofColumns();
	//	}

}
