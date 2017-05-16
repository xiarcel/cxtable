package cxtable.xtable_chess;

/*this class produces valid moves from a specific location for any knight
for chess plugin
*/


   import java.util.Vector;

    public class xKnightMoves{
   
      private int x,y;
   
      public xKnightMoves()
      {
      //*empty*//
      }
   
      public Vector lmove(int _x, int _y)
      {
         x=_x; y=_y;
         Vector diago = new Vector();
      	 xMove[] poss = new xMove[8];
	 poss[0] = new xMove((x-1),(y-2));
	 poss[1] = new xMove((x-2),(y-1));
	 poss[2] = new xMove((x+1),(y+2));
	 poss[3] = new xMove((x+2),(y+1));
	 poss[4] = new xMove((x-1),(y+2));
	 poss[5] = new xMove((x-2),(y+1));
 	 poss[6] = new xMove((x+1),(y-2));
	 poss[7] = new xMove((x+2),(y-1));
	 for (int i=0; i< poss.length; i++)
		{if (poss[i].on_board() == true)
			{diago.addElement(poss[i]);}
		}
	return diago;
	}

   
   }

