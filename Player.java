import java.util.HashSet;

class Player {
	private Room _currentRoom;
	private HashSet<Item> _inventory;
	private HashSet<String> _ability;

	public Player(Room startingRoom) {
		this._currentRoom = startingRoom;  
		this._inventory = new HashSet<Item>();
		this._ability = new HashSet<String>();
	}

	public Room getCurrentRoom() { 
		return this._currentRoom; 
	}
	public void setCurrentRoom(Room room) { 
		this._currentRoom = room; 
	}
	public Boolean isAble(String action) { 
		return this._ability.contains(action); 
	}
	public Item isHere(String item) { 
		HashSet<Item> here = new HashSet<Item>();
		here.addAll(this._inventory);
		here.addAll(this._currentRoom.getItems()); // join all items at this location into one set
		for(Item it:here) if(item == it.name) return it;
		return null;
	}
	public String look() { 
		return this._currentRoom.look(); 
	}
	public String inventory() { 
		if(this._inventory.size() == 0) return "You are holding nothing";
		String str = "You are holding:\n";
		for(Item item:this._inventory) str += item.show() + "\n";
		return str;
	}
	public String go(Command command) {
		if(!command.hasObject()) return "Go where?"; // if there is no second word, we don"t know where to go...
		String direction = command.getObjectWord();
		// Try to leave current room.
		Room nextRoom = this._currentRoom.getExit(direction);
		if (nextRoom == null) return "There is no way to go there!"; // get fails
		this._currentRoom = nextRoom;
		return this.look();
	}
	public String take(Command command) {
		if(!command.hasSecondWord()) return "Take what?"; // if there is no second word, we don"t know what to take...
		Item item = this.isHere(command.getObjectWord());
		if(item == null) return "I see no " + command.getSecondWord() + " here."; // unknown item
		if(item.isFixed()) return "It is secured in its place. You cannot take that.";
		if(this._currentRoom.has(item)) {
			this._currentRoom.take(item);
			this._inventory.add(item);
			// inherit abilities
			for(String a:item.getAbilities()) this._ability.add(a);
			return "OK";
		}
		return "You already have that!";
	}
	public String drop(Command command) {
		if(!command.hasSecondWord()) return "Drop what?"; // if there is no second word, we don"t know what to drop...
		let item = this.isHere(command.getObjectWord());
		if(item == null) return "I see no " + command.getSecondWord() + " here."; // unknown item
		if(this._inventory.has(item)) {
			this._inventory.delete(item);
			this._currentRoom.drop(item);
			// remove abilities
			for(String a:item.getAbilities()) this._ability.remove(a);
			return "OK";
		}
		return "You don\'t have it!";
	}
	public String unlock(Command command) {
		if(!command.hasSecondWord()) return command.getFirstWord() + " what?"; // if there is no second word, we don"t know what to take...
		Item item = this.isHere(command.getObjectWord());
		if(item == null) return "I see no " + command.getSecondWord() + " here."; // unknown item
		if(!this.isAble(command.getCommandWord())) return "You have nothing to " + command.getFirstWord() + " with."; // has the ability once it"s in possession
		if(!item.hasProperty(command.getCommandWord())) return "You cannot " + command.getFirstWord() + " " + item.show();
		// change status of item
		if(item.changeState(command.getCommandWord())) return "Done."; else return "Cannot do that again.";
	}
	public String lock(Command command) {
		if(!command.hasSecondWord()) return command.getFirstWord() + " what?"; // if there is no second word, we don"t know what to take...
		Item item = this.isHere(command.getObjectWord());
		if(item == null) return "I see no " + command.getSecondWord() + " here."; // unknown item
		if(!this._isAble(command.getCommandWord())) return "You have nothing to " + command.getFirstWord() + " with."; // has the ability once it"s in possession
		if(!item.hasProperty(command.getCommandWord())) return "You cannot " + command.getFirstWord() + " " + command.item.show();
		// change status of item
		if(item.changeState(command.getCommandWord())) return "Done."; else return "Cannot do that again.";
	}

}