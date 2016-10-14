package es.centic.android.curso;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.LiveFolders;
import android.text.TextUtils;
import android.util.Log;

public class NewsProvider extends ContentProvider{

	private static final int NEWS = 1;
	private static final int NEWS_ID = 2;
	private static final int LIVE_FOLDER = 3;
	
	private static final UriMatcher mUriMatcher;
	private static final HashMap<String, String> LIVE_FOLDER_PROJECTION_MAP;
	
	private NewsDBAdapter mDbAdapter;
	
	static{
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(News.AUTHORITY, News.PATH_NEWS, NEWS);
		mUriMatcher.addURI(News.AUTHORITY, News.PATH_NEWS_ID, NEWS_ID);
		mUriMatcher.addURI(News.AUTHORITY, News.PATH_LIVE_FOLDER, LIVE_FOLDER);
		
		//Live Folder Projection
		 LIVE_FOLDER_PROJECTION_MAP = new HashMap<String, String>();
		    LIVE_FOLDER_PROJECTION_MAP.put(LiveFolders._ID, News.Columns._ID +
		            " AS " + LiveFolders._ID);
		    LIVE_FOLDER_PROJECTION_MAP.put(LiveFolders.NAME, News.Columns.KEY_TITLE +
		            " AS " + LiveFolders.NAME);
		    LIVE_FOLDER_PROJECTION_MAP.put(LiveFolders.DESCRIPTION, News.Columns.KEY_LINK +
		            " AS " + LiveFolders.DESCRIPTION);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (mUriMatcher.match(uri)) {
		case NEWS:
			return News.CONTENT_TYPE;
		case NEWS_ID:
			return News.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Uri desconocida: "+uri);
		}
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mDbAdapter = new NewsDBAdapter(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(News.Columns.TABLE_NEWS);
		
		switch (mUriMatcher.match(uri)) {
		case NEWS:
			
			break;
		case NEWS_ID:
			qb.appendWhere(News.Columns._ID + "=" + uri.getPathSegments().get(News.POSITION_PATH_NEWS_ID));
			break;
		case LIVE_FOLDER:
			qb.setProjectionMap(LIVE_FOLDER_PROJECTION_MAP);
			break;
		default:
			throw new IllegalArgumentException("Uri desconocida: "+uri);
		}
		
		String orderBy;
		if(TextUtils.isEmpty(sortOrder))
			orderBy = News.DEFAULT_SORT_ORDER;
		else
			orderBy = sortOrder;
		
		SQLiteDatabase db = mDbAdapter.openDB();
		
		if(db == null)
			Log.e("DATABASE", "ES NULL");
		else
			Log.e("DATABASE", "NO ES NULL");
		//Query
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		switch (mUriMatcher.match(uri)) {
		case NEWS:
			return uri;
		case NEWS_ID:
			long id = mDbAdapter.insertNews(values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.withAppendedPath(News.CONTENT_URI, String.valueOf(id));
		default:
			throw new IllegalArgumentException("Uri desconocida: "+uri);
		}
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		switch (mUriMatcher.match(uri)) {
		case NEWS:
			return 0;
		case NEWS_ID:
			long id = Long.valueOf(uri.getPathSegments().get(News.POSITION_PATH_NEWS_ID));
			return mDbAdapter.updateNews(id, values);
		default:
			throw new IllegalArgumentException("Uri desconocida: "+uri);
		}
		
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		switch (mUriMatcher.match(uri)) {
		case NEWS:
			return 0;
		case NEWS_ID:
			long id = Long.valueOf(uri.getPathSegments().get(News.POSITION_PATH_NEWS_ID));
			return mDbAdapter.removeNews(id);
		default:
			throw new IllegalArgumentException("Uri desconocida: "+uri);
		}
		
	}
}
