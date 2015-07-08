import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class SystemTrayDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(!SystemTray.isSupported()) {
			System.out.print("systemtray is not support.");
			return;
		}
		
		MenuItem action = new MenuItem("action");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "action!!");
			}
		});
		
		MenuItem close = new MenuItem("close");
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
		Image image = toolkit.getImage("c:/sync.png");
		
		TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo", menu);
		trayIcon.setImageAutoSize(true);
		
		SystemTray tray = SystemTray.getSystemTray();
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}
}
