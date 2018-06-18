package com.example.ananthu.getbooks3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookViewActivity extends AppCompatActivity {

    private TextView description;
    private TextView txtHeader;
    private TextView txtFooter;
    private ImageView bookCover;
    private RelativeLayout rowContainer;
    private View layout;
    private RatingBar bookRating;
    private TextView ratingCount;
    private WebView webDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        //description = findViewById(R.id.description);
        Book book = (Book) getIntent().getSerializableExtra("book");
        //description.setText(Html.fromHtml(book.getDescription()));

        webDescription = findViewById(R.id.webDescription);

        String descriptionHTML =
                "<body style=\"text-align:justify;\">" +
                book.getDescription() +
                "</body>";

        webDescription.setBackgroundColor(Color.parseColor("#00000000"));
        webDescription.loadData(descriptionHTML, "text/html; charset=utf-8", "utf-8");
        webDescription.setTransitionGroup(true);

        setTitle(book.getTitle());

        txtHeader = findViewById(R.id.firstLine);
        txtFooter = findViewById(R.id.secondLine);
        bookCover = findViewById(R.id.bookCover);
        rowContainer = findViewById(R.id.row_container);
        bookRating = findViewById(R.id.bookRating);
        ratingCount = findViewById(R.id.ratingCount);

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


    }

}
