import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Item {
	private String _name;
	private Integer _status;
	private ArrayList<String> _message;
	private Boolean _fixed;
	private HashSet<String> _ability;
	private HashMap<String, Integer> _props;
	
	public Item(String name) {
		this._name = name;
		this._status = 0;
		this._message = new ArrayList<String>();
		this._fixed = false;
		// other properties for item
		this._ability = new HashSet<String>();
		this._props = new HashMap<String, Integer>();
	}

	public String getName() {
		return this._name; 
	}
	public Integer getState() { 
		return this._status; 
	}
	public void setState(Integer s) { 
		if (s >= 0 && s < this._message.size()) this._status = s; 
	}
	public void setMessage(String m) { 
		this._message.add(m); // must be added sequentuially
	} 
	public String show() { 
		return this._message.get(this._status); 
	}
	public String look() { 
		return "There is " + show() + " here."; 
	}
	public void setFixed() { 
		this._fixed = true; 
	}
	public Boolean isFixed() { 
		return this._fixed; 
	}
	public void addProperty(String prop, Integer state) { 
		this._props.put(prop, state); 
	}
	public Boolean hasProperty(String prop) { 
		return this._props.containsKey(prop); 
	}
	public void addAbility(String abil) { 
		this._ability.add(abil); 
	}
	public Boolean hasAbility(String abil) { 
		return this._ability.contains(abil); 
	}
	public HashSet<String> getAbilities() { 
		return this._ability; 
	}
	public Boolean changeState(String prop) { 
		Boolean change = this._status != this._props.get(prop);
		this._status = this._props.get(prop); 
		return change;
	}
}
