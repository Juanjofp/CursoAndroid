package com.franco.juanjo.pac1.tema2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by juanjo on 25/9/16.
 */

// Clase usada como modelo de Libro, implemento la interfaz Parcelable para poder
// enviarla pasarla entre actividades
public class Book
implements Parcelable {

    private String mId;
    private String mTitle;
    private String mAuthor;
    private Date mPublicationDate;
    private String mResume;
    private String mURL;

    public Book() {
        mId = null;
        mTitle = null;
        mAuthor = null;
        mPublicationDate = null;
        mResume = null;
        mURL = null;
    }

    public Book(String id, String title, String author, Date publicationDate, String resume, String URL) {
        mId = id;
        mTitle = title;
        mAuthor = author;
        mPublicationDate = publicationDate;
        mResume = resume;
        mURL = URL;
    }

    protected Book(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mAuthor = in.readString();
        mResume = in.readString();
        mURL = in.readString();
        mPublicationDate = new Date(in.readLong());
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public Date getPublicationDate() {
        return mPublicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        mPublicationDate = publicationDate;
    }

    public String getResume() {
        return mResume;
    }

    public void setResume(String resume) {
        mResume = resume;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeString(mResume);
        dest.writeString(mURL);
        dest.writeLong(mPublicationDate.getTime());
    }
}
