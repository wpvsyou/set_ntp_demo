package android.util;/*
 * Copyright (C) 2011 The Android Open Source Project
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

import android.content.Context;

public class NtpTrustedTime implements TrustedTime {
    private final static String TAG = "NtpTrustedTime";

    public static NtpTrustedTime getInstance(Context ctx) {
        Log.d(TAG, "getInstance XXXX");
        return null;
    }

    @Override
    public boolean forceRefresh() {
        Log.d(TAG, "forceRefresh XXXX");
        return false;
    }

    @Override
    public boolean hasCache() {
        Log.d(TAG, "hasCache XXXX");
        return false;
    }

    @Override
    public long getCacheAge() {
        Log.d(TAG, "getCacheAge XXXX");
        return 0;
    }

    @Override
    public long getCacheCertainty() {
        Log.d(TAG, "getCacheCertainty XXXX");
        return 0;
    }

    @Override
    public long currentTimeMillis() {
        Log.d(TAG, "currentTimeMillis XXXX");
        return 0;
    }

    /*private static final String TAG = "NtpTrustedTime";
    private static final boolean LOGD = true;

    private static NtpTrustedTime sSingleton;

    private final String mServer;
    private final long mTimeout;

    private boolean mHasCache;
    private long mCachedNtpTime;
    private long mCachedNtpElapsedRealtime;
    private long mCachedNtpCertainty;

    private NtpTrustedTime(String server, long timeout) {
        if (LOGD) Log.d(TAG, "creating NtpTrustedTime using " + server);
        mServer = server;
        mTimeout = timeout;
    }

    public static synchronized NtpTrustedTime getInstance(Context context) {
        if (sSingleton == null) {
            final Resources res = context.getResources();
            final ContentResolver resolver = context.getContentResolver();

            final String defaultServer = res.getString(
                    R.string.config_ntpServer);
            final long defaultTimeout = res.getInteger(
                    R.integer.config_ntpTimeout);

            final String secureServer = Settings.Global.getString(
                    resolver, "ntp_server");
            final long timeout = Settings.Global.getLong(
                    resolver, "ntp_timeout", defaultTimeout);

            final String server = secureServer != null ? secureServer : defaultServer;
            sSingleton = new NtpTrustedTime(server, timeout);
        }

        return sSingleton;
    }

    @Override
    public boolean forceRefresh() {
        Log.d(TAG, "forceRefresh");
        if (mServer == null) {
            Log.d(TAG, "forceRefresh mService is empty!");
            // missing server, so no trusted time available
            return false;
        }

        final CustomSntpClient client = new CustomSntpClient();
        if (client.requestTime(mServer, (int) mTimeout)) {
            mHasCache = true;
            mCachedNtpTime = client.getNtpTime();
            mCachedNtpElapsedRealtime = client.getNtpTimeReference();
            mCachedNtpCertainty = client.getRoundTripTime() / 2;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasCache() {
        return mHasCache;
    }

    @Override
    public long getCacheAge() {
        if (mHasCache) {
            return SystemClock.elapsedRealtime() - mCachedNtpElapsedRealtime;
        } else {
            return Long.MAX_VALUE;
        }
    }

    @Override
    public long getCacheCertainty() {
        if (mHasCache) {
            return mCachedNtpCertainty;
        } else {
            return Long.MAX_VALUE;
        }
    }

    @Override
    public long currentTimeMillis() {
        if (!mHasCache) {
            throw new IllegalStateException("Missing authoritative time source");
        }
        if (LOGD) Log.d(TAG, "currentTimeMillis() cache hit");

        // current time is age after the last ntp cache; callers who
        // want fresh values will hit makeAuthoritative() first.
        return mCachedNtpTime + getCacheAge();
    }

    public long getCachedNtpTime() {
        if (LOGD) Log.d(TAG, "getCachedNtpTime() cache hit");
        return mCachedNtpTime;
    }

    public long getCachedNtpTimeReference() {
        return mCachedNtpElapsedRealtime;
    }*/
}

