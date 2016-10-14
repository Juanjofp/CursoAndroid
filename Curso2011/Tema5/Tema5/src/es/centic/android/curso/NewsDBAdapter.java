package es.centic.android.curso;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDBAdapter implements News.Columns{

	private static final String DB_NAME = "noticias.db";
	private static final int DB_VERSION = 1;
	
	private Context mContext;
	private NewsDBHelper mNewsHelper;
	private SQLiteDatabase mNewsDB;
	
	private static final String CREATE_TABLE_NEWS = "create table " + TABLE_NEWS + " ("
													+ _ID + " integer primary key autoincrement,"
													+ KEY_TITLE + " text not null,"
													+ KEY_BODY + " text not null,"
													+ KEY_LINK + " text not null,"
													+ KEY_POSTED + " long);";
	
	private static final String DROP_TABLE_NEWS = "DROP TABLE IF EXISTS " + TABLE_NEWS;
	
	private class NewsDBHelper extends SQLiteOpenHelper {

		public NewsDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE_NEWS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL(DROP_TABLE_NEWS);
			onCreate(db);
		}
		
	}
	
	public NewsDBAdapter(Context ctx) {
		// TODO Auto-generated constructor stub
		mContext = ctx;
		mNewsHelper = new NewsDBHelper(mContext, DB_NAME, null, DB_VERSION);
	}
	
	public SQLiteDatabase openDB(){
		
		try{
			mNewsDB = mNewsHelper.getWritableDatabase();
		}catch (SQLiteException e) {
			// TODO: handle exception
			mNewsDB = mNewsHelper.getReadableDatabase();
		}
		
		return mNewsDB;
	}
	
	public void closeDB(){
		mNewsDB.close();
	}
	
	public long insertNews(News noticia){
		ContentValues cv = noticia.getValues();
		long id = mNewsDB.insert(TABLE_NEWS, null, cv);
		noticia.setId(id);
		return id;
	}
	
	public long insertNews(ContentValues cv){
		long id = mNewsDB.insert(TABLE_NEWS, null, cv);
		return id;
	}
	
	public boolean removeNews(News noticia){
		String where = _ID + "=" + noticia.getId();
		return mNewsDB.delete(TABLE_NEWS, where, null) > 0;
	}
	
	public int removeNews(long id){
		String where = _ID + "=" + id;
		return mNewsDB.delete(TABLE_NEWS, where, null);
	}
	
	public int updateNews(News noticia){
		String where = _ID + "=" + noticia.getId();
		ContentValues cv = noticia.getValues();
		return mNewsDB.update(TABLE_NEWS, cv, where, null);
	}
	
	public int updateNews(long id, ContentValues cv){
		String where = _ID + "=" + id;
		return mNewsDB.update(TABLE_NEWS, cv, where, null);
	}
	
	public News getOneNews(long id){
		Cursor c = mNewsDB.query(TABLE_NEWS, null, _ID + "=" + id, null, null, null, null);
		News n = null;
		if(c != null && c.getCount() > 0){
			c.moveToFirst();
			n = new News(c);
			c.close();
		}
		return n;
	}
	
	public Cursor getAllNews(){
		String[] projection = {_ID, KEY_TITLE};
		String order = KEY_POSTED +" DESC";
		String limit = "0,5";
		return mNewsDB.query(TABLE_NEWS, projection, null, null, null, null,order, limit);
	}
	
	public Cursor getNewsToday(){
		String[] projection = {_ID, KEY_TITLE};
		String order = KEY_POSTED +" DESC";
		String limit = "1,20";
		Date now = new Date();
		long today = new Date(now.getYear(), now.getMonth(), now.getDay()).getTime();
		return mNewsDB.query(TABLE_NEWS, projection, KEY_POSTED + " >= " + today, null, null, null, order, limit);
	}
}
