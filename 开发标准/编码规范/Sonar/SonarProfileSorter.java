import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

public class SonarProfileSorter {
	public static void main(String[] args)
		throws IOException {
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);

		List<String> inputLines = FileUtils.readLines(inputFile, "UTF-8");
		List<String> startLines = new ArrayList<>();
		List<String> endLines = new ArrayList<>();
		List<Rule> rules = new ArrayList<>();
		Rule currentRule = null;
		for (String line : inputLines) {
			String trimedLine = line.trim();
			if (trimedLine.equals("<rule>")) {
				currentRule = new Rule();
				rules.add(currentRule);
				currentRule.lines.add(line);
			} else if (trimedLine.equals("</rule>")) {
				currentRule.lines.add(line);
				currentRule = null;
			} else if (currentRule == null) {
				if (rules.isEmpty()) {
					startLines.add(line);
				} else {
					endLines.add(line);
				}
			} else {
				if (currentRule.repositoryKey == null && trimedLine.startsWith("<repositoryKey>")) {
					currentRule.repositoryKey = StringUtils.substringBetween(trimedLine, "<repositoryKey>", "</repositoryKey>");
				} else if (currentRule.key == null && trimedLine.startsWith("<key>")) {
					currentRule.key = StringUtils.substringBetween(trimedLine, "<key>", "</key>");
				}
				currentRule.lines.add(line);
			}
		}
		Collections.sort(rules, (r1, r2) -> new CompareToBuilder().append(r1.repositoryKey, r2.repositoryKey).append(r1.key, r2.key).toComparison());

		try (OutputStream os = new FileOutputStream(outputFile)) {
			IOUtils.writeLines(startLines, "\n", os, StandardCharsets.UTF_8);
			for (Rule rule : rules) {
				IOUtils.writeLines(rule.lines, "\n", os, StandardCharsets.UTF_8);
			}
			IOUtils.writeLines(endLines, "\n", os, StandardCharsets.UTF_8);
		}
	}

	private static class Rule {
		String repositoryKey;

		String key;

		List<String> lines = new ArrayList();
	}
}
