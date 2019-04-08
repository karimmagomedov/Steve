package com.javacore.Steve.command;

import com.javacore.Steve.Application;

public class CommandVersion extends ACommand {

    public CommandVersion(String name){
        super(name);
    }

    /**
     * Ovveride method execute for print the version
     */
    @Override
    public void execute(){
        System.out.println("My version is: " + Application.VERSION);
    }
}
