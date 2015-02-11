package com.example.rohilbh.pail;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import db.TaskDBHelper;

public class DisplayCategoryActivity extends ActionBarActivity
{
  private TaskDBHelper helper;
  private ListAdapter listAdapter;
  private String message;

  private void updateUI()
  {
    helper = new TaskDBHelper(DisplayCategoryActivity.this);
    SQLiteDatabase sqlDB = helper.getReadableDatabase();
    String[] arrayOfString1 = { "_id", "task" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = this.message;
    Cursor cursor = sqlDB.query("tasks", arrayOfString1, "category=?", arrayOfString2, null, null, null);
    listAdapter = new SimpleCursorAdapter(
              this,
              R.layout.task_view,
              cursor,
              new String[]{db.TaskContract.Columns.TASK},
              new int[]{R.id.taskTextView},
              0
    );
    ((ListView)findViewById(android.R.id.list)).setAdapter(this.listAdapter);
  }

  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    this.message = getIntent().getStringExtra("com.example.rohilbh.todolist.MESSAGE");
    helper = new TaskDBHelper(this);
    SQLiteDatabase sqlDB = helper.getReadableDatabase();
    String[] arrayOfString1 = { "_id", "task" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = this.message;

    Cursor cursor = sqlDB.query("tasks", arrayOfString1, "category=?", arrayOfString2, null, null, null);
    listAdapter = new SimpleCursorAdapter(
          this,
          R.layout.task_view,
          cursor,
          new String[]{db.TaskContract.Columns.TASK},
          new int[]{R.id.taskTextView},
          0
    );
    ((ListView)findViewById(android.R.id.list)).setAdapter(this.listAdapter);
  }

  public void onDoneButtonClick(View paramView)
  {
    String str = String.format("DELETE FROM %s WHERE %s = '%s'", new Object[] { "tasks", "task", ((TextView)((View)paramView.getParent()).findViewById(R.id.taskTextView)).getText().toString() });
    TaskDBHelper localTaskDBHelper = new TaskDBHelper(this);
    this.helper = localTaskDBHelper;
    this.helper.getWritableDatabase().execSQL(str);
    updateUI();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 2131296324)
      return true;
    return super.onOptionsItemSelected(paramMenuItem);
  }
}

/* Location:           /Users/rohilbh/temp3/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     com.example.rohilbh.pail.DisplayCategoryActivity
 * JD-Core Version:    0.6.2
 */