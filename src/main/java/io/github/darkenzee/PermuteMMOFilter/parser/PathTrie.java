package io.github.darkenzee.PermuteMMOFilter.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PathTrie {
	private final PathTrie parent;
	// List to allow for multiple spawns at path
	private final List<PathDetails> detailsList = new ArrayList<>();
	private final Map<String, PathTrie> childPaths = new LinkedHashMap<>();

	public PathTrie(PathTrie parent) {
		this.parent = parent;
	}

	private void appendDetails(PathDetails details) {
		detailsList.add(details);
	}

	public void addDetails(String pathFragment, PathDetails details) {
		String[] parts = pathFragment.split("[|]", 2);
		PathTrie child = childPaths.computeIfAbsent(parts[0], k -> new PathTrie(this));
		if (parts.length == 2) {
			child.addDetails(parts[1], details);
		} else {
			child.appendDetails(details);
		}
	}

	public PathTrie getParent() {
		return parent;
	}

	public PathTrie navigateToExistingParent(String pathFragment) {
		String[] parts = pathFragment.split("[|]", 2);
		PathTrie child = childPaths.get(parts[0]);
		if (child != null && parts.length == 2) {
			child = child.navigateToExistingParent(parts[1]);
		}
		return child;
	}

	public void generateChainInfo() {
		generateChainInfo(null);
	}

	private void generateChainInfo(PathTrie currentChainParent) {
		for (PathDetails currentDetails : detailsList) {
			for (PathDetails siblingDetails : detailsList) {
				if (currentDetails != siblingDetails) {
					currentDetails.addSibling(siblingDetails);
				}
			}
		}
		if (currentChainParent != null && detailsList.size() > 0) {
			for (PathDetails parentDetails : currentChainParent.detailsList) {
				for (PathDetails currentDetails : detailsList) {
					parentDetails.addChainChild(currentDetails);
				}
			}
		}
		for (PathTrie childPath : childPaths.values()) {
			childPath.generateChainInfo(this.detailsList.size() > 0 ? this : currentChainParent);
		}
	}

}
