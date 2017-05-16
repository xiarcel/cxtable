package cxtable.gui;
import cxtable.peer.xMssgListen;


/*this class is handed an xMssgListen object...and it then calls the 
post() method or appende() method*/

public interface xPostDispatch{
public void setMssgListen(xMssgListen xml);
}
