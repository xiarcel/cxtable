package cxtable.peer;
import cxtable.core_comm.xClientConn;

/*the implementer is listening for a peer switch-state*/


public interface xRemCommandListener{

public void switch_state(xClientConn x);
}
