package com.example.ananthu.getbooks3;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookViewActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private TextView description;
    private TextView txtHeader;
    private TextView txtFooter;
    private ImageView bookCover;
    private RelativeLayout rowContainer;
    private View layout;
    private RatingBar bookRating;
    private TextView ratingCount;
    private TextView webDescription;
    private ToggleButton descriptionToggle;
    private ToggleButton authorToggle;
    private ToggleButton infoToggle;
    private TextView isbn;
    private TextView totalPages;
    private LinearLayout moreInfo;
    private TextView url;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        final Book book = (Book) getIntent().getSerializableExtra("book");

        webDescription = findViewById(R.id.webDescription);
        webDescription.setText(Html.fromHtml(book.getDescription()));

        setTitle(book.getTitle());

        txtHeader = findViewById(R.id.firstLine);
        txtFooter = findViewById(R.id.secondLine);
        bookCover = findViewById(R.id.bookCover);
        rowContainer = findViewById(R.id.row_container);
        bookRating = findViewById(R.id.bookRating);
        ratingCount = findViewById(R.id.ratingCount);
        descriptionToggle = findViewById(R.id.descriptionToggle);
        authorToggle = findViewById(R.id.authorToggle);
        infoToggle = findViewById(R.id.infoToggle);
        isbn = findViewById(R.id.isbn);
        totalPages = findViewById(R.id.totalPages);
        moreInfo = findViewById(R.id.moreInfo);
        url = findViewById(R.id.url);

        bookCover = findViewById(R.id.bookCover);
        Picasso.get().load(book.getImageUrl()).into(bookCover);

        List<String> authorNameList = new ArrayList<>();
        for(Author i : book.getAuthors()){
            authorNameList.add(i.getName());
        }
        txtHeader.setText(book.getTitle());
        txtFooter.setText(TextUtils.join(", ", authorNameList));
        bookRating.setRating(Float.valueOf(String.valueOf(book.getAvgRating())));
        ratingCount.setText("(" + book.getReviewCount() + ")");
        isbn.setText(String.valueOf(book.getIsbn()));
        totalPages.setText(String.valueOf(book.getTotalPages()));

        try {

            if(book.getUrl().length() > 55) {
                url.setText(book.getUrl().substring(35, 57) + "...");
            }else {
                url.setText(book.getUrl().substring(35));
            }

        } catch (ArrayIndexOutOfBoundsException er){
            Log.d("link", book.getUrl());
            url.setText(book.getUrl());
        }

        descriptionToggle.setOnCheckedChangeListener(this);
        authorToggle.setOnCheckedChangeListener(this);
        infoToggle.setOnCheckedChangeListener(this);
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getUrl()));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            buttonView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_baseline_keyboard_arrow_down_24px, 0, 0, 0);

            if(buttonView.getId() == R.id.descriptionToggle){
                webDescription.setVisibility(View.VISIBLE);
            } else if(buttonView.getId() == R.id.infoToggle){
                moreInfo.setVisibility(View.VISIBLE);
            }
        } else {
            buttonView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_baseline_keyboard_arrow_right_24px, 0, 0, 0);

            if(buttonView.getId() == R.id.descriptionToggle){
                webDescription.setVisibility(View.GONE);
            } else if(buttonView.getId() == R.id.infoToggle){
                moreInfo.setVisibility(View.GONE);
            }
        }
    }
}
