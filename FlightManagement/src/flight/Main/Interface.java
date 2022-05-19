package flight.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import flight.manage.*;
import flight.query.*;
import flight.ticketbook.*;
import flight.ticketrefund.*;
/**
 * ����������
 */
class Interface extends JPanel implements ActionListener {
  private TicketRefund ticketRefund;
  private TicketBook ticketBook;
  
  //private JButton jbtitle = new JButton("�����ѯ��Ʊϵͳ");
  private JButton jbQuery = new JButton("��ѯ");
  private JButton jbManager = new JButton("����");
  private JButton jbTicketBook = new JButton("��Ʊ");
  private JButton jbTicketRefund = new JButton("��Ʊ");
  

  public Interface() {
    this.setLayout(null);
    //this.add(jbtitle);
    this.add(jbQuery);
    this.add(jbTicketBook);
    this.add(jbTicketRefund);
    this.add(jbManager);
    
    //jbtitle.setFont(new Font("Times", Font.PLAIN, 12));
    jbQuery.setFont(new Font("Times", Font.PLAIN, 12));
    jbTicketBook.setFont(new Font("Times", Font.PLAIN, 12));
    jbTicketRefund.setFont(new Font("Times", Font.PLAIN, 12));
    jbManager.setFont(new Font("Times", Font.PLAIN, 12));
    
   // jbtitle.setBounds(200,200,100,30);
    jbQuery.setBounds(90, 280, 80, 30);
    jbTicketBook.setBounds(190, 280, 80, 30);
    jbTicketRefund.setBounds(290, 280, 80, 30);
    jbManager.setBounds(390, 280, 80, 30);
    
    //jbtitle.addActionListener(this);
    jbQuery.addActionListener(this);
    jbManager.addActionListener(this);
    jbTicketBook.addActionListener(this);
    jbTicketRefund.addActionListener(this);
    
  }

  public void paintComponent(Graphics g) {
    ImageIcon imageIcon = new ImageIcon("background.jpg");
    Image image = imageIcon.getImage();
    g.drawImage(image, 0, 0, this);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == jbQuery) {//��ѯ
      Query query = new Query();
      showSubFrame(query);
    }

    else if (e.getSource() == jbManager) {//����
      String id = JOptionPane.showInputDialog(null, "����������ʺ�:",
                                              "�ʺ���֤", JOptionPane.PLAIN_MESSAGE);
      if (id == null)
        return;
      if (!id.equals("manage")) {
        JOptionPane.showMessageDialog(null, "�Բ���!����ʺŲ���ȷ!",
                                      "�ʺŴ���", JOptionPane.ERROR_MESSAGE);
        return;
      }

      String password = JOptionPane.showInputDialog(null, "�������������:",
          "������֤", JOptionPane.PLAIN_MESSAGE);
      if (password == null)
        return;
      if (!password.equals("manage")) {
        JOptionPane.showMessageDialog(null, "�Բ���!������벻��ȷ!",
                                      "�ʺŴ���", JOptionPane.ERROR_MESSAGE);
        return;
      }

      Management manager = new Management();
      showSubFrame(manager);
    }

    else if (e.getSource() == jbTicketBook) {//��Ʊ
      ticketBook = new TicketBook();
      showSubFrame(ticketBook);
    }

    else if (e.getSource() == jbTicketRefund) {//��Ʊ
      ticketRefund = new TicketRefund();
      showSubFrame(ticketRefund);
    }

  }

  private void showSubFrame(JFrame subFrame){//�����Ӵ��ڵ���ʾλ��
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //��Ļ��С
    subFrame.setLocation( (d.width - subFrame.getSize().width) / 2,
                             (d.height - subFrame.getSize().height) / 2);
    subFrame.show();

  }
}
