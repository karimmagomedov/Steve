package com.javacore.steve.command;

import com.javacore.steve.Application;

public class CommandAuthor extends ACommand {

    public CommandAuthor(String name){
        super(name);
    }
    /**
     * Ovveride method execute for print the name
     */
    @Override
    public void execute(){
        System.out.println("My author's name is: " + Application.AUTHOR);
    }
}
