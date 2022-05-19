package flight.ticketbook;

import flight.assist.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.Vector;
/**
 *��д�˿���Ϣ����ɶ�Ʊ����
 */
public class ClientFrame
    extends JFrame
    implements ActionListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private JLabel nameLabel = new JLabel("** ��ʵ����:");
  private JLabel idLabel = new JLabel("** ֤������:");
  private JLabel startLabel = new JLabel("    �����ص�:");
  private JLabel endLabel = new JLabel("    ����ص�:");
  private JLabel idLabel1 = new JLabel("(����֤����)");
  private JLabel starttimeLabel = new JLabel("    ����ʱ��:");
  private JLabel returntimeLabel = new JLabel(" ����ʱ��:");
  private JLabel flightLabel = new JLabel("    �� �� ��  :");
  private JLabel telephoneLabel = new JLabel("** ��ϵ�绰:");
  private JLabel emailLabel = new JLabel("  E - Mail :  ");
  //private JLabel psLabel = new JLabel("     ��̸���:  ");
  private JLabel seatLabel = new JLabel("     ѡ����λ(��+��):  "); //ѡ��
  private JLabel returnflightLabel = new JLabel("���̺���:");
  private JLabel adultticketnumberLabel = new JLabel("    ����Ʊ��:");
  //private JLabel childticketnumberLabel = new JLabel(" ��ͯƱ��:");
  private JLabel airfirmLabel = new JLabel(" ���չ�˾:");
  private JLabel styleLabel = new JLabel(" ��Ʊ����:");

  private JTextField jbtnameTextField = new JTextField(" ", 12); //����
  private JTextField jbtidTextField = new JTextField(" ", 20);   //֤����
  private JTextField jbtstartTextField = new JTextField(" ", 12);  //�����ص�
  private JTextField jbtendTextField = new JTextField(" ", 12);    
  private JTextField jbtstarttimeTextField = new JTextField(" ", 12);  //����ʱ��
  private JTextField jbtreturntimeTextField = new JTextField(" ", 12);   
  private JTextField jbtadultticketnumberTextField = new JTextField("1", 12);  //����Ʊ��
  //private JTextField jbtchildticketnumberTextField = new JTextField("0", 12);   //��ͯƱ��
  private JTextField jbtstyleTextField = new JTextField(12);  //��Ʊ����
  private JTextField jbtreturnflightTextField = new JTextField(12); 
  private JTextField jbtairfirmTextField = new JTextField(12);  //���չ�˾
  private JTextField jbttelephoneTextField = new JTextField(12);  //�绰
  private JTextField jbtemailTextField = new JTextField(12);   //����
  private JTextField jbtqqTextField = new JTextField(12);
  private JTextField jbtflightTextField = new JTextField(12);  //�����
  private JTextField jbtseatidTextField = new JTextField(12);  //ѡ��

  //private JTextArea textArea = new JTextArea(10, 3);

  private JButton submitButton = new JButton("��ɲ��ύ");
  private JButton rewriteButton = new JButton("�� �� �� ��");
  private JButton returnButton1 = new JButton("����ʵʱ��Ʊ");
  private JPanel p10 = new JPanel();

  private String[] string = new String[18];
  
  /*
    string[0]----------------��ɳ���
    string[1]----------------�������
    string[2]----------------�������ڣ���ʽΪ��****��**��**��
    string[3]----------------�������ڣ���ʽΪ��********
    string[4]----------------�����
    string[5]----------------���ͣ����̡�˫�̡�����
    string[6]----------------���չ�˾
    string[7]----------------�ܼ�
    string[8]----------------����+��Ʊ��������Ʊ��˫��Ʊ������Ʊ
    string[9]----------------���̺������ڣ���ʽΪ��****��**��**��
    string[10]---------------���̺������ڣ���ʽΪ��********
    string[11]---------------���̺����
    string[12]---------------����
    string[13]---------------����֤��
    string[14]---------------����Ʊ��
    string[15]---------------��ͯƱ��
    string[16]---------------������
    string[17]---------------��λ��Ϣ
    
   */
  int adultnumber = 0;
  //int childnumber = 0;
  int ticketnumber = 0;
  int seatid;
  

  private SeatInfo seatinformation = new SeatInfo();
  public ClientFrame() {
    jbtstarttimeTextField.setEditable(false);
    jbtreturntimeTextField.setEditable(false);
    jbtstartTextField.setEditable(false);
    jbtendTextField.setEditable(false);
    jbtflightTextField.setEditable(false);
    jbtairfirmTextField.setEditable(false);
    jbtreturnflightTextField.setEditable(false);
    jbtstyleTextField.setEditable(false);
    

    JPanel p1 = new JPanel();
    p1.setLayout(new FlowLayout(FlowLayout.LEFT));
    p1.add(nameLabel);
    p1.add(jbtnameTextField);

    JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout(FlowLayout.LEFT));
    p2.add(idLabel);
    p2.add(jbtidTextField);
    p2.add(idLabel1);

    JPanel p3 = new JPanel();
    p3.setLayout(new FlowLayout(FlowLayout.LEFT));
    p3.add(startLabel);
    p3.add(jbtstartTextField);
    p3.add(styleLabel);
    p3.add(jbtstyleTextField);

    JPanel p4 = new JPanel();
    p4.setLayout(new FlowLayout(FlowLayout.LEFT));
    p4.add(endLabel);
    p4.add(jbtendTextField);
    p4.add(airfirmLabel);
    p4.add(jbtairfirmTextField);

    JPanel p5 = new JPanel();
    p5.setLayout(new FlowLayout(FlowLayout.LEFT));
    p5.add(starttimeLabel);
    p5.add(jbtstarttimeTextField);
    p5.add(returntimeLabel);
    p5.add(jbtreturntimeTextField);

    JPanel p6 = new JPanel();
    p6.setLayout(new FlowLayout(FlowLayout.LEFT));
    p6.add(adultticketnumberLabel);
    p6.add(jbtadultticketnumberTextField);
    //p6.add(childticketnumberLabel);
    //p6.add(jbtchildticketnumberTextField);

    JPanel p7 = new JPanel();
    p7.setLayout(new FlowLayout(FlowLayout.LEFT));
    p7.add(telephoneLabel);
    p7.add(jbttelephoneTextField);
    p7.add(emailLabel);
    p7.add(jbtemailTextField);

    /*JPanel p9 = new JPanel();
    textArea.setLineWrap(true);
    textArea.setBorder(new LineBorder(new Color(220, 220, 255), 2));
    p9.setLayout(new BorderLayout());
    //p9.add(psLabel, BorderLayout.WEST);
    JScrollPane scrollPane = new JScrollPane(textArea);
    p9.add(scrollPane, BorderLayout.CENTER);*/
    
    //ѡ��
    JPanel p9 = new JPanel();
    p9.setLayout(new FlowLayout(FlowLayout.LEFT));
    p9.add(seatLabel);
    p9.add(jbtseatidTextField);

    JPanel p11 = new JPanel();
    p11.setLayout(new FlowLayout(FlowLayout.CENTER));
    p11.add(submitButton);
    p11.add(rewriteButton);
    p11.add(returnButton1);
    
    JPanel p12 = new JPanel();
    p12.setLayout(new FlowLayout(FlowLayout.LEFT));
    p12.add(flightLabel);
    p12.add(jbtflightTextField);
    p12.add(returnflightLabel);
    p12.add(jbtreturnflightTextField);
    
    

    p10.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    p10.setLayout(null);
    JLabel title = new JLabel("(��**�ı�����д)", JLabel.LEFT);
    p10.add(title);
    p10.add(p1);
    p10.add(p2);
    p10.add(p3);
    p10.add(p4);
    p10.add(p5);
    p10.add(p6);
    p10.add(p7);
    p10.add(p9);
    p10.add(p11);
    p10.add(p12);
    

    title.reshape(60, 20, 350, 10);
    p1.reshape(40, 30, 350, 30);
    p2.reshape(40, 60, 350, 30);
    p3.reshape(40, 90, 350, 30);
    p4.reshape(40, 120, 350, 30);
    p5.reshape(40, 150, 350, 30);
    p12.reshape(40, 180, 350, 30);
    p6.reshape(40, 210, 350, 30);
    p7.reshape(40, 240, 350, 30);
    p9.reshape(40, 275, 345, 80);
    p11.reshape(72, 365, 350, 30);
    rewriteButton.addActionListener(this);
    submitButton.addActionListener(this);
    returnButton1.addActionListener(this);
    this.getContentPane().add(p10);
    this.setTitle("�ͻ�����");
    this.setSize(450, 460);
  }
  /**
   *��ʼ�����ֶ�
   */
  public void initTextField(String[] string2) {
    this.string = string2;
    jbtstartTextField.setText(string[0]);
    jbtendTextField.setText(string[1]);
    jbtstarttimeTextField.setText(string[2]);

    jbtairfirmTextField.setText(string[6]);
    jbtflightTextField.setText(string[4]);
    jbtstyleTextField.setText(string[8]);

    if (string[5].equals("����")) {
      jbtreturnflightTextField.setVisible(false);
      returnflightLabel.setVisible(false);
      returntimeLabel.setVisible(false);
      jbtreturntimeTextField.setVisible(false);
      this.jbtnameTextField.setText("");
      this.jbtidTextField.setText("");
      this.jbtqqTextField.setText("");
      //this.textArea.setText("");
      this.jbtseatidTextField.setText(""); //ѡ��
      this.jbttelephoneTextField.setText("");
      this.jbtadultticketnumberTextField.setText("1");
      //this.jbtchildticketnumberTextField.setText("0");
      string[11] = ""; //û�з��̺���
      string[10] = ""; //����ʱ��Ϊ��
    }
    else if (string[5].equals("����")) {
      jbtreturnflightTextField.setVisible(true);
      returnflightLabel.setVisible(true);
      returntimeLabel.setVisible(true);
      jbtreturntimeTextField.setVisible(true);
      jbtreturnflightTextField.setText(string[11]);
      jbtreturntimeTextField.setText(string[9]);
    }
  }

  public void actionPerformed(ActionEvent e) {
    int len1 = jbtnameTextField.getText().trim().length();
    int len2 = jbtidTextField.getText().trim().length();
    int len6=  jbtseatidTextField.getText().trim().length(); //ѡ��
    int len3 = jbtadultticketnumberTextField.getText().trim().length();
    //int len4 = jbtchildticketnumberTextField.getText().trim().length();
    int len5 = jbttelephoneTextField.getText().trim().length();

    if (e.getSource() == submitButton) {//�ύ
      string[12] = jbtnameTextField.getText().trim();
      string[13] = jbtidTextField.getText().trim();
      //string[17] = jbtseatidTextField.getText().trim(); //ѡ��

      int ticketsLeftForSingle = 0; //���̵�ʣ��Ʊ����-1��ʾʣ��Ʊ�����Թ�Ӧ����Ʊ��
      int ticketsLeftForDouble = 0; //���̵�ʣ��Ʊ����-1��ʾʣ��Ʊ�����Թ�Ӧ����Ʊ��
      if (len1 == 0 || len2 == 0 ||  len3 == 0 || len5 == 0||len6 == 0) {
        String str = getstring(len1, len2,  len3, len5,len6);
        JOptionPane.showMessageDialog(this, str, "������Ϣ��",
                                      JOptionPane.ERROR_MESSAGE);
      }
      else {
    	  
        adultnumber = Integer.parseInt(jbtadultticketnumberTextField.getText().trim());
        //childnumber = Integer.parseInt(jbtchildticketnumberTextField.getText().trim());
        seatid=Integer.parseInt(jbtseatidTextField.getText().trim());
        
        string[14] = String.valueOf(adultnumber);
        //string[15] = String.valueOf(childnumber);

        ticketnumber = adultnumber;
        
        ResultSet Rs=dbManager.getResult("select seatid from seatInfo");
        Vector<String> vec=new Vector<String>();
		try {
			while(Rs.next()) {
				String seatid=Rs.getString("seatid");
				vec.add(seatid);
			
			string[15]=String.valueOf(seatid);  //ѡ��
			
			//����
			if (string[5].toString().trim().equals("����")) {
			  ticketsLeftForSingle = seatinformation.bookTicket(string[4], string[3], ticketnumber);
			  if (ticketsLeftForSingle == -1) {//ƱԴ���Թ�Ӧ
			    float adultfare = 0;
			    //float childfare = 0;
			    float totalMoney = 0;
			    try {
			      ResultSet rs = dbManager.getResult(
			          "select adultFare from flight where flight='" +
			          string[4] + "'");
			      while (rs.next()) {
			        adultfare = rs.getFloat(1);
			        //childfare = rs.getFloat(2);
			      }

			    }
			    catch (Exception ex) {
			    }
			    totalMoney = adultnumber * adultfare ;
			    String bookNum = string[3] + string[4] +
			        String.valueOf( (int) (500 * Math.random()));
			    string[16] = bookNum;
			    string[7] = "" + totalMoney;
			    JOptionPane.showMessageDialog(this,
			                                  "   ��ϲ���ύ�ɹ�\n��Ķ�������" + bookNum +
			                                  "\n" + "��Ӧ����ǮΪ" + totalMoney,
			                                  "�ͻ���Ϣ",
			                                  JOptionPane.INFORMATION_MESSAGE);
			    this.dispose();
			    new BookInfo(string); //д�붩Ʊ��Ϣ
			  }
			  else {//ƱԴ�����Թ�Ӧ
			    JOptionPane.showMessageDialog(this,
			                                  "�ǳ���Ǹ��ֻ��" + ticketsLeftForSingle + "�ź���Ʊʣ��\n��������ѡ��Ʊ��",
			                                  "�ͻ���Ϣ",
			                                  JOptionPane.INFORMATION_MESSAGE);
			    jbtadultticketnumberTextField.setText(" ");
			    //jbtchildticketnumberTextField.setText(" ");
			  }

			}
			//˫��
			else {
			  ticketsLeftForSingle = seatinformation.bookTicket(string[4], string[3], ticketnumber);
			  ticketsLeftForDouble = seatinformation.bookTicket(string[11], string[10], ticketnumber);

			  if (ticketsLeftForSingle == -1 && ticketsLeftForDouble == -1) {//ƱԴ���Թ�Ӧ
			    float adultfare = 0;
			    //float childfare = 0;
			    float totalMoney = 0;
			    try {
			      ResultSet rs = dbManager.getResult(
			          "select adultFare from flight where flight='" +
			          string[4] + "'");
			      while (rs.next()) {
			        adultfare = rs.getFloat(1);
			        //childfare = rs.getFloat(2);
			      }

			    }
			    catch (Exception ex) {
			    }
			    totalMoney = adultnumber * adultfare;
			    try {
			      ResultSet rs = dbManager.getResult(
			          "select adultFare from flight where flight='" +
			          string[11] + "'");
			      while (rs.next()) {
			        adultfare = rs.getFloat(1);
			        //childfare = rs.getFloat(2);
			      }

			    }
			    catch (Exception ex) {
			    }
			    totalMoney = totalMoney + adultnumber * adultfare ;
			        //childnumber * childfare;
			    String bookNum = string[3] + string[4] +
			        String.valueOf( (int) (500 * Math.random()));
			    string[16] = bookNum;
			    string[7] = "" + totalMoney;
			    JOptionPane.showMessageDialog(this,
			                                  "   ��ϲ���ύ�ɹ�\n��Ķ�������" + bookNum +
			                                  "\n" + "��Ӧ����ǮΪ" + totalMoney,
			                                  "�ͻ���Ϣ",
			                                  JOptionPane.INFORMATION_MESSAGE);
			    this.dispose();

			    new BookInfo(string);

			  }

			  else {//ƱԴ�����Թ�Ӧ
			    if (ticketsLeftForSingle != -1) {
			      JOptionPane.showMessageDialog(this,
			                                    "�ǳ���Ǹ��ֻ��" + ticketsLeftForSingle +
			                                    "�ŵ�һ����Ʊʣ��\n��������ѡ��Ʊ��",
			                                    "�ͻ���Ϣ",
			                                    JOptionPane.INFORMATION_MESSAGE);
			      jbtadultticketnumberTextField.setText(" ");
			      //jbtchildticketnumberTextField.setText(" ");
			    }
			    else {
			      if (ticketsLeftForDouble != -1) {
			        JOptionPane.showMessageDialog(this,
			                                      "�ǳ���Ǹ��ֻ��" + ticketsLeftForDouble +
			                                      "�ŷ��غ���Ʊʣ��\n��������ѡ��Ʊ��",
			                                      "�ͻ���Ϣ",
			                                      JOptionPane.INFORMATION_MESSAGE);
			        jbtadultticketnumberTextField.setText(" ");
			        //jbtchildticketnumberTextField.setText(" ");
			      }
			    }
			  }

			}

     }
		} catch (HeadlessException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    if (e.getSource() == rewriteButton) {//��ո��ֶ�
      jbtnameTextField.setText("");
      jbtidTextField.setText("");
      jbtadultticketnumberTextField.setText("");
      //jbtchildticketnumberTextField.setText("");
      jbttelephoneTextField.setText("");
      jbtqqTextField.setText("");
      jbtemailTextField.setText("");
      jbtseatidTextField.setText(""); //ѡ��
      //textArea.setText("");
    }
    if (e.getSource() == returnButton1) this.dispose(); //����
         
    }
    
  }

  public String getstring(int ticketsLeftForSingle, int ticketsLeftForDouble, int r, int l,int e) {
    if (ticketsLeftForSingle == 0)
      return "��������Ϊ�գ�";
    else if (ticketsLeftForDouble == 0)
      return "֤���Ų���Ϊ�գ�";
    else if (r == 0)
      return "����Ʊ������Ϊ�գ�";
    
    else if (l == 0)
      return "�绰���벻��Ϊ��";
    else if (e == 0)
        return "��λ��";
    
    return "ERROR!";
  }
}