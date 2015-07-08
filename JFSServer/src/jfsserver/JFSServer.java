/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfsserver;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author IzedTea
 */
public class JFSServer extends javax.swing.JFrame {

    private int index;
    private ArrayList<Client> clients = new ArrayList<>();

    public JFSServer() {
        initComponents();
        
        JMenuItem syncMenu = new JMenuItem("Sync");
        JMenuItem syncParamsMenu = new JMenuItem("Sync with Params");
        JMenuItem syncAllMenu = new JMenuItem("Sync All");
        
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(syncMenu);
        popupMenu.add(syncParamsMenu);
        popupMenu.add(syncAllMenu);
        
        syncMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clients.get(index).sendMessage("sync");
            }
        });
        
        syncParamsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clients.get(index).sendMessage("syncwithparams");
            }
        });
                
        syncAllMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Client c : clients) {
                    c.sendMessage("sync");
                }
            }
        });
        
        clientList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setIcon(MetalIconFactory.getTreeComputerIcon());
                label.setText(((Client)value).getAddress().getHostName());
                return label;
            }
        });
        
        clientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me) && !clientList.isSelectionEmpty() && clientList.locationToIndex(me.getPoint()) == clientList.getSelectedIndex()) {
                    popupMenu.show(clientList, me.getX(), me.getY());
                    
                    index = clientList.getSelectedIndex();
	    	}
            }
        });
        
        tray();
        
        listener();
    }
    
    private void listener() {
        try {
            final ServerSocket listener = new ServerSocket(6789);
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {         
                            Client client = new Client(listener.accept());
                            
                            client.addDisconnectedListener(new DisconnectedListener() {
                                @Override
                                public void onDisconnected(Client client) {
                                    clients.remove(client);
                                    
                                    bind();
                                }
                            });
                            
                            clients.add(client);
                            
                            bind();
                            
                            new Thread(client).start();
                        } catch (IOException ex) { }  
                    }
                }
            }).start();
        } catch (IOException ex) { }
    }
    
    private void bind() {
        DefaultListModel dlm = new DefaultListModel();
        
        for(Client c : clients) {
            dlm.addElement(c);
        }
        
        clientList.setModel(dlm);
    }
    
    private void tray() {
        MenuItem action = new MenuItem("Open");
        action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    setVisible(true);
            }
        });

        MenuItem close = new MenuItem("Exit");
        close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                }
        });

        PopupMenu menu = new PopupMenu();

        menu.add(action);
        menu.add(close);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(new java.io.File("").getAbsolutePath() + "\\sync.png");

        TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo", menu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        
        try { tray.add(trayIcon); } catch (AWTException ex) { }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        clientList = new javax.swing.JList();
        menuBar = new javax.swing.JMenuBar();
        schedulerMenu = new javax.swing.JMenu();
        logMenu = new javax.swing.JMenu();

        setTitle("JFSServer");

        clientList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(clientList);

        schedulerMenu.setText("Scheduler");
        menuBar.add(schedulerMenu);

        logMenu.setText("Log");
        logMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logMenuMouseClicked(evt);
            }
        });
        menuBar.add(logMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logMenuMouseClicked
        try {
            Runtime.getRuntime().exec("notepad.exe " + new File("").getAbsolutePath() + "\\sync.log");
        } catch (IOException ex) { }
    }//GEN-LAST:event_logMenuMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFSServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFSServer().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList clientList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu logMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu schedulerMenu;
    // End of variables declaration//GEN-END:variables
}

class Client implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private DisconnectedListener dl;
	
    public Client(Socket client) {
        this.client = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ex) { }
    }
    
    public void addDisconnectedListener(DisconnectedListener dl) {
        this.dl = dl;
    }

    @Override
    public void run() {
        while(client.isConnected()) {
            try {
                String message;

                if((message = in.readLine()) == null) {
                    dl.onDisconnected(this);
                    break;
                } 
                
                log(message);
            } catch (SocketException ex) {
                dl.onDisconnected(this);
                break;
            } catch (IOException ex) { }
        }

        try { in.close(); } catch (IOException ex) { }
    }
    
    private void log(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = sdf.format(new Date());
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new java.io.File("").getAbsolutePath() + "\\sync.log", true))) {
            bw.write(d + ":" + getAddress().getHostName() + "[" + getAddress().getHostAddress() + "]" + ":" + message + "\r\n");
            bw.flush();
            bw.close();
        } catch (IOException ex) { } 
    }

    public InetAddress getAddress() {
        return client.getInetAddress();
    }
        
    public void sendMessage(String message) {
        out.println(message);
    }
}

interface DisconnectedListener {
    public void onDisconnected(Client client);
}

