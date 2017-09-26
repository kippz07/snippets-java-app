package com.jennyjspartaglobal.spartasnippets;

import android.app.LoaderManager;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Snippet> {

    private TextView questionTextView;
    private TextView answerTextView;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = (TextView) findViewById(R.id.question);
        answerTextView = (TextView) findViewById(R.id.answer);
        refreshButton = (Button) findViewById(R.id.refresh);

        final LoaderManager loaderManager = getLoaderManager();

        try {
            loaderManager.initLoader(1, null, this);
        } catch (NullPointerException n) {
            Log.e("InternetLog", "No Internet Connection");
        }


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loaderManager.restartLoader(1, null, MainActivity.this);
                } catch (NullPointerException n) {
                    Log.e("InternetLog", "No Internet Connection");
                }

            }
        });
    }

    @Override
    public Loader<Snippet> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new SnippetLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Snippet> loader, Snippet snippets) {

        try {
            questionTextView.setText(snippets.getQuestion());
            answerTextView.setText(snippets.getAnswer());
        } catch (NullPointerException n) {
            Log.e("InternetLog", "No Internet Connection");
        }

    }

    @Override
    public void onLoaderReset(Loader<Snippet> loader) {
        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
    }
}
