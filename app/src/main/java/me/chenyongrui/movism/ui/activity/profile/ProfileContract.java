package me.chenyongrui.movism.ui.activity.profile;

import java.util.List;

import me.chenyongrui.movism.data.api.model.tmdb.MovieCredits;
import me.chenyongrui.movism.data.api.model.tmdb.Profile;

public interface ProfileContract {
    interface View {
        String getRealPosterURL(String posterURL);

        void showProfileData(Profile profile);

        void showCreditsData(List<MovieCredits> credits);
    }

    interface Presenter {
        void clearSubscription();

        void presentProfileData(int profileID);

        void presentCastCreditsData(int profileID);

        void presentCrewCreditsData(int profileID);
    }
}
