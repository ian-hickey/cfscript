package Cfscript.object.parser;

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
        sb.append("var randomname = new HashMap<String, Object>();\n");
        for (var pair : ctx.structPair()) {
            var key = pair.ID().getText();
            var value = stack.pop();
            sb.append(String.format("randomname.put(\"%s\", %s);\n", key, value));
        }
        stack.push(sb.toString());
    }

    @Override
    public void exitArray(ObjectParser.ArrayContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("new ArrayList<Object>(Arrays.asList(");
        boolean first = true;
        for (var value : ctx.value()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(stack.pop());
        }
        sb.append("))");
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

