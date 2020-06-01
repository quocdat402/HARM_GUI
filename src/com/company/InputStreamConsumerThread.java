package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamConsumerThread extends Thread
{
  private InputStream is;
  private boolean sysout;
  private StringBuilder output = new StringBuilder();

  
  /**
   * Returns output from python code in the System
   * */
  public InputStreamConsumerThread (InputStream is, boolean sysout)
  {
    this.is=is;
    this.sysout=sysout;
  }

  public void run()
  {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(is)))
    {
      for (String line = br.readLine(); line != null; line = br.readLine())
      {
        if (sysout)
          System.out.println(line);    
        output.append(line).append("\n");
      }
    } catch (IOException e) {
    	
		e.printStackTrace();
	}
  }
  public String getOutput(){
    return output.toString();
  }
}