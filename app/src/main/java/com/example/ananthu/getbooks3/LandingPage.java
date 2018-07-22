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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    private final int INTERNET_PERMISSION = 1;
    private GoodreadRequest mGoodreadRequest;
    List<Book> books = new ArrayList<>();

    private List<Integer> randomBookIds = new ArrayList<>(
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String test = new Gson().toJson(new Author());
        Author author = new Gson().fromJson(test, Author.class);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLandingPage);

        // for smooth scrolling in recycler view
        recyclerView.setNestedScrollingEnabled(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new BookRecyclerViewAdapter(books);
        recyclerView.setAdapter(mAdapter);


        requestInternetPermission();

        mGoodreadRequest = new GoodreadRequest(getString(R.string.GR_API_Key), this);

        for(int i = 0; i < randomBookIds.size(); i++){

            mGoodreadRequest.getBook(randomBookIds.get(i), new SuccessFailedCallback() {
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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestInternetPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

            } else {
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
                return;
            }
        }
    }
}
