package flight.query;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.GridLayout;
/**
 * ��ʾ��ѯ����ı���
 */
public class ResultTable
    extends JFrame {
  JTable table;
  JTable table2;
  JScrollPane pane;
  JScrollPane pane2;
  /**
   *�Ե�����ʽ��ʾ��ѯ��������ڵ��̲�ѯ��
   */
  public ResultTable(String title, ResultSet rs) {
    this.setSize(700, 400);

    String[] name = {
        "�����", "��˾", "��ɵ�", "Ŀ�ĵ�", "���ʱ��", "����ʱ��", "��ͯƱ��", "����Ʊ��", "�ۿ�", "��Ʊ��","����"};
    String[][] data = new String[0][0];
    DefaultTableModel defaultModel = new DefaultTableModel(data, name);
    table = new JTable(defaultModel);
    pane = new JScrollPane(table);
    this.getContentPane().add(pane);

    try {
      while (rs.next()) {
        Vector vector = new Vector();
        vector.addElement(rs.getString(1));
        vector.addElement(rs.getString(2));
        vector.addElement(rs.getString(3));
        vector.addElement(rs.getString(4));
        vector.addElement(rs.getString(5));
        vector.addElement(rs.getString(6));
        vector.addElement(rs.getString(7));
        vector.addElement(rs.getString(8));
        vector.addElement(rs.getString(9));
        vector.addElement(rs.getString(10));
        vector.addElement(rs.getString(12));
        defaultModel.addRow(vector);
      }
      table.revalidate();
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }
    catch (Exception ex) {
      System.out.println(ex.toString());
    }
    this.setTitle(title);
  }

  /**
   *��˫����ʽ��ʾ��ѯ���������˫�̲�ѯ�����̲�ѯ��
   */
  public ResultTable(String title, DefaultTableModel model1,
                     DefaultTableModel model2) {
    this.setSize(700, 400);
    this.getContentPane().setLayout(new GridLayout(2, 1));
    table = new JTable(model1);
    table2 = new JTable(model2);
    pane = new JScrollPane(table);
    this.getContentPane().add(pane);
    pane2 = new JScrollPane(table2);
    this.getContentPane().add(pane2);

    this.setTitle(title);
  }

}