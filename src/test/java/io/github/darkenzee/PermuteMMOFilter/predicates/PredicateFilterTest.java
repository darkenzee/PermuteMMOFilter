package io.github.darkenzee.PermuteMMOFilter.predicates;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;

public class PredicateFilterTest {

	@Test
	public void testSomeFilters() throws FileNotFoundException, IOException {
		List<PathDetails> paths = PathDetails.loadFromFile(new File("src/test/resources/io/github/darkenzee/PermuteMMOFilter/PermuteMMO_8745357205966506115.txt"));
		assertEquals(37655, paths.size());
		
		List<IPredicate> predicates = new ArrayList<>();
		PredicateFilter underTest = new PredicateFilter(predicates);
		
		assertEquals(37655, underTest.applyFilters(paths).size());
		
		predicates.add(d -> d.getGender() == PokemonGender.Female);
		assertEquals(37655, underTest.applyFilters(paths).size());
	}
}
