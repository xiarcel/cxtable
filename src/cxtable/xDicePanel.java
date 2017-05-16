package cxtable;
   import java.awt.*;
   import java.awt.event.*;
	import cxtable.plugin.*;
	import cxtable.peer.*;



	/*this is an example of a multi-peer 'auto' plugin...the chat/game configs
	would specify a plugin, such as Dice.  It would choose separate frame 
	or inlaid panel, and it would call the plugin's appropriate methods.
	setVars will be called if the config file specifies..
	
	<PLUG>
		<NAME>xDicePanel</NAME>
		<VARS>...vars specified</VARS>
		<P2P>false</P2p>
		</PLUG>
	
	In the case of xDicePanel, it would be
	
	<PLUG><NAME>xDicePanel</NAME</PLUG>
	
	*/


    public class xDicePanel implements xDiceListener, xPluginable{
   
   
      private xDie[] die;
      private String key = "Dice_Roll";
      private String skey = "<Dice_Roll>";
      private String ekey = "</Dice_Roll>";
   	private Panel p = new Panel();
      private Panel upper = new Panel();
      private TextField nm = new TextField(5);
      private TextArea ta = new TextArea("",8,33,TextArea.SCROLLBARS_VERTICAL_ONLY);
      private xOutListen xol=null;
      private String name;
   
      public xDicePanel()
      {}
      public boolean setVars(String s){
         return true;}
   
      public void setOut(xOutListen x)
      {xol=x;}
      public String who(){
         return "xDicePanel";}
      public Panel as_panel()
      {
         return create();}
   
   
      public Frame as_frame()
      {
      /*this is likely not to be ever used, as the "Dice" by itself
       would not make much of a frame, mainly in here to conform with
       xPluginable's as_frame() requirement*/
      
         Frame f = new Frame("Dice module plugin");
         f.setLayout(new BorderLayout());
         f.add(create(),BorderLayout.CENTER);
         f.pack();
         return f;
      }
   
      public void read(String s)
      {
         ta.append(s+"\n");
      }

	public boolean readAll()
	{return true;}

	public String[] readKeys()
	{return new String[0];}

   
      public void launch(xPlugDataListener x)
      {
         x.reglaunch("");
      }
   
      public boolean requireName(){
         return true;}
      public void setNam(String s)
      {name=s;}
   
   
   
   
      public Panel create()
      {
         die = new xDie[]{new xDie(1,100,"%",this), new xDie(1,20,"D20",this), new xDie(3,18,"3D6",this), new xDie(1,16,"D16",this),
               new xDie(1,12,"D12",this),new xDie(1,10,"D10",this), new xDie(1,8,"D8",this), new xDie(1,6,"D6",this),
               new xDie(1,4,"D4",this), new xDie(1,3,"D3",this), new xDie(1,2,"D2",this)};
         upper.setLayout(new GridLayout(6,2));
         for (int i=0; i<die.length; i++)
         {
            upper.add(die[i].create());
         }
         upper.add(nm);
         p.setLayout(new BorderLayout());
         p.add(upper,BorderLayout.NORTH);
         p.add(ta,BorderLayout.SOUTH);
         p.setVisible(true);
         return p;
      }
   
      public String getKey()
      {
         return key;}
   
      public void setKey(String s)
      {
         key=s;
         skey="<"+key+">";
         ekey="</"+key+">";
      }
   
      public int num()
      {
         int x=1;
         try {x=Integer.parseInt(nm.getText());}
            catch(Exception e){
               x=1;}
         nm.setText("");
         return x;
      }
   
      public void roll(String s)
      {	String v ="{{"+name+"}}:"+s;
         ta.append(v+"\n");
         if (xol !=null)
         {		
         String tt_send = skey+v+ekey;
            xol.send(tt_send);
          }
      
      }
   
      public void append(String[] s)
      {
         for (int i=0; i<s.length;i++)
            {
         	int u = s[i].length();
            u--;
            if (s[i].charAt(u) == '\n')
            {ta.append(s[i]);}
            else{	
               ta.append(s[i]+"\n");
            	}
         }
      }
   
      public void run()
      {
         ta.append("Rolls of the dice:\n");
      }
   
   
      public static void main(String[] args)
      {
         Frame f=new Frame("Test of dice");
         f.setLayout(new BorderLayout());
         f.addWindowListener(
                               new WindowAdapter(){
                                  public void windowClosing(WindowEvent we){
                                     System.exit(0);}});
         xDicePanel xdp = new xDicePanel();
         f.add(xdp.create(),BorderLayout.CENTER);
         f.pack();
         f.setVisible(true);
      }
   
   /*altered 7-12-01 to remove 'chattiness', as a S.o.println for each
   and every dice roll (in fact, 3 of them) does NOTHING for debugging
   purposes at this stage
   
   	altered 7-16-01 to remove 'packing' and unpacking..
   	
   */
   }
