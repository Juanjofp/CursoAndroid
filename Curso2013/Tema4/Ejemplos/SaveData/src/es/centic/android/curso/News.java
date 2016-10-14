package es.centic.android.curso;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class News{
	
	//Authority
	public static final String AUTHORITY = "com.juanjofp.provider.news";
	
	//Content Provider
	public static final String SCHEME = "content://";
	public static final String PATH_NEWS = "news";
	public static final String PATH_NEWS_ID = "news/#";
	public static final String PATH_LIVE_FOLDER = "live_folders/news";
	public static final int POSITION_PATH_NEWS_ID = 1;
	public static final Uri CONTENT_URI = Uri.parse(SCHEME+AUTHORITY+"/"+PATH_NEWS);
	public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME+AUTHORITY+"/"+PATH_NEWS+"/");
	public static final Uri CONTENT_LIVE_FOLDER = Uri.parse(SCHEME+AUTHORITY+"/"+PATH_LIVE_FOLDER);
	public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME+AUTHORITY+"/"+PATH_NEWS_ID);
	
	//MIME types
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.juanjofp.news";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.juanjofp.news";
	
	//Ordenacion por defecto
	public static final String DEFAULT_SORT_ORDER = Columns.KEY_POSTED + " DESC";
	
	//Campos de la base de datos
	public interface Columns extends BaseColumns{
		
		public static final String TABLE_NEWS = "tabla_noticias";
		
		public static final String KEY_TITLE = "titulo_noticia";
		public static final String KEY_BODY = "cuerpo_noticia";
		public static final String KEY_LINK = "enlace_noticia";
		public static final String KEY_POSTED = "fecha_noticia";
		
		public static final int COLUMN_ID = 0; 
		public static final int COLUMN_TITLE = 1; 
		public static final int COLUMN_BODY = 2;
		public static final int COLUMN_LINK = 3;
		public static final int COLUMN_POSTED = 4;
		
	}

	
	
	
	//Atributos de una noticia
	private long mId;
	private String mTitle;
	private String mBody;
	private String mLink;
	private long mPosted;
	
	public News(String title, String body, String link, long date) {
		// TODO Auto-generated constructor stub
		mId = -1;
		mTitle = title;
		mBody = body;
		mLink = link;
		mPosted = date;
	}
	
	public News(Cursor row){
		if(row != null){
			
			mId = row.getInt(row.getColumnIndexOrThrow(Columns._ID));
			mTitle = row.getString(row.getColumnIndexOrThrow(Columns.KEY_TITLE));
			mBody = row.getString(row.getColumnIndexOrThrow(Columns.KEY_BODY));
			mLink = row.getString(row.getColumnIndexOrThrow(Columns.KEY_LINK));
			mPosted = row.getLong(row.getColumnIndexOrThrow(Columns.KEY_POSTED));
		}
	}

	public void setId(long id){
		mId = id;
	}
	
	public ContentValues getValues(){
		ContentValues cv = new ContentValues();
		
		cv.put(Columns.KEY_TITLE, mTitle);
		cv.put(Columns.KEY_BODY, mBody);
		cv.put(Columns.KEY_LINK, mLink);
		cv.put(Columns.KEY_POSTED, mPosted);
		
		return cv;
	}
	
	public boolean isSaved(){
		return mId >= 0 ? true: false;
	}

	public long getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getBody() {
		return mBody;
	}

	public String getLink() {
		return mLink;
	}

	public long getPosted() {
		return mPosted;
	}
	
	public Date getDatePosted(){
		return new Date(mPosted);
	}
	
	public String getWhere(){
		if(isSaved())
			return Columns._ID + "=" + mId;
		return null;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mId+" :: "+getTitle()+" :: "+ getDatePosted();
	}
}
