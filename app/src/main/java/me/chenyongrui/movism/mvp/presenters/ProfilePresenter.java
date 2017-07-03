package me.chenyongrui.movism.mvp.presenters;


public interface ProfilePresenter {

    void unsubscribeRx();

    void presentProfiletData(int profileID);

    void presentCastCreditsData(int profileID);

    void presentCrewCreditsData(int profileID);
}
