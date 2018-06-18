package com.example.ananthu.getbooks3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AuthorRecyclerViewAdapter extends RecyclerView.Adapter<AuthorRecyclerViewAdapter.MyViewHolder>{

private List<Author> authorList;

/**
 * View holder class
 * */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView image;

    public MyViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.authorName);
        image = view.findViewById(R.id.authorImage);
    }
}

    public AuthorRecyclerViewAdapter(List<Author> authorList) {
        this.authorList = authorList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Author a = authorList.get(position);
        holder.name.setText(a.getName());
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
