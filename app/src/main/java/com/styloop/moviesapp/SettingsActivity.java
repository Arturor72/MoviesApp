package com.styloop.moviesapp;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import java.util.List;


public class SettingsActivity extends PreferenceActivity  implements Preference.OnPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_order_by_key)));
    }

    public void bindPreferenceSummaryToValue(Preference preference){
        preference.setOnPreferenceChangeListener(this);
        Context contextPreference=preference.getContext();
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(contextPreference);
        Object value=sharedPreferences.getString(preference.getKey(), "");
        onPreferenceChange(preference,value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value=newValue.toString();
        if(preference instanceof  ListPreference){
            ListPreference listPreference=(ListPreference)preference;
            int index=listPreference.findIndexOfValue(value);
            if(index>=0){
                preference.setSummary(listPreference.getEntries()[index]);
            }
        }else{
            preference.setSummary(value);
        }

        return true;
    }
}
