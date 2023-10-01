package org.ionatomics;

import cfscript.parser.CfscriptLexer;
import cfscript.parser.CfscriptParser;
import cfscript.parser.CfscriptSourceListener;
import cfscript.parser.CfscriptSymbolListener;
import cfscript.typewriter.SymbolTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;

public class Main {
    public static void main(String[] args) throws IOException {
        Path startingDir = Paths.get(System.getProperty("user.dir"));
        SymbolTable symbolTable = new SymbolTable();
        String mode = "debug";
        String packageName = "org.ionatomics.darkmatter";
        if (args.length > 0) {
            mode = "live";
            packageName = args[0];
        }

        String finalMode = mode;
        String finalPackageName = packageName;

        Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                String dirName = dir.getFileName().toString();
                if ("target".equals(dirName)) {
                    // Skip the "target" directory and its subdirectories
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                String filePath = file.toString();
                if (fileName.endsWith(".cfc") || fileName.endsWith(".dms")) {
                    // Process the file IF it has not been processed since last change.
                    // content: Use to calculate the checksum.
                    var checksum = calculateChecksum(file);
                    System.out.println("checksum: " + checksum);
                    Path outputPath = Paths.get("checksums.txt");
                    String outputLine = getOutputLine(filePath, checksum);
                    var calculatedPackage = "";
                    if (!checksumExistsInFile(outputPath, outputLine)) {
                        System.out.println(filePath);
                        String[] parts = filePath.replace(File.separator + fileName, "")
                                .split(File.separator + "src"+File.separator +"main"+File.separator + "cfscript" + File.separator);
                        if (parts.length > 1) {
                            calculatedPackage = parts[1].replace(File.separator, ".");
                        } else {
                            System.err.println("Unable to calculate package name. Ensure files are in " +
                                    "the src/main/cfscript directory");
                            // Handle the case where the array doesn't have at least two elements
                        }

                        doSymbolize(file.toString(), symbolTable);
                        //symbolTable.asString();
                        doParse(file.toString(), symbolTable, finalMode, calculatedPackage);
                        saveChecksumToFile(filePath, checksum); // save the checksum
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void doSymbolize(String filePath, SymbolTable symbolTable) throws IOException {
        var content = Files.readString(Paths.get(filePath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptSymbolListener(filePath, symbolTable);
        walker.walk(listener, tree);
    }

    public static void doParse(String filePath, SymbolTable symbolTable, String finalMode, String finalPackageName) throws IOException {
        var content = Files.readString(Paths.get(filePath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var rewriter = new TokenStreamRewriter(tokens);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptSourceListener(rewriter, tokens, filePath, finalPackageName, symbolTable);
        walker.walk(listener, tree);

        System.out.println("*******************************");
        String processedContent = rewriter.getText(); // Replace this with the processed content
        if (finalMode == "debug") {
            System.out.println(rewriter.getText());
        } else if (finalMode == "live") {
            // Define the path where you want to save the Java file
            String outputFilePath = filePath
                    .replace(".cfc", ".java")
                    .replace(".dms", ".java")
                    .replace(File.separator + "cfscript", File.separator + "java")
                    .replace(finalPackageName, finalPackageName.replace(".", File.separator));

            Path out = Paths.get(outputFilePath);
            if (Files.exists(out)) {
                Files.delete(out);
            }
            // Maybe we need to create parent directories for the file.
            createJavaDirectories(removeFileFromPath(outputFilePath));

            try (BufferedWriter writer = Files.newBufferedWriter(out,
                    StandardOpenOption.CREATE)) {
                writer.write(processedContent);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Java file saved at: " + outputFilePath);
        }
        System.out.println("*******************************");

    }

    public static void createJavaDirectories(String filePath) {
        String[] splitPath = filePath.split(File.separator + "src"
                + File.separator
                + "main" + File.separator
                + "java" + File.separator);
        String basePath = splitPath[0] + File.separator + "src" + File.separator + "main" + File.separator + "java";
        String[] pathParts = splitPath[1].split(File.separator);
        String currentPath = "";
        //System.out.println("Checking Directory for " + basePath);
        for (String pathPart : pathParts) {
            currentPath += File.separator + pathPart;
            File directory = new File(basePath + currentPath);
            if (!directory.exists()) {
                //System.out.println("Creating Directory for " + currentPath);
                directory.mkdir();
            }
        }
    }

    public static String removeFileFromPath(String filePath) {
        int lastSlashIndex = filePath.lastIndexOf(File.separator);
        if (lastSlashIndex == -1) {
            return filePath;
        }
        return filePath.substring(0, lastSlashIndex);
    }
    public static String calculateChecksum(Path filePath) throws IOException {
        CRC32 crc = new CRC32();

        try (InputStream in = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) != -1) {
                crc.update(buffer, 0, len);
            }
        }

        return Long.toHexString(crc.getValue());
    }

    public static void saveChecksumToFile(String filePath, String checksum) throws IOException {
        Path outputPath = Paths.get("checksums.txt");

        List<String> lines = new ArrayList<>();  // Initialize an empty list to hold the lines

        // Check if the file exists
        if (Files.exists(outputPath)) {
            // If the file exists, read all lines from the file into the list
            lines = Files.readAllLines(outputPath);
        }

        // This flag will be used to track whether we've found and updated an existing entry
        boolean found = false;

        // Iterate through each line in the list
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // Split the line into its components based on whitespace
            String[] parts = line.split("\\s+", 2);

            // Check if the filePath matches the current line
            if (parts.length > 0 && parts[0].equals(filePath)) {
                // Update the checksum for this filePath
                lines.set(i, filePath + " " + checksum);
                found = true;
                break;
            }
        }

        // If we didn't find an existing entry, add a new one
        if (!found) {
            lines.add(filePath + " " + checksum);
        }

        // Write the updated list of lines back to the file
        Files.write(outputPath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String getOutputLine(String outputPath, String checksum) {
        return outputPath + " " + checksum;
    }

    private static boolean checksumExistsInFile(Path outputPath, String outputLine) throws IOException {
        // Load existing checksums
        List<String> existingLines = Collections.emptyList();
        if (Files.exists(outputPath)) {
            existingLines = Files.readAllLines(outputPath);
            for (String line : existingLines) {
                if (line.equals(outputLine)) {
                    // Checksum already exists in file, no need to append
                    return true;
                }
            }
        }
        return false;
    }
}