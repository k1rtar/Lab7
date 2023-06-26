package com.kirtar.lab_7.messages;

import java.io.Serializable;

/**
 * A class that returns a response from the server to the client
 */
public class ServerResponse implements Serializable{
    private ResultStatus status;
    private String message;
    public ServerResponse(ResultStatus status,String message)
    {
        this.status = status;
        this.message = message;
    }
    public ResultStatus getResultStatus()
    {
         return status;
    }
    public String getMessage()
    {
         return message;
    }

    public void setResultStatus(ResultStatus status)
    {
         this.status = status;
    }

    public void setMessage(String message)
    {
         this.message = message;
    }
}

