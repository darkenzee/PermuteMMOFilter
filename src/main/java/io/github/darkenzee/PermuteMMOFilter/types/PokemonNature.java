package io.github.darkenzee.PermuteMMOFilter.types;

public enum PokemonNature implements IMatchesExcpected<PokemonNature> {
	Any,
	Adamant,
	Bashful,
	Bold,
	Brave,
	Calm,
	Careful,
	Docile,
	Gentle,
	Hardy,
	Hasty,
	Impish,
	Jolly,
	Lax,
	Lonely,
	Mild,
	Modest,
	Naive,
	Naughty,
	Quiet,
	Quirky,
	Rash,
	Relaxed,
	Sassy,
	Serious,
	Timid;

	@Override
	public boolean matchesExpected(PokemonNature expectedNature) {
		switch (expectedNature) {
		case Any:
			return true;
		default:
			return this.equals(expectedNature);
		}
	}
}
