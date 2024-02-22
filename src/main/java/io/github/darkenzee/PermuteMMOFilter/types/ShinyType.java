package io.github.darkenzee.PermuteMMOFilter.types;

public enum ShinyType implements IMatchesExcpected<ShinyType> {
	Any("✼"), Star("★"), Square("■"), Either("★■"), Not("🛇");
	
	private final String symbol;
	
	private ShinyType(String symbol) {
		this.symbol = symbol;
	}
	
	public String symbol() {
		return symbol;
	}

	public boolean matchesExpected(ShinyType expectedType) {
		switch (expectedType) {
		case Any:
			return true;
		case Either:
			return this.equals(Star) || this.equals(Square);
		default:
			return this.equals(expectedType);
		}
	}
}
