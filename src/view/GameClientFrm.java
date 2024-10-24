package view;


import controller.Client;
import java.awt.BorderLayout;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
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
public class GameClientFrm extends javax.swing.JFrame {

    private final User competitor;
    private final JButton[][] button;
    private final int[][] competitorMatrix;
    private final int[][] matrix;
    private final int[][] userMatrix;

    //if changing it you will need to redesign icon
    private final int size = 15;
    private final Timer timer;
    private Integer second;
    private Integer minute;
    private int numberOfMatch;
    private final String[] normalItem;
    private final String[] winItem;
    private final String[] iconItem;
    private final String[] preItem;

    private JButton preButton;
    private int userWin;
    private int competitorWin;
    private boolean isSending;
    private boolean isListening;
    private final String competitorIP;

    public GameClientFrm(User competitor, int room_ID, int isStart, String competitorIP) {
        initComponents();
        numberOfMatch = isStart;
        this.competitor = competitor;
        this.competitorIP = competitorIP;

        isSending = false;
        isListening = false;


        //init score
        userWin = 0;
        competitorWin = 0;

        this.setTitle("DuoiHinhBatChu");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);

//        gamePanel.setLayout(new GridLayout(size, size));
        // Tạo ImageIcon từ đường dẫn ảnh
        ImageIcon imageIcon = new ImageIcon("assets/dhbc/new_init/dhbc12.png");

// Tạo JLabel chứa hình ảnh
        JLabel imageLabel = new JLabel(imageIcon);

// Xóa tất cả các thành phần cũ của imagePanel (nếu có)
        imagePanel.removeAll();

// Đặt layout cho imagePanel
        imagePanel.setLayout(new BorderLayout());

// Thêm JLabel vào imagePanel
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        correctAnswer="o_an_quan";
// Cập nhật và vẽ lại imagePanel
        imagePanel.revalidate();
        imagePanel.repaint();


        //Setup play button
        button = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j] = new JButton("");
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                gamePanel.add(button[i][j]);
            }
        }

        //SetUp play Matrix
        competitorMatrix = new int[size][size];
        matrix = new int[size][size];
        userMatrix = new int[size][size];

        //Setup UI
        playerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        competitorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setAlignmentX(JLabel.CENTER);
        playerNicknameValue.setText(Client.user.getNickname());
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        playerNumberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        playerButtonImage.setIcon(new ImageIcon("assets/game/" + Client.user.getAvatar() + ".jpg"));
        roomNameLabel.setText("Phòng: " + room_ID);
        vsIcon.setIcon(new ImageIcon("assets/game/swords-1.png"));
        competitorNicknameValue.setText(competitor.getNickname());
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        competitorNumberOfWinValue.setText(Integer.toString(competitor.getNumberOfWin()));
        competotorButtonImage.setIcon(new ImageIcon("assets/game/" + competitor.getAvatar() + ".jpg"));
        competotorButtonImage.setToolTipText("Xem thông tin đối thủ");

        countDownLabel.setVisible(false);
        messageTextArea.setEditable(false);
        scoreLabel.setText("Tỉ số: 0-0");

        //Setup timer
        second = 60;
        minute = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                if (second == 0) {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second = 60;
                    minute = 0;
                    try {
                        Client.openView(Client.View.GAME_CLIENT, "Bạn đã thua do quá thời gian", "Đang thiết lập ván chơi mới");
                        increaseWinMatchToCompetitor();
                        Client.socketHandle.write("lose,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }

                } else {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                }

            }

        });

        //Setup icon
        normalItem = new String[2];
        normalItem[1] = "assets/image/o2.jpg";
        normalItem[0] = "assets/image/x2.jpg";
        winItem = new String[2];
        winItem[1] = "assets/image/owin.jpg";
        winItem[0] = "assets/image/xwin.jpg";
        iconItem = new String[2];
        iconItem[1] = "assets/image/o3.jpg";
        iconItem[0] = "assets/image/x3.jpg";
        preItem = new String[2];
        preItem[1] = "assets/image/o2_pre.jpg";
        preItem[0] = "assets/image/x2_pre.jpg";
        setupButton();

        setEnableButton(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGame();
            }
        });

    }

    public void exitGame() {
        try {
            timer.stop();

            Client.socketHandle.write("left-room,");
            Client.closeAllViews();
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeAllViews();
        Client.openView(Client.View.HOMEPAGE);
    }

    public void stopAllThread() {
        timer.stop();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        playerNumberOfWinLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        playerNicknameLabel = new javax.swing.JLabel();
        playerNumberOfGameLabel = new javax.swing.JLabel();
        competitorNumberOfWinLabel = new javax.swing.JLabel();
        competitorNicknameLabel = new javax.swing.JLabel();
        competotorNumberOfGameLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        playerNicknameValue = new javax.swing.JLabel();
        playerNumberOfGameValue = new javax.swing.JLabel();
        playerNumberOfWinValue = new javax.swing.JLabel();
        competitorNicknameValue = new javax.swing.JLabel();
        competotorNumberOfGameValue = new javax.swing.JLabel();
        competitorNumberOfWinValue = new javax.swing.JLabel();
        countDownLabel = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        imagePanel = new javax.swing.JPanel();
        answerField = new javax.swing.JTextField();
        submitBut = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        competitorLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        roomNameLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        playerButtonImage = new javax.swing.JLabel();
        vsIcon = new javax.swing.JLabel();
        competotorButtonImage = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);

        playerNumberOfWinLabel.setText("Số ván thắng");

        playerNicknameLabel.setText("Nickname");

        playerNumberOfGameLabel.setText("Số ván chơi");

        competitorNumberOfWinLabel.setText("Số ván thắng");

        competitorNicknameLabel.setText("Nickname");

        competotorNumberOfGameLabel.setText("Số ván chơi");

        messageTextArea.setColumns(20);
        messageTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        messageTextArea.setRows(5);
        jScrollPane1.setViewportView(messageTextArea);

        playerNicknameValue.setText("{nickname}");

        playerNumberOfGameValue.setText("{sovanchoi}");

        playerNumberOfWinValue.setText("{sovanthang}");

        competitorNicknameValue.setText("{nickname}");

        competotorNumberOfGameValue.setText("{sovanchoi}");

        competitorNumberOfWinValue.setText("{sovanthang}");

        countDownLabel.setForeground(new java.awt.Color(255, 0, 0));
        countDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countDownLabel.setText("Thời gian:00:20");

        gamePanel.setBackground(new java.awt.Color(227, 253, 227));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        answerField.setBackground(new java.awt.Color(255, 255, 204));
        answerField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerFieldActionPerformed(evt);
            }
        });

        submitBut.setBackground(new java.awt.Color(153, 255, 255));
        submitBut.setText("Submit");
        submitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(answerField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(submitBut)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(answerField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(submitBut)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        playerLabel.setForeground(new java.awt.Color(255, 255, 255));
        playerLabel.setText("Bạn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        competitorLabel.setForeground(new java.awt.Color(255, 255, 255));
        competitorLabel.setText("Đối thủ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(competitorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(competitorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        roomNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomNameLabel.setText("{Tên Phòng}");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roomNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("Tỉ số:  0-0");

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        playerButtonImage.setBackground(new java.awt.Color(102, 102, 102));

        competotorButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                competotorButtonImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(vsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addComponent(playerButtonImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(playerNumberOfWinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)
                                                .addComponent(playerNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(competitorNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(39, 39, 39)
                                                .addComponent(competitorNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(competotorNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(competitorNumberOfWinLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(27, 27, 27)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(competotorNumberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(competitorNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(96, 96, 96)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(countDownLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(playerNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(playerNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(playerNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(playerNumberOfGameValue))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerNicknameLabel)
                            .addComponent(playerNicknameValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerNumberOfGameLabel)
                            .addComponent(playerNumberOfGameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerNumberOfWinLabel)
                            .addComponent(playerNumberOfWinValue))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorNicknameLabel)
                            .addComponent(competitorNicknameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competotorNumberOfGameLabel)
                            .addComponent(competotorNumberOfGameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorNumberOfWinLabel)
                            .addComponent(competitorNumberOfWinValue))))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(scoreLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countDownLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
            .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        //for(int i=0; i<5; i++){
            //    for(int j=0;j<5;j++){
                //        gamePanel.add(button[i][j]);
                //    }
            //}

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void competotorButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_competotorButtonImageActionPerformed

        Client.openView(Client.View.COMPETITOR_INFO, competitor);

    }//GEN-LAST:event_competotorButtonImageActionPerformed


    private void updateImage() {
        // Mảng chứa đường dẫn đến 5 ảnh
        // Mảng chứa đường dẫn đến 5 ảnh và câu trả lời tương ứng
        String[][] imagesWithAnswers = {
                {"assets/dhbc/dhbc1.png", "1"},
                {"assets/dhbc/dhbc2.png", "1"},
                {"assets/dhbc/dhbc3.png", "1"},
                {"assets/dhbc/dhbc4.png", "1"},
                {"assets/dhbc/dhbc5.png", "1"},
                {"assets/dhbc/dhbc6.png", "1"},
                {"assets/dhbc/dhbc7.png", "1"},
                {"assets/dhbc/dhbc8.png", "1"},
                {"assets/dhbc/dhbc9.png", "1"},
                {"assets/dhbc/dhbc10.png", "1"}
        };

        // Tạo đối tượng Random để chọn ngẫu nhiên
        Random random = new Random();

        // Chọn ngẫu nhiên một phần tử từ mảng
        int randomIndex = random.nextInt(imagesWithAnswers.length);
        String randomImagePath = imagesWithAnswers[randomIndex][0];
        correctAnswer = imagesWithAnswers[randomIndex][1];  // Lưu câu trả lời đúng

        // Tạo ImageIcon mới với đường dẫn của ảnh ngẫu nhiên
        ImageIcon imageIcon = new ImageIcon(randomImagePath);

        // Tạo JLabel chứa hình ảnh
        JLabel imageLabel = new JLabel(imageIcon);

        // Xóa tất cả các thành phần cũ của imagePanel (nếu có)
        imagePanel.removeAll();

        // Đặt layout cho imagePanel
        imagePanel.setLayout(new BorderLayout());

        // Thêm JLabel vào imagePanel
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Cập nhật và vẽ lại imagePanel
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void answerFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerFieldActionPerformed
        // TODO add your handling code here:
        submitButActionPerformed (evt);
    }//GEN-LAST:event_answerFieldActionPerformed
    private String correctAnswer;

    private int correctAnswerCount = 0;
    private int questionCount = 0;

    private void submitButActionPerformed(java.awt.event.ActionEvent evt) {
        // Lấy đáp án từ JTextField
        String userAnswer = answerField.getText();

        // So sánh đáp án người dùng nhập với đáp án đúng đã được cập nhật khi hiển thị ảnh
        if (userAnswer.equals(correctAnswer)) {
            // Nếu đúng, hiển thị thông báo "Correct!"
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
            correctAnswerCount++;
        } else {
            // Nếu sai, hiển thị thông báo "Wrong! Try again."
            JOptionPane.showMessageDialog(this, "Wrong! Try again.", "Result", JOptionPane.WARNING_MESSAGE);
        }

        questionCount++;
        if (questionCount == 3) {
            System.out.println(correctAnswerCount);
            sendCorrectAnswers(correctAnswerCount);
            questionCount = 0;
            correctAnswerCount = 0;

        } else {
            // Cập nhật ảnh mới sau khi trả lời
            updateImage();
        }
    }

    public void sendCorrectAnswers(int correctAnswers) {
        try {
            Client.socketHandle.write("correct-answers," + correctAnswers);
            System.out.println("gui dap an" + "correct-answers,");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleResultMessage(String message) {
        String[] parts = message.split(",");
        String result = parts[1];
        JOptionPane.showMessageDialog(this, result);
    }


    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/click.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }




    public void stopTimer() {
        timer.stop();
    }

    int not(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }

    void setupButton() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int a = i, b = j;

                button[a][b].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            button[a][b].setDisabledIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                            button[a][b].setEnabled(false);
                            playSound();
                            second = 60;
                            minute = 0;
                            matrix[a][b] = 1;
                            userMatrix[a][b] = 1;
                            button[a][b].setEnabled(false);
                            try {
//                                if (checkRowWin() == 1 || checkColumnWin() == 1 || checkRightCrossWin() == 1 || checkLeftCrossWin() == 1) {
//                                    //Xử lý khi người chơi này thắng
//                                    setEnableButton(false);
//                                    increaseWinMatchToUser();
//                                    Client.openView(Client.View.GAME_NOTICE, "Bạn đã thắng", "Đang thiết lập ván chơi mới");
//                                    Client.socketHandle.write("win," + a + "," + b);
//                                } else {
//                                    Client.socketHandle.write("caro," + a + "," + b);
////                                    displayCompetitorTurn();
//
//                                }
                                setEnableButton(false);
                                timer.stop();
                            } catch (Exception ie) {
                                ie.printStackTrace();
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                        }
                    }
                });
                button[a][b].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (button[a][b].isEnabled()) {
                            button[a][b].setBackground(Color.GREEN);
                            button[a][b].setIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                        }
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if (button[a][b].isEnabled()) {
                            button[a][b].setBackground(null);
                            button[a][b].setIcon(new ImageIcon("assets/image/blank.jpg"));
                        }
                    }
                });
            }
        }
    }







    public void startTimer() {
        countDownLabel.setVisible(true);
        second = 60;
        minute = 0;
        timer.start();
    }



    public void increaseWinMatchToUser() {
        Client.user.setNumberOfWin(Client.user.getNumberOfWin() + 1);
        playerNumberOfWinValue.setText("" + Client.user.getNumberOfWin());
        userWin++;
        scoreLabel.setText("Tỉ số: " + userWin + "-" + competitorWin);
        String tmp = messageTextArea.getText();
        tmp += "--Bạn đã thắng, tỉ số hiện tại là " + userWin + "-" + competitorWin + "--\n";
        messageTextArea.setText(tmp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void increaseWinMatchToCompetitor() {
        competitor.setNumberOfWin(competitor.getNumberOfWin() + 1);
        competitorNumberOfWinValue.setText("" + competitor.getNumberOfWin());
        competitorWin++;
        scoreLabel.setText("Tỉ số: " + userWin + "-" + competitorWin);
        String tmp = messageTextArea.getText();
        tmp += "--Bạn đã thua, tỉ số hiện tại là " + userWin + "-" + competitorWin + "--\n";
        messageTextArea.setText(tmp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void displayDrawGame() {
        String tmp = messageTextArea.getText();
        tmp += "--Ván chơi hòa--\n";
        messageTextArea.setText(tmp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void showDrawRequest() {
        int res = JOptionPane.showConfirmDialog(rootPane, "Đối thử muốn cầu hóa ván này, bạn đồng ý chứ", "Yêu cầu cầu hòa", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            try {
                timer.stop();
                setEnableButton(false);
                Client.socketHandle.write("draw-confirm,");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        } else {
            try {
                Client.socketHandle.write("draw-refuse,");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }




    public void newgame() {

        if (numberOfMatch % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
            startTimer();
//            displayUserTurn();
            countDownLabel.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đối thủ đi trước");
//            displayCompetitorTurn();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setIcon(new ImageIcon("assets/image/blank.jpg"));
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
                userMatrix[i][j] = 0;
            }
        }
        setEnableButton(true);
        if (numberOfMatch % 2 != 0) {
            blockGame();
        }


        preButton = null;
        numberOfMatch++;
    }

    public void updateNumberOfGame() {
        competitor.setNumberOfGame(competitor.getNumberOfGame() + 1);
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        Client.user.setNumberOfGame(Client.user.getNumberOfGame() + 1);
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
    }

    public void blockGame() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
//                drawRequestButton.setVisible(false);
            }
        }
        timer.stop();
        setEnableButton(false);
    }

    public void setEnableButton(boolean b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    button[i][j].setEnabled(b);
                }
            }
        }
    }



    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField answerField;
    private javax.swing.JLabel competitorLabel;
    private javax.swing.JLabel competitorNicknameLabel;
    private javax.swing.JLabel competitorNicknameValue;
    private javax.swing.JLabel competitorNumberOfWinLabel;
    private javax.swing.JLabel competitorNumberOfWinValue;
    private javax.swing.JButton competotorButtonImage;
    private javax.swing.JLabel competotorNumberOfGameLabel;
    private javax.swing.JLabel competotorNumberOfGameValue;
    private javax.swing.JLabel countDownLabel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JLabel playerButtonImage;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerNicknameLabel;
    private javax.swing.JLabel playerNicknameValue;
    private javax.swing.JLabel playerNumberOfGameLabel;
    private javax.swing.JLabel playerNumberOfGameValue;
    private javax.swing.JLabel playerNumberOfWinLabel;
    private javax.swing.JLabel playerNumberOfWinValue;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton submitBut;
    private javax.swing.JLabel vsIcon;
    // End of variables declaration//GEN-END:variables


}