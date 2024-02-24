package io.github.darkenzee.PermuteMMOFilter.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonNature;
import io.github.darkenzee.PermuteMMOFilter.types.ShinyType;

public class PathDetails {

	private static final int PATTERN_FLAGS = Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

	private final String originalText;
	private Boolean compactFormat;

	private String path;
	private String species;
	private String pid;
	private String ec;
	private String ivs;
	private String[] ivSplit;

	private PokemonNature nature;
	private PokemonGender gender;
	private ShinyType shinyType;

	private Integer spawn;
	private Integer shinyRolls;
	private Integer level;
	private Integer height;
	private Integer weight;
	private Integer fullPathLength;

	private Integer ivHp;
	private Integer ivAtk;
	private Integer ivDef;
	private Integer ivSpAtk;
	private Integer ivSpDef;
	private Integer ivSpeed;

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
	private int siblingCount = 0;

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

	private static Pattern SPECIES_PATTERN_COMPACT = Pattern.compile("^\\*.* =\\s*(?:α-)?([-a-zA-Z0-9]+)[ :]",
			PATTERN_FLAGS);
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

	private String[] getIvSplit() {
		if (ivSplit == null) {
			ivSplit = getIvs().split("/");
		}
		return ivSplit;
	}

	public int getIVHp() {
		if (ivHp == null) {
			ivHp = Integer.parseInt(getIvSplit()[0]);
		}
		return ivHp;
	}

	public int getIVAtk() {
		if (ivAtk == null) {
			ivAtk = Integer.parseInt(getIvSplit()[1]);
		}
		return ivAtk;
	}

	public int getIVDef() {
		if (ivDef == null) {
			ivDef = Integer.parseInt(getIvSplit()[2]);
		}
		return ivDef;
	}

	public int getIVSpAtk() {
		if (ivSpAtk == null) {
			ivSpAtk = Integer.parseInt(getIvSplit()[3]);
		}
		return ivSpAtk;
	}

	public int getIVSpDef() {
		if (ivSpDef == null) {
			ivSpDef = Integer.parseInt(getIvSplit()[4]);
		}
		return ivSpDef;
	}

	public int getIVSpeed() {
		if (ivSpeed == null) {
			ivSpeed = Integer.parseInt(getIvSplit()[5]);
		}
		return ivSpeed;
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
		if (getChainParent() != null) {
			return getChainParent().getFullPathLength();
		} else {
			return 0;
		}
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

	public boolean isChainLabelled() {
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

	public boolean isChain() {
		return getChainParent() != null || chainChildren.size() > 0;
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

	public void setSiblingCount(int siblingCount) {
		this.siblingCount = siblingCount;
	}

	public int getPathSpawnCount() {
		return 1 + siblingCount + (getChainParent() != null ? getChainParent().getPathSpawnCount() : 0);
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.setExcludeFieldNames("chainParent", "chainChildren").toString();
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
}
