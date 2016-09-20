class Word {
	private String _word;
	private Integer _type;
	
	public Word(String word, Integer type) {
		this._word = word;
		this._type = type;
	}
	public String getWord() { 
		return this._word; 
	}
	public Integer getType() { 
		return this._type; 
	}
}