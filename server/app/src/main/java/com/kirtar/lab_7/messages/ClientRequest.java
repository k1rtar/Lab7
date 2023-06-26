package com.kirtar.lab_7.messages;

import java.io.Serializable;

/**
 * A class that makes a request to the server to execute a command with the specified arguments
 */
public class ClientRequest<T> implements Serializable{
     private String commandType;
     private T commandArgument;
     private String userInfo;

     public ClientRequest(String commandType, T commandArgument,String userInfo)
     {
          this.commandType = commandType;
          this.commandArgument = commandArgument;
          this.userInfo = userInfo;
     }
     public String getCommandType()
     {
          return commandType;
     }
     public T getCommandArgument()
     {
          return commandArgument;
     }
     public String getUserInfo()
     {
          return userInfo;
     }
     public void setUserInfo(String userInfo)
     {
          this.userInfo = userInfo;
     }
     public void setCommandType(String commandType)
     {
          this.commandType = commandType;
     }

     public void setCommandArgument(T commandArgument)
     {
          this.commandArgument = commandArgument;
     }

}
