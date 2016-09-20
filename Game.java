import java.util.ArrayList,

/**
 *  The main class
**/
class Game {
/**
 * Create the game and initialise its internal map. Action are handled by the player
 * Player needs to know what items must be handled.
 */
	private Parser _parser;
	private Player _player;
	
	public Game() {
		this._parser = new Parser();
		//this._parser.addListener(this.processCommand); // from here on we listen to any commands
		// declare player
		this._player = null;
		// keep a list of items in a map (for easy retrieval of the object itself)
//		this._items = new Map();

		this.initialise();
		//this._rooms = this.createRooms(); // keep a list of rooms
		//this._items = this.createItems();
		this.printWelcome();
	}
	
	private void initialise() {
		// read in the messages
		//for (count in data.messages) {
		//zuul.messages[data.messages[count][0]] = data.messages[count][1];
		//}    

		// create the rooms, rooms are linked together via exits, so no need to keep a list
		// index is only needed during initialization, and acts as identifier for the exits
		ArrayList<Room> rooms = new ArrayList<Room>();
		rooms[0] = new Room("lingo"); // room 0 is a special one
		for (int count = 0; count < data.rooms.size(); count++) {
			rooms[data.rooms[count][0]] = new Room(data.rooms[count][1]);
		}

		// create the items and move them to the correct initial location
		// keep a temporary map as we need that to correctly create exits & conditions
		HashMap<String, Item> items = new HashMap<String, Item>();
		for (int count = 0; count < data.items.size(); count++) {
			String name = data.items[count].shift(); // 1st
			String msg = data.items[count].shift(); // 2nd, array of messages
			String initroom = data.items[count].shift(); // 3rd initial location
			String fixed = typeof(data.items[count][0])==="number"?data.items[count].shift():0; // 4rd optional, room number for fixed item
			String props = data.items[count]; // 4rd or 5th: properties of item
			Item item = new Item(name);
			for (String s:msg) item.message = msg[s];
			// put it in place
			rooms[initroom].drop(item);
			// fixed: -1 fixed in place, > 0 second room, 0 not present (not fixed) 
			if(fixed) item.setFixed();
			if(fixed > 0) rooms[fixed].drop(item); // goes to a neighbor room as well
			// add properties
			for (String[] s:props) {
				String[] p = props[s].split(":");
				if (p[0] == "has") item.addProperty(p[1], p[2] || -1);
				if (p[0] == "can") item.addAbility(p[1]);
			}
			// add item to map
			items.put(name, item);
		}
		
		// first we need all the rooms and items before we can add the exits & their conditions
		for (int count = 0; count < data.exits.size(); count++) {
			String source = rooms[data.exits[count].shift()]; // going from this one ...
			String dest = rooms[data.exits[count].shift()];   // ... to this one ...
			String direction = data.exits[count].shift();     // ... travel in this way
			Exit exit = source.addExit(direction, dest);
			// check whether there are more conditions for the same exit
			String[] conditions = data.exits[count]; // array of conditions
			for(String[] c:conditions) {
				String[] it = (typeof conditions[c] === "number") ?
					["pct", conditions[c]*100] :
					conditions[c].split(":");
				it[1] = Number.parseInt(it[1]);
				if(it[0] == "pct") exit.addCondition(null, 0, it[1]);
				else exit.addCondition(items.get(it[0]), it[1], 100);
			}
		}

//	// setup the vocabulary
//	// "word"->id->action[type:do,go,is][action to call][optional index to items]
//	// actions
//	for (count in data.words) { 
//		// this will define the action to take on a given number (id)
//		zuul.actions[+count + 1] = [data.words[count][0], data.words[count][1]];
//		// at least one word will get a reference to the action above
//		zuul.vocabulary[data.words[count][1]] = +count + 1; // do not start at 0
//		// now, add synonyms to the word above (same ref to action)
//		var syn = data.words[count].slice(2);
//		for (s in syn) zuul.vocabulary[syn[s]] = +count + 1;	
//	}
//
//		// create ref. in actions, and add if it shouldn"t exist
//		var a = zuul.vocabulary[item.getName()]; // find the item name in vocabulary
//		if (a === undefined) { // item name is not there
//			console.log("item added to vocabulary: " + item.getName());
//			zuul.actions.push(["is", item.getName()]);
//			zuul.vocabulary[item.getName()] = zuul.actions.length - 1;
//			a = zuul.actions.length - 1;
//		}
//		zuul.actions[a].push(item);
//	}
		// initialize the player, now that we have created its starting room
		//this.player = new Player(this._rooms[1]);  // start game outside
		this._player = new Player(rooms[data.player.start]);  // start game outside

	}

    /**
     * Print out the opening message for the player.
     */
	public void printWelcome() {
		this._parser.println();
		this._parser.println("Welcome to the World of Zuul!");
		this._parser.println("World of Zuul is a new, incredibly boring adventure game.");
		this._parser.println("Type \"help\" if you need help.");
		this._parser.println();
		this._parser.println(this._player.look());
    }
    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @return true If the command ends the game, false otherwise.
     */
	public void processCommand() {
		let command = this._parser.getCommand();
		if(command.isUnknown()) {
			this._parser.println("I don\"t know what you mean...");
			return;
		}
		switch (command.getCommandWord()) {
			case "help": this.printHelp(); break;
			case "go": this._parser.println(this._player.go(command)); break;
			case "look": this._parser.println(this._player.look()); break;
			case "inventory": this._parser.println(this._player.inventory()); break;
			case "take": this._parser.println(this._player.take(command)); break;
			case "drop": this._parser.println(this._player.drop(command)); break;
			case "unlock": this._parser.println(this._player.unlock(command)); break;
			case "lock": this._parser.println(this._player.lock(command)); break;
			case "quit": this.quit(command); break;
			default: this._parser.println("not implemented");
		}
	}
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
	public void printHelp() {
		this._parser.println("You are lost. You are alone. You wander");
		this._parser.println("around at the university.");
		this._parser.println();
		this._parser.println("Your command words are:");
		this._parser.println(this.parser.showCommands());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
	public void quit(Command command) {
		if(command.hasSecondWord()) this._parser.println("Quit what?");
		else this._parser.println("Thank you for playing.  Good bye.");  // day goodbye
	}
	
}

public static void main(String[] args) {
	Game game = new Game();
}

