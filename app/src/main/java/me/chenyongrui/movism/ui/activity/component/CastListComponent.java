package me.chenyongrui.movism.ui.activity.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.activity.CastListActivity;
import me.chenyongrui.movism.ui.activity.module.CastListModule;

@ActivityScope
@Subcomponent(modules = {CastListModule.class})
public interface CastListComponent {
    void inject(CastListActivity activity);

}
