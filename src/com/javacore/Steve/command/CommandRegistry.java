package com.javacore.Steve.command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This my enum class for checking the registry
 */
public enum CommandRegistry {
    INSTANCE;
    /**
     * Created Map for making the list of commands
     */
   static Map<String, ACommand> commands;

    static{
        commands = new HashMap<>();
        CommandAuthor commandAuthor = new CommandAuthor(("author"));
        commands.put("version", new CommandVersion("version"));
        commands.put("author", commandAuthor);
        commands.put("creator", commandAuthor);
        commands.put("father", commandAuthor);
    }
    /**
     * Method for checking presence of commands
     * @param name the name of command
     * @return presence of command (true or false)
     */
    public boolean hasCommands(String name){
        return commands.containsKey(name);
    }
    /**
     * Method for get command from the list
     * @param name the key of map
     * @return get element with the introduced key
     */
    public ACommand getCommand(String name){
        return  commands.get(name);
    }
    /**
     * Method for print the list of commands in the Map
     */
    public void listCommands(){
        Iterator it = commands.entrySet().iterator();
        System.out.println("List of commands");
        while (((Iterator) it).hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            System.out.println(entry.getKey());
        }
    }
}
