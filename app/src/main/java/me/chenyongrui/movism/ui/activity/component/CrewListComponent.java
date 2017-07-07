package me.chenyongrui.movism.ui.activity.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.activity.CrewListActivity;
import me.chenyongrui.movism.ui.activity.module.CrewListModule;

@ActivityScope
@Subcomponent(modules = {CrewListModule.class})
public interface CrewListComponent {
    void inject(CrewListActivity activity);

}
