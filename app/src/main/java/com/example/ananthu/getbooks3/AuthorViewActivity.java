package com.example.ananthu.getbooks3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AuthorViewActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{


    private ImageView authorImage;
    private TextView authorName;
    private TextView about;
    private TextView webAbout;
    private List<Book> books = new ArrayList<>();

    private RecyclerView recyclerView;
    private BookRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    GoodreadRequest mGoodreadRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_view);

        authorName = findViewById(R.id.firstLine);
        authorImage = findViewById(R.id.authorImage);
        about = findViewById(R.id.webAbout);
        recyclerView = findViewById(R.id.recycler_view);

        // for smooth scrolling in recycler view
        recyclerView.setNestedScrollingEnabled(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new BookRecyclerViewAdapter(books);
        recyclerView.setAdapter(mAdapter);

        webAbout = findViewById(R.id.webAbout);
        ToggleButton aboutToggle = findViewById(R.id.aboutToggle);
        ToggleButton bookToggle = findViewById(R.id.bookToggle);
        ToggleButton favoriteToggle = findViewById(R.id.favorite_toggle);

        aboutToggle.setOnCheckedChangeListener(this);
        bookToggle.setOnCheckedChangeListener(this);
        favoriteToggle.setOnCheckedChangeListener(this);

        mGoodreadRequest = new GoodreadRequest(getString(R.string.GR_API_Key), this);
        final Author author = (Author) getIntent().getSerializableExtra("author");
        final ScrollView content = findViewById(R.id.content);
        final ProgressBar loadingIcon = findViewById(R.id.loading_icon);

        mGoodreadRequest.getAuthor(author.getId(), new SuccessFailedCallback() {
            @Override
            public void success(String response) {
                author.getFullDetails(response);
                updateDetails(author);
                content.setVisibility(View.VISIBLE);
                loadingIcon.setVisibility(View.GONE);
            }

            @Override
            public void failed() {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT);
            }
        });


    }

    public void updateDetails(Author author){
        authorName.setText(author.getName());
        Picasso.get().load(author.getImg()).transform(new CircleTransform()).into(authorImage);
        about.setText(Html.fromHtml(author.getAbout()));

        List<Integer> bookIds = author.getBookIds();

        for(int i = 0; i < Math.min(6, bookIds.size()); i++){

            mGoodreadRequest.getBook(bookIds.get(i), new SuccessFailedCallback() {
                @Override
                public void success(String response) {

                    Book book = new Book(response);
                    //testTV.setText(test.toString());
                    mAdapter.add(book);
                }

                @Override
                public void failed() {
                   Log.e("request", "error getting book from id");
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            if(buttonView.getId() == R.id.descriptionToggle){
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_down_24px, 0, 0, 0);
                webAbout.setVisibility(View.VISIBLE);
            } else if(buttonView.getId() == R.id.authorToggle){
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_down_24px, 0, 0, 0);
                recyclerView.setVisibility(View.VISIBLE);
            } else if(buttonView.getId() == R.id.favorite_toggle){
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_stars_gold_14px, 0, 0, 0);
            }
        } else {

            if(buttonView.getId() == R.id.descriptionToggle){
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_right_24px, 0, 0, 0);
                webAbout.setVisibility(View.GONE);
            } else if(buttonView.getId() == R.id.authorToggle){
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_keyboard_arrow_right_24px, 0, 0, 0);
                recyclerView.setVisibility(View.GONE);
            } else if(buttonView.getId() == R.id.favorite_toggle){
                buttonView.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.ic_baseline_stars_grey_14px, 0, 0, 0);
            }
        }
    }
}
