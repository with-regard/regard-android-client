package io.withregard.android;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.UUID;

abstract class AbstractRegardClient implements RegardClient {
    private static final String URL = "http://api.withregard.io/track/v1/%s/%s/event";

    private final String _product;
    private final String _organization;
    private final String _userId;
    private final String _sessionId;

    public AbstractRegardClient(String userId, String organization, String product) {
        this._organization = organization;
        this._product = product;
        this._userId = userId;
        this._sessionId = UUID.randomUUID().toString();
    }

    @Override
    public String getUserDataURL() {
        return "https://www.withregard.io/dashboard/userevents/" + _organization + '/' + _product + '/' + _userId;
    }

    @Override
    public void trackEvent(RegardEvent event) {
        new AsyncEventPoster(new RegardListener() {
            @Override
            public void eventTracked(boolean success) {
            }
        }).execute(event);
    }

    private class AsyncEventPoster extends AsyncTask<RegardEvent, Integer, Boolean> {
        private final RegardListener listener;

        private AsyncEventPoster(RegardListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(RegardEvent... events) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                String jsonMessage = events[0].AsJSONObject(_userId, _sessionId).toString();
                HttpPost request = new HttpPost(String.format(URL, _organization, _product));
                request.setEntity(new StringEntity(jsonMessage, "UTF8"));
                request.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(request);
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            this.listener.eventTracked(aBoolean);
        }
    }
}
