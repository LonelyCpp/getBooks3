package com.example.ananthu.getbooks3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AuthorViewActivity extends AppCompatActivity {


    private ImageView authorImage;
    private TextView authorName;
    private TextView about;
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

        mGoodreadRequest = new GoodreadRequest(getString(R.string.GR_API_Key), this);
        final Author author = (Author) getIntent().getSerializableExtra("author");


        mGoodreadRequest.getAuthor(author.getId(), new SuccessFailedCallback() {
            @Override
            public void success(String response) {
                author.getFullDetails(response);
                updateDetails(author);
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
                    Toast.makeText(
                            getApplicationContext(),
                            "some error occurred",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
