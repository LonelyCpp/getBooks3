package com.example.ananthu.getbooks3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ananthu.getbooks3.adapters.BookRecyclerViewAdapter;
import com.example.ananthu.getbooks3.model.Book;
import com.example.ananthu.getbooks3.network.GoodreadRequest;
import com.example.ananthu.getbooks3.network.SuccessFailedCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LandingPage extends AppCompatActivity {

    private final int INTERNET_PERMISSION = 1;
    private GoodreadRequest mGoodreadRequest;
    private InternalStorage cache;
    List<Book> books = new ArrayList<>();

    private List<Integer> favBookIds = new ArrayList<>(
            Arrays.asList(
                    30256224, 30688013, 22535503,
                    22557272, 31848288, 31208653,
                    17345242, 853510, 12067)
        );

    private RecyclerView recyclerView;
    private BookRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cache = new InternalStorage(this);

        recyclerView = findViewById(R.id.recyclerViewLandingPage);

        // for smooth scrolling in recycler view
        recyclerView.setNestedScrollingEnabled(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new BookRecyclerViewAdapter(books);
        recyclerView.setAdapter(mAdapter);


        requestInternetPermission();

        mGoodreadRequest = new GoodreadRequest(getString(R.string.GR_API_Key), this);


        loadFavBooks();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });



    }
    

    private void loadFavBooks(){
        favBookIds = cache.getFavListCache();
        for(int i = 0; i < favBookIds.size(); i++){

            if(cache.getCachedBookById(favBookIds.get(i)) == null){

                mGoodreadRequest.getBook(favBookIds.get(i), new SuccessFailedCallback() {
                    @Override
                    public void success(String response) {

                        Book book = new Book(response);
                        cache.cacheBook(book);
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

            } else{
                mAdapter.add(cache.getCachedBookById(favBookIds.get(i)));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestInternetPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.INTERNET},
                                INTERNET_PERMISSION);
                    }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case INTERNET_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    Toast.makeText(this, "Internet Access denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
