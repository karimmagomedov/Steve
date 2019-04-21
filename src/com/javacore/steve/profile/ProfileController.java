package com.javacore.steve.profile;

import com.javacore.steve.common.ConsoleCanvas;

public class ProfileController {

    private ProfileModel profileModel;
    private ProfileView view;
    private ProfileStore store;
    private ConsoleCanvas canvas;

    {
        store.INSTANCE.loadData();
        view = new ProfileView();
    }

    public void showProfile(int id){
        ProfileModel model = store.INSTANCE.getProfile(id);
        if (model == null){
            System.out.println("No record found with id : " + id);
        }else{
            view.setModel(model);
            view.draw(canvas);
        }

//        model.setActive(true);
//        model.setId(1);
//        model.setFirstName("Steve");
//        model.setLastName("Balmer");
//        view.setModel(model);
//        view.draw();
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public ProfileView getView() {
        return view;
    }

    public void setView(ProfileView view) {
        this.view = view;
    }
}
