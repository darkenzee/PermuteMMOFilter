package io.github.darkenzee.PermuteMMOFilter.types;

public enum PokemonGender {
	Any("⚥"), Male("♂"), Female("♀"), Genderless("○");
	
	private final String symbol;
	
	private PokemonGender(String symbol) {
		this.symbol = symbol;
	}
	
	public String symbol() {
		return symbol;
	}
}
