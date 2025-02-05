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

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.AttributeSet;

import org.portalrom.portalromparts.widget.RingtonePreference;

public class ProfileRingtonePreference extends RingtonePreference {
    private static final String TAG = "ProfileRingtonePreference";

    public ProfileRingtonePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPrepareRingtonePickerIntent(Intent ringtonePickerIntent) {
        super.onPrepareRingtonePickerIntent(ringtonePickerIntent);

        /*
         * Since this preference is for choosing the default ringtone, it
         * doesn't make sense to show a 'Default' item.
         */
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
    }

    private Uri mRingtone;

    void setRingtone(Uri uri) {
        mRingtone = uri;
    }

    @Override
    protected Uri onRestoreRingtone() {
        if (mRingtone == null) {
            return super.onRestoreRingtone();
        } else {
            return mRingtone;
        }
    }
}
