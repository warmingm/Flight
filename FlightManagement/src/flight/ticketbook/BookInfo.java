package flight.ticketbook;

import flight.assist.DataBaseManager;
/**
 * 操作数据库表bookInfo的辅助类，用于插入一条订单信息
 */
public class BookInfo {
  DataBaseManager dbManager = DataBaseManager.getInstance();
  public BookInfo(String[] string) {
    String sql = "insert into bookInfo values('" +
        string[16] + "','" + //订单号
        string[12] + "','" + //姓名
        string[13] + "','" + //身份证号
        string[4] + "','" + //航班号
        string[11] + "','" + //返程航班号
        string[5] + "','" + //单程、双程、联程
        string[3] + "','" + //日期
        string[10] + "','" + //返回日期
        string[15] + "','" + //座位信息
        string[14] + "','" + //成人票数
        string[7] + "')" ;//总价
        
       // string[17]+ "')";//座位信息

    try {
      dbManager.updateSql(sql);
    }
    catch (Exception ex) {
    }
  }
}
