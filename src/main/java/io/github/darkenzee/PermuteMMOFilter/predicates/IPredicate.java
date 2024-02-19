package io.github.darkenzee.PermuteMMOFilter.predicates;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;

public interface IPredicate {
	
	public boolean matches(PathDetails details);
}
