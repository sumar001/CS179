package typechecker;

import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, cSymbol> classes;

    public SymbolTable() {

        classes = new HashMap<String, cSymbol>();
    }

    public void addClass(String x) {
        cSymbol c = new cSymbol(x);
        classes.put(x, c);
    }

    public cSymbol getClass(String x) {

        return classes.get(x);
    }
}