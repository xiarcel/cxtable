package cxtable.xtable_chess;
import java.util.Vector;

 public class xKingLocation{

private int x,y,side;

public xKingLocation(int s, int _x, int _y)
{
x=_x; y=_y; side=s;
}

public int getSide(){return side;}

public int getx(){return x;}

public int gety(){return y;}

public static xKingLocation[] findKings(xChessBox[][] board)
{
Vector v=new Vector();
for (int i=0; i<xChessManager.upper; i++)
	{
	for (int j=0; j<xChessManager.upper; j++)
		{
		xPiece xp = board[i][j].getPiece();
		if (xp.getControl().equals("KG"))
			{
			if (xChessGamex.debug){System.out.println("Found king at "+i+","+j);}
			v.addElement(new xKingLocation(xp.getSide(),xp.getx(),xp.gety()));
			}
		}
	}
if ((v.size()==0)&(xChessGamex.debug)){System.out.println("??No kings??");}
xKingLocation[] xkl =new xKingLocation[v.size()];
for (int z=0; z<xkl.length; z++)
	{
	xkl[z] = (xKingLocation)v.elementAt(z);
	}
if (xChessGamex.debug){System.out.println("There are "+xkl.length+" kings");}

return xkl;
}

}

			
