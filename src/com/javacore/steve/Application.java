package com.javacore.steve;

import com.javacore.steve.command.ACommand;
import com.javacore.steve.command.CommandRegistry;
import com.javacore.steve.common.Canvas;
import com.javacore.steve.common.ConsoleCanvas;
import com.javacore.steve.db.Query;
import com.javacore.steve.db.Record;
import com.javacore.steve.db.Table;
import com.javacore.steve.db.DataBase;
import com.javacore.steve.profile.ProfileController;
import com.javacore.steve.profile.ProfileModel;
import com.javacore.steve.profile.ProfileView;
import com.javacore.steve.state.ApplicationState;
import com.javacore.steve.state.StateIdle;
import com.javacore.steve.state.StateExecutingCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    static public final String APP_NAME = "steve";
    static public final String AUTHOR = "Karim Magomedov";
    static public final String VERSION = "0.0.0";

    static ApplicationState currentState;

    public static void main(String[] args) {

    }

    public static void changeState (ApplicationState newState, String commandName){
        if (currentState != null){
            currentState.exit();
        }

        currentState = newState;
        currentState.enter(commandName);

    }
}
