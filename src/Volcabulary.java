import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Volcabulary {
	private List<String> words;

	// constructor
	public Volcabulary (List<String> words) {
		this.words = words;
	}
	//
	public Volcabulary (File file) {
		Scanner scanner;
		this.words = new ArrayList<String>();
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return;
		}
		scanner.useDelimiter(",");
		while(scanner.hasNext()){
			this.words.add(scanner.next());
		}
		scanner.close();
	}


	// add words
	public void addWords (List<String> words) {
		this.words.addAll(words);
	}

	// add word
	public void addWord (String word) {
		this.words.add(word);
	}

	// get size
	public int getSize () {
		return this.words.size();
	}

	// draw words
	public List<String> drawWords (int number_of_words ) {
		if (number_of_words > this.getSize()) {
			return null;
		} else {
			Collections.shuffle(this.words);
			return this.words.subList(0, number_of_words);
		}
	}

}
