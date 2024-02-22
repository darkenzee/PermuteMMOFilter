package io.github.darkenzee.PermuteMMOFilter.types;

public interface IMatchesExcpected<T extends IMatchesExcpected<T>> {
	boolean matchesExpected(T expected);
}
