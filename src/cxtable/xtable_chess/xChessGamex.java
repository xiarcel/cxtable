/*this class is the main pluginable class for the Chess Game... it handles
all operations in/out...as well as calling of methods in the other chess 
classes, ie. Checkmate check, Check check...move validation*/

package cxtable.xtable_chess;

   import java.awt.*;
   import java.awt.event.*;
   import java.util.Vector;
	import cxtable.plugin.*;
	import cxtable.*;
	import cxtable.peer.*;


   public class xChessGamex extends Thread implements ActionListener,xPluginable, xChessMoveListener{
   
      private Label[] horizontal;
	public static boolean debug = true;
      private Label whatever;
      private String key="ChessPlug";
      private String skey="<ChessPlug>";
      private String ekey="</ChessPlug>";
      private xChessMove xcm;
      private xChessManager xchessman;
      private Panel entry,main;
      private TextField from,to;
      private xLineSplit xls= new xLineSplit();
      private Button move = new Button("Press to move");
   
      private boolean odd;
      private xChessBox[][] xcb;
      private boolean controlbool, player;
      private xChessValidator xcv;
      private String against ="";
      private xOutListen xol;
      private boolean networked = false;
   
      public xChessGamex()
      {whatever = new Label("The game starts with White");
         xcm = new xChessMove();
         entry = new Panel();
         from = new TextField(15);
         to = new TextField(15);
         horizontal = new Label[]{new Label("-A-"), new Label("-B-"), new Label("-C-"), new Label("-D-"), new Label("-E-"), new Label("-F-"), new Label("-G-"), new Label("-H-")};
         xcb = new xChessBox[8][8];
         xcv = new xChessValidator(xcb);
         xchessman = new xChessManager();
         main = new Panel();
         odd = true;
      }
   
   	/*These methods will be the methods for the xPluginable*/
      public String getKey(){
         return key;}
   
      public boolean requireName()
      {
         return false;}
      public void setNam(String s){
      }
      public void setKey(String s){
         key=s;
         skey="<"+key+">";
         ekey="</"+key+">";
      }
      public String who(){
         return "xChessGamex";}
      public void append(String[] s)
      {
         if (s.length<1){
            return;}
         if (s[0]==null){System.out.println("In xChess, null s[0]");
            return;}
         for (int i=0; i<s.length;i++)
         {
         
            read(s[i]);
         }
      }
      public boolean setVars(String s)
      {
         String[] vars = xls.split("PLUG",s);
         if (vars.length < 1) {
            return false;}
         String[] pss = xls.split("PASSIVE",vars[0]);
         String bool="";
         if (pss.length <1 )
         {bool=xls.ssplit("RSIDE",vars[0]);}
         else {bool=xls.ssplit("LSIDE",vars[0]);}
         if (bool.equals("")) {
            return false;}
         String[] nms = xls.split("NAME",vars[0]);
         if(nms.length > 0){against=nms[0];}
         else {against="Anonymous";}
      
         if (bool.equals("WHITE")|bool.equals("TRUE"))
         {player=true;}
         if (bool.equals("BLACK")|bool.equals("FALSE"))
         {player=false;}
         else {
            return false;}
         return true;
      }
   
      public void setOut(xOutListen x)
      {xol=x;}
   
      public void setNetworked(boolean b)
      {
         networked=b;
      }
   
      public Panel as_panel()
      {Panel outer=new Panel();
         outer.setLayout(new BorderLayout());
         outer.add(main,BorderLayout.SOUTH);
         outer.add(entry,BorderLayout.NORTH);
         return outer;}
   
      public Frame as_frame()
      {
         Frame f=new Frame("Play versus "+against);
         f.setLayout(new BorderLayout());
         f.add(main,BorderLayout.SOUTH);
         f.add(entry,BorderLayout.NORTH);
         f.pack();
         f.setVisible(true);
         return f;
      }
   
   
      public void read(String s)
      {
	System.out.println("In xChessGamex Read..");
         String[] mvs = xls.split("MOVE",s);
         if (mvs.length < 1) {
            System.out.println("No <MOVE>");
            return;}
         String[] _f = xls.split("FROM",mvs[0]);
         String[] _t = xls.split("TO",mvs[0]);
         String[] _s = xls.split("SIDE",mvs[0]);
         if ((_s.length < 1) | (_f.length < 1)| (_t.length < 1))
         {System.out.println(_f.length+":"+_t.length+":"+_s.length);
            return;}
         boolean it;
         _s[0]=_s[0].trim();
         if ((_s[0].equals("BLACK"))|(_s[0].equals("FALSE")))
         {it = false;}
         else if ((_s[0].equals("WHITE"))|(_s[0].equals("TRUE")))
         {it = true;}
         else{
            System.out.println("Returning from method, _s[0] == "+_s[0]);
            return;}
         System.out.println(player+" processing move");
         processMove(_f[0],_t[0],it,false);
      }
   
      public void launch(xPlugDataListener x)
      {new Thread(new xChessLauncher(x)).start();}
   
   	/*end of xPluginable interface*/
   
      public void run()
      {
         entry.setLayout(new GridLayout(3,2));
         entry.add(whatever);
         entry.add(new Label(""));      
         entry.add(from);
         entry.add(to);
         entry.add(move);   
         move.addActionListener(this);
         main.setLayout(new GridLayout(9,9));
      
         for (int x=0; x<8; x++)
         {
            odd = !odd;
            for (int y=0; y<8; y++)
            {xcb[x][y] = new xChessBox(odd,x,y,this);
               odd=!odd;}
         }
         xcb[0][0].occupy(new xRook(2,0,0)); xcb[0][7].occupy(new xRook(2,0,7));
         xcb[0][1].occupy(new xKnight(2,0,1)); xcb[0][6].occupy(new xKnight(2,0,6));
         xcb[0][2].occupy(new xBishop(2,0,2)); xcb[0][5].occupy(new xBishop(2,0,5));
         xcb[0][4].occupy(new xKing(2,0,4)); xcb[0][3].occupy(new xQueen(2,0,3));
         for (int i=0; i<8; i++)
         {
            xcb[1][i].occupy(new xPawn(2,1,i));
            xcb[6][i].occupy(new xPawn(0,6,i));
         }
         xcb[7][0].occupy(new xRook(0,7,0)); xcb[7][7].occupy(new xRook(0,7,7));
         xcb[7][1].occupy(new xKnight(0,7,1)); xcb[7][6].occupy(new xKnight(0,7,6));
         xcb[7][2].occupy(new xBishop(0,7,2)); xcb[7][5].occupy(new xBishop(0,7,5));
         xcb[7][4].occupy(new xKing(0,7,4)); xcb[7][3].occupy(new xQueen(0,7,3));
         for (int v=2; v<6; v++)
         {
            for (int u=0; u<8; u++)
            {xcb[v][u].occupy(new xEmptyPiece());}
         }
      
         main.add(new Label("   "));
         if (player == true)
         {
            for (int z=0; z<8; z++)
            {main.add(horizontal[z]);
            }
            Panel[] nums = new Panel[8];
         
            for (int i=0; i<8; i++)
            {
               nums[(7-i)]= new Panel();
               nums[(7-i)].setLayout(new BorderLayout());
               nums[(7-i)].add(new Label(""+(7-i+1)), BorderLayout.EAST);
               main.add(nums[7-i]);
               for (int j=0; j<8; j++)
               {main.add(xcb[i][j].create());}
            }
         }
         else {
            for (int z=7; z>-1; z--)
            {main.add(horizontal[z]);
            }
            Panel[] nums = new Panel[8];
         
            for (int i=7; i>-1; i--)
            {
               nums[(7-i)]= new Panel();
               nums[(7-i)].setLayout(new BorderLayout());
               nums[(7-i)].add(new Label(""+(8-i)), BorderLayout.EAST);
               main.add(nums[7-i]);
               for (int j=7; j>-1; j--)
               {main.add(xcb[i][j].create());}
            }
         }
      
      
      
         controlbool=true;
         if(controlbool == player)
         {move.setEnabled(true);}
         else{move.setEnabled(false);}
      
      }
   
      public void actionPerformed(ActionEvent ae)
      {String _f = from.getText();
         from.setText("");
         String _t = to.getText();
         to.setText("");
         move.setEnabled(false);
         processMove(_f,_t,controlbool,true);
      }
   
   
      public void processMove(String fm,String t,boolean who, boolean local)
      
      {	
         if (who != controlbool)
         {System.out.println("moves did not line up!");
            return;}
      
         int fx,tx,fy,ty;
         if ((fm.length() != 2)| (t.length() !=2))
         {whatever.setText("Invalid move!"); move.setEnabled(true);
         
            return;}                                       
         fy = xcm.process(fm.charAt(0));                                       
         ty = xcm.process(t.charAt(0));                     
         if (fy == -1 | ty == -1){                                          
            return;}
      
         try{
         
            fx= Integer.parseInt(new String(""+fm.charAt(1)));                                          
            tx= Integer.parseInt(new String(""+t.charAt(1)));                                       
         }
         
            catch(Exception e)                                                                                        
            {
               return;}
         if (debug==true){System.out.println("Set fx and tx");}
         fx = 8-fx;
         tx = 8-tx;                                       
         if (fx<0 | fx> 7 | tx<0 | tx>7){
            return;}
	int controlint=-1; if (controlbool){controlint=0;}else{controlint=2;}

         if (xcb[fx][fy].getPiece().getSide() != controlint){whatever.setText("Not your piece!");move.setEnabled(true);
         
            return;}
         if (xcb[fx][fy].getPiece().getControl().equals("EM")==true) {whatever.setText("Illegal Move!!");move.setEnabled(true);
         if (debug==true){System.out.println("Can't move empty piece!");}
 
            return;}                                       
         if (xcb[tx][ty].getPiece().getControl().equals("EM")==false)
         {
            if (xcb[tx][ty].getPiece().getSide() == xcb[fx][fy].getPiece().getSide())
            {
	if (debug==true){System.out.println("Cannot move on top of your own");}
               whatever.setText("Illegal Move!!");
               move.setEnabled(true);
               return;}
         }
         Vector tp = xcb[fx][fy].getPiece().valid_spots();
         while (!tp.isEmpty())
         {xMove temp = (xMove)tp.elementAt(0);
            int e = temp.getx();
            int d = temp.gety();
            if ((tx == e)&(ty==d))
            {
               xPiece tempxp = xcb[fx][fy].getPiece();
               if (xcv.validate(fx,fy,tx,ty)==false)
               {
		if(debug==true){System.out.println("Invalidated");}
		whatever.setText("Illegal Move!!");
                  move.setEnabled(true);
                  return;}
            
               xChessBox[][] muty = xchessman.mutable(xcb);
               muty[tx][ty].occupy(tempxp);
               muty[fx][fy].occupy(new xEmptyPiece());
               tp.removeAllElements();
            	/*put the stuff to check for 'Check'*/
            
               xKingLocation[] xkl = xKingLocation.findKings(muty);
	boolean w_i_c=false;  boolean b_i_c=false;
               for (int q=0; q<xkl.length; q++)
	{
	if (xkl[q].getSide()==0)
	{w_i_c = xchessman.check(xkl[q].getx(),xkl[q].gety(),muty,0);}
	if (xkl[q].getSide()==2)
	{b_i_c = xchessman.check(xkl[q].getx(),xkl[q].gety(),muty,2);}
    	}
               if ((controlbool == true) & (w_i_c==true))
               {whatever.setText("Illegal Move::You would be in check");move.setEnabled(true);
               
                  return;}
               if ((controlbool == false) & (b_i_c == true))
               {whatever.setText("Illegal Move::You would be in check");move.setEnabled(true);
               
                  return;}
               String chkstring = new String("");
               if ((controlbool==true) & (b_i_c == true))
               {chkstring=chkstring+", Black in Check!";
               }
               if ((controlbool==false) & (w_i_c == true))
               {chkstring=chkstring+", White in Check!";}
            
            	/*here..............................*/
            
               tempxp.move(tx,ty);
               for (int ii=0; ii<8;ii++)
               {
                  for (int jj=0;jj<8;jj++)
                  {xPiece txp = muty[ii][jj].getPiece();
                     xcb[ii][jj].occupy(txp);
                     txp.move(ii,jj);
	   txp.ded(1);
                  }
               }
            	/*This is the call to xxxmate (Check | Stale)Mate checker*/
               int cont_int;
	if (controlbool)
	     {cont_int=2;}
	     else{cont_int=0;}

               if (xchessman.xxxmate(xcb,cont_int) == true)
               {
                  if ((b_i_c==true) | (w_i_c==true))
                  {
                     chkstring=chkstring+"<CHECKMATE>";
                  }
                  else {chkstring=chkstring+"<STALEMATE>";}
               }
            
               if (local == true)
               {	
		 if (debug==true){System.out.println("In local");}
                  String side="";
                  if (player==true){side="WHITE";}
                  else{side="BLACK";}
               
                  String mov = "<MSSG>"+skey+"<MOVE><SIDE>"+side+"</SIDE><FROM>"+fm+"</FROM>";
                  mov=mov+"<TO>"+t+"</TO></MOVE>"+ekey+"</MSSG>";
                  if(debug==true){System.out.println(mov);
                  System.out.println("Packed Move");}
               
                  try{xol.send(mov);}
                     catch(Exception ee){
                        System.out.println("A move send failed\nmove:\n"+mov);
                        ee.printStackTrace();}
               }
            
               controlbool = !controlbool;
               move.setEnabled((player==controlbool));
               whatever.setText("Move accepted"+chkstring);
            
               return;}
            tp.removeElementAt(0);
         }
         if(debug==true){System.out.println("Move not found");}
	move.setEnabled(true);
         whatever.setText("Illegal Move!!");
         return;                           
      }                                        
   
      public void select_piece(int x, int y)
      {
         String to_set = new String(""+xcm.proc(y)+x);
         int ftf = from.getText().length(); int ttf = to.getText().length();
         if ( ((ftf==0) & (ttf==0)) | ((ftf==0)&(ttf!=0)) ) 
         {
            from.setText(to_set);
         }
         if ( (ftf!=0) & (ttf==0))
         {
            to.setText(to_set);
         }
         if ((ftf !=0) & (ttf !=0))
         {
            from.setText(to_set); to.setText("");
         }
      
      
      
       /*put implementation here*/
      }
   
   
      public static void main(String[] args)
      {
         xChessGamex white = new xChessGamex();
         xChessGamex black = new xChessGamex();
         xPipedOut xpow = new xPipedOut(black);
         xPipedOut xpob = new xPipedOut(white);
         white.setOut(xpow);
         black.setOut(xpob);
         white.setVars("<PLUG><PASSIVE></PASSIVE><LSIDE>TRUE</LSIDE><NAME>Bob</NAME></PLUG>");
         black.setVars("<PLUG><RSIDE>FALSE</RSIDE><NAME>Raul</NAME></PLUG>");
         new Thread(white).start();
         new Thread(black).start();
         try{Thread.sleep(1000);}
            catch(Exception e){
            }
         white.as_frame().addWindowListener(
                                 new WindowAdapter()
                                 {
                                    public void windowClosing(WindowEvent we){
                                       System.exit(0);}});
      
         black.as_frame().addWindowListener(
                                 new WindowAdapter()
                                 {
                                    public void windowClosing(WindowEvent we){
                                       System.exit(0);}});
      
      
      }

/*new xListener methods*/
public boolean readAll()
{return true;}
public String[] readKeys()
{return new String[0];}

   }
