package cxtable.xtable_chess;

import java.awt.*;
import java.awt.event.*;


public class xTournoUtilities{
static int B = xPiece.BLACK;
static int W = xPiece.WHITE;
static int R = xPiece.RED;
static int G = xPiece.GREEN;

public static xChessBox[][] create_board()
{
xMove.setUpper(15);
xChessValidator.setSize(16);


xChessBox[][] board = new xChessBox[16][16];
boolean odd=true;

/*create boxes, black in top left corner*/

for (int xx=0; xx<16; xx++)
	{odd=!odd;
	for(int yy=0; yy<16; yy++)
		{board[xx][yy] = new xChessBox(odd,xx,yy,null);
		 odd=!odd;
		}
	}

/*set GUTTERS*/

for (int i=0; i<=3; i++)
	{
	board[0][i].occupy(new xGutter());
	board[1][i].occupy(new xGutter());
	board[14][i].occupy(new xGutter());
	board[15][i].occupy(new xGutter());
	}
for (int j=12;j<=15;j++)
	{
	board[0][j].occupy(new xGutter());
	board[1][j].occupy(new xGutter());
	board[14][j].occupy(new xGutter());
	board[15][j].occupy(new xGutter());
	}
for (int k=0; k<=1; k++)
	{
	board[2][k].occupy(new xGutter());
	board[3][k].occupy(new xGutter());
	board[12][k].occupy(new xGutter());
	board[13][k].occupy(new xGutter());
	}

for (int m=14; m<=15; m++)
	{
	board[2][m].occupy(new xGutter());
	board[3][m].occupy(new xGutter());
	board[12][m].occupy(new xGutter());
	board[13][m].occupy(new xGutter());
	}
  
 /*set EMPTIES*/
for (int x=2; x<=13; x++)
	{
	for (int y=2; y<=13; y++)
		{
		board[x][y].occupy(new xEmptyPiece());
		}
	}


/*Set BLACK pieces*/
board[0][4].occupy(new xRook(B,0,4));
board[0][11].occupy(new xRook(B,0,11));
board[0][5].occupy(new xKnight(B,0,5));
board[0][10].occupy(new xKnight(B,0,10));
board[0][6].occupy(new xBishop(B,0,6));
board[0][9].occupy(new xBishop(B,0,9));
board[0][7].occupy(new xQueen(B,0,7));
board[0][8].occupy(new xKing(B,0,8));
for (int w =4; w<=11; w++)
	{
	board[1][w].occupy(new xPawn(B,1,w));
	}

/*Set WHITE pieces*/
board[15][4].occupy(new xRook(W,15,4));
board[15][11].occupy(new xRook(W,15,11));
board[15][5].occupy(new xKnight(W,15,5));
board[15][10].occupy(new xKnight(W,15,10));
board[15][6].occupy(new xBishop(W,15,6));
board[15][9].occupy(new xBishop(W,15,9));
board[15][7].occupy(new xQueen(W,15,7));
board[15][8].occupy(new xKing(W,15,8));
for (int v=4; v<=11; v++)
	{
	board[14][v].occupy(new xPawn(W,14,v));
	}

/*Set GREEN pieces*/
board[4][0].occupy(new xRook(G,4,0));
board[11][0].occupy(new xRook(G,11,0));
board[5][0].occupy(new xKnight(G,5,0));
board[10][0].occupy(new xKnight(G,10,0));
board[6][0].occupy(new xBishop(G,6,0));
board[9][0].occupy(new xBishop(G,9,0));
board[7][0].occupy(new xQueen(G,7,0));
board[8][0].occupy(new xKing(G,8,0));
for (int p=4; p<=11; p++)
	{
	board[p][1].occupy(new xPawn(G,p,1));
	}

/*set RED pieces*/
board[4][15].occupy(new xRook(R,4,15));
board[11][15].occupy(new xRook(R,11,15));
board[5][15].occupy(new xKnight(R,5,15));
board[10][15].occupy(new xKnight(R,10,15));
board[6][15].occupy(new xBishop(R,6,15));
board[9][15].occupy(new xBishop(R,9,15));
board[7][15].occupy(new xQueen(R,7,15));
board[8][15].occupy(new xKing(R,8,15));

for (int q=4; q<=11; q++)
	{
	board[q][14].occupy(new xPawn(R,q,14));
	}

/*this should be a complete board*/

return board;
}


public static Panel getView(xChessBox[][] xcb, int player)
{

Label[] alpha = new Label[]{new Label("-A-"), new Label("-B-"), new Label("-C-"), new Label("-D-"),
	new Label("-E-"), new Label("-F-"), new Label("-G-"), new Label("-H-"), new Label("-I-"),
	new Label("-J-"), new Label("-K-"), new Label("-L-"), new Label("-M-"), new Label("-N-"),
	new Label("-O-"), new Label("-P-")};
Panel[] alphas=new Panel[16];
Panel[] nums =new Panel[16];
for (int vv=0; vv<nums.length;vv++)
	{
	Label l=new Label("-"+(vv+1)+"-");
	nums[vv]=new Panel(); nums[vv].add(l);
	alphas[vv]=new Panel(); alphas[vv].add(alpha[vv]);
	}

Panel view = new Panel();
view.setLayout(new GridLayout(17,17));
view.add(new Panel());
if ((player<0)|(player>3)){player=0;}

switch(player)
	{
	case 0:
		{
		for (int u=0;u<16;u++)
		   {
		   view.add(alphas[u]);
		   }
	        for (int x=0; x<16; x++)
			{
			view.add(nums[(15-x)]);
			for (int y=0; y<16; y++)
				{
				view.add(xcb[x][y].create());
				}
			}
		break;
		}

	case 1:{
		for (int u=15; u>-1; u--)
			{
			view.add(nums[u]);
			}
		for (int y=15; y>-1;y--)
			{
			view.add(alphas[y]);
			for(int x=0; x<16; x++)
				{
				view.add(xcb[x][y].create());
				}
			}
		break;
		}

	case 2:{
		for (int u=15; u>-1; u--)
			{
			view.add(alphas[u]);
			}
		for (int x=15; x>-1; x--)
			{
			view.add(nums[(15-x)]);
			for (int y=15; y>-1; y--)
				{
				view.add(xcb[x][y].create());
				}
			}
		break;
		}
	case 3:{
		for (int u=0; u<16; u++)
			{
			view.add(nums[u]);
			}
		for (int y=0; y<16; y++)
			{
			view.add(alphas[y]);
			for (int x=15; x>-1;x--)
				{view.add(xcb[x][y].create());
				}
			}
		break;
		}
				


	default: {break;}
	}

return view;
}

public static void main(String[] args)
{
Frame[] f=new Frame[4];
for (int i=0; i<f.length;i++)
	{
	f[i]=new Frame("Color:"+getPlayerLabel(i));
	f[i].setLayout(new BorderLayout());
	xChessBox[][] boxes = create_board();
	f[i].add(getView(boxes,i),BorderLayout.CENTER);
	f[i].pack();
	f[i].addWindowListener(new WindowAdapter()
				{public void windowClosing(WindowEvent we){System.exit(0);}
				});
	f[i].setVisible(true);
	}

}
	
public static String getPlayerLabel(int i)
{
 if (i == B) {return "Black";}
 if (i == W) {return "White";}
 if (i == G) {return "Green";}
 if (i == R) {return "Red";}
 return "unknown";
}

}
