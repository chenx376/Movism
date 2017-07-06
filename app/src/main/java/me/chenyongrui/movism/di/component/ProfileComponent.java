package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.ProfileModule;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.view.activities.ProfileActivity;

@ActivityScope
@Subcomponent(modules = {ProfileModule.class})
public interface ProfileComponent {
    void inject(ProfileActivity activity);

}
