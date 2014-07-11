package io.withregard.android;

/*
A client for sending analytics back to the Regard service.
 */
public interface RegardClient {
    String getUserDataURL();

    void trackEvent(RegardEvent event);
}
