/*for xChessgamex plugin*/  
package cxtable.xtable_chess; 
import java.util.Vector;

    public class xBishopMoves{
   
	private int up = xMove.upper;
   
      public xBishopMoves()
      {
      //*empty*//
      }
   
      public Vector diag(int x, int y)
      {
       
         Vector diago = new Vector();
      
    
         for (int i=0; i<up; i++)
            {
            int k=i+1;
	xMove nw = new xMove((x-k),(y-k));
	xMove ne = new xMove((x-k),(y+k));
	xMove sw = new xMove((x+k),(y-k));
	xMove se = new xMove((x+k),(y+k));
            if (nw.on_board()==true) {diago.addElement(nw);}
            if (ne.on_board()==true) {diago.addElement(ne);}
            if (sw.on_board()==true) {diago.addElement(sw);}
            if (se.on_board()==true) {diago.addElement(se);}
         }
      
         return diago;
      
      }
   
   }

