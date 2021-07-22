import syntaxtree.*;

import java.util.*;

public class J2V {

    public static void main(String[] args){
        new MiniJavaParser(System.in);
        VaporVisitor vv = new VaporVisitor();
        try{
            Node root = MiniJavaParser.Goal();
            root.accept(vv);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
    }
}