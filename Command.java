import java.util.HashMap;

class Command {
/**
 * Create a command object. First and second word must be supplied, but either one (or both) can be null.
 * @param firstWord The first word of the command. Null if the command was not recognised.
 * @param secondWord The second word of the command.
 */
	private String _first;
	private String _second;
	private String _command;
	private String _object;

	public Command(HashMap<String, Word> vocabulary, String firstWord, String secondWord) {
		this._first = firstWord;
		this._second = secondWord;

		Word wd1 = vocabulary.get(firstWord);
		Word wd2 = vocabulary.get(secondWord);

		// check word1, if it's type is 0 it's a direction, 1 an action, 2 an item
		if(wd1 == null) this._command = null;
		else switch (wd1.getType()) {
			case 0: this._command = "go"; this._object = wd1.getWord(); break;
			case 1: this._command = wd1.getWord(); break;
			case 2: this._command = null; this._object = wd1.getWord(); break;
			default: this._command = null;
		}
		if(wd2 != null) switch (wd2.getType()) {
			case 0: this._object = wd2.getWord(); break;
			case 1: this._command = wd2.getWord(); break;
			case 2: this._object = wd2.getWord(); break;
			default: this._command = null;
		}
	}

	public String getFirstWord() { 
		return this._first; 
	}
	public String getCommandWord() { 
		return this._command; 
	}
	public String getSecondWord() { 
		return this._second; 
	}
	public String getObjectWord() { 
		return this._object; 
	}
	public Boolean isUnknown() { 
		return (this._command == null); 
	}
	public Boolean hasSecondWord() { 
		return (this.secondWord != null); 
	}
	public Boolean hasObject() { 
		return (this.objectWord != null); 
	}
}
