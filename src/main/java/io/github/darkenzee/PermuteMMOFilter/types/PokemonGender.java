package io.github.darkenzee.PermuteMMOFilter.types;

public enum PokemonGender implements IMatchesExcpected<PokemonGender> {
	Any("⚥"), Male("♂"), Female("♀"), Genderless("○");

	private final String symbol;

	private PokemonGender(String symbol) {
		this.symbol = symbol;
	}

	public String symbol() {
		return symbol;
	}

	@Override
	public boolean matchesExpected(PokemonGender expectedGender) {
		switch (expectedGender) {
		case Any:
			return true;
		default:
			return this.equals(expectedGender);
		}
	}
}
