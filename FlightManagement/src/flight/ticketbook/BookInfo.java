package flight.ticketbook;

import flight.assist.DataBaseManager;
/**
 * �������ݿ��bookInfo�ĸ����࣬���ڲ���һ��������Ϣ
 */
public class BookInfo {
  DataBaseManager dbManager = DataBaseManager.getInstance();
  public BookInfo(String[] string) {
    String sql = "insert into bookInfo values('" +
        string[16] + "','" + //������
        string[12] + "','" + //����
        string[13] + "','" + //����֤��
        string[4] + "','" + //�����
        string[11] + "','" + //���̺����
        string[5] + "','" + //���̡�˫�̡�����
        string[3] + "','" + //����
        string[10] + "','" + //��������
        string[15] + "','" + //��λ��Ϣ
        string[14] + "','" + //����Ʊ��
        string[7] + "')" ;//�ܼ�
        
       // string[17]+ "')";//��λ��Ϣ

    try {
      dbManager.updateSql(sql);
    }
    catch (Exception ex) {
    }
  }
}