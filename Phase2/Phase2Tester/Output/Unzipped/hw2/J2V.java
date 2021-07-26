import syntaxtree.*;
import typechecker.*;

public class J2V {
   public static void main(String[] args) {
      try {
         new MiniJavaParser(System.in);
         Node root = MiniJavaParser.Goal();
         DfsVisitor dv = new DfsVisitor();
         root.accept(dv);
         if (dv.getErrors() == true) {
            System.exit(1);
         } else {
            GjdVisitor gv = new GjdVisitor(dv.getSymbolTable());
            root.accept(gv, dv.getSymbolTable());
            if(gv.getErrors() == true){
               System.exit(1);
            }
            else{
               Translation tv = new Translation(dv.getSymbolTable());
               root.accept(tv);
            }
            
         }
      } catch (ParseException e) {
         System.exit(1);
      }
   }
}
