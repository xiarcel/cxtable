package cxtable.peer;
import cxtable.core_comm.xClientConn;

/*this class listens for a 'switch to client' when it implements this interface
It was a server side... this is part of the peer-enable thing*/

public interface xSwitchListener{

public void switch_to_client(xClientConn x);
}
