package cfscript.object.parser;

import java.util.Stack;

public class ObjectCustomListener extends ObjectBaseListener {
    private Stack<String> stack = new Stack<>();
    private int indentLevel = 0;

    private String indent() {
        return "    ".repeat(indentLevel);
    }

    public String getResult() {
        return stack.pop();
    }

    @Override
    public void exitStruct(ObjectParser.StructContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("new HashMap<String, Object>() {{ ");

        for (var pair : ctx.structPair()) {
            var key = pair.ID().getText();
            var value = pair.value().getText();
            sb.append(indent()).append(String.format("put(\"%s\", %s);", key, value));
        }
        sb.append("}}");
        stack.push(sb.toString());

    }

    @Override
    public void exitArray(ObjectParser.ArrayContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("new ArrayList<Object>() {{");
        for (var value : ctx.value()) {
            sb.append("add(").append(value.getText()).append(");");
        }
        sb.append("}}");

        stack.push(sb.toString());
    }

    @Override
    public void exitValue(ObjectParser.ValueContext ctx) {
        if (ctx.STRING() != null) {
            stack.push(String.format("%s", ctx.STRING().getText()));
        } else if (ctx.NUMBER() != null) {
            stack.push(ctx.NUMBER().getText());
        } else if (ctx.ID() != null) {
            stack.push(ctx.ID().getText());
        }
    }
}

