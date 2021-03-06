package me.chenyongrui.movism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.BuildConfig;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.utils.Constant;


public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        SetupToolBar(toolbar, "Settings", null, true);


        getFragmentManager().beginTransaction().
                replace(R.id.settings_container,
                        new MyPreferenceFragment()).commit();

    }


    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            SwitchPreference imageQualityPref = (SwitchPreference) findPreference("image_quality");
            imageQualityPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SwitchPreference switchPreference = (SwitchPreference) preference;
                    if (!switchPreference.isChecked()) {
                        Constant.LIST_IMAGE_QUALITY = "w342";
                        Constant.DETAIL_IMAGE_QUALITY = "w780";
                        Constant.AVATAR_IMAGE_QUALITY = "w185";
                    } else {

                        Constant.LIST_IMAGE_QUALITY = "w185";
                        Constant.DETAIL_IMAGE_QUALITY = "w500";
                        Constant.AVATAR_IMAGE_QUALITY = "w92";
                    }
                    return true;
                }
            });

            CheckBoxPreference darkModePref = (CheckBoxPreference) findPreference("night_mode");
            darkModePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    boolean isNightMode = Boolean.valueOf(o.toString());
                    Constant.isNightMode = isNightMode;
                    if (isNightMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    getActivity().recreate();
                    return true;
                }
            });


            Preference license = findPreference("license");
            license.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), LicenseActivity.class));
                    return true;
                }
            });


            Preference about = findPreference("about");
            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), AboutActivity.class));
                    return true;
                }
            });

            Preference version = findPreference("version");
            version.setSummary(BuildConfig.VERSION_NAME);


        }


    }

}