package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.CrewListModule;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.view.activities.CrewListActivity;

@ActivityScope
@Subcomponent(modules = {CrewListModule.class})
public interface CrewListComponent {
    void inject(CrewListActivity activity);

}
