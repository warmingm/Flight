package flight.assist;

import java.sql.*;
/**
 * 数据库访问辅助类，使用了Singleton软件设计模式
 */
public class DataBaseManager {
  private static DataBaseManager instance;
  private Connection con=null;
  private ResultSet rs;
  private Statement stmt;
  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=flight";
  String userName="sa";
  String userPwd="sa";

  public static DataBaseManager getInstance() {
    if (instance == null) {
      instance = new DataBaseManager();
    }
    return instance;
  }

  private DataBaseManager() {
	 try
	{
		 //导入驱动
		Class.forName(driverName);
		System.out.println("加载驱动成功！");
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("加载驱动失败！");
	}
	try{
		//连接数据库
		con = DriverManager.getConnection(dbURL,userName,userPwd);
		System.out.println("连接数据库成功！");
	}catch(Exception e)
	{
		e.printStackTrace();
		System.out.print("SQL Server连接失败！");
	}
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                 ResultSet.CONCUR_UPDATABLE);
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }
  }

  public ResultSet getResult(String strSQL) {
    try {
      rs = stmt.executeQuery(strSQL);
      return rs;
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
      return null;
    }

  }

  public int updateSql(String strSQL) {
    try {
      int i = stmt.executeUpdate(strSQL);
      con.commit();
      return i;
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
      return -1;
    }
  }

  public void closeConnection() {
    try {
      con.close();
      this.instance=null;
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }
  }
}
