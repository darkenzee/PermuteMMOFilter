package io.github.darkenzee.PermuteMMOFilter;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class PathDetailsTest {

	private static final String COMPACT_PATH_EXAMPLE1 = "* A1|S4|S4                              >>>       Spawn4 =   Pichu (M):  6 * 24/09/16/07/06/26 Hardy   -- NOT ALPHA -- Skittish: Multi scaring!";

	private static final String FULL_DETAILS_EXAMPLE1;
	static {
		try {
			FULL_DETAILS_EXAMPLE1 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE1.txt"), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetPathFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("A1|S4|S4", underTest.getPath());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetPathFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("B1|B1|S2|S2|S3|B1|B1", underTest.getPath());
		//System.err.println(underTest.toString());
	}
	
}
