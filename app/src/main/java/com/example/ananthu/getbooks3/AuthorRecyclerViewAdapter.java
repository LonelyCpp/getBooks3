package com.example.ananthu.getbooks3;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AuthorRecyclerViewAdapter extends RecyclerView.Adapter<AuthorRecyclerViewAdapter.MyViewHolder>{

private List<Author> authorList;

/**
 * View holder class
 * */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView image;
    public LinearLayout row;

    public MyViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.authorName);
        image = view.findViewById(R.id.authorImage);
        row = view.findViewById(R.id.authorRowContainer);
    }
}

    public AuthorRecyclerViewAdapter(List<Author> authorList) {
        this.authorList = authorList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Author a = authorList.get(position);
        holder.name.setText(a.getName());
        Picasso
            .get()
            .load(a.getImg())
            .transform(new CircleTransform())
            .into(holder.image);

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AuthorViewActivity.class);
                i.putExtra("author", a);
                v.getContext().startActivity(i);
            }
            });
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.author_row_layout,parent, false);
        return new MyViewHolder(v);
    }
}
