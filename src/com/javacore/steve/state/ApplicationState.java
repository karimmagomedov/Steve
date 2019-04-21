package com.javacore.steve.state;

import com.javacore.steve.command.ACommand;

public abstract class ApplicationState {

    public abstract void enter (String commandName);

    public abstract void onCommand(String commandName);

    public abstract void exit();

}
