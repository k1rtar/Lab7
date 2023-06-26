package com.kirtar.lab_7;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;

import com.kirtar.lab_7.commands.*;
import com.kirtar.lab_7.commands.concrete.*;
import com.kirtar.lab_7.database.Database;
import com.kirtar.lab_7.iomanagers.*;
import com.kirtar.lab_7.messages.*;
import com.kirtar.lab_7.models.*;

import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Date;
import java.util.HashSet;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.*;

/**
 * The main class through which the entire program is launched
 */


public class Main {
    public static Date date = new Date();
    private static int port = 8888;
    //private static final Object lock = new Object();
    private static Set<String> session = new HashSet<>();

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("Unable to parse port from command line arguments, standard port is used");
            }
        }

        Queue<Flat> collection = new PriorityQueue<>(port, null);
        try {
            collection = DBReader.loadDataFromDBtoCollection(Database.getDataBaseConnection());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File upload error! Check if the specified path to the file is correct (the path must be absolute)");
        }
        Receiver receiver = new Receiver(collection);
        RequestToCommand rqstToCmd = new RequestToCommand(receiver);
        ServerSocketChannel serverSocketChannel;

        Thread serverInput = new ServerInput(receiver, collection);
        serverInput.start();
        LinkedList<String> lastCommands = new LinkedList<>();

        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println("Server started on port: " + port);

            while (true) {
                try {
                    SocketChannel clientChannel = serverSocketChannel.accept();
                    System.out.println("Received a new request from the machine " + clientChannel.getRemoteAddress());
                    // Reading requests
                    CompletableFuture<ClientRequest<?>> readingTask = CompletableFuture.supplyAsync(() -> {
                        try {
                            ObjectInputStream ios = new ObjectInputStream(clientChannel.socket().getInputStream());
                            ClientRequest<?> request = (ClientRequest<?>) ios.readObject();
                            System.out.println("Request: " + request.getCommandType());
                            return request;
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error reading request!");
                        }
                        return null;
                    }, forkJoinPool);

                    readingTask.thenApplyAsync(request -> {
                        // Processing requests
                        AtomicReference<ServerResponse> resultRef = new AtomicReference<>(new ServerResponse
                        (ResultStatus.ERROR, "Command missing, request failed"));
                        AtomicReference<Command> command = new AtomicReference<>(null);
                        AtomicReference<RequestToCommand> reqToCmd = new AtomicReference<>(rqstToCmd);
                        AtomicReference<ClientRequest<?>> req = new AtomicReference<>(request);
                        System.out.println("Processing request ...");
                        //
                            command.set(reqToCmd.get().requestToCommand(req.get()));
                            System.out.println(command.get());
                            // Регистрация/авторизация пользователя
                            if ((command.get() instanceof RegisterUserCommand || command.get() instanceof LoginUserCommand) 
                            && command.get() != null) {
                                resultRef.set(command.get().execute());
                                if (resultRef.get().getResultStatus() == ResultStatus.OK) {
                                    session.add(command.get().getUser());
                                    System.out.println("Execution result: successful");
                                } else {
                                    System.out.println("Execution result: failed");
                                }
                                System.out.println();
                            } else if (!session.contains(command.get().getUser())) {
                                System.out.println(command.get().getUser());
                                for (String el : session) {
                                    System.out.println(el);
                                }

                                System.out.println("Access error! Reconnect to the server and try logging in again. Log in or create a new account");
                                resultRef.set(new ServerResponse(ResultStatus.ERROR, "Access error! Reconnect to the server and try logging in again. Log in or create a new account"));
                            } else {
                                if (req.get().getCommandType().equals("history")) {
                                    command.set(new HistoryCommand(receiver, lastCommands));
                                }

                                if (command.get() != null) {
                                    {
                                        lastCommands.add(req.get().getCommandType());
                                        resultRef.set(command.get().execute());
                                        if (resultRef.get().getResultStatus() == ResultStatus.OK) {
                                            System.out.println("Execution result: successful");
                                        } else {
                                            System.out.println("Execution result: failed");
                                        }
                                        System.out.println();
                                    }
                                }
                            }
                            sendResponse(clientChannel, resultRef.get());
                        //}
                        return null;
                    }, executorService);

                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            }
        } catch (IOException exc) {
            System.out.println("Server startup error! Most likely the port is busy!");
        }
    }

    // Logic for sending a response to the client
    private static void sendResponse(SocketChannel clientChannel, ServerResponse response) {
        Thread sendThread = new Thread(() -> {
            try {
                OutputStream os = clientChannel.socket().getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                System.out.println(response.getResultStatus());
                oos.writeObject(response);
                clientChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendThread.start();
    }
}
