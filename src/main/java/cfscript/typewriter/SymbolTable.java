package cfscript.typewriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private final Stack<Map<String, Symbol>> scopes = new Stack<>();
    private int scopeLevel = 0;

    public void enterScope() {
        scopeLevel++;
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopeLevel--;
        scopes.pop();
    }

    public void addSymbol(String name, Symbol symbol) {
        symbol.setScopeLevel(scopeLevel);
        scopes.peek().put(name, symbol);
    }

    public Symbol getSymbol(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Symbol> scope = scopes.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;  // or throw an exception
    }
    public void asString() {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Symbol> scope = scopes.get(i);

            System.out.println(scope.toString());
        }
    }
}
