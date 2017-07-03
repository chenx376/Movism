package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.ProfileModule;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.activities.ProfileActivity;

@ActivityScope
@Subcomponent(modules = {ProfileModule.class})
public interface ProfileComponent {
    void inject(ProfileActivity activity);

}
