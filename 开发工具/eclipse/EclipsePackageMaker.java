import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class EclipsePackageMaker {
    public static void main(String[] args)
        throws IOException {
        File sourceDir = new File(args[0]);
        File targetDir = new File(args[1]);
        File outputFile = new File(args[2]);

        List<String> changedList = new ArrayList<>();
        List<String> removedList = new ArrayList<>();
        visitDir(sourceDir, targetDir, null, changedList, removedList);

        try (OutputStream os = new FileOutputStream(outputFile)) {
            IOUtils.writeLines(Arrays.asList("Changed Files:"), "\n", os, StandardCharsets.UTF_8);
            IOUtils.writeLines(changedList, "\n", os, StandardCharsets.UTF_8);
            IOUtils.writeLines(Arrays.asList("", "Removed Files:"), "\n", os, StandardCharsets.UTF_8);
            IOUtils.writeLines(removedList, "\n", os, StandardCharsets.UTF_8);
        }
    }

    private static void visitDir(File sourceDir, File targetDir, String basePath, List<String> changedList, List<String> removedList)
        throws IOException {
        if (!targetDir.exists()) {
            removedList.add(basePath);
            return;
        }
        if (!targetDir.isDirectory()) {
            changedList.add(basePath);
            return;
        }

        File[] sourceFiles = sourceDir.listFiles();
        for (File sourceFile : sourceFiles) {
            String fileName = sourceFile.getName();
            String filePath = buildFilePath(basePath, fileName);

            if (sourceFile.isDirectory()) {
                visitDir(sourceFile, new File(targetDir, fileName), filePath, changedList, removedList);
            } else if (sourceFile.isFile()) {
                visitFile(sourceFile, new File(targetDir, fileName), filePath, changedList, removedList);
            } else {
                changedList.add(filePath);
            }
        }
        File[] targetFiles = targetDir.listFiles();
        if (targetFiles.length == 0) {
            Files.delete(targetDir.toPath());
        }
    }

    private static void visitFile(File sourceFile, File targetFile, String basePath, List<String> changedList, List<String> removedList)
        throws IOException {
        if (!targetFile.exists()) {
            removedList.add(basePath);
            return;
        }
        if (!targetFile.isFile()) {
            changedList.add(basePath);
            return;
        }

        String sourceHash = hashFileContent(sourceFile);
        String targetHash = hashFileContent(targetFile);
        if (sourceHash.equals(targetHash)) {
            Files.delete(targetFile.toPath());
        } else {
            changedList.add(basePath);
        }
    }

    private static String buildFilePath(String basePath, String fileName) {
        if (StringUtils.isBlank(basePath)) {
            return fileName;
        } else {
            return basePath + '/' + fileName;
        }
    }

    private static String hashFileContent(File file)
        throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return DigestUtils.sha512Hex(is);
        }
    }
}
