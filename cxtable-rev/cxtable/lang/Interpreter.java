package cxtable.lang;

import java.lang.reflect.*;
import java.util.*;

public class Interpreter extends Thread{
	
	private HashMap string_literals=new HashMap();
	private String source;
	
	public Interpreter (String source)
	{
		this.source=source;
	}
	
	public void run()
	{
		System.out.println(preprocessSource(source,string_literals));
		
	}
	
	public String preprocessSource(String src, HashMap hash)
	{
		src=pullStringLiterals(src,hash);
		Statement[] statements=getStatements(src);
		return src;
		
		
	}
	
	private String pullStringLiterals(String src, HashMap hash)
	{
		
		StringBuffer token=new StringBuffer();
		StringBuffer replace=new StringBuffer();
		int literal_id=0;
		boolean inside_literal=false;
		char last_char='\0';
		
		for (int i=0; i<src.length();i++)
		{
			char c = src.charAt(i);
			
			if (inside_literal && (c=='\\'))
			{
				last_char=c;
				continue;
			}
			
			if (inside_literal && c != '\"' )
			{
				
				token.append(c);
				last_char=c;
				continue;
			}
			
			if (last_char=='\\')
			{
				
				c=fixEscapeChar(c);
				if (inside_literal)
				{
				token.append(c);
				}
				else {replace.append(c);}
				
				last_char='\0';
				continue;
			}
			
			if (inside_literal && c == '\"')
			{	
				inside_literal=false;
				String label="$_"+literal_id+"_$";
				literal_id++;
				hash.put(label,token.toString());
				token.setLength(0);
				replace.append(label);
				continue;
			}
			if (!inside_literal && c == '\"')
			{
				last_char='\0';
				inside_literal=true;
				continue;
			}
			replace.append(c);
			last_char=c;
		}
		String m=token.toString();
		if (!m.equals("")) {
			String label="$_"+literal_id+"_$";
			hash.put(label,m);
			token.setLength(0);
		}
		
		return replace.toString();
	}
	
	private char fixEscapeChar(char e)
	{
		//other logic for special case..
		return e;
		
	}
		
		
	
	public Statement[] getStatements(String source)
	{
	return null;
	}
	
	public static void main(String[] args) throws Exception
	{
		new Interpreter(args[0]).start();
	}
	
}

