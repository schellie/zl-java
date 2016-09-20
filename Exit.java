import java.util.HashSet;

public class Exit {
	private Room _target;
	private HashSet<Condition> _condition;

	public Exit(Room target) {
		this._target = target; // Room
		this._condition = new HashSet<Condition>(); // Set<Condition>
	}
	
	public Room getExit() {
		for (Condition c:this._condition) {
			if(!c.check()) return null;
		}
		return this._target; // for now
	}
	public void addCondition(Item item, Integer state, Integer pct) { 
		this._condition.add(new Condition(item, state, pct)); 
	}
}
