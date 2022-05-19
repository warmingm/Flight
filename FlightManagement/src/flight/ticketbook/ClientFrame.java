package flight.ticketbook;

import flight.assist.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.Vector;
/**
 *填写顾客信息，完成订票任务
 */
public class ClientFrame
    extends JFrame
    implements ActionListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private JLabel nameLabel = new JLabel("** 真实姓名:");
  private JLabel idLabel = new JLabel("** 证件号码:");
  private JLabel startLabel = new JLabel("    出发地点:");
  private JLabel endLabel = new JLabel("    到达地点:");
  private JLabel idLabel1 = new JLabel("(身份证号码)");
  private JLabel starttimeLabel = new JLabel("    出发时间:");
  private JLabel returntimeLabel = new JLabel(" 返回时间:");
  private JLabel flightLabel = new JLabel("    航 班 号  :");
  private JLabel telephoneLabel = new JLabel("** 联系电话:");
  private JLabel emailLabel = new JLabel("  E - Mail :  ");
  //private JLabel psLabel = new JLabel("     简短附言:  ");
  private JLabel seatLabel = new JLabel("     选择座位(行+列):  "); //选座
  private JLabel returnflightLabel = new JLabel("返程航班:");
  private JLabel adultticketnumberLabel = new JLabel("    成人票数:");
  //private JLabel childticketnumberLabel = new JLabel(" 儿童票数:");
  private JLabel airfirmLabel = new JLabel(" 航空公司:");
  private JLabel styleLabel = new JLabel(" 机票类型:");

  private JTextField jbtnameTextField = new JTextField(" ", 12); //姓名
  private JTextField jbtidTextField = new JTextField(" ", 20);   //证件号
  private JTextField jbtstartTextField = new JTextField(" ", 12);  //出发地点
  private JTextField jbtendTextField = new JTextField(" ", 12);    
  private JTextField jbtstarttimeTextField = new JTextField(" ", 12);  //出发时间
  private JTextField jbtreturntimeTextField = new JTextField(" ", 12);   
  private JTextField jbtadultticketnumberTextField = new JTextField("1", 12);  //成人票数
  //private JTextField jbtchildticketnumberTextField = new JTextField("0", 12);   //儿童票数
  private JTextField jbtstyleTextField = new JTextField(12);  //机票类型
  private JTextField jbtreturnflightTextField = new JTextField(12); 
  private JTextField jbtairfirmTextField = new JTextField(12);  //航空公司
  private JTextField jbttelephoneTextField = new JTextField(12);  //电话
  private JTextField jbtemailTextField = new JTextField(12);   //邮箱
  private JTextField jbtqqTextField = new JTextField(12);
  private JTextField jbtflightTextField = new JTextField(12);  //航班号
  private JTextField jbtseatidTextField = new JTextField(12);  //选座

  //private JTextArea textArea = new JTextArea(10, 3);

  private JButton submitButton = new JButton("完成并提交");
  private JButton rewriteButton = new JButton("重 新 输 入");
  private JButton returnButton1 = new JButton("返回实时订票");
  private JPanel p10 = new JPanel();

  private String[] string = new String[18];
  
  /*
    string[0]----------------起飞城市
    string[1]----------------到达城市
    string[2]----------------航班日期，格式为：****年**月**日
    string[3]----------------航班日期，格式为：********
    string[4]----------------航班号
    string[5]----------------类型：单程、双程、联程
    string[6]----------------航空公司
    string[7]----------------总价
    string[8]----------------类型+“票”：单程票、双程票、联程票
    string[9]----------------返程航班日期，格式为：****年**月**日
    string[10]---------------返程航班日期，格式为：********
    string[11]---------------返程航班号
    string[12]---------------姓名
    string[13]---------------身份证号
    string[14]---------------成人票数
    string[15]---------------儿童票数
    string[16]---------------订单号
    string[17]---------------座位信息
    
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
    
    //选座
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
    JLabel title = new JLabel("(带**的必须填写)", JLabel.LEFT);
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
    this.setTitle("客户资料");
    this.setSize(450, 460);
  }
  /**
   *初始化各字段
   */
  public void initTextField(String[] string2) {
    this.string = string2;
    jbtstartTextField.setText(string[0]);
    jbtendTextField.setText(string[1]);
    jbtstarttimeTextField.setText(string[2]);

    jbtairfirmTextField.setText(string[6]);
    jbtflightTextField.setText(string[4]);
    jbtstyleTextField.setText(string[8]);

    if (string[5].equals("单程")) {
      jbtreturnflightTextField.setVisible(false);
      returnflightLabel.setVisible(false);
      returntimeLabel.setVisible(false);
      jbtreturntimeTextField.setVisible(false);
      this.jbtnameTextField.setText("");
      this.jbtidTextField.setText("");
      this.jbtqqTextField.setText("");
      //this.textArea.setText("");
      this.jbtseatidTextField.setText(""); //选座
      this.jbttelephoneTextField.setText("");
      this.jbtadultticketnumberTextField.setText("1");
      //this.jbtchildticketnumberTextField.setText("0");
      string[11] = ""; //没有返程航班
      string[10] = ""; //返程时间为空
    }
    else if (string[5].equals("往返")) {
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
    int len6=  jbtseatidTextField.getText().trim().length(); //选座
    int len3 = jbtadultticketnumberTextField.getText().trim().length();
    //int len4 = jbtchildticketnumberTextField.getText().trim().length();
    int len5 = jbttelephoneTextField.getText().trim().length();

    if (e.getSource() == submitButton) {//提交
      string[12] = jbtnameTextField.getText().trim();
      string[13] = jbtidTextField.getText().trim();
      //string[17] = jbtseatidTextField.getText().trim(); //选座

      int ticketsLeftForSingle = 0; //单程的剩余票数，-1表示剩余票数足以供应欲定票数
      int ticketsLeftForDouble = 0; //返程的剩余票数，-1表示剩余票数足以供应欲定票数
      if (len1 == 0 || len2 == 0 ||  len3 == 0 || len5 == 0||len6 == 0) {
        String str = getstring(len1, len2,  len3, len5,len6);
        JOptionPane.showMessageDialog(this, str, "错误信息！",
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
			
			string[15]=String.valueOf(seatid);  //选座
			
			//单程
			if (string[5].toString().trim().equals("单程")) {
			  ticketsLeftForSingle = seatinformation.bookTicket(string[4], string[3], ticketnumber);
			  if (ticketsLeftForSingle == -1) {//票源足以供应
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
			                                  "   恭喜！提交成功\n你的定单号是" + bookNum +
			                                  "\n" + "你应付价钱为" + totalMoney,
			                                  "客户信息",
			                                  JOptionPane.INFORMATION_MESSAGE);
			    this.dispose();
			    new BookInfo(string); //写入订票信息
			  }
			  else {//票源不足以供应
			    JOptionPane.showMessageDialog(this,
			                                  "非常抱歉！只有" + ticketsLeftForSingle + "张航班票剩余\n请您重新选择票数",
			                                  "客户信息",
			                                  JOptionPane.INFORMATION_MESSAGE);
			    jbtadultticketnumberTextField.setText(" ");
			    //jbtchildticketnumberTextField.setText(" ");
			  }

			}
			//双程
			else {
			  ticketsLeftForSingle = seatinformation.bookTicket(string[4], string[3], ticketnumber);
			  ticketsLeftForDouble = seatinformation.bookTicket(string[11], string[10], ticketnumber);

			  if (ticketsLeftForSingle == -1 && ticketsLeftForDouble == -1) {//票源足以供应
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
			                                  "   恭喜！提交成功\n你的定单号是" + bookNum +
			                                  "\n" + "你应付价钱为" + totalMoney,
			                                  "客户信息",
			                                  JOptionPane.INFORMATION_MESSAGE);
			    this.dispose();

			    new BookInfo(string);

			  }

			  else {//票源不足以供应
			    if (ticketsLeftForSingle != -1) {
			      JOptionPane.showMessageDialog(this,
			                                    "非常抱歉！只有" + ticketsLeftForSingle +
			                                    "张第一航班票剩余\n请您重新选择票数",
			                                    "客户信息",
			                                    JOptionPane.INFORMATION_MESSAGE);
			      jbtadultticketnumberTextField.setText(" ");
			      //jbtchildticketnumberTextField.setText(" ");
			    }
			    else {
			      if (ticketsLeftForDouble != -1) {
			        JOptionPane.showMessageDialog(this,
			                                      "非常抱歉！只有" + ticketsLeftForDouble +
			                                      "张返回航班票剩余\n请您重新选择票数",
			                                      "客户信息",
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
    if (e.getSource() == rewriteButton) {//清空各字段
      jbtnameTextField.setText("");
      jbtidTextField.setText("");
      jbtadultticketnumberTextField.setText("");
      //jbtchildticketnumberTextField.setText("");
      jbttelephoneTextField.setText("");
      jbtqqTextField.setText("");
      jbtemailTextField.setText("");
      jbtseatidTextField.setText(""); //选座
      //textArea.setText("");
    }
    if (e.getSource() == returnButton1) this.dispose(); //返回
         
    }
    
  }

  public String getstring(int ticketsLeftForSingle, int ticketsLeftForDouble, int r, int l,int e) {
    if (ticketsLeftForSingle == 0)
      return "姓名不能为空！";
    else if (ticketsLeftForDouble == 0)
      return "证件号不能为空！";
    else if (r == 0)
      return "成人票数不能为空！";
    
    else if (l == 0)
      return "电话号码不能为空";
    else if (e == 0)
        return "座位号";
    
    return "ERROR!";
  }
}
