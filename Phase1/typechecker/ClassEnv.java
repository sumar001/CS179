/* Class Environment */

package typechecker;
import java.util.*;
import java.util.Set;
import java.util.HashMap;


public class ClassEnv {
    private Symbol name;
    private HashMap<Symbol, String> fields;
    private HashMap<String, MethodEnv> methods;


    public ClassEnv(String n) {
        name = Symbol.symbol(n);
        fields = new HashMap<Symbol, String>();
        methods = new HashMap<String, MethodEnv>();
    }

    public String getClassId() {
        return this.name.toString();
    }

    public void addMethod(String name, String type) {

        this.methods.put(name, new MethodEnv(name, type));
    }

    public void addFields(String name, String type) {
        this.fields.put(Symbol.symbol(name), type);
    }

    public MethodEnv getMethod(String name) {
        return this.methods.get(name);
    }

    public String getField(String name) {
        return this.fields.get(Symbol.symbol(name));
    }

    public int methodSize() {
        return methods.size();
    }

    public Set<String> getMethodNames() {
        return methods.keySet();
    }

}