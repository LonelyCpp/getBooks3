package com.example.ananthu.getbooks3.adapters;

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

import com.example.ananthu.getbooks3.BookViewActivity;
import com.example.ananthu.getbooks3.R;
import com.example.ananthu.getbooks3.model.Author;
import com.example.ananthu.getbooks3.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookViewHolder> {

    private List<Book> values;

    public BookRecyclerViewAdapter(List<Book> dataSet) {
        values = dataSet;
    }

    public void clear() {
        values = new ArrayList<>();
        notifyDataSetChanged();
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
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.book_row_layout, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        final Book book = values.get(position);
        holder.txtHeader.setText(book.getTitle());
        holder.rowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), BookViewActivity.class);
                i.putExtra("book", book);

                Pair<View, String> bookCover = Pair.create((View) holder.bookCover, "book_cover_activity_transition");

                // adds activity transition to book image
                ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation((Activity) v.getContext(), bookCover);
                v.getContext().startActivity(i, options.toBundle());
            }
        });

        List<String> authorNameList = new ArrayList<>();
        for (Author i : book.getAuthors()) {
            authorNameList.add(i.getName());
        }
        holder.txtFooter.setText(TextUtils.join(", ", authorNameList));
        Picasso.get().load(book.getImageUrl()).into(holder.bookCover);

        try {
            holder.bookRating.setRating(Float.valueOf(String.valueOf(book.getAvgRating())));
            holder.ratingCount.setText("(" + book.getReviewCount() + ")");
        } catch (NullPointerException ex) {
            holder.bookRating.setVisibility(View.GONE);
            holder.ratingCount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        final ImageView bookCover;
        final View layout;
        final RatingBar bookRating;
        final TextView ratingCount;
        final TextView txtHeader;
        final TextView txtFooter;
        final RelativeLayout rowContainer;

        BookViewHolder(View v) {
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
}
