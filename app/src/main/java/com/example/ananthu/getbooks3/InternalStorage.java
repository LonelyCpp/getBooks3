package com.example.ananthu.getbooks3;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class InternalStorage{

    private Context mContext;
    private Map<Integer, Book> bookCache;
    private Map<Integer, Author> authorCache;
    public final static String BOOK_CACHE = "BOOK_CACHE";
    public final static String AUTHOR_CACHE = "AUTHOR_CACHE";

    public InternalStorage(Context context) {

        mContext = context;

        try {
            bookCache = (HashMap<Integer, Book>) InternalStorage.readObject(mContext, InternalStorage.BOOK_CACHE);
            authorCache = (HashMap<Integer, Author>) InternalStorage.readObject(mContext, InternalStorage.AUTHOR_CACHE);
        }catch (ClassNotFoundException er){
            Toast.makeText(mContext, "classnotfound", Toast.LENGTH_LONG).show();
        }catch (IOException er){

            try {
                bookCache = new HashMap<>();
                authorCache = new HashMap<>();
                InternalStorage.writeObject(mContext, InternalStorage.BOOK_CACHE, bookCache);
                InternalStorage.writeObject(mContext, InternalStorage.AUTHOR_CACHE, authorCache);
            } catch (IOException err){
                Log.d("cache", "cannot write into storage");
            }
            Toast.makeText(mContext, "ioex", Toast.LENGTH_LONG).show();
        }
    }

    public void cacheBook(Book book){

        try {
            bookCache.put(book.getId(), book);
            InternalStorage.writeObject(mContext, InternalStorage.BOOK_CACHE, bookCache);
        } catch (IOException err){
            Log.d("cache", "cannot write into storage");
        }
    }

    public Book getCachedBookById(Integer id){
        if(bookCache.containsKey(id)){
            return bookCache.get(id);
        }
        return null;
    }

    public void cacheAuthor(Author author){

        try {
            authorCache.put(author.getId(), author);
            InternalStorage.writeObject(mContext, InternalStorage.AUTHOR_CACHE, authorCache);
        } catch (IOException err){
            Log.d("cache", "cannot write into storage");
        }
    }

    public Author getCachedAuthorById(Integer id){
        if(authorCache.containsKey(id)){
            return authorCache.get(id);
        }
        return null;
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