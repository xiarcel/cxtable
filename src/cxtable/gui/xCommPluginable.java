package cxtable.gui;

import cxtable.registry.xRegDisplay;

/*this interface requires knowing what features xLinkableComm provides..
as well as what to do w/ an xPanel or xRegDisplay...  A "dynamic"
setup-file reading class needs to be written to take data stored in
a file and convert it to an xCommPluginable object...

An implementer CAN choose to ignore the xPanel (reporter) and
the xRegDisplay....  Any "pluginables" or "xPanels" (xReadDeposits)
 can be added to xLinkableComm's "readsorter"..with the link_reader(Object)
call..however...

Any "globally" functional plugins (such as xDicePanel) should
be created from within the xLinkableComm with a call to:
create_plugin(String <classname>, true, true);

*/



public interface xCommPluginable extends Runnable{

public void setLinkable(xLinkableComm xlc);
public void setNam(String n);
public void make_visible(boolean b);
public void setRegDisplay(xRegDisplay xrd);
public void setReport(xPanel x);


}
