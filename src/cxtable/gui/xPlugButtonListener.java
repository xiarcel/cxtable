package cxtable.gui;

import cxtable.*;
import cxtable.core_comm.*;


import java.awt.*;
import java.awt.event.*;

 public class xPlugButtonListener implements  ActionListener{

private String name; private xRegistry serv,client;
private xReadSorter sort;

public xPlugButtonListener(Button b,String n, xRegistry s, xRegistry c, xReadSorter x)
{
sort=x;
name=n; serv=s; client=c;
b.addActionListener(this);
}

public void actionPerformed(ActionEvent ae)
{
new Thread(new xPlugPopup(serv,client,name,sort)).start();
}

}
