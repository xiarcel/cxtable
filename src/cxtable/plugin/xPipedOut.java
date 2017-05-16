package cxtable.plugin;
import cxtable.core_comm.xListener;


/*used to route outs to the ins of other listeners...  used
to test the xChessGamex away from the xTable*/


 public class xPipedOut implements xOutListen{

private xListener xl;


public xPipedOut(xListener x)
{
xl=x;
}

public void send(String s)
{
xl.read(s);
}

}
