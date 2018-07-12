package com.example.ananthu.getbooks3;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ananthu on 26-05-2018.
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder>{

    private List<Book> values;

    public BookRecyclerViewAdapter(List<Book> myDataset) {
        values = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView bookCover;
        public RelativeLayout rowContainer;
        public View layout;
        public RatingBar bookRating;
        public TextView ratingCount;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter = v.findViewById(R.id.secondLine);
            bookCover = v.findViewById(R.id.bookCover);
            rowContainer = v.findViewById(R.id.row_container);
            bookRating = v.findViewById(R.id.bookRating);
            ratingCount = v.findViewById(R.id.ratingCount);

            bookRating.setIsIndicator(true);
            bookRating.setMax(5);
            bookRating.setNumStars(5);
            bookRating.setStepSize((float) 0.01);
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
                inflater.inflate(R.layout.book_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Book book = values.get(position);
        holder.txtHeader.setText(book.getTitle());
        holder.rowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), BookViewActivity.class);
                i.putExtra("book", book);

                Pair<View, String> bookCover = Pair.create((View)holder.bookCover, "book_cover_activity_transition");

                // adds activity transition to book image
                ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation((Activity) v.getContext(), bookCover);
                v.getContext().startActivity(i, options.toBundle());
            }
        });

        List<String> authorNameList = new ArrayList<>();
        for(Author i : book.getAuthors()){
            authorNameList.add(i.getName());
        }
        holder.txtFooter.setText(TextUtils.join(", ", authorNameList));
        Picasso.get().load(book.getImageUrl()).into(holder.bookCover);

        try {
            holder.bookRating.setRating(Float.valueOf(String.valueOf(book.getAvgRating())));
            holder.ratingCount.setText("(" + book.getReviewCount() + ")");
        } catch (NullPointerException ex){
            holder.bookRating.setVisibility(View.GONE);
            holder.ratingCount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
