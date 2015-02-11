package com.example.rohilbh.pail;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import db.TaskDBHelper;
import java.io.PrintStream;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity
{
    public static final String EXTRA_MESSAGE = "com.example.rohilbh.todolist.MESSAGE";
    private TaskDBHelper helper;
    private ListAdapter listAdapter;

    private void promptUser(final String paramString)
    {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(paramString);
        localBuilder.setMessage("Check out...");
        final EditText localEditText = new EditText(this);
        localBuilder.setView(localEditText);
        DialogInterface.OnClickListener local2 = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                String str = localEditText.getText().toString();
                SQLiteDatabase localSQLiteDatabase = MainActivity.this.helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.clear();
                long l = Calendar.getInstance().getTimeInMillis();
                values.put("task", str);
                values.put("category", paramString);
                values.put("time", Long.valueOf(l));
                localSQLiteDatabase.insertWithOnConflict("tasks", null, values, 4);
                MainActivity.this.updateUI();
                MainActivity.this.setReminder(str);
            }
        };
        localBuilder.setPositiveButton("Add", local2);
        localBuilder.setNegativeButton("Cancel", null);
        localBuilder.create().show();
    }

    private void updateUI()
    {
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query("tasks", new String[] { "_id", "task", "category" }, null, null, null, null, null);
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

    public void displayCategory(String paramString)
    {
        Intent intent = new Intent(this, DisplayCategoryActivity.class);
        intent.putExtra("com.example.rohilbh.todolist.MESSAGE", paramString);
        startActivity(intent);
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        updateUI();
    }

    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        getMenuInflater().inflate(R.menu.menu_main, paramMenu);
        return true;
    }

    public void onDoneButtonClick(View paramView)
    {
        String sql = String.format("DELETE FROM %s WHERE %s = '%s'", new Object[] { "tasks", "task", ((TextView)((View)paramView.getParent()).findViewById(R.id.taskTextView)).getText().toString() });
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        switch (paramMenuItem.getItemId())
        {
            case R.id.action_add_task:
                final String[] arrayOfString = { "Books", "Movies", "Music", "Restaurants" };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select The Category");
                DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        String str = arrayOfString[paramAnonymousInt];
                        System.out.println(str);
                        MainActivity.this.promptUser(str);
                        paramAnonymousDialogInterface.dismiss();
                    }
                };
                builder.setSingleChoiceItems(arrayOfString, -1, local1);
                builder.create().show();
                return true;
            case R.id.books:
                displayCategory("Books");
                return true;
            case R.id.movies:
                displayCategory("Movies");
                return true;
            case R.id.music:
                displayCategory("Music");
                return true;
            case R.id.restaurants:
                displayCategory("Restaurants");
                return true;
            default:
                return false;
        }

    }

    public void setReminder(String paramString)
    {
        this.helper.getReadableDatabase().query("tasks", new String[] { "_id", "time" }, "task=?", new String[] { paramString }, null, null, null).moveToFirst();
        Handler localHandler = new Handler();
        Runnable local3 = new Runnable()
        {
            public void run()
            {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(MainActivity.this);
                localBuilder.setTitle("Reminder");
                localBuilder.setMessage("You should check out a");
                DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        String str = String.format("DELETE FROM %s WHERE %s = '%s'", new Object[] { "tasks", "task", "a" });
                        MainActivity.this.helper.getWritableDatabase().execSQL(str);
                        MainActivity.this.updateUI();
                    }
                };
                localBuilder.setPositiveButton("Checked!", local1);
                localBuilder.setNegativeButton("Later", null);
                localBuilder.create().show();
            }
        };
        localHandler.postDelayed(local3, 10000L);
    }
}

/* Location:           /Users/rohilbh/temp3/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     com.example.rohilbh.pail.MainActivity
 * JD-Core Version:    0.6.2
 */