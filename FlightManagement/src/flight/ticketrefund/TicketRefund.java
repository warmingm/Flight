package flight.ticketrefund;

import flight.assist.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.sql.*;
/**
 * ��Ʊ
 */

public class TicketRefund
    extends JFrame
    implements ActionListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private JTextField bookIDTextField = new JTextField(15), customerIDTextField = new JTextField(20),
      nameTextField = new JTextField(8), flightInfoTextField = new JTextField(27),
      childNumTextField = new JTextField(8), adultNumTextField = new JTextField(8),
      originCostTextField = new JTextField(8), refundCostTextField = new JTextField(8),
      timeTextField1 = new JTextField(8),
      timeTextField2 = new JTextField(8),
      childRefundNumTextField = new JTextField("0", 10),
      adultRefundNumTextField = new JTextField("0", 10);

  private JButton queryButton = new JButton("��ѯ"), okButton = new JButton("��Ʊ"),
      cancelButton = new JButton("�˳�"), rewriteButton = new JButton("����");

  private JLabel timeLabel1 = new JLabel("      ����ʱ��"),
      timeLabel2 = new JLabel("            ");

  private String name, flight1, flight2, ticketType,
      leaveTime1, leaveTime2,
      childNum, adultNum, cost,seatid;//����ѡ��

  private SeatInfo seatInfo = new SeatInfo();

  public TicketRefund() {
    nameTextField.setEditable(false);
    flightInfoTextField.setEditable(false);
    childNumTextField.setEditable(false);
    adultNumTextField.setEditable(false);
    originCostTextField.setEditable(false);
    refundCostTextField.setEditable(false);
    timeTextField1.setEditable(false);
    timeTextField2.setEditable(false);

    JLabel orderIDLabel = new JLabel("�� �� �� ");
    JLabel jlID = new JLabel("   ����֤��");

    JPanel jpInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpInput.setBorder(new TitledBorder("������Ϣ"));

    jpInput.add(orderIDLabel);
    jpInput.add(bookIDTextField);
    jpInput.add(jlID);
    jpInput.add(customerIDTextField);

    JLabel jlName = new JLabel("�ͻ�����");
    JLabel jlFlight = new JLabel("  ������Ϣ");
    JLabel jlChild = new JLabel("��ͯƱ��");
    JLabel jlAdult = new JLabel("����Ʊ��");
    JLabel jlOriCost = new JLabel("ԭ  Ʊ  ��");
    JLabel jlTuiCost = new JLabel("��  Ʊ  ��");

    JPanel jpTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpTop.add(jlName);
    jpTop.add(nameTextField);
    jpTop.add(jlFlight);
    jpTop.add(flightInfoTextField);

    JPanel jpNum = new JPanel();
    jpNum.setLayout(new GridLayout(2, 1));
    JPanel jp1 = new JPanel();
    jp1.add(jlChild);
    jp1.add(childNumTextField);
    JPanel jp2 = new JPanel();
    jp2.add(jlAdult);
    jp2.add(adultNumTextField);
    jpNum.add(jp1);
    jpNum.add(jp2);

    JPanel jpCost = new JPanel();
    jpCost.setLayout(new GridLayout(2, 1));
    JPanel jp3 = new JPanel();
    jp3.add(jlOriCost);
    jp3.add(originCostTextField);
    JPanel jp4 = new JPanel();
    jp4.add(jlTuiCost);
    jp4.add(refundCostTextField);
    jpCost.add(jp3);
    jpCost.add(jp4);

    JPanel jpTime = new JPanel();
    jpTime.setLayout(new GridLayout(2, 1));
    JPanel jp5 = new JPanel();
    jp5.add(timeLabel1);
    jp5.add(timeTextField1);
    JPanel jp6 = new JPanel();
    jp6.add(timeLabel2);
    jp6.add(timeTextField2);
    jpTime.add(jp5);
    jpTime.add(jp6);

    JPanel jpCenter = new JPanel();
    jpCenter.setLayout(new BorderLayout());
    jpCenter.add(jpNum, BorderLayout.WEST);
    jpCenter.add(jpCost, BorderLayout.CENTER);
    jpCenter.add(jpTime, BorderLayout.EAST);

    JPanel jpInfo = new JPanel();
    jpInfo.setBorder(new TitledBorder("������Ϣ"));
    jpInfo.setLayout(new BorderLayout());
    jpInfo.add(jpTop, BorderLayout.NORTH);
    jpInfo.add(jpCenter, BorderLayout.CENTER);

    timeLabel2.setVisible(false);
    timeTextField2.setVisible(false);

    JPanel jpTuiPiao = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jpTuiPiao.setBorder(new TitledBorder("��Ʊ"));
    jpTuiPiao.add(new JLabel("��Ʊ��:"));
    jpTuiPiao.add(new JLabel("��ͯƱ"));
    jpTuiPiao.add(childRefundNumTextField);
    jpTuiPiao.add(new JLabel("����Ʊ:"));
    jpTuiPiao.add(adultRefundNumTextField);

    JPanel jpButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jpButtons.setBorder(new TitledBorder("����"));
    jpButtons.add(queryButton);
    jpButtons.add(rewriteButton);
    jpButtons.add(okButton);
    jpButtons.add(cancelButton);

    JPanel jp = new JPanel();
    jp.setLayout(new BorderLayout());
    jp.add(jpInput, BorderLayout.NORTH);
    jp.add(jpInfo, BorderLayout.CENTER);
    jp.add(jpTuiPiao, BorderLayout.SOUTH);

    JPanel jpTotal = new JPanel();
    jpTotal.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    jpTotal.setLayout(new BorderLayout());
    jpTotal.add(jp, BorderLayout.CENTER);
    jpTotal.add(jpButtons, BorderLayout.SOUTH);

    this.getContentPane().add(jpTotal);

    queryButton.addActionListener(this);
    rewriteButton.addActionListener(this);
    okButton.addActionListener(this);
    cancelButton.addActionListener(this);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        TicketRefund.this.setVisible(false);
        TicketRefund.this.dispose();
      }
    }
    );

    this.setSize(470, 370);
    this.setResizable(false);
    this.setTitle("������Ʊϵͳ");

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == queryButton)//��ѯ
      query();
    else if (e.getSource() == rewriteButton)//���
      rewrite();
    else if (e.getSource() == okButton)//�ύ
      refund();
    else if (e.getSource() == cancelButton) {//ȡ��
      this.setVisible(false);
      this.dispose();
    }
  }
  /**
   * ��ѯ������Ϣ
   */
  private void query() {
    String bookID = bookIDTextField.getText().trim();
    if (bookID.length() == 0) {
      JOptionPane.showMessageDialog(null, "�����Ų���Ϊ��",
                                    "������Ϣ", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String id = customerIDTextField.getText().trim();
    if (id.length() == 0) {
      JOptionPane.showMessageDialog(null, "����֤�Ų���Ϊ��",
                                    "������Ϣ", JOptionPane.ERROR_MESSAGE);
      return;
    }

    boolean isValid = getClientInfo(bookID, id);

    if (isValid == false) {
      rewrite();
      return;
    }

    else {
      nameTextField.setText(name);
      childNumTextField.setText(childNum);
      adultNumTextField.setText(adultNum);
      originCostTextField.setText(cost);
      refundCostTextField.setText("�˻�70%��Ǯ");

      if (Integer.parseInt(childNum) == 0) {
        childRefundNumTextField.setText("0");
        childRefundNumTextField.setEditable(false);
      }
      else {
        childRefundNumTextField.setEditable(true);
      }

      if (Integer.parseInt(adultNum) == 0) {
        adultRefundNumTextField.setText("0");
        adultRefundNumTextField.setEditable(false);
      }
      else {
        adultRefundNumTextField.setEditable(true);
      }

      if (ticketType.equals("����")) {
        timeLabel1.setText("����ʱ��");
        timeTextField1.setText(leaveTime1);

        timeLabel2.setVisible(false);
        timeTextField2.setVisible(false);

        flightInfoTextField.setText("(���̻�Ʊ)" + "�����:" + flight1);
      }

      else if (ticketType.equals("����")) {
        timeLabel1.setText("����ʱ��");
        timeTextField1.setText(leaveTime1);

        timeLabel2.setText("����ʱ��");
        timeLabel2.setVisible(true);

        timeTextField2.setText(leaveTime2);
        timeTextField2.setVisible(true);

        flightInfoTextField.setText("(������Ʊ) " + " ȥ: " + flight1 + "; ��: " + flight2);
      }

      else if (ticketType.equals("����")) {
        timeLabel1.setText("��һ����ʱ��");
        timeTextField1.setText(leaveTime1);

        timeLabel2.setText("�ڶ�����ʱ��");
        timeLabel2.setVisible(true);
        timeTextField2.setText(leaveTime2);
        timeTextField2.setVisible(true);

        flightInfoTextField.setText("(���̻�Ʊ) " + "����1: " + flight1 + "; ����2: " +
                              flight2);
      }
    }
  }
  /**
   * ��ո��ֶ�
   */
  private void rewrite() {
    bookIDTextField.setText("");
    customerIDTextField.setText("");
    nameTextField.setText("");
    flightInfoTextField.setText("");

    childNumTextField.setText("");
    adultNumTextField.setText("");
    originCostTextField.setText("");
    refundCostTextField.setText("");

    timeTextField1.setText("");
    timeTextField2.setText("");

    childRefundNumTextField.setText("0");
    adultRefundNumTextField.setText("0");

    timeLabel1.setText("����ʱ��");

    childRefundNumTextField.setEditable(true);
    adultRefundNumTextField.setEditable(true);

    timeLabel2.setVisible(false);
    timeTextField2.setVisible(false);
  }
  /**
   * ��Ʊ
   */
  private void refund() {
    String bookID = bookIDTextField.getText().trim();
    if (bookID.length() == 0) {
      JOptionPane.showMessageDialog(null, "�����Ų���Ϊ��",
                                    "������Ϣ", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String customerid = customerIDTextField.getText().trim();
    if (customerid.length() == 0) {
      JOptionPane.showMessageDialog(null, "����֤�Ų���Ϊ��",
                                    "������Ϣ", JOptionPane.ERROR_MESSAGE);
      return;
    }

    boolean isValid = getClientInfo(bookID, customerid);

    if (isValid == false) {
      rewrite();
      return;
    }

    else {
      String childRefundNum = childRefundNumTextField.getText().trim();
      String adultRefundNum = adultRefundNumTextField.getText().trim();

      int cRefundNum = Integer.parseInt(childRefundNum);
      int aRefundNum = Integer.parseInt(adultRefundNum);

      if (cRefundNum == 0 && aRefundNum == 0) {
        JOptionPane.showMessageDialog(null, "��������Ʊ��",
                                      "������Ϣ", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (cRefundNum != 0 && cRefundNum > Integer.parseInt(childNum)) {
        JOptionPane.showMessageDialog(null, "��Ʊ�������Ѷ�Ʊ��,�밴\"��ѯ\"��ť�鿴��Ϣ",
                                      "������Ϣ", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (aRefundNum != 0 && aRefundNum > Integer.parseInt(adultNum)) {
        JOptionPane.showMessageDialog(null, "��Ʊ�������Ѷ�Ʊ��,�밴\"��ѯ\"��ť�鿴��Ϣ",
                                      "������Ϣ", JOptionPane.ERROR_MESSAGE);
        return;
      }

      operationForRefund(bookID, customerid, cRefundNum, aRefundNum);
    }

  }
  /**
   * �������ݿ�
   */
  private void operationForRefund(String bookID, String customerId,
                                   int childRefundNum, int adultRefundNum) {
    int newChildNum = Integer.parseInt(childNum) - childRefundNum;
    int newAdultNum = Integer.parseInt(adultNum) - adultRefundNum;
    float refundCost = caculateRefundCost(childRefundNum, adultRefundNum);
    float newCost = Float.parseFloat(cost) - refundCost;

    try {
      String sql = "update bookInfo set childTickets='" +
          String.valueOf(newChildNum) +
          "',adultTickets='" + String.valueOf(newAdultNum) +
          "',cost='" + String.valueOf(newCost) + "' where bookID='" + bookID +
          "' and customerID='" + customerId + "'";
      int result = dbManager.updateSql(sql);
      if (result > 0) {
        int totalReundNum = childRefundNum + adultRefundNum;

        seatInfo.refundTicket(flight1, leaveTime1, totalReundNum);

        if (flight2.length() != 0)
          seatInfo.refundTicket(flight2, leaveTime2, totalReundNum);

        if (newChildNum == 0 && newAdultNum == 0)
          JOptionPane.showMessageDialog(null,
                                        "��ϲ����Ʊ�ɹ�!" + "\n" + "�ö�����������!" + "\n" +
                                        "�㽫���" + refundCost +
                                        "����ƱǮ" + "\n" + "��ӭ���ٴ�ѡ������!",
                                        "��Ʊ�ɹ�", JOptionPane.INFORMATION_MESSAGE);
        else
          JOptionPane.showMessageDialog(null,
                                        "��ϲ����Ʊ�ɹ�!" + "\n" + "������ʣ��" +
                                        newChildNum +
                                        "�Ŷ�ͯƱ��" + newAdultNum + "�ų���Ʊ" + "\n" +
                                        "�㽫���" + refundCost +
                                        "����ƱǮ" + "\n" + "��ӭ���ٴ�ѡ������!", "��Ʊ�ɹ�",
                                        JOptionPane.INFORMATION_MESSAGE);
      }
      else {
        JOptionPane.showMessageDialog(null, "���ݿ����ʧ��", "���ݿ����ʧ��",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * ������Ʊ���
   */
  private float caculateRefundCost(int childTuiPiaoShu, int adultTuiPiaoShu) {
    float tuiPiaoCost = 0;

    try {
      String sqlString =
          "select childFare,adultFare from flight where flight='" + flight1 +
          "'";
      ResultSet rs = dbManager.getResult(sqlString);

      float childFare1 = 0;
      float adultFare1 = 0;
      while (rs.next()) {
        childFare1 = rs.getFloat(1);
        adultFare1 = rs.getFloat(2);
      }

      float childFare2 = 0;
      float adultFare2 = 0;
      if (flight2.length() != 0) {
        String sqlString2 =
            "select childFare,adultFare from flight where flight='" + flight2 +
            "'";
        ResultSet rs2 = dbManager.getResult(sqlString2);

        while (rs2.next()) {
          childFare2 = rs2.getFloat(1);
          adultFare2 = rs2.getFloat(2);
        }
      }

      if (flight2.length() == 0) {
        tuiPiaoCost = (childFare1 * childTuiPiaoShu +
                       adultFare1 * adultTuiPiaoShu) * (float) 0.7;
      }
      else {
        tuiPiaoCost = ( (childFare1 + childFare2) * childTuiPiaoShu +
                       (adultFare1 + adultFare2) * adultTuiPiaoShu) *
            (float) 0.7;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return tuiPiaoCost;
  }
  /**
   * ��ѯ�˿ͼ�������Ϣ
   */
  private boolean getClientInfo(String bookID, String customerID) {
    boolean isBookExist = false;
    boolean isIDRight = false;
    String sql = "select * from bookInfo where bookID='" + bookID + "'";
    ResultSet rs = dbManager.getResult(sql);
    try {
      if (rs.next()) {
        isBookExist = true;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (isBookExist == false) {
      JOptionPane.showMessageDialog(null, "�����Ų�����", "������Ϣ",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    }

    sql = "select * from bookInfo where bookID='" + bookID +
        "' and customerID='" + customerID + "'";
    rs = dbManager.getResult(sql);
    try {
      if (rs.next()) {
        isIDRight = true;
        name = rs.getString("name").trim();
        flight1 = rs.getString("flight").trim();
        flight2 = rs.getString("returnFlight").trim();
        ticketType = rs.getString("bookType").trim();
        leaveTime1 = rs.getString("ticketDate").trim();
        leaveTime2 = rs.getString("returnDate").trim();
        childNum = rs.getString("childTickets").trim();
        adultNum = rs.getString("adultTickets").trim();
        cost = rs.getString("cost").trim();
        seatid=rs.getString("seatid").trim();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (isBookExist == true && isIDRight == false) {
      JOptionPane.showMessageDialog(null, "����֤�Ų���ȷ", "������Ϣ",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    }

    boolean isValid = canReturn(leaveTime1);

    if (!isValid) {
      JOptionPane.showMessageDialog(null, "��Ʊ�Ѿ�����!��������!",
                                    "������Ϣ", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (isBookExist == true && isIDRight == true) {
      if (Integer.parseInt(childNum) == 0 && Integer.parseInt(adultNum) == 0) {
        JOptionPane.showMessageDialog(null, "�ö������Ѿ���Ч!",
                                      "������Ϣ", JOptionPane.ERROR_MESSAGE);
        return false;
      }
      return true;
    }
    return false;
  }
  /**
  * �ж��Ƿ�������Ʊ����
  */
  private boolean canReturn(String time) {
    String year = time.substring(0, 4);
    String month = time.substring(4, 6);
    String day = time.substring(6, 8);

    int y = Integer.parseInt(year);
    int m = Integer.parseInt(month);
    int d = Integer.parseInt(day);

    //Get the present time
    Calendar cal = Calendar.getInstance();

    cal.setTime(new java.util.Date());

    int py = cal.get(Calendar.YEAR);
    int pm = cal.get(Calendar.MONTH) + 1;
    int pd = cal.get(Calendar.DAY_OF_MONTH);
    if (y < py)return false;
    if (y == py) {
      if (m < pm)
        return false;
      else if (m == pm && d < pd)
        return false;
    }
    return true;
  }
}