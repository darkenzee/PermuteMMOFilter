package io.github.darkenzee.PermuteMMOFilter.types;

public enum ShinyType {
	Any("✼"), Star("★"), Square("■"), Not("🛇");
	
	private final String symbol;
	
	private ShinyType(String symbol) {
		this.symbol = symbol;
	}
	
	public String symbol() {
		return symbol;
	}
}
