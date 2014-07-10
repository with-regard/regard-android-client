package io.withregard.android;

/*
A package level interface to be notified what is happening with the RegardClient. In the future
this can be exposed, but keep it internal until we get feedback from users.
 */
interface RegardListener {

    /*
    Attempt at tracking the event has been completed

    @success: Was the event tracked successfully.
     */
    public void eventTracked(boolean success);
}
