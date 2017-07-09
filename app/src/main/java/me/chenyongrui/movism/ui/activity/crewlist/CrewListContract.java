package me.chenyongrui.movism.ui.activity.crewlist;

import java.util.List;

import me.chenyongrui.movism.data.api.model.tmdb.Crew;

public interface CrewListContract {
    interface View {
        void showCrewListData(List<Crew> crewList);

        void setCrewListAdapter();
    }

    interface Presenter {
        void clearSubscription();

        void presentCrewListData(int movieID);
    }
}
