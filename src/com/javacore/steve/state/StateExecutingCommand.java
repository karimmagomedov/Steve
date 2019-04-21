package com.javacore.steve.state;

import com.javacore.steve.Application;

public class StateExecutingCommand extends ApplicationState {

    @Override
    public void enter (String commandName){
        System.out.println("Entering State Executing command..." + commandName);
    }

    private void executeCommand(String commandName){
        //Code for execution
        Application.changeState(new StateIdle(),"idle");
    }

    @Override
    public void onCommand(String commandName){
        System.out.println("Busy executing command, your new command : " + commandName + "has to wait");
    }

    @Override
    public void exit(){
        System.out.println("Leaving state executing command");
    }
}
