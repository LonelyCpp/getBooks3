package com.example.ananthu.getbooks3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Ananthu on 26-05-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Book> values;

    public RecyclerViewAdapter(List<Book> myDataset) {
        values = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public SimpleDraweeView bookCover;
        public RelativeLayout rowContainer;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter = v.findViewById(R.id.secondLine);
            bookCover = v.findViewById(R.id.bookCover);
            rowContainer = v.findViewById(R.id.row_container);
        }
    }

    public void add(Book item) {
        int position = values.size();
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Book book = values.get(position);
        holder.txtHeader.setText(book.getTitle());
        holder.rowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO item click handler
                // Toast.makeText(v.getContext(), "you clicked " + book.getTitle(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(), BookViewActivity.class);
                i.putExtra("book", book);
                v.getContext().startActivity(i);
            }
        });

        List<String> authorNameList = new ArrayList<>();
        for(Author i : book.getAuthors()){
            authorNameList.add(i.getName());
        }
        holder.txtFooter.setText(TextUtils.join(", ", authorNameList));
        holder.bookCover.setImageURI( Uri.parse(book.getImageUrl()) );

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
