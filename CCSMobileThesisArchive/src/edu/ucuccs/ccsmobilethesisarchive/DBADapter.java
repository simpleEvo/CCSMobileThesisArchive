package edu.ucuccs.ccsmobilethesisarchive;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBADapter extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db_thesis";
	private static final String TABLE_NAME = "tb_thesis";

	private static final String FIELD_ID = "thesis_id";
	private static final String FIELD_TITLE = "thesis_title";
	private static final String FIELD_RESEARCHER = "thesis_researcher";
	private static final String FIELD_ADVISER = "thesis_adviser";
	private static final String FIELD_YEAR = "thesis_year";
	private static final String FIELD_ABSTRACT = "thesis_abstract";

	public DBADapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_LISTITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ FIELD_ID + " INTEGER PRIMARY KEY," + FIELD_TITLE + " TEXT,"
				+ FIELD_RESEARCHER + " TEXT," + FIELD_ADVISER + " TEXT,"
				+ FIELD_YEAR + " TEXT," + FIELD_ABSTRACT + " TEXT" + ")";
		db.execSQL(CREATE_LISTITEM_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);

	}

	void addThesis(GsThesis gsthesis) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_TITLE, gsthesis.getTitle());
		values.put(FIELD_RESEARCHER, gsthesis.getResearcher());
		values.put(FIELD_ADVISER, gsthesis.getAdviser());
		values.put(FIELD_YEAR, gsthesis.getYear());
		values.put(FIELD_ABSTRACT, gsthesis.getAbs());

		// INSERTING ROW
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	void DropTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		onUpgrade(db, 1, 1);
	}

	public List<GsThesis> getThesisList() {
		List<GsThesis> itemList = new ArrayList<GsThesis>();
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				GsThesis listitem = new GsThesis();
				listitem.setId(Integer.parseInt(cursor.getString(0)));
				listitem.setTitle(cursor.getString(1));
				listitem.setResearcher(cursor.getString(2));
				listitem.setAdviser(cursor.getString(3));
				listitem.setYear(cursor.getString(4));
				listitem.setAbs(cursor.getString(5));

				itemList.add(listitem);
			} while (cursor.moveToNext());
		}
		return itemList;
	}

	public GsThesis findThesis(String thesistitle) {
		String query = "Select * FROM " + TABLE_NAME + " WHERE " + FIELD_TITLE + " =  \"" + thesistitle + "\"";
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		GsThesis thesis = new GsThesis();
		
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			thesis.setId(Integer.parseInt(cursor.getString(0)));
			thesis.setTitle(cursor.getString(1));
			thesis.setResearcher(cursor.getString(2));
			cursor.close();
		} else {
			thesis = null;
		}
	        db.close();
		return thesis;
	}
}
