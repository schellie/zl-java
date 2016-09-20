import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;

/**
 *  This class will handle the i/o with the browser and parse input
**/
class Parser {
	private HashMap<String, Word> _vocab;
	private Scanner reader;
	
	public Parser() {
		this._vocab = new HashMap<String, Word>();
		for (int count:data.words) {
			this._vocab.put(data.words[count][1], new Word(data.words[count][1], data.words[count][0]));
			ArrayList<String> syn = data.words[count].subList(2, data.words[count].size());
			for (String s:syn) this._vocab.put(s, new Word(data.words[count][1], data.words[count][0]));
		}
	}

	public void println(String str) { 
		System.out.println(str); 
	}
	public void print(String str) { 
		System.out.print(str); 
	}
	public String readln() { 
		//return System.out.readln(); 
		return "";
	}
	public void addListener(String func) { 
		//this.system.addListener(func); 
	}
    /**
     * @return The next command from the user.
     */
	public Command getCommand() {
		String line;
		String word1 = null;
		String word2 = null;
		System.out.print("> ");
		line = reader.nextLine();
		Scanner tokenizer = new Scanner(line);
		if(tokenizer.hasNext()) {
			word1 = tokenizer.next();      // get first word
			if(tokenizer.hasNext()) {
				word2 = tokenizer.next();      // get second word
				// note: we just ignore the rest of the input line.
			}
		}
		// Now check whether this word is known. If so, create a command
		// with it. If not, create a "null" command (for unknown command).
		return new Command(this._vocab, word1, word2);
	}
	
    /**
     * Print a list with valid commandwords
     */
	public String showCommands() { 
		String commands = "";
		Set<String> keys = this._vocab.keySet();
		for(String comm:keys) commands += comm + " ";
		return commands;
	}
}
