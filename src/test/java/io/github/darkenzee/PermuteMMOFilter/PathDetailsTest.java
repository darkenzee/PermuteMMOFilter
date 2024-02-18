package io.github.darkenzee.PermuteMMOFilter;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class PathDetailsTest {

	private static final String COMPACT_PATH_EXAMPLE1 = "* A1|S4|S4                              >>>       Spawn4 =   Pichu (F):  6 * 24/09/16/07/06/26 Hardy   -- NOT ALPHA -- Skittish: Multi scaring!";
	private static final String COMPACT_PATH_EXAMPLE2 = "* A1|S4|S4                              >>>       Spawn4 =   Bronzor:  6 * 24/09/16/07/06/26 Hardy   -- NOT ALPHA -- Skittish: Multi scaring!";
	private static final String COMPACT_PATH_EXAMPLE3 = "* A1|A4|A2|G1|CR|A1|A1                  >>> Bonus Spawn1 =   Bronzong:  3 ■ 31/31/10/31/14/20 Timid   -- NOT ALPHA";
	private static final String COMPACT_PATH_EXAMPLE4 = "* A2|A1|A1|A2|CR|A1|A1|A1               >>> Bonus Spawn1 = α-Carnivine (M): 11 * 24/19/31/31/31/31 Quiet";

	private static final String FULL_DETAILS_EXAMPLE1;
	private static final String FULL_DETAILS_EXAMPLE2;
	private static final String FULL_DETAILS_EXAMPLE3;
	private static final String FULL_DETAILS_EXAMPLE4;
	static {
		try {
			FULL_DETAILS_EXAMPLE1 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE1.txt"), "UTF-8");
			FULL_DETAILS_EXAMPLE2 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE2.txt"), "UTF-8");
			FULL_DETAILS_EXAMPLE3 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE3.txt"), "UTF-8");
			FULL_DETAILS_EXAMPLE4 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE4.txt"), "UTF-8");
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
	
	@Test
	public void testGetSpeciesFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("Pichu", underTest.getSpecies());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetSpeciesFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("Turtwig", underTest.getSpecies());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetPidFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("Unknown", underTest.getPid());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetPidFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("A2B949C3", underTest.getPid());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetEcFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("Unknown", underTest.getEc());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetEcFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("1C6FA316", underTest.getEc());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetIvsFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("24/09/16/07/06/26", underTest.getIvs());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetIvsFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("28/24/9/5/28/3", underTest.getIvs());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetNatureFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(PokemonNature.Hardy, underTest.getNature());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetNatureFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE4);
		assertEquals(PokemonNature.Quiet, underTest.getNature());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetNatureFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(PokemonNature.Modest, underTest.getNature());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetGenderFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(PokemonGender.Female, underTest.getGender());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetGenderFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(PokemonGender.Male, underTest.getGender());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetGenderFromGenderless() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE2);
		assertEquals(PokemonGender.Genderless, underTest.getGender());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetShinyFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(ShinyType.Star, underTest.getShinyType());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetShinyFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(ShinyType.Star, underTest.getShinyType());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetShinySquareFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertEquals(ShinyType.Square, underTest.getShinyType());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetNotShinyFromFull() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertEquals(ShinyType.Not, underTest.getShinyType());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetShinySquareFromFull() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE3);
		assertEquals(ShinyType.Square, underTest.getShinyType());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetSpawnFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(4, underTest.getSpawn());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetSpawnFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(1, underTest.getSpawn());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetShinyRollsFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(6, underTest.getShinyRolls());
		//System.err.println(underTest.toString());
	}

	@Test
	public void testGetShinyRollsFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(20, underTest.getShinyRolls());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetShinyRollsFromNonShinyFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertEquals(-1, underTest.getShinyRolls());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetLevelFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(-1, underTest.getLevel());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetLevelFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(1, underTest.getLevel());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetHeightFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(-1, underTest.getHeight());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetHeightFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(107, underTest.getHeight());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetFullPathLengthFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(3, underTest.getFullPathLength());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetFullPathLengthFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(7, underTest.getFullPathLength());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetChainParentPathFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(0, underTest.getChainParentPathLength());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetChainParentPathFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(0, underTest.getChainParentPathLength());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetFullPathLengthFromFullDetailsChain() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertEquals(7, underTest.getFullPathLength());
		//System.err.println(underTest.toString());
	}
	
	@Test
	public void testGetChainParentPathFromFullDetailsChain() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertEquals(6, underTest.getChainParentPathLength());
		//System.err.println(underTest.toString());
	}
}
