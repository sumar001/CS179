package typechecker;

import java.util.*;
import visitor.*;
import syntaxtree.*;

public class GjdVisitor extends GJDepthFirst<String, SymbolTable> {
    private ClassEnv currClass = null;
    private MethodEnv currMethod = null;
    private SymbolTable symbtable;
    private boolean errs = false;

    public boolean findErrors(){

        return errs;
    }

    public GjdVisitor(SymbolTable symb_t) {
        this.symbtable = symb_t;
    }

    public String visit(ClassDeclaration cd, SymbolTable symb_t){
        currClass = symbtable.getClass(Helper.className(cd));
        currMethod = null;
        cd.f4.accept(this, symb_t);
        return null;
    }

    public String visit(MethodDeclaration md, SymbolTable symb_t){

        currMethod = currClass.getMethod(Helper.methodName(md));
        String m_type = Helper.getType(md.f1);
        String returnType = md.f10.accept(this, symb_t);
        if(m_type != null || m_type != "" || returnType != null || returnType != "" ){
            returnType = getIDType(returnType);
            if(m_type != returnType){
                errs = true;
                return null;
            }
        }
        md.f8.accept(this, symb_t);
        return null;
    }

    public String getIDType(String x){
        if(symbtable.getClass(x) != null){
            return x;
        }
        else{
            if(x == "int" || x == "boolean"){
                return x;
            }
            String name = "";
            if(currMethod.getValType(x) != null){
                name = currMethod.getValType(x);
            }
            else if(currMethod.getParamType(x) != null){
                name = currMethod.getParamType(x);
            }
            else if(currClass.getField(x) != null){
                name = currClass.getField(x);
            }
            return name;
        }
    }

    public String visit(Statement stm, SymbolTable symb_t) {
        stm.f0.choice.accept(this, symb_t);
        return null;
    }

    public String visit(AssignmentStatement as, SymbolTable symb_t) {
        String id = Helper.getId(as.f0);
        String id2 = as.f2.accept(this, symb_t);
        if(id != null && id2 != null){
            String id_type = getIDType(id);
            String id_type2 = getIDType(id2);
            if(id_type != id_type2 || id_type == null || id_type2 == null){
                errs = true;
            }
        }

        return null;
    }

    public String visit(PrintStatement ps, SymbolTable symb_t) {
        String statementType = getIDType(ps.f2.accept(this, symb_t));
        if(statementType != "int"){
            errs = true;
        }
        return null;
    }

    public String visit(Expression exp, SymbolTable symb_t){

        return exp.f0.choice.accept(this, symb_t);
    }


    public String visit(MessageSend msd, SymbolTable symb_t){

        String c_name = getIDType(msd.f0.accept(this, symb_t));
        String m_name = Helper.getId(msd.f2);
        ClassEnv c_Class = symbtable.getClass(c_name);
        if(c_Class != null){
            MethodEnv c_Method = c_Class.getMethod(m_name);
            if(c_Method == null){
                errs = true;
                return "";
            }
            else{
                if(msd.f4.node != null){
                    int callSize = Helper.getListSize(msd.f4.node);
                    int methodSize = c_Method.paramSize();
                    if(methodSize != callSize){
                        errs = true;
                    }
                }
                String m_type = c_Method.getMethodType();
                return m_type;
            }
        }
        else{
            errs = true;
            return "";
        }


    }

    public String visit(PrimaryExpression pexp, SymbolTable symb_t){
        String c_name = pexp.f0.choice.accept(this, symb_t);
        return c_name;
    }


    public String visit(AllocationExpression allExp, SymbolTable symb_t){
        String c_name = Helper.getId(allExp.f1);
        return c_name;
    }

    public String visit(IntegerLiteral il, SymbolTable symb_t){
        String c_name = "int";
        return c_name;
    }

    public String visit(ArrayLookup alookup, SymbolTable symb_t){
        String id1 = getIDType(alookup.f0.accept(this, symb_t));
        String id2 = getIDType(alookup.f2.accept(this, symb_t));
        if(id1 == "int []" && id2 == "int"){
            return id2;
        }
        else{
            errs = true;
            return "";
        }
    }

    public String visit(FalseLiteral fl, SymbolTable symb_t){
        String c_name = "boolean";
        return c_name;
    }

    public String visit(TrueLiteral tl, SymbolTable symb_t){
        String c_name = "boolean";
        return c_name;
    }

    public String visit(TimesExpression multExp, SymbolTable symb_t){
        String first = getIDType(multExp.f0.accept(this, symb_t));
        String second = getIDType(multExp.f2.accept(this, symb_t));
        if(first == second){
            return first;
        }
        else{
            return null;
        }
    }

    public String visit(ThisExpression n, SymbolTable symb_t){
        return currClass.getClassId();
    }

    public String visit(BracketExpression bracExp, SymbolTable symb_t){
        return bracExp.f1.accept(this, symb_t);
    }

    public String visit(PlusExpression plusExp, SymbolTable symb_t){
        String first = getIDType(plusExp.f0.accept(this, symb_t));
        String second = getIDType(plusExp.f2.accept(this, symb_t));
        if(first == second){
            return first;
        }
        else{
            return null;
        }
    }

    public String visit(Identifier iden, SymbolTable symb_t){

        return Helper.getId(iden);
    }

    public String visit(MinusExpression minusExp, SymbolTable symb_t){
        String first = getIDType(minusExp.f0.accept(this, symb_t));
        String second = getIDType(minusExp.f2.accept(this, symb_t));
        if(first == second){
            return first;
        }
        else{
            return null;
        }
    }



}