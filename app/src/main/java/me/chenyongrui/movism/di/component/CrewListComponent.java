package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.CrewListModule;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.activities.CrewListActivity;

@ActivityScope
@Subcomponent(modules = {CrewListModule.class})
public interface CrewListComponent {
    void inject(CrewListActivity activity);

}
