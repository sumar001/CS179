package typechecker;

import java.util.HashSet;
import visitor.*;
import syntaxtree.*;
import java.util.Set;


class ErrorMsg {
    boolean err;
    void alrt(String msg) {

        err = true;
    }
}

public class DfsVisitor extends DepthFirstVisitor {
    ErrorMsg error = new ErrorMsg();
    private SymbolTable st = new SymbolTable();
    private ClassEnv currClass;
    private MethodEnv currMethod;

    public boolean foundErrors() {

        return error.err;
    }

    public SymbolTable getSymbolTable() {

        return this.st;
    }

    public void visit(Goal n) {

        NodeList classNames = new NodeList(n.f0);
        if (n.f1.size() > 0) {
            for (int i = 0; i < n.f1.size(); i++) {
                classNames.addNode(((TypeDeclaration) n.f1.elementAt(i)).f0.choice);
            }
        }

        if (Helper.Distinc_Class(classNames)) {
            n.f0.accept(this);
            n.f1.accept(this);
        } else {
            error.alrt("Identical names of Class");
        }
    }

    public void visit(MainClass n) {
        String cname = Helper.className(n);
        st.newClass(cname);
        currClass = st.getClass(cname);
        currClass.addMethod("main", "void");
        currMethod = currClass.getMethod("main");
        n.f14.accept(this);
    }

    public void visit(ClassDeclaration n) {
        String cname = Helper.className(n);
        st.newClass(cname);
        currClass = st.getClass(cname);
        currMethod = null;
        if (Helper.idDistinct(n.f3)) {
            n.f3.accept(this);
        } else {
            error.alrt("Identical ID names");
        }
        if (Helper.Distinc_Method(n.f4)) {
            n.f4.accept(this);
        } else {
            error.alrt("Method names are same");
        }

    }

    public void visit(ClassExtendsDeclaration n) {
        String cname = Helper.className(n);
        st.newClass(cname);
        currClass = st.getClass(cname);
        currMethod = null;
        String superClass = Helper.getId(n.f3);
        ClassEnv super_class = st.getClass(superClass);
        Set<String> super_methods = super_class.getMethodNames();
        if(Helper.noOverloading(super_methods, n.f6)){
            if (Helper.idDistinct(n.f5)) {
                n.f5.accept(this);
            } else {
                error.alrt("Identical ID names");
            }
            if (Helper.Distinc_Method(n.f6)) {
                n.f6.accept(this);
            } else {
                error.alrt("Identical method name");
            }
        } else {
            error.alrt("Method is Overloaded");
        }
    }

    public void visit(MethodDeclaration n) {
        currClass.addMethod(Helper.methodName(n), Helper.methodType(n));
        currMethod = currClass.getMethod(Helper.methodName(n));
        if (n.f4.node != null) {
            if (Helper.parameterDistinct((FormalParameterList) n.f4.node)) {
                n.f4.accept(this);
            } else {
                error.alrt("Identical {Params} name");
            }
        }
        if (Helper.idDistinct(n.f7)) {
            n.f7.accept(this);
        } else {
            error.alrt("Identical ID names");
        }
    }

    public void visit(VarDeclaration n) {
        if(currMethod == null && currClass != null){
            currClass.addFields(Helper.getId(n.f1), Helper.getType(n.f0));
        }
        else if (currMethod != null) {
            currMethod.addVal(Helper.getId(n.f1), Helper.getType(n.f0));
        }
    }

    public void visit(FormalParameter n){
        if(currMethod != null && currClass != null){
            currMethod.addParam(Helper.getId(n.f1), Helper.getType(n.f0));
        }
    }
}