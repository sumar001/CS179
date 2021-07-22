package typechecker;
import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, ClassEnv> st1;

    public SymbolTable() {
        st1 = new HashMap<String, ClassEnv>();
    }

    public void newClass(String x) {
        ClassEnv c = new ClassEnv(x);
        st1.put(x, c);
    }

    public ClassEnv getClass(String x) {

        return st1.get(x);
    }
}