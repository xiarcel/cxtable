/*part of the chess game plugin..computes valid rook moves*/

package cxtable.xtable_chess;
import java.util.Vector;

 public class xRookMoves{

private int up =xMove.upper;

public xRookMoves()
{
//*empty*//

}

public Vector v_h(int x, int y)
{
Vector it = new Vector();
 for (int i=0; i< up; i++)
	{
	if (i==y) {continue;}
	xMove xm=new xMove(x,i); 
	if (xm.on_board()==true) {it.addElement(xm);}
	}
 for (int j=0; j<up; j++)
	{
	if (j==x) {continue;}
	xMove xm=new xMove(j,y); 
	if (xm.on_board()==true) {it.addElement(xm);}
	}
 return it;
}

}


