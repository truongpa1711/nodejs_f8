package view;


import controller.Client;
import controller.SocketHandle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Admin
 */
public class HomePageFrm extends javax.swing.JFrame {
    /**
     * Creates new form GiaoDienChinhFrm
     */
    private JButton btnShowOnlineUsers;
    private OnlineUsersFrame onlineUsersFrame;
    private ViewHistoryFrm viewHistoryFrm;
    
    public HomePageFrm() {
        initComponents();
        this.setTitle("Duoi Hinh Bat Chu Nhom 1");
        this.setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        jLabel4.setText(Client.user.getNickname());
        numberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        numberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        jLabel8.setIcon(new ImageIcon("assets/avatar/" + Client.user.getAvatar() + ".jpg"));
        
        if (Client.user.getNumberOfGame() == 0) {
            winRatioValue.setText("-");
        } else {
            winRatioValue.setText(String.format("%.2f", (float) Client.user.getNumberOfWin() / Client.user.getNumberOfGame() * 100) + "%");
        }
        drawValue.setText("" + Client.user.getNumberOfDraw());
        markValue.setText("" + (Client.user.getScore()));
       
        setVisible(true); // Hiển thị giao diện
          
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        createRoomButton = new javax.swing.JButton();
        scoreBoardButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numberOfWinLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        numberOfWinValue = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        numberOfGameValue = new javax.swing.JLabel();
        numberOfGameLabel = new javax.swing.JLabel();
        markLabel = new javax.swing.JLabel();
        markValue = new javax.swing.JLabel();
        winRatioLabel = new javax.swing.JLabel();
        winRatioValue = new javax.swing.JLabel();
        drawLabel = new javax.swing.JLabel();
        drawValue = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        quickGameButton = new javax.swing.JButton();
        friendListButton = new javax.swing.JButton();
        statusButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButtonHistory = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 204));

        createRoomButton.setBackground(new java.awt.Color(102, 255, 255));
        createRoomButton.setText("Tạo Phòng");
        createRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRoomButtonActionPerformed(evt);
            }
        });

        scoreBoardButton.setBackground(new java.awt.Color(102, 255, 255));
        scoreBoardButton.setText("Bảng xếp hạng");
        scoreBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreBoardButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NickName");
        jLabel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jLabel1AncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        numberOfWinLabel.setForeground(new java.awt.Color(255, 255, 255));
        numberOfWinLabel.setText("Số ván thắng");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("{day la Nick name}");

        numberOfWinValue.setForeground(new java.awt.Color(255, 255, 255));
        numberOfWinValue.setText("{day la so van thang}");

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));

        numberOfGameValue.setForeground(new java.awt.Color(255, 255, 255));
        numberOfGameValue.setText("{day la so van da choi}");

        numberOfGameLabel.setForeground(new java.awt.Color(255, 255, 255));
        numberOfGameLabel.setText("Số ván đã chơi");

        markLabel.setForeground(new java.awt.Color(255, 255, 255));
        markLabel.setText("Điểm");

        markValue.setForeground(new java.awt.Color(255, 255, 255));
        markValue.setText("{day la diem}");

        winRatioLabel.setForeground(new java.awt.Color(255, 255, 255));
        winRatioLabel.setText("Tỉ lệ thắng");

        winRatioValue.setForeground(new java.awt.Color(255, 255, 255));
        winRatioValue.setText("{day la ti le thang}");

        drawLabel.setForeground(new java.awt.Color(255, 255, 255));
        drawLabel.setText("Số ván hòa");

        drawValue.setForeground(new java.awt.Color(255, 255, 255));
        drawValue.setText("{day la so van hoa}");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numberOfWinLabel)
                            .addComponent(jLabel1)
                            .addComponent(numberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(drawLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(markLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(winRatioLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(markValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(winRatioValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(drawValue, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(numberOfGameValue))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numberOfGameLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numberOfWinValue)
                            .addComponent(numberOfWinLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(drawLabel)
                            .addComponent(drawValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(winRatioLabel)
                            .addComponent(winRatioValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(markValue)
                            .addComponent(markLabel)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        logoutButton.setBackground(new java.awt.Color(255, 51, 51));
        logoutButton.setText("Đăng xuất");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        quickGameButton.setBackground(new java.awt.Color(102, 255, 255));
        quickGameButton.setText("Vào Phòng");
        quickGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickGameButtonActionPerformed(evt);
            }
        });

        friendListButton.setBackground(new java.awt.Color(102, 255, 255));
        friendListButton.setText("Danh sách bạn bè");
        friendListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendListButtonActionPerformed(evt);
            }
        });

        statusButton.setBackground(new java.awt.Color(102, 255, 255));
        statusButton.setText("Xem trạng thái");
        statusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusButtonActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(153, 0, 51));
        jTextField1.setFont(new java.awt.Font("Brush Script MT", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("       Duoi Hinh Bat Chu");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButtonHistory.setBackground(new java.awt.Color(102, 255, 255));
        jButtonHistory.setText("Xem lịch sử");
        jButtonHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(quickGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scoreBoardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(friendListButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(createRoomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(44, 44, 44))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quickGameButton)
                    .addComponent(createRoomButton))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonHistory)
                    .addComponent(friendListButton))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scoreBoardButton)
                    .addComponent(statusButton))
                .addGap(33, 33, 33)
                .addComponent(logoutButton)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void openOnlineUsersFrame() {
        onlineUsersFrame.setVisible(true);
    }

    public void updateOnlineUsers(List<User> onlineUsers) {
        if (onlineUsersFrame == null || !onlineUsersFrame.isVisible()) {
            onlineUsersFrame = new OnlineUsersFrame();  // Khởi tạo nếu cần
            onlineUsersFrame.dispose();
            
        }
        onlineUsersFrame.updateOnlineUsers(onlineUsers);  // Cập nhật danh sách
    }
    
 
    private void jLabel1AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel1AncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1AncestorMoved

    private void createRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRoomButtonActionPerformed
      
            try {
                Client.socketHandle.write("create-room,");
                Client.closeView(Client.View.HOMEPAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        
    }//GEN-LAST:event_createRoomButtonActionPerformed

    private void scoreBoardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreBoardButtonActionPerformed
        Client.openView(Client.View.RANK);
    }//GEN-LAST:event_scoreBoardButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        try {
            Client.socketHandle.write("offline," + Client.user.getID());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
//        Client.closeView(Client.View.HOMEPAGE);
            dispose();
        Client.openView(Client.View.LOGIN);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void friendListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendListButtonActionPerformed
        Client.closeView(Client.View.HOMEPAGE);
        Client.openView(Client.View.FRIEND_LIST);
    }//GEN-LAST:event_friendListButtonActionPerformed

    private void quickGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickGameButtonActionPerformed
        Client.closeView(Client.View.HOMEPAGE);
        Client.openView(Client.View.FIND_ROOM);
    }//GEN-LAST:event_quickGameButtonActionPerformed

    private void statusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusButtonActionPerformed
        
         
        
        try {
            Client.socketHandle.write("check-online,");
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
       
    }//GEN-LAST:event_statusButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButtonHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoryActionPerformed
        try {
            Client.socketHandle.write("view-history,");
        } catch (IOException ex) {
            Logger.getLogger(HomePageFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButtonHistoryActionPerformed

 


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createRoomButton;
    private javax.swing.JLabel drawLabel;
    private javax.swing.JLabel drawValue;
    private javax.swing.JButton friendListButton;
    private javax.swing.JButton jButtonHistory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel markLabel;
    private javax.swing.JLabel markValue;
    private javax.swing.JLabel numberOfGameLabel;
    private javax.swing.JLabel numberOfGameValue;
    private javax.swing.JLabel numberOfWinLabel;
    private javax.swing.JLabel numberOfWinValue;
    private javax.swing.JButton quickGameButton;
    private javax.swing.JButton scoreBoardButton;
    private javax.swing.JButton statusButton;
    private javax.swing.JLabel winRatioLabel;
    private javax.swing.JLabel winRatioValue;
    // End of variables declaration//GEN-END:variables
}
