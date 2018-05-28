package com.example.ananthu.getbooks3;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

public class BookViewActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public SimpleDraweeView bookCover;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        
        // below two calls are required for transitions to work on fresco
        getWindow().setSharedElementEnterTransition(
                DraweeTransition.createTransitionSet(
                        ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP
                )
        );
        getWindow().setSharedElementReturnTransition(
                DraweeTransition.createTransitionSet(
                        ScalingUtils.ScaleType.FIT_CENTER,ScalingUtils.ScaleType.CENTER_CROP
                )
        );

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Book book = (Book) getIntent().getSerializableExtra("book");
        mTextMessage.setText(book.toString());

        bookCover = findViewById(R.id.bookCover);
        bookCover.setImageURI( Uri.parse(book.getImageUrl()) );


    }

}
