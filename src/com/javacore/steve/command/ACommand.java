package com.javacore.steve.command;

/**
 * This my abstract ACommand class from which will be extended another command classes
 */
public abstract class ACommand {
    protected String name;
    protected String description;

    /**
     * Created the constructor for getting a command
     * @param name the name of command
     */
    public ACommand(String name){
        this.name = name;
    }
    /**
     * Method to execute the command
     */
    public void execute(){
        System.out.println("Command \""+name+"\" not supported");
    }
}
