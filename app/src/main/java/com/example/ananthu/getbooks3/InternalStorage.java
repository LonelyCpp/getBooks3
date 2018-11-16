package com.example.ananthu.getbooks3;

import android.content.Context;
import android.util.Log;

import com.example.ananthu.getbooks3.model.Author;
import com.example.ananthu.getbooks3.model.Book;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternalStorage {

    private Context mContext;
    private Map<Integer, Book> bookCache;
    private Map<Integer, Author> authorCache;

    private List<Integer> favListCache;
    private final static String BOOK_CACHE = "BOOK_CACHE";
    private final static String AUTHOR_CACHE = "AUTHOR_CACHE";
    private final static String FAV_CACHE = "FAV_CACHE";

    public InternalStorage(Context context) {

        mContext = context;

        try {
            bookCache = (HashMap<Integer, Book>) InternalStorage.readObject(mContext, InternalStorage.BOOK_CACHE);
            authorCache = (HashMap<Integer, Author>) InternalStorage.readObject(mContext, InternalStorage.AUTHOR_CACHE);
        } catch (ClassNotFoundException er) {
            Log.d("cache", "ClassNotFoundException");
        } catch (IOException er) {

            try {
                bookCache = new HashMap<>();
                authorCache = new HashMap<>();
                InternalStorage.writeObject(mContext, InternalStorage.BOOK_CACHE, bookCache);
                InternalStorage.writeObject(mContext, InternalStorage.AUTHOR_CACHE, authorCache);
            } catch (IOException err) {
                Log.d("cache", "cannot write into storage");
            }
        }

        try {
            favListCache = (ArrayList<Integer>) InternalStorage.readObject(mContext, InternalStorage.FAV_CACHE);
        } catch (ClassNotFoundException er) {
            Log.d("cache", "ClassNotFoundException");
        } catch (IOException er) {

            try {
                favListCache = new ArrayList<>();
                InternalStorage.writeObject(mContext, InternalStorage.FAV_CACHE, favListCache);
            } catch (IOException err) {
                Log.d("cache", "cannot write into storage");
            }
        }

    }

    public void cacheBook(Book book) {

        try {
            bookCache.put(book.getId(), book);
            InternalStorage.writeObject(mContext, InternalStorage.BOOK_CACHE, bookCache);
        } catch (IOException err) {
            Log.d("cache", "cannot write into storage");
        }
    }

    public Book getCachedBookById(Integer id) {
        if (bookCache.containsKey(id)) {
            return bookCache.get(id);
        }
        return null;
    }

    public void cacheAuthor(Author author) {

        try {
            authorCache.put(author.getId(), author);
            InternalStorage.writeObject(mContext, InternalStorage.AUTHOR_CACHE, authorCache);
        } catch (IOException err) {
            Log.d("cache", "cannot write into storage");
        }
    }

    public Author getCachedAuthorById(Integer id) {
        if (authorCache.containsKey(id)) {
            return authorCache.get(id);
        }
        return null;
    }

    public List<Integer> getFavListCache() {
        return favListCache;
    }

    public boolean isFavorite(Integer id) {

        for (Integer i : favListCache) {
            if (i.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void addToFavList(Integer id) {
        try {
            this.favListCache.add(id);
            InternalStorage.writeObject(mContext, InternalStorage.FAV_CACHE, favListCache);
        } catch (IOException err) {
            Log.d("cache", "cannot write into storage");
        }
    }

    public void removeFromFavList(Integer id) {

        try {
            for (int i = 0; i < favListCache.size(); i++) {
                if (favListCache.get(i).equals(id)) {
                    favListCache.remove(i);
                    break;
                }
            }
            InternalStorage.writeObject(mContext, InternalStorage.FAV_CACHE, favListCache);

        } catch (IOException err) {
            Log.d("cache", "cannot write into storage");
        }
    }

    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }
}