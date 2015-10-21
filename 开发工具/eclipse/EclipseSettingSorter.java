import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class EclipseSettingSorter {
	public static void main(String[] args) throws IOException {
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);

		List<String> inputLines = FileUtils.readLines(inputFile, "UTF-8");
		List<String> startLines = new ArrayList<>();
		List<String> endLines = new ArrayList<>();
		List<String> settingLines = new ArrayList<>();
		for (String line : inputLines) {
			if (line.startsWith("<setting")) {
				settingLines.add(line);
			} else if (settingLines.isEmpty()) {
				startLines.add(line);
			} else {
				endLines.add(line);
			}
		}
		Collections.sort(settingLines);

		FileUtils.writeLines(outputFile, "UTF-8", startLines, "\n");
		FileUtils.writeLines(outputFile, "UTF-8", settingLines, "\n", true);
		FileUtils.writeLines(outputFile, "UTF-8", endLines, "\n", true);
	}
}
