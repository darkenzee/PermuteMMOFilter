package io.github.darkenzee.PermuteMMOFilter.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class PathDetailsParser {
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
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		PathTrie root = new PathTrie(null);
		Map<Integer, PathDetails> lastPathOfLength = new HashMap<>();
		while((line = reader.readLine()) != null) {
			if (line.length() == 0) continue;
			if (line.startsWith("*")) {
				if (builder.length() > 0) {
					PathDetails next = new PathDetails(builder.toString().trim());
					results.add(next);
					lastPathOfLength.put(next.getFullPathLength(), next);
					if (next.getPath().contains("->")) {
						root.navigateToExistingParent(findParentPath(lastPathOfLength, next))
						.addDetails(next.getPath().replaceAll("-> ", ""), next);
					} else {
						root.addDetails(next.getPath(), next);
					}
				}
				builder.setLength(0);
			}
			builder.append(line).append("\n");
		}
		if (builder.length() > 0) {
			PathDetails next = new PathDetails(builder.toString().trim());
			results.add(next);
			lastPathOfLength.put(next.getFullPathLength(), next);
			if (next.getPath().contains("->")) {
				root.navigateToExistingParent(findParentPath(lastPathOfLength, next))
				.addDetails(next.getPath().replaceAll("-> ", ""), next);
			} else {
				root.addDetails(next.getPath(), next);
			}
		}
		root.generateChainInfo();
		return results;
	}
	
	private static String findParentPath(Map<Integer, PathDetails> lastPathOfLength, PathDetails next) {
		PathDetails parent = lastPathOfLength.get(next.getPath().split(" ").length - 1);
		if (parent.getPath().contains("->" )) {
			return findParentPath(lastPathOfLength, parent) + "|" + parent.getPath().replaceAll("-> ", "");
		} else {
			return parent.getPath();
		}
	}
}
