package com.jennyjspartaglobal.spartasnippets;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;

/**
 * Created by Tech-A42 on 26/09/2017.
 */

public class SnippetLoader extends AsyncTaskLoader<Snippet> {

    private String mUrl = "https://spartasnippets.herokuapp.com/pi.json";

    public SnippetLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() { forceLoad(); }

    @Override
    public Snippet loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        Snippet snippet = QueryUtils.fetchSnippetData();
        return snippet;
    }
}
