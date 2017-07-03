package me.chenyongrui.movism.mvp.views;

import java.util.List;

import me.chenyongrui.movism.mvp.model.tmdb.CastMovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.CrewMovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;

public interface ProfileView {

    void showProfileData(Profile profile);

    void showCastCreditsData(List<CastMovieCredits> castMovieCredits);

    void showCrewCreditsData(List<CrewMovieCredits> crewMovieCredits);
}
