package cxtable.comm;
import java.net.*;

public interface CommandServerListener
{
	public void process(Socket s);
	public void add(CommandServerListener csl);
}

