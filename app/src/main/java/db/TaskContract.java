package db;

public class TaskContract
{
  public static final String DB_NAME = "com.example.TodoList.db.tasks";
  public static final int DB_VERSION = 1;
  public static final String TABLE = "tasks";

  public class Columns
  {
    public static final String CATEGORY = "category";
    public static final String TASK = "task";
    public static final String TIME = "time";
    public static final String _ID = "_id";

    public Columns()
    {
    }
  }
}

/* Location:           /Users/rohilbh/temp3/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     db.TaskContract
 * JD-Core Version:    0.6.2
 */