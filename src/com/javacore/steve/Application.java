package com.javacore.Steve;

import com.javacore.Steve.command.ACommand;
import com.javacore.Steve.command.CommandRegistry;

public class Application {

    static public final String APP_NAME = "Steve";
    static public final String AUTHOR = "Karim Magomedov";
    static public final String VERSION = "0.0.0";

    public static void main(String[] args) {

        String commandName = "version";
        ACommand command = CommandRegistry.INSTANCE.getCommand(commandName);
        ((ACommand) command).execute();
        commandName = "author";
        command = CommandRegistry.INSTANCE.getCommand(commandName);
        ((ACommand) command).execute();
        CommandRegistry.INSTANCE.listCommands();
    }
}
