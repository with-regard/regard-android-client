package io.withregard.android;

/*
A client for sending analytics back to the Regard service.
 */
public interface RegardClient {
    void trackEvent(RegardEvent event);
}
