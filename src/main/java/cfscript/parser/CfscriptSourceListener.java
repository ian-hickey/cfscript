package cfscript.parser;

import cfscript.object.parser.ObjectCustomListener;
import cfscript.object.parser.ObjectLexer;
import cfscript.object.parser.ObjectParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class CfscriptSourceListener extends CfscriptBaseListener {

    private final TokenStreamRewriter rewriter;
    private final String packageName;
    private String componentName = "";
    private String context = "";
    private String filepath = "";
    private CommonTokenStream tokens = null;
    private StringBuilder translation = new StringBuilder();
    private ArrayList<String> imports = new ArrayList<>();

    private void println(String text) {
        System.out.println(text);
    }
    public StringBuilder getTranslation() {
        return translation;
    }

    public CfscriptSourceListener(TokenStreamRewriter rewriter, CommonTokenStream tokens, String filePath, String finalPackageName) {
        super();
        this.rewriter = rewriter;
        this.tokens = tokens;
        this.filepath = filePath;
        this.packageName = finalPackageName;
        // Imports common to all source files
        addImportIfNotFound(imports, "import java.util.*;");
        addImportIfNotFound(imports, "import java.lang.*;");
        addImportIfNotFound(imports, "import io.quarkus.qute.*;"); // Qute Template Library
        addImportIfNotFound(imports, "import io.quarkus.mailer.Mailer;");
        addImportIfNotFound(imports, "import io.quarkus.logging.Log;"); // Simplified Logging
        addImportIfNotFound(imports, "import static cfscript.library.StdLib.*;"); // Cfscript STD functions like len and isNull.
        addImportIfNotFound(imports, "import jakarta.enterprise.context.*;"); // Handle Application, Singleton and Request Scopes
        addImportIfNotFound(imports, "import org.eclipse.microprofile.config.inject.ConfigProperty;");
    }


    @Override
    public void enterComponentDefinition(CfscriptParser.ComponentDefinitionContext ctx) {
        this.context = "component";

        // Iterate the attributes and find the component. TODO: default to the filename
        findComponentName(ctx);
        var newComponentText = "public class " + this.componentName + " ";
        var annotation = "";
        // Translate CFScript component to Java class
        for (var id : ctx.annotation()) {
            // if the return value
            annotation = id.getText();
            // handle imports.
            if (annotation.startsWith("@Path")) {
                addImportIfNotFound(imports, "import jakarta.ws.rs.*;");
                addImportIfNotFound(imports, "import jakarta.ws.rs.core.*;");
                addImportIfNotFound(imports, "import jakarta.transaction.Transactional;");
                addImportIfNotFound(imports, "import java.net.URI;");
            }else if (annotation.startsWith("@Entity")) {
                addImportIfNotFound(imports, "import jakarta.persistence.*;");
            }
            newComponentText = annotation + "\n" + newComponentText;
        }

        rewriter.replace(ctx.start, ctx.stop, newComponentText);
    }

    private void findComponentName(CfscriptParser.ComponentDefinitionContext ctx) {
        this.componentName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(this.getFileName(this.filepath));
    }

    public String getFileName(String filepath) {
        // Convert the file path string to a Path object
        Path path = Paths.get(filepath);

        // Get the file name from the Path object
        Path fileNamePath = path.getFileName();

        // If the path ends with a directory separator, the file name will be null
        if (fileNamePath == null) {
            return "default";  // Or whatever default value you want to use
        }

        // Convert the file name Path object to a string
        String fileNameWithExtension = fileNamePath.toString();

        // Get the index of the last dot '.' in the file name
        int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

        // Check if there is a dot in the file name and extract the part before it
        String fileNameWithoutExtension;
        if (lastDotIndex != -1) {
            fileNameWithoutExtension = fileNameWithExtension.substring(0, lastDotIndex);
        } else {
            // If there's no dot in the file name, use the entire file name
            fileNameWithoutExtension = fileNameWithExtension;
        }

        return fileNameWithoutExtension;
    }

    @Override
    public void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {
        //println("Enter Property");
        this.context = "property";
        // Translate CFScript property to Java class
        String propertyName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse("defaultName");
        String propertyValue = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("value"))
                .map(kv -> kv.StringLiteral().getText())
                .findFirst()
                .orElse(null);

        String propertyType = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("type"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse("String");

        var property = "";
        if (propertyType.toLowerCase().equals("array")) {
            property = "ArrayList<Object> " + propertyName + " = new ArrayList<Object>();";
        } else if ((propertyType.toLowerCase().equals("String") || propertyType.toLowerCase().equals("Integer")) || propertyValue == null) {
            property = propertyType + " " + propertyName + ";";
        }else {
            property = propertyType + " " + propertyName + " = " + propertyValue + ";";
        };

        // Check property annotations
        var annotation = "";
        for (var id : ctx.annotation()) {
            annotation = id.getText();
            property = annotation + "\n" + property;

            // Add imports
            if (annotation.startsWith("@Inject")) {
                addImportIfNotFound(imports, "import jakarta.inject.Inject;");
            }else if (annotation.startsWith("@Produces") || annotation.startsWith("@Consumes")) {
                addImportIfNotFound(imports, "import jakarta.ws.rs.core.*;");
            }
        }
        rewriter.replace(ctx.start, ctx.stop, property);
    }


    @Override
    public void exitComponent(CfscriptParser.ComponentContext ctx) {
        // Add Package
        addImportIfNotFound(imports, "package "+ this.packageName +";\n");

        if (!imports.isEmpty()) {
            Iterator<String> iterator = imports.iterator();
            while(iterator.hasNext()){
                rewriter.insertBefore(ctx.start, iterator.next() + "\n");
            }
        }

    }

    @Override
    public void enterFunctionDefinition(CfscriptParser.FunctionDefinitionContext ctx) {
        //println("Entered FunctionDeclaration");
        this.context = "function";
        // TODO: Check if this is a constructor and handle
        var functionName = ctx.functionName().getText();
        var functionScope="private";
        var functionReturn="void";
        var isConstructor = false;
        for (var id : ctx.Identifier()) {
            if (id.toString().toLowerCase().equals("public") ||
                    id.toString().toLowerCase().equals("private") ||
                    id.toString().toLowerCase().equals("remote")) {
                functionScope = id.getText();
            }else {
                // if the return value
                functionReturn = id.getText();
                if (functionReturn.toLowerCase().equals("response")) {
                    addImportIfNotFound(imports, "import jakarta.ws.rs.core.Response;");
                }
            }
        }
        // If this is a constructor, then name it as one.
        if (functionName.toLowerCase().equals("init")) {
            // use the constructor name instead of the method name
            isConstructor = true;
            functionName = this.getFileName(this.filepath);
        }
        var newFunction = functionScope + " " + (!isConstructor ? functionReturn+" " : "") + functionName;
        rewriter.replace(ctx.start, ctx.stop, newFunction);
    }

    @Override
    public void enterObjectLiteral(CfscriptParser.ObjectLiteralContext ctx) {
        parseObject(ctx.getText(), ctx);
    }

    @Override
    public void enterArrayLiteral(CfscriptParser.ArrayLiteralContext ctx) {
        parseObject(ctx.getText(), ctx);
    }

    @Override
    public void exitStructKeyAccess(CfscriptParser.StructKeyAccessContext ctx) {
        var newAccess = ctx.getText().replace("[", "").replace("]", "");
        newAccess = ".get(" + newAccess + ")";
        rewriter.replace(ctx.start, ctx.stop, newAccess);
    }

    private void parseObject(String text, ParserRuleContext ctx) {
        String arrayLiteralText = text;
        ObjectLexer lexer = new ObjectLexer(CharStreams.fromString(arrayLiteralText));
        ObjectParser parser = new ObjectParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.prog();
        ObjectCustomListener listener = new ObjectCustomListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        String javaCode = listener.getResult();
        rewriter.replace(ctx.start, ctx.stop, javaCode);
    }

    private void addImportIfNotFound(ArrayList<String> imports, String s) {
        if (!imports.contains(s)) {
            imports.add(s);
        }
    }
}

