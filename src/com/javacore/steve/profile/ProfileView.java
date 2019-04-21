package com.javacore.steve.profile;

import com.javacore.steve.common.BaseView;
import com.javacore.steve.common.Canvas;

public class ProfileView extends BaseView {


    @Override
    public void draw(Canvas canvas){
        canvas.drawSquare(10);
        canvas.drawText("Criminal profile view");
        canvas.drawText("ID: " + ((ProfileModel)model).getId());
        canvas.drawText("Name: " + ((ProfileModel)model).getName());
        canvas.drawText("Active: " + ((ProfileModel)model).isActive());
    }
}
