package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.CastListModule;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.view.activities.CastListActivity;

@ActivityScope
@Subcomponent(modules = {CastListModule.class})
public interface CastListComponent {
    void inject(CastListActivity activity);

}
