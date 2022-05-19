package flight.query;

import flight.assist.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

public class AirFirmQuery extends JPanel implements ActionListener
{
	   //A bean used to connect to the database and execute SQL operation
	static SqlBean sqlBean = new SqlBean();	
	   	
	private static DefaultComboBoxModel model = new DefaultComboBoxModel();
	private static JComboBox jcb = new JComboBox(model);
	
	   //Used to store the item you choosed inth combobox
	private String airfirm;
	
	private JButton jbQuery = new JButton("查询");	
	
	   //Setup GUI in the Constructor method
	public AirFirmQuery()
	{	
	       //*****************************************************	
	       
		JLabel jl = new JLabel("  航空公司查询");
	    jl.setFont(new Font("Times",Font.BOLD,23));
	    JPanel jpTop = new JPanel();
	    jpTop.add(jl);    
	   
	    JLabel jl2 = new JLabel("        请输入航班名称或者,本查询将");
	    jl2.setHorizontalAlignment(JLabel.CENTER);
	    jl2.setFont(new Font("Times",Font.PLAIN,12));
	    
	    JLabel jl3 = new JLabel("提供你该航空公司所有的航班情况:");
	    jl3.setHorizontalAlignment(JLabel.CENTER);
	    jl3.setFont(new Font("Times",Font.PLAIN,12));
	    JPanel jpLabel = new JPanel(new BorderLayout());
	    jpLabel.add(jl2,BorderLayout.NORTH);
	    jpLabel.add(jl3,BorderLayout.CENTER);
	    
	       //******************************************************
	    
	    JPanel jpBox = new JPanel();
	    jpBox.add(jcb);
	    
	    JPanel jpButton = new JPanel();
	    jpButton.add(jbQuery);
	    
	    JPanel jp = new JPanel();
	    jp.add(jpBox);
	    jp.add(jpButton);
	    
	       //*****************************************************
	    
	    JPanel jpCenter = new JPanel();
	    jpCenter.setLayout(new BorderLayout());
	    jpCenter.add(jpLabel,BorderLayout.NORTH);
	    jpCenter.add(jp,BorderLayout.CENTER);
	    
	    JPanel jpQuery = new JPanel();
	    jpQuery.setLayout(new BorderLayout());
	    jpQuery.add(jpTop,BorderLayout.NORTH);
	    jpQuery.add(jpCenter,BorderLayout.CENTER);	  
	    
	    this.setLayout(new BorderLayout());
	    this.add(new JLabel("        "),BorderLayout.NORTH);
	    this.add(jpQuery,BorderLayout.CENTER);	  
	    
	       //Add listener to the query button
	    jbQuery.addActionListener(this);		
	}
	
	public static void updateAirFirmComboBox(String newPlace,int insertOrDelete)
	{
		if (insertOrDelete == 1)
		{
			if (model.getIndexOf(newPlace) == -1)
			   jcb.addItem(newPlace);
		}
		   
		else if (insertOrDelete == 2)
		{
			if (model.getIndexOf(newPlace) != -1)
			   jcb.removeItem(newPlace);
		}
		   
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.airfirm = (String)jcb.getSelectedItem();
		
		   //Do the query work
		executeAirFirmQuery();
	}
	
	public void executeAirFirmQuery()
	{
		   //Form the sqlString
		String sqlString = "SELECT DISTINCT * FROM " +
		                   "flight " +
		                   "WHERE airfirm=" + "\'" + airfirm + "\'";
	                  
	    ResultSet rs = sqlBean.executeQuery(sqlString);
	     
	    if (rs != null)
	       showResult(rs);
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);
	}
	
	   //Get the result string from the result set,
       //and then display the result in a dialog
	public void showResult(ResultSet rs)
	{
		String result = "                                                     " +
		                "航空公司查询" + "\n";
		
		result += "查询的航空公司:" + airfirm + "\n";
		result += "航班号    航空公司            起飞地点  抵达地点  起飞时间  抵达时间  " + 
		          "儿童票价   成人票价   折扣  班期 " + "\n";
		   
		   //Used to determine whether there are no records found       
		int originLength = result.length();
		          
		String time1,time2;
	    String childFare,adultFare,discount1,discount2,seat;	
		
		try
		{
			while(rs.next())
			{
				result += rs.getString("flight") + rs.getString("airfirm") + rs.getString("start") + 
				          rs.getString("destination");
				          
				   //When you get the time from the resultset,it is like "1200".
				   //So we should change it into the form "12:00".
				time1 = rs.getString("leaveTime");
				time2 = rs.getString("arriveTime");
				   //getTime(String time) is used to change the time form into standard one
				time1 = getTime(time1);
				time2 = getTime(time2);
				
				result += time1 + "     " + time2  + "     ";
				
				   //Make sure that the following items have the exactly bits,
				   //so that they can be display in a neat format				
				childFare = String.valueOf(rs.getFloat("childFare"));
				adultFare = String.valueOf(rs.getFloat("adultFare"));
				discount1 = String.valueOf(rs.getFloat("discount1"));
				discount2 = String.valueOf(rs.getFloat("discount2"));
				seat = String.valueOf(rs.getInt("seat"));
				
				   //Make every item in a neat format
				while(childFare.length() != 11)
				   childFare += " ";
				while(adultFare.length() != 11)
				   adultFare += " ";
				while(discount1.length() != 8)
				   discount1 += " ";							
				   
				result += childFare + adultFare + discount1 +
				          rs.getString("week");
				result += "\n";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		   //Means there are no records found
		   //So give user message that couldn't find correlate infomation
		if (result.length() == originLength)
		{
			result += "                                                    " +
			          "对不起,找不到你想要的航班信息!" + "\n";
		}
		 	
		   //Display the result in a messageDialog				
		JOptionPane.showMessageDialog(null,result,"查询结果",JOptionPane.PLAIN_MESSAGE);
	}
	
	   //The method used to change the time form 
	private String getTime(String time)
	{
		String time1,time2;
		time1 = time.substring(0,2);
		time2 = time.substring(2,4);
		
		time1 = time1.concat(":");
		time1 = time1.concat(time2);
		
		return time1;
	}
}///:~