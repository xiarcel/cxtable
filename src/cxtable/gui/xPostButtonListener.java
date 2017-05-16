package cxtable.gui;

import cxtable.core_comm.*;


import java.awt.*;
import java.awt.event.*;

 public class xPostButtonListener implements  ActionListener{

private String name;private xPanel one,two;
private xRegistry serv;
private TextArea ta;


public xPostButtonListener(Button b,String n, xRegistry s,xPanel o, xPanel t, TextArea tta)
{;
name=n; serv=s;
b.addActionListener(this);
one=o; two=t;
ta=tta;
}

public void actionPerformed(ActionEvent ae)
{
new Thread(new xPostPopup(serv,ta.getText(),name,one,two)).start();
ta.setText("");
}

}
