package io.github.darkenzee.PermuteMMOFilter.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class PathDetailsParserTest {
	@Test
	public void testLoadFromFile() throws FileNotFoundException, IOException {
		List<PathDetails> paths = PathDetailsParser.loadFromFile(new File("src/test/resources/io/github/darkenzee/PermuteMMOFilter/PermuteMMO_8745357205966506115.txt"));
		assertEquals(37655, paths.size());
	}
}
