/* Method Environment */
package typechecker;
import java.util.HashMap;

public class MethodEnv {
    private HashMap<Symbol, String> val;
    private String m_id;
    private HashMap<Symbol, String> parameters;
    private String m_type;

    public MethodEnv(String name, String type) {
        val = new HashMap<Symbol, String>();
        m_id = name;
        parameters = new HashMap<Symbol, String>();
        m_type = type;
    }

    public String getMethodId() {

        return this.m_id;
    }

    public String getMethodType(){

        return this.m_type;
    }

    public String getValType(String x){
        return this.val.get(Symbol.symbol(x));
    }

    public void addVal(String name, String type) {

        this.val.put(Symbol.symbol(name), type);
    }

    public void addParam(String name, String type) {

        this.parameters.put(Symbol.symbol(name), type);
    }

    public String getParamType(String x) {

        return this.parameters.get(Symbol.symbol(x));
    }

    public int paramSize(){

        return this.parameters.size();
    }
}