package io.withregard.android;

import android.content.SharedPreferences;

public class RegardClientFactory {
    private static RegardClient _regardClient = null;

    public RegardClient getClient(SharedPreferences preferences, String organization, String product) {
        if (_regardClient == null)
            _regardClient = new AndroidRegardClient(preferences, organization, product);

        return _regardClient;
    }
}
