package flight.assist;

import java.sql.*;
/**
 * ���ݿ���ʸ����࣬ʹ����Singleton������ģʽ
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
		 //��������
		Class.forName(driverName);
		System.out.println("���������ɹ���");
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("��������ʧ�ܣ�");
	}
	try{
		//�������ݿ�
		con = DriverManager.getConnection(dbURL,userName,userPwd);
		System.out.println("�������ݿ�ɹ���");
	}catch(Exception e)
	{
		e.printStackTrace();
		System.out.print("SQL Server����ʧ�ܣ�");
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
