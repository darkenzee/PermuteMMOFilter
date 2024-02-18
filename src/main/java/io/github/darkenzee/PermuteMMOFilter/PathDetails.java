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
			Matcher m = PATH_PATTERN.matcher(getOriginalText());
			if (m.find()) {
				path = m.group(1);
			} else {
				throw new IllegalArgumentException("Could not identify path in input text");
			}
		}
		return path;
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
}
