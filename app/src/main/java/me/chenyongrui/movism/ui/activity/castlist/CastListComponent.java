package me.chenyongrui.movism.ui.activity.castlist;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;

@ActivityScope
@Subcomponent(modules = {CastListModule.class})
public interface CastListComponent {
    void inject(CastListActivity activity);

}
