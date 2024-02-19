package io.github.darkenzee.PermuteMMOFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PathDetails {

	private static final int PATTERN_FLAGS = Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

	private final String originalText;
	private Boolean compactFormat;

	private String path;
	private String species;
	private String pid;
	private String ec;
	private String ivs;

	private PokemonNature nature;
	private PokemonGender gender;
	private ShinyType shinyType;

	private Integer spawn;
	private Integer shinyRolls;
	private Integer level;
	private Integer height;
	private Integer weight;
	private Integer chainParentPathLength;
	private Integer fullPathLength;

	private Boolean alpha;
	private Boolean bonus;
	private Boolean chain;
	private Boolean skittish;
	private Boolean skittishAggressive;
	private Boolean skittishMultiScaring;
	private Boolean singleAdvances;
	private Boolean spawnsMultipleResults;

	private PathDetails chainParent;
	private final List<PathDetails> chainChildren = new ArrayList<>();

	public PathDetails(String originalText) {
		this.originalText = originalText;
	}

	private boolean isCompactFormat() {
		if (compactFormat == null) {
			compactFormat = originalText.split("\r?\n").length == 1;
		}
		return compactFormat;
	}

	public String getOriginalText() {
		return originalText;
	}

	private static Pattern PATH_PATTERN = Pattern.compile("^\\* (.*?)\\s+>>>", PATTERN_FLAGS);

	public String getPath() {
		if (path == null) {
			path = applyPattern(PATH_PATTERN, "Could not identify path in input text");
		}
		return path;
	}

	private static Pattern SPECIES_PATTERN_COMPACT = Pattern.compile("^\\*.* =\\s*(?:α-)?(\\S+)[ :]", PATTERN_FLAGS);
	private static Pattern SPECIES_PATTERN_FULL = Pattern.compile("^\\*.* - (\\S+)$", PATTERN_FLAGS);

	public String getSpecies() {
		if (species == null) {
			species = applyPattern(isCompactFormat() ? SPECIES_PATTERN_COMPACT : SPECIES_PATTERN_FULL,
					"Could not identify species in input text");
		}
		return species;
	}

	private static Pattern PID_PATTERN = simplePropertyPattern("PID");

	public String getPid() {
		if (pid == null) {
			pid = applyPattern(PID_PATTERN);
		}
		return pid;
	}

	private static Pattern EC_PATTERN = simplePropertyPattern("EC");

	public String getEc() {
		if (ec == null) {
			ec = applyPattern(EC_PATTERN);
		}
		return ec;
	}

	private static Pattern IVS_PATTERN_COMPACT = Pattern.compile("((?:\\d+\\/){5}\\d+)", PATTERN_FLAGS);
	private static Pattern IVS_PATTERN_FULL = simplePropertyPattern("IVs");

	public String getIvs() {
		if (ivs == null) {
			ivs = applyPattern(isCompactFormat() ? IVS_PATTERN_COMPACT : IVS_PATTERN_FULL);
		}
		return ivs;
	}

	private static Pattern NATURE_PATTERN_COMPACT = Pattern.compile("(?:(?:\\d+\\/){5}\\d+)\\s+(\\S+)", PATTERN_FLAGS);
	private static Pattern NATURE_PATTERN_FULL = simplePropertyPattern("Nature");

	public PokemonNature getNature() {
		if (nature == null) {
			nature = PokemonNature
					.valueOf(applyPattern(isCompactFormat() ? NATURE_PATTERN_COMPACT : NATURE_PATTERN_FULL,
							"Could not identify nature in input text"));
		}
		return nature;
	}

	private static Pattern GENDER_PATTERN = Pattern.compile("\\(([FM])\\)", PATTERN_FLAGS);

	public PokemonGender getGender() {
		if (gender == null) {
			String genderStr = applyPattern(GENDER_PATTERN);
			switch (genderStr) {
			case "F":
				gender = PokemonGender.Female;
				break;
			case "M":
				gender = PokemonGender.Male;
				break;
			default:
				gender = PokemonGender.Genderless;
			}
		}
		return gender;
	}

	private static Pattern SHINY_PATTERN_COMPACT = Pattern.compile(" ([■*]) ", PATTERN_FLAGS);
	private static Pattern SHINY_PATTERN_FULL = simplePropertyPattern("Shiny");

	public ShinyType getShinyType() {
		// ★■*
		if (shinyType == null) {
			String shinyStr = applyPattern(isCompactFormat() ? SHINY_PATTERN_COMPACT : SHINY_PATTERN_FULL);
			switch (shinyStr) {
			case "★":
			case "*":
				shinyType = ShinyType.Star;
				break;
			case "■":
				shinyType = ShinyType.Square;
				break;
			default:
				shinyType = ShinyType.Not;
			}
		}
		return shinyType;
	}

	private static Pattern SPAWN_PATTERN = Pattern.compile("Spawn(\\d)", PATTERN_FLAGS);

	public int getSpawn() {
		if (spawn == null) {
			spawn = Integer.valueOf(applyPattern(SPAWN_PATTERN, "Could not identify spawn number in input text"));
		}
		return spawn;
	}

	private static Pattern SHINY_ROLLS_PATTERN_COMPACT = Pattern.compile(":\\s+(\\d+)\\s", PATTERN_FLAGS);
	private static Pattern SHINY_ROLLS_PATTERN_FULL = simplePropertyPattern("Shiny Rolls");

	public int getShinyRolls() {
		if (shinyRolls == null) {
			if (getShinyType() == ShinyType.Not) {
				shinyRolls = -1;
			} else {
				shinyRolls = Integer.valueOf(
						applyPattern(isCompactFormat() ? SHINY_ROLLS_PATTERN_COMPACT : SHINY_ROLLS_PATTERN_FULL));
			}
		}
		return shinyRolls;
	}

	private static Pattern LEVEL_PATTERN = simplePropertyPattern("Level");

	public int getLevel() {
		if (level == null) {
			String levelStr = applyPattern(LEVEL_PATTERN);
			switch (levelStr) {
			case "Unknown":
				level = -1;
				break;
			default:
				level = Integer.valueOf(levelStr);
			}
		}
		return level;
	}

	private static Pattern HEIGHT_PATTERN = simplePropertyPattern("Height");

	public int getHeight() {
		if (height == null) {
			String heightStr = applyPattern(HEIGHT_PATTERN);
			switch (heightStr) {
			case "Unknown":
				height = -1;
				break;
			default:
				height = Integer.valueOf(heightStr);
			}
		}
		return height;
	}

	private static Pattern WEIGHT_PATTERN = simplePropertyPattern("Weight");

	public int getWeight() {
		if (weight == null) {
			String weightStr = applyPattern(WEIGHT_PATTERN);
			switch (weightStr) {
			case "Unknown":
				weight = -1;
				break;
			default:
				weight = Integer.valueOf(weightStr);
			}
		}
		return weight;
	}

	public int getChainParentPathLength() {
		if (chainParentPathLength == null) {
			chainParentPathLength = getFullPathLength() - getPath().replaceAll("-> ", "").split("[|]").length;
		}
		return chainParentPathLength;
	}

	public int getFullPathLength() {
		if (fullPathLength == null) {
			fullPathLength = getPath().split("[| ]").length;
		}
		return fullPathLength;
	}

	public boolean isAlpha() {
		if (alpha == null) {
			if (isCompactFormat()) {
				alpha = getOriginalText().contains("α-");
			} else {
				alpha = !getOriginalText().contains("NOT αlpha");
			}
		}
		return alpha;
	}

	public boolean isBonus() {
		if (bonus == null) {
			bonus = getOriginalText().contains("Bonus");
		}
		return bonus;
	}

	public boolean isChain() {
		if (chain == null) {
			chain = getOriginalText().contains("Chain result!");
		}
		return chain;
	}

	public boolean isSkittish() {
		if (skittish == null) {
			skittish = getOriginalText().contains("Skittish");
		}
		return skittish;
	}

	public boolean isSkittishAggressive() {
		if (skittishAggressive == null) {
			skittishAggressive = getOriginalText().toLowerCase(Locale.getDefault()).contains("aggressive!");
		}
		return skittishAggressive;
	}

	public boolean isSkittishMultiScaring() {
		if (skittishMultiScaring == null) {
			skittishMultiScaring = getOriginalText().contains("Multi scaring");
		}
		return skittishMultiScaring;
	}

	public boolean isSingleAdvances() {
		if (singleAdvances == null) {
			singleAdvances = getOriginalText().contains("Single advances!");
		}
		return singleAdvances;
	}

	public boolean isSpawnsMultipleResults() {
		if (spawnsMultipleResults == null) {
			spawnsMultipleResults = getOriginalText().contains("Spawns multiple results!");
		}
		return spawnsMultipleResults;
	}

	public PathDetails getChainParent() {
		return chainParent;
	}

	public void setChainParent(PathDetails chainParent) {
		this.chainParent = chainParent;
	}

	public List<PathDetails> getChainChildren() {
		return Collections.unmodifiableList(chainChildren);
	}

	public void addChainChild(PathDetails chainChild) {
		chainChildren.add(chainChild);
		chainChild.setChainParent(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	private static Pattern simplePropertyPattern(String name) {
		return Pattern.compile(name + ":\\s+(\\S+)", PATTERN_FLAGS);
	}

	private String applyPattern(Pattern p) {
		return applyPattern(p, null);
	}

	private String applyPattern(Pattern p, String errorOnFindFail) {
		Matcher m = p.matcher(getOriginalText());
		if (m.find()) {
			return m.group(1);
		} else if (errorOnFindFail != null) {
			throw new IllegalArgumentException(errorOnFindFail);
		}
		return "Unknown";
	}

	public static List<PathDetails> loadFromFile(File inputFile) throws FileNotFoundException, IOException {
		try (FileInputStream fis = new FileInputStream(inputFile)) {
			return loadFromInputStream(fis);
		}
	}

	public static List<PathDetails> loadFromString(String inputString) throws IOException {
		try (InputStream inputStream = IOUtils.toInputStream(inputString, Charset.defaultCharset())) {
			return loadFromInputStream(inputStream);
		}
	}

	private static List<PathDetails> loadFromInputStream(InputStream inputStream) throws IOException {
		List<PathDetails> results = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		PathDetails currentChainParent = null;
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		while((line = reader.readLine()) != null) {
			if (line.length() == 0) continue;
			if (line.startsWith("*")) {
				if (builder.length() > 0) {
					PathDetails next = new PathDetails(builder.toString().trim());
					results.add(next);
					if (next.isChain()) {
						if (currentChainParent == null) {
							currentChainParent = next;
						} else {
							if (next.getChainParentPathLength() > currentChainParent.getFullPathLength()) {
								//Nested Chain
								currentChainParent = results.get(results.size() - 1);
							} else if (next.getChainParentPathLength() < currentChainParent.getFullPathLength()) {
								//Pop out of nesting
								currentChainParent = currentChainParent.getChainParent();
							}
							if (currentChainParent != null) {
								//Add next child to current parent
								currentChainParent.addChainChild(next);
							} else {
								currentChainParent = next;
							}
						}
					} else {
						currentChainParent = null;
					}
				}
				builder.setLength(0);
			}
			builder.append(line).append("\n");
		}
		if (builder.length() > 0) {
			PathDetails next = new PathDetails(builder.toString().trim());
			results.add(next);
			if (next.isChain()) {
				if (next.getChainParentPathLength() > currentChainParent.getFullPathLength()) {
					//Nested Chain
					currentChainParent = results.get(results.size() - 1);
				} else if (next.getChainParentPathLength() < currentChainParent.getFullPathLength()) {
					//Pop out of nesting
					currentChainParent = currentChainParent.getChainParent();
				}
				//Add next child to current parent
				currentChainParent.addChainChild(next);
			}
		}
		return results;
	}
}
