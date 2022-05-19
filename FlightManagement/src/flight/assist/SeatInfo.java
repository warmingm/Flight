package flight.assist;

import java.sql.*;
import javax.swing.*;
/**
 * 操作数据库表orderedSeats的辅助类，用于判断航班是否已满及刷新航班已预定的票数
 */
public class SeatInfo {
  DataBaseManager dbManager = DataBaseManager.getInstance();
  public SeatInfo() {
  }
  /**
   *判断航班是否已满
   */
  public boolean isFull(String flightNum, String day) {
    String sqlString = "select orderedSeats from orderedSeats where flight='" +
        flightNum +
        "' and flightdate='" + day + "'";
    ResultSet rs = dbManager.getResult(sqlString);
    try {
      if (!rs.next()) {//这一日的航班尚未录入，录入此航班
        sqlString = "insert into orderedSeats values('" + flightNum + "','" +
            day + "',0)";
        dbManager.updateSql(sqlString);
        return false;
      }
      else {//查询已订票数和航班容量，从而判断航班是否已满
        int orderSeats = rs.getInt(1);
        sqlString = "select seat from flight where flight='" + flightNum + "'";
        rs = dbManager.getResult(sqlString);
        int totalSeats = 0;
        if (rs.next())
          totalSeats = rs.getInt(1);
        if (totalSeats == orderSeats)
          return true;
        else
          return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   *订票，增加航班已订票数
   */
  public int bookTicket(String flightNum, String day, int seats) {
    int leftSeats = 0;

    String sql = "select orderedSeats from orderedSeats where flight='" +
        flightNum +
        "' and flightdate='" + day + "'";
    ResultSet rs = dbManager.getResult(sql);
    int bookedSeats = 0;
    try {
      if (rs.next()) {
        bookedSeats = rs.getInt(1);
      }
      else {
        JOptionPane.showMessageDialog(null, "航班信息尚未录入!",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    String sqlString = "select seat,week from flight where flight='" +
        flightNum + "' ";
    rs = dbManager.getResult(sqlString);
    int totalSeats = 0;
    String week = "";
    try {
      while (rs.next()) {
        totalSeats = rs.getInt(1);
        week = rs.getString(2);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    leftSeats = totalSeats - bookedSeats;

    if (leftSeats >= seats) {
      sql = "update orderedSeats set orderedSeats=" + (bookedSeats + seats) +
          "where flight='" + flightNum +
          "' and flightdate='" + day + "'";
      dbManager.updateSql(sql);
      return -1;
    }
    else
      return leftSeats;
  }

  /**
   *退票，减少航班已订票数
  */
  public void refundTicket(String flightNum, String day, int seats) {
    String sql = "select orderedSeats from orderedSeats where flight='" +
        flightNum +
        "' and flightdate='" + day + "'";
    ResultSet rs = dbManager.getResult(sql);
    int bookedSeats = 0;
    try {
      if (rs.next()) {
        bookedSeats = rs.getInt(1);
      }
      else {
        JOptionPane.showMessageDialog(null, "航班信息尚未录入!",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        return;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (bookedSeats < seats){
      JOptionPane.showMessageDialog(null, "退票数大于已定票数!",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return;
    }
    else {
      sql = "update orderedSeats set orderedSeats=" + (bookedSeats - seats) +
          "where flight='" + flightNum +
          "' and flightdate='" + day + "'";
      dbManager.updateSql(sql);
    }
  }
}
