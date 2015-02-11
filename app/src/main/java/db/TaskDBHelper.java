package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDBHelper extends SQLiteOpenHelper
{
  public TaskDBHelper(Context paramContext)
  {
    super(paramContext, "com.example.TodoList.db.tasks", null, 1);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    String str = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER)", new Object[] { "tasks", "task", "category", "time" });
    StringBuilder localStringBuilder = new StringBuilder();
    Log.d("TaskDBHelper", "Query to form table: " + str);
    paramSQLiteDatabase.execSQL(str);
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS tasks");
    onCreate(paramSQLiteDatabase);
  }
}

/* Location:           /Users/rohilbh/temp3/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     db.TaskDBHelper
 * JD-Core Version:    0.6.2
 */