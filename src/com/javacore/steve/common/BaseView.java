package com.javacore.steve.common;

public abstract class BaseView {

    protected BaseModel model;

    public void draw() {
        System.out.println("Drawing should be implemented...");
    }
    public void draw(Canvas canvas){
        System.out.println("Drawing on canvas should be implemented...");
    }

    public void setModel(BaseModel model){
        this.model = model;
    }
}
