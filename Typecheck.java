import java.util.*;
import syntaxtree.*;
import typechecker.*;

public class Typecheck {
    public static void main(String[] args) {
        try {
            new MiniJavaParser(System.in);
            Node root = MiniJavaParser.Goal();

            //The accept will go down the tree and accept everything
            DfsVisitor dv = new DfsVisitor();
            root.accept(dv);
            if (dv.foundErrors() == true) {
                System.out.println("Type error");
                System.exit(1);
            }
            else {
                GjdVisitor gv = new GjdVisitor(dv.getSymbolTable());
                root.accept(gv, dv.getSymbolTable());
                if(gv.findErrors() == true){
                    System.out.println("Type error");
                    System.exit(1);
                }
                else{
                    System.out.println("Program type checked successfully");
                }

            }
        } catch (ParseException e) {
            System.out.println("Type error");
            System.exit(1);
        }
    }
}