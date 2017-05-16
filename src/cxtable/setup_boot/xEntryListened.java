package cxtable.setup_boot;

/*interface for a data entry piece...when done..it gets the type..
Used most precisely with entry for the setup piece to create the setup files
*/


public interface xEntryListened{

public String[] getAnswers();
public void kill();
public int getWhich();
}
