package io.github.darkenzee.PermuteMMOFilter.predicates;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;

public class PredicateFilter {
	private final List<IPredicate> predicates;
	
	public PredicateFilter(IPredicate... predicates) {
		this(Arrays.asList(predicates));
	}
	
	public PredicateFilter(List<IPredicate> predicates) {
		this.predicates = predicates;
	}
	
	public List<PathDetails> applyFilters(List<PathDetails> paths) {
		return paths.stream()
		.filter(this::applyFilter)
		.collect(Collectors.toList());
	}
	
	public boolean applyFilter(PathDetails details) {
		boolean passesPredicates = predicates.size() == predicates.stream().filter(p->p.matches(details)).count();
		if (passesPredicates) {
			return true;
		}
		if (details.getChainChildren().size() > 0) {
			return this.applyFilters(details.getChainChildren()).size() > 0;
		}
		return false;
	}
}
