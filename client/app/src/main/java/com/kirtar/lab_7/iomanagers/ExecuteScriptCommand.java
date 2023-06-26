package com.kirtar.lab_7.iomanagers;


import com.kirtar.lab_7.Main;

public class ExecuteScriptCommand {
    public static InputConsoleManager runner = Main.runner;
    private ExecuteScriptManager executeScriptManager;
    private String filename;
    public ExecuteScriptCommand(ExecuteScriptManager executeScriptManager,String filename)
    {
        this.executeScriptManager = executeScriptManager;
        this.filename = filename;
    }

    public void execute()
    {
        executeScriptManager.execute_script(filename,runner);
    }

}
