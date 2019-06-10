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
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternalStorage {
    private static final String TAG = InternalStorage.class.getName();

    private static InternalStorage instance;

    private WeakReference<Context> mContext;
    private Map<Integer, Book> bookCache;
    private Map<Integer, Author> authorCache;

    private List<Integer> favListCache;
    private final static String BOOK_CACHE = "BOOK_CACHE";
    private final static String AUTHOR_CACHE = "AUTHOR_CACHE";
    private final static String FAV_CACHE = "FAV_CACHE";

    /**
     * initialize singleton
     * @param context - advisable to use application context to avoid memory leaks
     */
    public static void init(Context context){
        instance = new InternalStorage(context);
    }


    public static InternalStorage getInstance() {
        if(instance == null){
            throw new Error("Internal storage was not initialised");
        }
        return instance;
    }

    /**
     * Reads cache from internal storage. if no cache files were found, new files are created
     *
     * @param context application context
     */
    private InternalStorage(Context context) {

        mContext = new WeakReference<>(context);

        try {
            bookCache = (HashMap<Integer, Book>) InternalStorage.readObject(mContext.get(), InternalStorage.BOOK_CACHE);
            authorCache = (HashMap<Integer, Author>) InternalStorage.readObject(mContext.get(), InternalStorage.AUTHOR_CACHE);
        } catch (ClassNotFoundException er) {
            Log.e(TAG, "InternalStorage: ", er);
        } catch (IOException er) {

            try {
                bookCache = new HashMap<>();
                authorCache = new HashMap<>();
                InternalStorage.writeObject(mContext.get(), InternalStorage.BOOK_CACHE, bookCache);
                InternalStorage.writeObject(mContext.get(), InternalStorage.AUTHOR_CACHE, authorCache);
            } catch (IOException err) {
                Log.e(TAG, "InternalStorage: cannot write into storage", err);
            }
        }

        try {
            favListCache = (ArrayList<Integer>) InternalStorage.readObject(mContext.get(), InternalStorage.FAV_CACHE);
        } catch (ClassNotFoundException er) {
            Log.e(TAG, "InternalStorage: ", er);
        } catch (IOException er) {
            try {
                favListCache = new ArrayList<>();
                InternalStorage.writeObject(mContext.get(), InternalStorage.FAV_CACHE, favListCache);
            } catch (IOException err) {
                Log.e(TAG, "InternalStorage: cannot write into storage", err);
            }
        }

    }

    /**
     * Writes a book into the cache
     *
     * @param book book object
     */
    public void cacheBook(Book book) {

        try {
            bookCache.put(book.getId(), book);
            InternalStorage.writeObject(mContext.get(), InternalStorage.BOOK_CACHE, bookCache);
        } catch (IOException err) {
            Log.e(TAG, "cacheBook: cannot write into storage", err);
        }
    }

    /**
     * gets a book from cache
     *
     * @param id goodreads id of the book
     * @return book object
     */
    public Book getCachedBookById(Integer id) {
        if (bookCache.containsKey(id)) {
            return bookCache.get(id);
        }
        return null;
    }

    /**
     * Writes an author into the cache
     *
     * @param author author object
     */
    public void cacheAuthor(Author author) {

        try {
            authorCache.put(author.getId(), author);
            InternalStorage.writeObject(mContext.get(), InternalStorage.AUTHOR_CACHE, authorCache);
        } catch (IOException err) {
            Log.e(TAG, "cacheAuthor: cannot write into storage", err);
        }
    }

    /**
     * Gets an author from cache
     *
     * @param id goodreads id of the author
     * @return author object
     */
    public Author getCachedAuthorById(Integer id) {
        if (authorCache.containsKey(id)) {
            return authorCache.get(id);
        }
        return null;
    }

    /**
     * Lists favorite books of the user
     *
     * @return a list of goodreads ids of the books
     */
    public List<Integer> getFavListCache() {
        Log.d(TAG, "getFavListCache: " + this.favListCache);
        return this.favListCache;

    }

    public boolean isFavorite(Integer id) {

        for (Integer i : favListCache) {
            if (i.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * adds a book id to favorite list
     *
     * @param id goodreads id of the book
     */
    public void addToFavList(Integer id) {
        Log.d(TAG, "addToFavList: " + id);
        try {
            this.favListCache.add(id);
            InternalStorage.writeObject(mContext.get(), InternalStorage.FAV_CACHE, favListCache);
        } catch (IOException err) {
            Log.e(TAG, "addToFavList: cannot write into storage", err);
        }
    }

    /**
     * removes a book id from favorite list
     *
     * @param id goodreads id of the book
     */
    public void removeFromFavList(Integer id) {
        Log.d(TAG, "removeFromFavList: " + id);
        try {
            for (int i = 0; i < favListCache.size(); i++) {
                if (favListCache.get(i).equals(id)) {
                    favListCache.remove(i);
                    break;
                }
            }
            InternalStorage.writeObject(mContext.get(), InternalStorage.FAV_CACHE, favListCache);

        } catch (IOException err) {
            Log.e(TAG, "removeFromFavList: cannot write into storage", err);
        }
        Log.d(TAG, "removeFromFavList: " + this.favListCache);
    }

    /**
     * Writes an object into a file in internal storage
     *
     * @param context  application context
     * @param fileName file name string
     * @param object   value object
     * @throws IOException if internal storage is not accessible
     */
    private static void writeObject(Context context, String fileName, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    /**
     * Reads an object from internal storage
     *
     * @param context  application context
     * @param fileName file name string
     * @return object from the file
     * @throws IOException            if internal storage is not accessible
     * @throws ClassNotFoundException if the class of a serialized object could not be found.
     */
    private static Object readObject(Context context, String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }
}