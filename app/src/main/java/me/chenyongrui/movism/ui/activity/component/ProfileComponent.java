package me.chenyongrui.movism.ui.activity.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.activity.ProfileActivity;
import me.chenyongrui.movism.ui.activity.module.ProfileModule;

@ActivityScope
@Subcomponent(modules = {ProfileModule.class})
public interface ProfileComponent {
    void inject(ProfileActivity activity);

}
