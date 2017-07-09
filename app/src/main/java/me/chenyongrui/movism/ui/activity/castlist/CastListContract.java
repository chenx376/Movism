package me.chenyongrui.movism.ui.activity.castlist;

import java.util.List;

import me.chenyongrui.movism.data.api.model.tmdb.Cast;

public interface CastListContract {
    interface View {
        void showCastListData(List<Cast> castList);

        void setCastListAdapter();
    }

    interface Presenter {
        void clearSubscription();

        void presentCastListData(int movieID);


    }
}
