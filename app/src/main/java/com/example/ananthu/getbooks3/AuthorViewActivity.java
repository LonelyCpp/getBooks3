package com.example.ananthu.getbooks3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ananthu.getbooks3.adapters.BookRecyclerViewAdapter;
import com.example.ananthu.getbooks3.model.Author;
import com.example.ananthu.getbooks3.model.AuthorBuilder;
import com.example.ananthu.getbooks3.model.Book;
import com.example.ananthu.getbooks3.model.BookBuilder;
import com.example.ananthu.getbooks3.network.GoodreadRequest;
import com.example.ananthu.getbooks3.network.SuccessFailedCallback;
import com.example.ananthu.getbooks3.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AuthorViewActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = AuthorViewActivity.class.getName();

    private Author author;
    private ImageView authorImage;
    private TextView authorName;
    private TextView webAbout;
    private List<Book> books = new ArrayList<>();

    private RecyclerView authorRecyclerView;
    private BookRecyclerViewAdapter bookRecyclerViewAdapter;
    private GoodreadRequest mGoodreadRequest;
    private InternalStorage cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_view);
        cache = InternalStorage.getInstance();

        authorName = findViewById(R.id.firstLine);
        authorImage = findViewById(R.id.authorImage);
        authorRecyclerView = findViewById(R.id.recycler_view);

        // for smooth scrolling in recycler view
        authorRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        authorRecyclerView.setLayoutManager(layoutManager);


        bookRecyclerViewAdapter = new BookRecyclerViewAdapter(books);
        authorRecyclerView.setAdapter(bookRecyclerViewAdapter);

        webAbout = findViewById(R.id.webAbout);
        ToggleButton aboutToggle = findViewById(R.id.aboutToggle);
        ToggleButton bookToggle = findViewById(R.id.bookToggle);


        aboutToggle.setOnCheckedChangeListener(this);
        bookToggle.setOnCheckedChangeListener(this);


        mGoodreadRequest = new GoodreadRequest(getString(R.string.GR_API_Key), this);
        author = (Author) getIntent().getSerializableExtra("author");


        if (cache.getCachedAuthorById(author.getId()) == null) {
            mGoodreadRequest.getAuthor(author.getId(), new SuccessFailedCallback() {
                @Override
                public void success(String response) {
                    author = AuthorBuilder.getAboutDetails(response, author);
                    cache.cacheAuthor(author);
                    updateDetails(author);
                }

                @Override
                public void failed() {
                    Toast.makeText(
                            getApplicationContext(),
                            "something went wrong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        } else {
            author = cache.getCachedAuthorById(author.getId());
            updateDetails(author);
        }


    }

    private void updateDetails(Author author) {
        authorName.setText(author.getName());
        Log.d(TAG, "updateDetails: " + author.getImg());
        Picasso
                .get()
                .load(author.getImg())
                .transform(new CircleTransform())
                .into(authorImage);
        webAbout.setText(Html.fromHtml(author.getAbout()));

        List<Integer> bookIds = author.getBookIds();

        for (int i = 0; i < Math.min(6, bookIds.size()); i++) {

            if (cache.getCachedBookById(bookIds.get(i)) == null) {
                mGoodreadRequest.getBook(bookIds.get(i), new SuccessFailedCallback() {
                    @Override
                    public void success(String response) {

                        Book book = BookBuilder.getBookFromXML(response);
                        cache.cacheBook(book);
                        bookRecyclerViewAdapter.add(book);
                    }

                    @Override
                    public void failed() {
                        Log.e(TAG, "failed: getting book from id");
                    }
                });
            } else {
                bookRecyclerViewAdapter.add(cache.getCachedBookById(bookIds.get(i)));
            }

        }

        findViewById(R.id.content).setVisibility(View.VISIBLE);

        findViewById(R.id.loading_icon).setVisibility(View.GONE);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.descriptionToggle) {
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_down_24px, 0, 0, 0);
                webAbout.setVisibility(View.VISIBLE);
            } else if (buttonView.getId() == R.id.authorToggle) {
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_down_24px, 0, 0, 0);
                authorRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {

            if (buttonView.getId() == R.id.descriptionToggle) {
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_right_24px, 0, 0, 0);
                webAbout.setVisibility(View.GONE);
            } else if (buttonView.getId() == R.id.authorToggle) {
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_right_24px, 0, 0, 0);
                authorRecyclerView.setVisibility(View.GONE);
            }

        }
    }
}
