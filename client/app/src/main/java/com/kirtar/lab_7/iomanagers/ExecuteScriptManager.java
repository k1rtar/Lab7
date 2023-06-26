package com.kirtar.lab_7.iomanagers;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class ExecuteScriptManager {
    public void execute_script(String filename, InputConsoleManager runner)
    {
        try{
            Scanner scanner = new Scanner(new File(filename));
            
            runner.run(scanner);
            System.out.println("EXECUTE_SCRIPT");
            }
            catch (FileNotFoundException e) {
                System.out.println("Error! File("+filename+") not found");
        }
    }
    
}
