import java.util.HashSet;
import java.util.HashMap;

public class Room {
/**
 * Create a room described "description". Initially, it has no exits.
 * @param description The room"s description.
 */
	private String _descr;
	private HashMap<String, Exit> _exits;
	private HashSet<Item> _items;
	
	public Room(String description) {
		this._descr = description; // String
		this._exits = new HashMap<String, Exit>(); // direction -> next room
		this._items = new HashSet<Item>();
	}
	
	public String look() { 
		return "You are " + this._descr + ".\n" + getExitString() + ".\n" + getItemString(); 
	}
	public Exit addExit(String direction, Room neighbor) { 
		Exit exit = new Exit(neighbor);
		this._exits.put(direction, exit); 
		return exit;
	}
	public Exit getExit(String direction) { 
		return this._exits.containsKey(direction) ? this._exits.get(direction).getExit() : null; 
	}
	public HashSet<Item> getItems() { 
		return this._items; 
	}
	public String getExitString() {
		String returnString = "Exits:";
		Set<String> keys = this._exits.keySet();
		for(String exit:keys) returnString += " " + exit;
		return returnString;
	}
	public String getItemString() {
		if (this._items.size() == 0) return "There is nothing to be found here";
		int count = 0;
		String returnString = "You find: ";
		for (Item item:this._items) {
			if (count++ > 0) returnString += ", ";
			returnString += item.show();
		}
		return returnString;
	}
	public void drop(Item object) { 
		this._items.add(object); 
	}
	public Item take(Item object) { 
		return this._items.remove(object); 
	}
	public Boolean has(Item object) { 
		return this._items.contains(object); 
	}
}
