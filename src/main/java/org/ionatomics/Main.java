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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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
            System.out.println(args[0]);
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

                    if (!checksumExistsInFile(outputPath, outputLine)) {
                        doParse(file.toString(), symbolTable, finalMode, finalPackageName);
                        saveChecksumToFile(filePath, checksum); // save the checksum
                    }else{
                        System.out.println("No change - skipping");
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        //symbolTable.asString();
    }

    public static void doSymbolize(String filePath, SymbolTable symbolTable) throws IOException {
        System.out.println(filePath);
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
        System.out.println(filePath);
        var content = Files.readString(Paths.get(filePath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var rewriter = new TokenStreamRewriter(tokens);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptSourceListener(rewriter, tokens, filePath, finalPackageName);
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
                    .replace("src/main/cfscript", "src/main/java")
                    .replace(finalPackageName, finalPackageName.replace(".", "/"));

            Path out = Paths.get(outputFilePath);
            if (Files.exists(out)) {
                Files.delete(out);
            }
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
        String outputLine = getOutputLine(filePath, checksum);

        if (checksumExistsInFile(outputPath, outputLine)) return;

        // Append checksum to file
        String outputEntry = outputLine + System.lineSeparator();
        Files.write(outputPath, outputEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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