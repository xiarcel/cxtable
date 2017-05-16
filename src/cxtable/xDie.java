package cxtable;

/*this class represents a dice button that handles rolling the rand() and
processing from the panel how many it is to roll... the Dice plugin is
a global p2p...that is..it does not connect 1-to-1..but rather.. many-to-1 and
1-to-many... broadcasting its results that any running the Plugin can 
process them...and processing the results from other DIce plugins.
It is standard in the current xCommShell*/


   import java.awt.*;
   import java.awt.event.*;

    public class xDie implements ActionListener{
   
      private String name; private int min,max,rl;
      private xDiceListener xdl;
      private Button dice;
   
      public xDie(int mn, int mx, String n, xDiceListener x)
      {
         xdl = x; name =n; min =mn; max=mx;
      }
   
      public Button create()
      {
         dice = new Button(name);
         dice.addActionListener(this);
      /*for example: max =18, min =3.  rl = 15, then rl = 16.  Random will return 0-15 + 3
      or 3-18*/
      
         rl = max-min;
         rl++;
         return dice;
      }
   
      public void actionPerformed(ActionEvent ae)
      {
         int num = xdl.num();
         String s = "";
         if (num > 1) {s="s";}
      
         String rllstring = "Rolling "+name+" "+num+" time"+s+"\n\t{{";
      
         for (int x =0; x<num; x++)
         {
            String cm = ","; 
            if (x==0) {cm="";}
            int z = (int)(Math.random()*rl);
            z=z+min;
            rllstring=rllstring+cm+z;
         }
         rllstring = rllstring+"}}";
         xdl.roll(rllstring);
      
      /*done actionPerformed*/
      }
   
   /*done*/
   }
