package com.kirtar.lab_7.network;

import java.net.Socket;

import com.kirtar.lab_7.messages.ClientRequest;
import com.kirtar.lab_7.messages.ResultStatus;
import com.kirtar.lab_7.messages.ServerResponse;

import java.net.InetAddress;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class ServerConnection {
    private int port;
    public ServerConnection(int port)
    {
        this.port = port;
    }

    public ServerResponse processTheRequest(ClientRequest<?> request)
    {
        if (request==null){throw new IllegalArgumentException("Request cannot be null!");}
        InetAddress host; 
        try{
            host = InetAddress.getLocalHost();
            try (Socket socket = new Socket(host, port)) {
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(request);
                
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ServerResponse result = (ServerResponse) objectInputStream.readObject();
                return result;
            }
        }

        catch (Exception e)
        {
            //e.printStackTrace();
            return new ServerResponse(ResultStatus.OK, "Failed to connect to the server, the command was cancelled.");
        }
        

    }
    
}
