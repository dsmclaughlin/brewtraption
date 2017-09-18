package org.brewtraption.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>Contains logic to run commands on the local machine. Command will block the thread until completion.</p>
 *
 * <p>Note that if you run a command that blocks forever, such as vim, then there is no time-out implemented at this level.</p>
 */
public class CommandRunner {

  private static final Logger logger = LogManager.getLogger(CommandRunner.class);

  private ProcessBuilder pb;

  public CommandRunner(final String ...command) {
    this.pb = new ProcessBuilder(command);
    logger.info("Creating command: " + commandAsString(command));
  }

  public Result runCommand() {
    logger.info(pb.toString());
    Process process = null;
    Result.Status status = Result.Status.FAILURE;

    try {
      process = pb.start();
      StreamGobbler gobbleStdOut = new StreamGobbler(process.getInputStream());
      StreamGobbler gobbleStdErr = new StreamGobbler(process.getErrorStream());
      gobbleStdOut.start();
      gobbleStdErr.start();
      int exitValue = process.waitFor();
      gobbleStdOut.join();
      gobbleStdErr.join();

      if (exitValue == 0) {
        logger.debug("Command completed successfully");
        status = Result.Status.SUCCESS;
      }
      Result result = new Result(status, "Exit value: " + exitValue);
      result.setStdErr(gobbleStdErr.getFullStreamContents());
      result.setStdOut(gobbleStdOut.getFullStreamContents());
      return result;
    } catch(IOException e) {
      String message = "IO problem encountered while running command " + pb.toString();
      logger.error(message,e);
      return new Result(status,message,e);
    } catch(InterruptedException e) {
      String message = "Interrupted during command";
      logger.error(message,e);
      return new Result(status, message,e);
    } finally {
      if(process!=null) {
        try {
          process.getOutputStream().close(); // Other streams were closed by the streamgobbler
        } catch (IOException e) {
          logger.warn("IO problem encountered when closing process output stream",e);
        }
        process.destroy();
      }
    }
  }

  private static class StreamGobbler extends Thread {
    private static final Logger logger = LogManager.getLogger(StreamGobbler.class);
    private InputStream is;
    private String fullStreamContents = "";

    private StreamGobbler(final InputStream is) {
      this.is = is;
    }

    @Override public void run() {
      BufferedReader read = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
      String line;
      try {
        StringBuilder sb = new StringBuilder();
        while((line=read.readLine())!=null) {
          sb.append(line).append(System.getProperty("line.separator"));
        }
        fullStreamContents = sb.toString();
      } catch (IOException e) {
        logger.error("IO problem encountered while gobbling stream",e);
      } finally {
        if(is!=null) {
          try {
            is.close();
          } catch (IOException e) {
            logger.warn("IO problem encountered while closing gobbled input stream",e);
          }
        }
      }
    }

    public String getFullStreamContents() {
      return fullStreamContents;
    }
  }

  private static String commandAsString(final String[] commandArray) {
    StringBuilder sb = new StringBuilder();
    for (String command : commandArray) {
      sb.append(command).append(" ");
    }
    return sb.toString();
  }

  public String toString() {
    List<String> commandList =  pb.command();
    String[] commandListArray = commandList.toArray(new String[commandList.size()]);
    return commandAsString(commandListArray).trim();
  }
}
