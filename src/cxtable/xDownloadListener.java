package cxtable;

/*this class handles being notified either of a successful download
via the currently not implemented file-sharing thing...  or a failed one*/


public interface xDownloadListener{

public void process(String file, String contents);
public void dl_error(String file, Exception e);
}
