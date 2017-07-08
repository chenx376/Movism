package me.chenyongrui.movism.ui.activity.profile;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;

@ActivityScope
@Subcomponent(modules = {ProfileModule.class})
public interface ProfileComponent {
    void inject(ProfileActivity activity);

}
