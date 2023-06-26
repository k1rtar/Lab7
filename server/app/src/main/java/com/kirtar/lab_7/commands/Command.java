package com.kirtar.lab_7.commands;
import com.kirtar.lab_7.messages.*;
/**
 * An abstract command with a single method
 */
public interface Command {
     ServerResponse execute();

    void setArg(Object commandArgument);
    void setUser(String user);
    String getUser();
}
