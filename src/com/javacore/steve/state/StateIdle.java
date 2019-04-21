package com.javacore.steve.state;

public class StateIdle extends ApplicationState{

    @Override
    public void enter (String commandName){
        System.out.println("Entering idle state");
    }

    @Override
    public void onCommand(String commandName){
        System.out.println("Not command received, starting execution..." + commandName);
    }

    @Override
    public void exit(){
        System.out.println("Leaving state executing command");
    }

}
