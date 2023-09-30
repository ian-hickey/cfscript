package cfscript.typewriter;

import java.util.*;

public class SymbolTable {
    private final List<Symbol> symbols = new ArrayList<>();
    private int scopeLevel = 0;

    public void addSymbol(String name, Symbol symbol) {
        if (getSymbol(name) == null) {
            symbols.add(symbol);
        }
    }

    public Symbol getSymbol(String name) {
        for (int i = symbols.size() - 1; i >= 0; i--) {
            Symbol s = symbols.get(i);
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;  // or throw an exception
    }
    public void asString() {
        for (int i = symbols.size() - 1; i >= 0; i--) {
            Symbol s = symbols.get(i);
            System.out.println(s.toString());
        }
    }

}
