import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Volcabulary {
	private List<String> words;
	private String title;

	// constructor
	public Volcabulary (String title,List<String> words) {
		this.words = words;
		this.title = title;
	}
	
	public Volcabulary () {
		this(0);
	}
	
	public void printDescp () {
		System.out.println("number of volcabulary sets: "+ Integer.toString(Volcabulary.getNumSets()));
		System.out.println("title of this set is: "+ this.title);
		System.out.println("number of words in this set is: "+ this.words.size());
//		System.out.println("");
		for (String word: words) {
			System.out.print(word+", ");
		}
//		System.out.print("\n");
//		System.out.print(words.get(0));
	}
	
	public static String[] getTitles () {
		File file = new File("volcabularies/database.txt");
		int numlines =0;
		String[] titles = new String[Volcabulary.getNumSets()];
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return new String[0];
		}
		scanner.useDelimiter("\n");
		while(scanner.hasNext()){
			if (numlines%2==0) {
				titles[numlines/2]=scanner.next();
			} else {
				scanner.next();
			}
			numlines++;
		}
		scanner.close();
		return titles;
	}
	
	public static int getNumSets() {
		File file = new File("volcabularies/database.txt");
		int numlines =0;
		
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		scanner.useDelimiter("\n");
		while(scanner.hasNext()){
			scanner.next();
			numlines++;
		}
		scanner.close();
		return numlines/2;
	}
	
	
	public Volcabulary (int index) {
		File file = new File("volcabularies/database.txt");
		int numsets = Volcabulary.getNumSets();
		Scanner scanner;
		Scanner scanner2;
		if (index>=numsets || index<0) {
			index=0;
		}
		

		this.words = new ArrayList<String>();
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return;
		}
		
		scanner.useDelimiter("\n");
		int numlines = 0;
		while(scanner.hasNext()){
			if (numlines == index*2) {
				this.title = scanner.next();
				String line = scanner.next();
				scanner2 = new Scanner(line);
				scanner2.useDelimiter(",");
				while(scanner2.hasNext()){
					String str = scanner2.next();
					this.words.add(str);
				}
				break;
			}
			scanner.next();
			numlines++;
		}
		scanner.close();
		this.printDescp();
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
