/*
 * Copyright (C) 2022 The Portal Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.portalrom.portalromparts.profiles;

import java.util.UUID;

import android.net.Uri;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import portalrom.app.Profile;
import portalrom.app.ProfileGroup;
import portalrom.app.ProfileGroup.Mode;
import portalrom.app.ProfileManager;

import org.portalrom.portalromparts.R;
import org.portalrom.portalromparts.SettingsPreferenceFragment;

public class ProfileGroupConfig extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final CharSequence KEY_SOUNDMODE = "sound_mode";
    private static final CharSequence KEY_VIBRATEMODE = "vibrate_mode";
    private static final CharSequence KEY_LIGHTSMODE = "lights_mode";
    private static final CharSequence KEY_RINGERMODE = "ringer_mode";
    private static final CharSequence KEY_SOUNDTONE = "soundtone";
    private static final CharSequence KEY_RINGTONE = "ringtone";

    Profile mProfile;
    ProfileGroup mProfileGroup;

    private ListPreference mSoundMode;
    private ListPreference mRingerMode;
    private ListPreference mVibrateMode;
    private ListPreference mLightsMode;
    private ProfileRingtonePreference mRingTone;
    private ProfileRingtonePreference mSoundTone;
    private ProfileManager mProfileManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.profile_settings);

        final Bundle args = getArguments();
        if (args != null) {
            mProfile = (Profile) args.getParcelable("Profile");
            UUID uuid = UUID.fromString(args.getString("ProfileGroup"));

            mProfileManager = ProfileManager.getInstance(getActivity());
            mProfileGroup = mProfile.getProfileGroup(uuid);

            mRingerMode = findPreference(KEY_RINGERMODE);
            mSoundMode = findPreference(KEY_SOUNDMODE);
            mVibrateMode = findPreference(KEY_VIBRATEMODE);
            mLightsMode = findPreference(KEY_LIGHTSMODE);
            mRingTone = findPreference(KEY_RINGTONE);
            mSoundTone = findPreference(KEY_SOUNDTONE);

            mRingTone.setShowSilent(false);
            mSoundTone.setShowSilent(false);

            mSoundMode.setOnPreferenceChangeListener(this);
            mRingerMode.setOnPreferenceChangeListener(this);
            mVibrateMode.setOnPreferenceChangeListener(this);
            mLightsMode.setOnPreferenceChangeListener(this);
            mSoundTone.setOnPreferenceChangeListener(this);
            mRingTone.setOnPreferenceChangeListener(this);

            updateState();
        }
    }

    private void updateState() {
        mVibrateMode.setValue(mProfileGroup.getVibrateMode().name());
        mSoundMode.setValue(mProfileGroup.getSoundMode().name());
        mRingerMode.setValue(mProfileGroup.getRingerMode().name());
        mLightsMode.setValue(mProfileGroup.getLightsMode().name());

        mVibrateMode.setSummary(mVibrateMode.getEntry());
        mSoundMode.setSummary(mSoundMode.getEntry());
        mRingerMode.setSummary(mRingerMode.getEntry());
        mLightsMode.setSummary(mLightsMode.getEntry());

        if (mProfileGroup.getSoundOverride() != null) {
            mSoundTone.setRingtone(mProfileGroup.getSoundOverride());
        }

        if (mProfileGroup.getRingerOverride() != null) {
            mRingTone.setRingtone(mProfileGroup.getRingerOverride());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mVibrateMode) {
            mProfileGroup.setVibrateMode(Mode.valueOf((String) newValue));
        } else if (preference == mSoundMode) {
            mProfileGroup.setSoundMode(Mode.valueOf((String) newValue));
        } else if (preference == mRingerMode) {
            mProfileGroup.setRingerMode(Mode.valueOf((String) newValue));
        } else if (preference == mLightsMode) {
            mProfileGroup.setLightsMode(Mode.valueOf((String) newValue));
        } else if (preference == mRingTone) {
            Uri uri = Uri.parse((String) newValue);
            mProfileGroup.setRingerOverride(uri);
        } else if (preference == mSoundTone) {
            Uri uri = Uri.parse((String) newValue);
            mProfileGroup.setSoundOverride(uri);
        }

        mProfileManager.updateProfile(mProfile);

        updateState();
        return true;
    }
}
