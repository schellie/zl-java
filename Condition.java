import java.lang.Math;

/**
 * Conditions to check: 
 *		- unconditional
 *		- % propability
 *		- unconditional but forbidden to npc's
 *		- carry item
 *		- carry item or in same room as item
 *		- item must *not* have status x
**/
public class Condition {
	private Item _item;
	private Integer _state;
	private Integer _pct;
	
	public Condition(Item item, Integer state, Integer pct) {
		this._item = item; // Item
		this._state = state; // forbidden status, -1 must carry, -2 must be here
		this._pct = pct; // 100 is unconditional, < 100 percentage, -100 forbidden to npc
	}
	public Boolean check() {
		if(this._item == null) return (Math.random() * 100 <= this._pct);
		if(this._item.getState() == _state) return false; else return true;
	}
}
