package io.github.darkenzee.PermuteMMOFilter.types;

public enum AnyYesNo implements IMatchesExcpected<AnyYesNo> {
	Any, Yes, No;

	@Override
	public boolean matchesExpected(AnyYesNo expected) {
		switch (expected) {
		case Any:
			return true;
		default:
			return this.equals(expected);
		}
	}
}
