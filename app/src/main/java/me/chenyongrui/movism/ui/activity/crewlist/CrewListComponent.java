package me.chenyongrui.movism.ui.activity.crewlist;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;

@ActivityScope
@Subcomponent(modules = {CrewListModule.class})
public interface CrewListComponent {
    void inject(CrewListActivity activity);

}
