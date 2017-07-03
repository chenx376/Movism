package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.CastListModule;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.activities.CastListActivity;

@ActivityScope
@Subcomponent(modules = {CastListModule.class})
public interface CastListComponent {
    void inject(CastListActivity activity);

}
