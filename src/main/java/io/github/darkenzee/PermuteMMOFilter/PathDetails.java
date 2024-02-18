package io.github.darkenzee.PermuteMMOFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static Pattern SPECIES_PATTERN_COMPACT = Pattern.compile("^\\*.* =\\s*(\\S+) ", PATTERN_FLAGS);
	private static Pattern SPECIES_PATTERN_FULL = Pattern.compile("^\\*.* - (\\S+)$", PATTERN_FLAGS);

	public String getSpecies() {
		if (species == null) {
			species = applyPattern(isCompactFormat() ? SPECIES_PATTERN_COMPACT : SPECIES_PATTERN_FULL, "Could not identify species in input text");
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
			nature = PokemonNature.valueOf(applyPattern(isCompactFormat() ? NATURE_PATTERN_COMPACT : NATURE_PATTERN_FULL, "Could not identify nature in input text"));
		}
		return nature;
	}

	public PokemonGender getGender() {
		return gender;
	}

	public ShinyType getShinyType() {
		return shinyType;
	}

	public Integer getSpawn() {
		return spawn;
	}

	public Integer getShinyRolls() {
		return shinyRolls;
	}

	public Integer getLevel() {
		return level;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWeight() {
		return weight;
	}

	public Integer getChainParentPathLength() {
		return chainParentPathLength;
	}

	public Integer getFullPathLength() {
		return fullPathLength;
	}

	public Boolean isAlpha() {
		return alpha;
	}

	public Boolean isBonus() {
		return bonus;
	}

	public Boolean isChain() {
		return chain;
	}

	public Boolean isSkittish() {
		return skittish;
	}

	public Boolean isSkittishAggressive() {
		return skittishAggressive;
	}

	public Boolean isSkittishMultiScaring() {
		return skittishMultiScaring;
	}

	public Boolean isSingleAdvances() {
		return singleAdvances;
	}

	public Boolean isSpawnsMultipleResults() {
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
}
