package in.eduhelp.placementhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteConnection extends SQLiteOpenHelper {
    public SQLiteConnection(Context context)
    {
        super(context,"PlacementHelperDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table employee(id int,name text,organization text, designation text,experience text, email text primary key,password text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
