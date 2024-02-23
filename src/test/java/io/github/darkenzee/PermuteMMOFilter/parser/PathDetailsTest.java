package io.github.darkenzee.PermuteMMOFilter.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonNature;
import io.github.darkenzee.PermuteMMOFilter.types.ShinyType;

public class PathDetailsTest {

	private static final String COMPACT_PATH_EXAMPLE1 = "* A1|S4|S4                              >>>       Spawn4 =   Pichu (F):  6 * 24/09/16/07/06/26 Hardy   -- NOT ALPHA -- Skittish: Multi scaring!";
	private static final String COMPACT_PATH_EXAMPLE2 = "* A1|S4|S4                              >>>       Spawn4 =   Bronzor:  6 * 24/09/16/07/06/26 Hardy   -- NOT ALPHA -- Skittish: Multi scaring with aggressive!";
	private static final String COMPACT_PATH_EXAMPLE3 = "* A1|A4|A2|G1|CR|A1|A1                  >>> Bonus Spawn1 =   Bronzong:  3 ■ 31/31/10/31/14/20 Timid   -- NOT ALPHA";
	private static final String COMPACT_PATH_EXAMPLE4 = "* A2|A1|A1|A2|CR|A1|A1|A1               >>> Bonus Spawn1 = α-Carnivine (M): 11 * 24/19/31/31/31/31 Quiet";
	private static final String COMPACT_PATH_EXAMPLE5 = "* -> -> -> -> -> -> -> -> -> A1|A2      >>> Bonus Spawn2 =   Bronzong: 10 * 31/07/31/17/25/31 Rash    -- NOT ALPHA ~~ Chain result!";
	private static final String COMPACT_PATH_EXAMPLE6 = "* A1|A1|A1|A1|A1|G1|G2|CR|A1|A1         >>> Bonus Spawn1 = α-Rotom: 19 * 31/12/31/28/31/31 Hardy   -- Single advances!";
	private static final String COMPACT_PATH_EXAMPLE7 = "* A3|A1|A2|G2|G1|CR|A3                  >>> Bonus Spawn1 = α-Carnivine (M):  6 ■ 24/31/31/27/31/31 Relaxed ~~ Spawns multiple results!";
	private static final String COMPACT_PATH_EXAMPLE8 = "* A1|S4|S4                              >>>       Spawn4 =   Porygon2:  6 * 24/09/16/07/06/26 Hardy";
	private static final String COMPACT_PATH_EXAMPLE9 = "* A1|S4|S4                              >>>       Spawn4 =   Porygon-Z:  6 * 24/09/16/07/06/26 Hardy";

	private static final String FULL_DETAILS_EXAMPLE1;
	private static final String FULL_DETAILS_EXAMPLE2;
	private static final String FULL_DETAILS_EXAMPLE3;
	private static final String FULL_DETAILS_EXAMPLE4;
	private static final String FULL_DETAILS_EXAMPLE5;
	private static final String FULL_DETAILS_EXAMPLE6;
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
			FULL_DETAILS_EXAMPLE5 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE5.txt"), "UTF-8");
			FULL_DETAILS_EXAMPLE6 = IOUtils
					.toString(PathDetailsTest.class.getResourceAsStream("FULL_DETAILS_EXAMPLE6.txt"), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetPathFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("A1|S4|S4", underTest.getPath());
	}

	@Test
	public void testGetPathFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("B1|B1|S2|S2|S3|B1|B1", underTest.getPath());
	}

	@Test
	public void testGetSpeciesFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("Pichu", underTest.getSpecies());
	}

	@Test
	public void testGetSpeciesFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE2);
		assertEquals("Bronzor", underTest.getSpecies());
	}

	@Test
	public void testGetSpeciesFromCompact3() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE8);
		assertEquals("Porygon2", underTest.getSpecies());
	}

	@Test
	public void testGetSpeciesFromCompact4() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE9);
		assertEquals("Porygon-Z", underTest.getSpecies());
	}

	@Test
	public void testGetSpeciesFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("Turtwig", underTest.getSpecies());
	}

	@Test
	public void testGetPidFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("Unknown", underTest.getPid());
	}

	@Test
	public void testGetPidFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("A2B949C3", underTest.getPid());
	}

	@Test
	public void testGetEcFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("Unknown", underTest.getEc());
	}

	@Test
	public void testGetEcFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("1C6FA316", underTest.getEc());
	}

	@Test
	public void testGetIvsFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals("24/09/16/07/06/26", underTest.getIvs());
	}

	@Test
	public void testGetIvsFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals("28/24/9/5/28/3", underTest.getIvs());
	}

	@Test
	public void testGetNatureFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(PokemonNature.Hardy, underTest.getNature());
	}

	@Test
	public void testGetNatureFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE4);
		assertEquals(PokemonNature.Quiet, underTest.getNature());
	}

	@Test
	public void testGetNatureFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(PokemonNature.Modest, underTest.getNature());
	}

	@Test
	public void testGetGenderFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(PokemonGender.Female, underTest.getGender());
	}

	@Test
	public void testGetGenderFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(PokemonGender.Male, underTest.getGender());
	}

	@Test
	public void testGetGenderFromGenderless() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE2);
		assertEquals(PokemonGender.Genderless, underTest.getGender());
	}

	@Test
	public void testGetShinyFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(ShinyType.Star, underTest.getShinyType());
	}

	@Test
	public void testGetShinyFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(ShinyType.Star, underTest.getShinyType());
	}

	@Test
	public void testGetShinySquareFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertEquals(ShinyType.Square, underTest.getShinyType());
	}

	@Test
	public void testGetNotShinyFromFull() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertEquals(ShinyType.Not, underTest.getShinyType());
	}

	@Test
	public void testGetShinySquareFromFull() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE3);
		assertEquals(ShinyType.Square, underTest.getShinyType());
	}

	@Test
	public void testGetSpawnFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(4, underTest.getSpawn());
	}

	@Test
	public void testGetSpawnFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(1, underTest.getSpawn());
	}

	@Test
	public void testGetShinyRollsFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(6, underTest.getShinyRolls());
	}

	@Test
	public void testGetShinyRollsFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(20, underTest.getShinyRolls());
	}

	@Test
	public void testGetShinyRollsFromNonShinyFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertEquals(-1, underTest.getShinyRolls());
	}

	@Test
	public void testGetLevelFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(-1, underTest.getLevel());
	}

	@Test
	public void testGetLevelFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(1, underTest.getLevel());
	}

	@Test
	public void testGetHeightFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(-1, underTest.getHeight());
	}

	@Test
	public void testGetHeightFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(107, underTest.getHeight());
	}

	@Test
	public void testGetFullPathLengthFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertEquals(3, underTest.getFullPathLength());
	}

	@Test
	public void testGetFullPathLengthFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertEquals(7, underTest.getFullPathLength());
	}

	@Test
	public void testIsAlphaFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertFalse(underTest.isAlpha());
	}

	@Test
	public void testIsAlphaFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE4);
		assertTrue(underTest.isAlpha());
	}

	@Test
	public void testIsAlphaFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertTrue(underTest.isAlpha());
	}

	@Test
	public void testIsAlphaFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE3);
		assertFalse(underTest.isAlpha());
	}

	@Test
	public void testIsBonusFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertFalse(underTest.isBonus());
	}

	@Test
	public void testIsBonusFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE4);
		assertTrue(underTest.isBonus());
	}

	@Test
	public void testIsBonusFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertTrue(underTest.isBonus());
	}

	@Test
	public void testIsBonusFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertFalse(underTest.isBonus());
	}

	@Test
	public void testIsChainFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertFalse(underTest.isChainLabelled());
	}

	@Test
	public void testIsChainFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE5);
		assertTrue(underTest.isChainLabelled());
	}

	@Test
	public void testIsChainFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertTrue(underTest.isChainLabelled());
	}

	@Test
	public void testIsChainFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE1);
		assertFalse(underTest.isChainLabelled());
	}

	@Test
	public void testIsSkittshFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertFalse(underTest.isSkittish());
	}

	@Test
	public void testIsSkittishFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE1);
		assertTrue(underTest.isSkittish());
	}

	@Test
	public void testIsSkittishFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertTrue(underTest.isSkittish());
	}

	@Test
	public void testIsSkittishFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertFalse(underTest.isSkittish());
	}

	@Test
	public void testIsSkittshAggressiveFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertFalse(underTest.isSkittishAggressive());
	}

	@Test
	public void testIsSkittishAggressiveFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE2);
		assertTrue(underTest.isSkittishAggressive());
	}

	@Test
	public void testIsSkittishAggressiveFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE5);
		assertTrue(underTest.isSkittishAggressive());
	}

	@Test
	public void testIsSkittishAggressiveFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertFalse(underTest.isSkittishAggressive());
	}

	@Test
	public void testIsSkittshMultiScaringFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertFalse(underTest.isSkittishMultiScaring());
	}

	@Test
	public void testIsSkittishMultiScaringFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE2);
		assertTrue(underTest.isSkittishMultiScaring());
	}

	@Test
	public void testIsSkittishMultiScaringFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertTrue(underTest.isSkittishMultiScaring());
	}

	@Test
	public void testIsSkittishMultiScaringFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertFalse(underTest.isSkittishMultiScaring());
	}

	@Test
	public void testIsSingleAdvancesFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertFalse(underTest.isSingleAdvances());
	}

	@Test
	public void testIsSingleAdvancesFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE6);
		assertTrue(underTest.isSingleAdvances());
	}

	@Test
	public void testIsSingleAdvancesFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE2);
		assertTrue(underTest.isSingleAdvances());
	}

	@Test
	public void testIsSingleAdvancesFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertFalse(underTest.isSingleAdvances());
	}

	@Test
	public void testIsSpawnsMultipleResultsFromCompact() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE3);
		assertFalse(underTest.isSpawnsMultipleResults());
	}

	@Test
	public void testIsSpawnsMultipleResultsFromCompact2() {
		PathDetails underTest = new PathDetails(COMPACT_PATH_EXAMPLE7);
		assertTrue(underTest.isSpawnsMultipleResults());
	}

	@Test
	public void testIsSpawnsMultipleResultsFromFullDetails() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE6);
		assertTrue(underTest.isSpawnsMultipleResults());
	}

	@Test
	public void testIsSpawnsMultipleResultsFromFullDetails2() {
		PathDetails underTest = new PathDetails(FULL_DETAILS_EXAMPLE4);
		assertFalse(underTest.isSpawnsMultipleResults());
	}

	@Test
	public void testChildrenLinkage() {
		PathDetails underTest1 = new PathDetails(COMPACT_PATH_EXAMPLE1);
		PathDetails underTest2 = new PathDetails(COMPACT_PATH_EXAMPLE2);
		underTest1.addChainChild(underTest2);
		assertEquals(0, underTest2.getChainChildren().size());
		assertSame(underTest1, underTest2.getChainParent());
		assertEquals(1, underTest1.getChainChildren().size());
		assertSame(underTest2, underTest1.getChainChildren().get(0));
		assertNull(underTest1.getChainParent());
	}

}
