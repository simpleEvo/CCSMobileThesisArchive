package edu.ucuccs.ccsmobilethesisarchive;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBADapter extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db_thesis";
	private static final String TABLE_NAME = "tb_thesis";
	private static final String TABLE_RATE = "tb_rate";
	private static final String TABLE_RAC = "tb_rac";

	private static final String FIELD_ID = "thesis_id";
	private static final String FIELD_TITLE = "thesis_title";
	private static final String FIELD_RESEARCHER = "thesis_researcher";
	private static final String FIELD_ADVISER = "thesis_adviser";
	private static final String FIELD_YEAR = "thesis_year";
	private static final String FIELD_ABSTRACT = "thesis_abstract";
	private static final String FIELD_RATING = "rate_rating";
	private static final String FIELD_TID = "rate_thesisid";
	private static final String FIELD_RID = "rate_id";
	private static final String FIELD_RAC = "rac_id";
	private static final String FIELD_RACFNAME = "rac_fname";
	private static final String FIELD_RACLNAME = "rac_lname";
	private static final String FIELD_RACCOMMENT = "rac_comment";
	private static final String FIELD_RACRATE = "rac_rate";
	private static final String FIELD_RACUID = "rac_uid";
	private static final String FIELD_RACTID = "rac_tid";

	public DBADapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_LISTITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ FIELD_ID + " TEXT," + FIELD_TITLE + " TEXT,"
				+ FIELD_RESEARCHER + " TEXT," + FIELD_ADVISER + " TEXT,"
				+ FIELD_YEAR + " TEXT," + FIELD_ABSTRACT + " TEXT" + ")";
		db.execSQL(CREATE_LISTITEM_TABLE);
		String CREATE_LISTRATE_TABLE = "CREATE TABLE " + TABLE_RATE + "("
				+ FIELD_RID + " INTEGER PRIMARY KEY," + FIELD_TID + " TEXT,"
				+ FIELD_RATING + " TEXT " + ")";
		db.execSQL(CREATE_LISTRATE_TABLE);
		String CREATE_RAC_TABLE = "CREATE TABLE " + TABLE_RAC + "(" + FIELD_RAC
				+ " INTEGER PRIMARY KEY," + FIELD_RACFNAME + " TEXT,"
				+ FIELD_RACLNAME + " TEXT, " + FIELD_RACCOMMENT + " TEXT, "
				+ FIELD_RACRATE + " INTEGER, " + FIELD_RACUID + " TEXT, "
				+ FIELD_RACTID + " TEXT " + ")";
		db.execSQL(CREATE_RAC_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAC);
		onCreate(db);

	}

	void addThesis(GsThesis gsthesis) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_ID, gsthesis.getId());
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
				listitem.setId(cursor.getString(0));
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

	public List<GsThesis> findTitle(String searchTerm) {

		List<GsThesis> recordsList = new ArrayList<GsThesis>();

		// select query
		String sql = "";
		sql += "SELECT * FROM " + TABLE_NAME;
		sql += " WHERE " + FIELD_TITLE + " LIKE '%" + searchTerm + "%'";
		sql += " OR " + FIELD_RESEARCHER + " LIKE '%" + searchTerm + "%'";
		sql += " ORDER BY " + FIELD_TITLE + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				// int productId =
				// Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
				GsThesis listitem = new GsThesis();
				listitem.setId(cursor.getString(0));
				listitem.setTitle(cursor.getString(1));
				listitem.setResearcher(cursor.getString(2));
				listitem.setAdviser(cursor.getString(3));
				listitem.setYear(cursor.getString(4));
				listitem.setAbs(cursor.getString(5));
				recordsList.add(listitem);

				// add to list
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		// return the list of records
		return recordsList;
	}

	//rate
	void addRate(GsRate gsrate) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_TID, gsrate.getRthesisid());
		values.put(FIELD_RATING, gsrate.getRate());

		// INSERTING ROW
		db.insert(TABLE_RATE, null, values);
		db.close();
	}

	public List<GsRate> getRateList() {
		List<GsRate> itemList = new ArrayList<GsRate>();
		String selectQuery = "SELECT  * FROM " + TABLE_RATE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				GsRate listitem = new GsRate();
				listitem.setRid(Integer.parseInt(cursor.getString(0)));
				listitem.setRthesisid(cursor.getString(1));
				listitem.setRate(cursor.getString(2));

				itemList.add(listitem);
			} while (cursor.moveToNext());
		}
		return itemList;
	}

	public List<GsRate> findThesisId(String searchTerm) {

		List<GsRate> recordsList = new ArrayList<GsRate>();

		// select query
		String sql = "";
		sql += "SELECT * FROM " + TABLE_RATE;
		sql += " WHERE " + FIELD_TID + " LIKE '%" + searchTerm + "%'";
		sql += " ORDER BY " + FIELD_RATING + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				// int productId =
				// Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
				GsRate listitem = new GsRate();
				listitem.setRthesisid(cursor.getString(1));
				listitem.setRate(cursor.getString(2));
				recordsList.add(listitem);

				// add to list
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		// return the list of records
		return recordsList;
	}
	
	//RAC
	
	void addRate(GsRac gsrac) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_RACFNAME, gsrac.getRac_fname());
		values.put(FIELD_RACLNAME, gsrac.getRac_lname());
		values.put(FIELD_RACRATE, gsrac.getRac_rate());
		values.put(FIELD_RACCOMMENT, gsrac.getRac_comment());
		values.put(FIELD_RACUID, gsrac.getRac_uid());
		values.put(FIELD_RACTID, gsrac.getRac_tid());

		// INSERTING ROW
		db.insert(TABLE_RAC, null, values);
		db.close();
	}
	
	
	public List<GsRac> findTID(String searchTerm) {

		List<GsRac> recordsList = new ArrayList<GsRac>();

		// select query
		String sql = "";
		sql += "SELECT * FROM " + TABLE_RAC;
		sql += " WHERE " + FIELD_RACTID + " LIKE '%" + searchTerm + "%'";
		sql += " ORDER BY " + FIELD_RACRATE + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				// int productId =
				// Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
				GsRac listitem = new GsRac();
				listitem.setRac_id(Integer.parseInt(cursor.getString(0)));
				listitem.setRac_fname(cursor.getString(1));
				listitem.setRac_lname(cursor.getString(2));
				listitem.setRac_rate(cursor.getString(3));
				listitem.setRac_comment(cursor.getString(4));
				listitem.setRac_uid(cursor.getString(5));
				listitem.setRac_tid(cursor.getString(6));
				recordsList.add(listitem);

				// add to list
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		// return the list of records
		return recordsList;
	}
}
