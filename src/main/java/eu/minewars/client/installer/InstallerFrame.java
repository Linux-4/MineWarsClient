package eu.minewars.client.installer;

import java.io.*;
import java.awt.event.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import eu.minewars.client.MineWarsClient;

@SuppressWarnings("serial")
public class InstallerFrame extends JFrame {
	private JLabel ivjLabelOfVersion;
	private JLabel ivjLabelMcVersion;
	private JPanel ivjPanelCenter;
	private JButton ivjButtonInstall;
	private JButton ivjButtonClose;
	private JPanel ivjPanelBottom;
	private JPanel ivjPanelContentPane;
	IvjEventHandler ivjEventHandler;
	private JTextArea ivjTextArea;
	private JLabel ivjLabelFolder;
	private JTextField ivjFieldFolder;
	private JButton ivjButtonFolder;

	public InstallerFrame() {
		this.ivjLabelOfVersion = null;
		this.ivjLabelMcVersion = null;
		this.ivjPanelCenter = null;
		this.ivjButtonInstall = null;
		this.ivjButtonClose = null;
		this.ivjPanelBottom = null;
		this.ivjPanelContentPane = null;
		this.ivjEventHandler = new IvjEventHandler();
		this.ivjTextArea = null;
		this.ivjLabelFolder = null;
		this.ivjFieldFolder = null;
		this.ivjButtonFolder = null;
		this.initialize();
	}

	private void customInit() {
		try {
			this.pack();
			this.setIconImage(ImageIO.read(InstallerFrame.class.getResourceAsStream("/images/logo.png")));
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			final File dirMc = Utils.getWorkingDirectory();
			this.getFieldFolder().setText(dirMc.getPath());
			this.getButtonInstall().setEnabled(false);
			final String mcVer = MineWarsClient.VERSION;
			Utils.dbg("Minecraft Version: " + mcVer);
			this.getLabelOfVersion().setText(MineWarsClient.NAME);
			this.getLabelMcVersion().setText("für Minecraft " + mcVer);
			this.getButtonInstall().setEnabled(true);
			this.getButtonInstall().requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			final InstallerFrame frm = new InstallerFrame();
			Utils.centerWindow(frm, null);
			frm.show();
		} catch (Exception e) {
			final String msg = e.getMessage();
			if (msg != null && msg.equals("QUIET")) {
				return;
			}
			e.printStackTrace();
			String str = Utils.getExceptionStackTrace(e);
			str = str.replace("\t", "  ");
			final JTextArea textArea = new JTextArea(str);
			textArea.setEditable(false);
			final Font f = textArea.getFont();
			final Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
			textArea.setFont(f2);
			final JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(600, 400));
			JOptionPane.showMessageDialog(null, scrollPane, "Fehler", 0);
		}
	}

	private void handleException(final Throwable e) {
		final String msg = e.getMessage();
		if (msg != null && msg.equals("QUIET")) {
			return;
		}
		e.printStackTrace();
		String str = Utils.getExceptionStackTrace(e);
		str = str.replace("\t", "  ");
		final JTextArea textArea = new JTextArea(str);
		textArea.setEditable(false);
		final Font f = textArea.getFont();
		final Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
		textArea.setFont(f2);
		final JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(600, 400));
		JOptionPane.showMessageDialog(null, scrollPane, "Fehler", 0);
	}

	private JLabel getLabelOfVersion() {
		if (this.ivjLabelOfVersion == null) {
			try {
				(this.ivjLabelOfVersion = new JLabel()).setName("LabelOfVersion");
				this.ivjLabelOfVersion.setBounds(2, 5, 385, 42);
				this.ivjLabelOfVersion.setFont(new Font("Dialog", 1, 18));
				this.ivjLabelOfVersion.setHorizontalAlignment(0);
				this.ivjLabelOfVersion.setPreferredSize(new Dimension(385, 42));
				this.ivjLabelOfVersion.setText(MineWarsClient.VERSION + " ...");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjLabelOfVersion;
	}

	private JLabel getLabelMcVersion() {
		if (this.ivjLabelMcVersion == null) {
			try {
				(this.ivjLabelMcVersion = new JLabel()).setName("LabelMcVersion");
				this.ivjLabelMcVersion.setBounds(2, 38, 385, 25);
				this.ivjLabelMcVersion.setFont(new Font("Dialog", 1, 14));
				this.ivjLabelMcVersion.setHorizontalAlignment(0);
				this.ivjLabelMcVersion.setPreferredSize(new Dimension(385, 25));
				this.ivjLabelMcVersion.setText("fürr Minecraft ...");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjLabelMcVersion;
	}

	private JPanel getPanelCenter() {
		if (this.ivjPanelCenter == null) {
			try {
				(this.ivjPanelCenter = new JPanel()).setName("PanelCenter");
				this.ivjPanelCenter.setLayout(null);
				this.ivjPanelCenter.add(this.getLabelOfVersion(), this.getLabelOfVersion().getName());
				this.ivjPanelCenter.add(this.getLabelMcVersion(), this.getLabelMcVersion().getName());
				this.ivjPanelCenter.add(this.getTextArea(), this.getTextArea().getName());
				this.ivjPanelCenter.add(this.getLabelFolder(), this.getLabelFolder().getName());
				this.ivjPanelCenter.add(this.getFieldFolder(), this.getFieldFolder().getName());
				this.ivjPanelCenter.add(this.getButtonFolder(), this.getButtonFolder().getName());
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelCenter;
	}

	private JButton getButtonInstall() {
		if (this.ivjButtonInstall == null) {
			try {
				(this.ivjButtonInstall = new JButton()).setName("ButtonInstall");
				this.ivjButtonInstall.setPreferredSize(new Dimension(100, 26));
				this.ivjButtonInstall.setText("Installieren");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjButtonInstall;
	}

	private JButton getButtonClose() {
		if (this.ivjButtonClose == null) {
			try {
				(this.ivjButtonClose = new JButton()).setName("ButtonClose");
				this.ivjButtonClose.setPreferredSize(new Dimension(100, 26));
				this.ivjButtonClose.setText("Abbrechen");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjButtonClose;
	}

	private JPanel getPanelBottom() {
		if (this.ivjPanelBottom == null) {
			try {
				(this.ivjPanelBottom = new JPanel()).setName("PanelBottom");
				this.ivjPanelBottom.setLayout(new FlowLayout(1, 15, 10));
				this.ivjPanelBottom.setPreferredSize(new Dimension(390, 55));
				this.ivjPanelBottom.add(this.getButtonInstall(), this.getButtonInstall().getName());
				this.ivjPanelBottom.add(this.getButtonClose(), this.getButtonClose().getName());
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelBottom;
	}

	private JPanel getPanelContentPane() {
		if (this.ivjPanelContentPane == null) {
			try {
				(this.ivjPanelContentPane = new JPanel()).setName("PanelContentPane");
				this.ivjPanelContentPane.setLayout(new BorderLayout(5, 5));
				this.ivjPanelContentPane.setPreferredSize(new Dimension(394, 203));
				this.ivjPanelContentPane.add(this.getPanelCenter(), "Center");
				this.ivjPanelContentPane.add(this.getPanelBottom(), "South");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelContentPane;
	}

	private void initialize() {
		try {
			this.setName("InstallerFrame");
			this.setSize(404, 236);
			this.setDefaultCloseOperation(0);
			this.setResizable(false);
			this.setTitle(MineWarsClient.NAME + " Installer");
			this.setContentPane(this.getPanelContentPane());
			this.initConnections();
		} catch (Throwable ivjExc) {
			this.handleException(ivjExc);
		}
		this.customInit();
	}

	public void onInstall() {
		try {
			final File dirMc = new File(this.getFieldFolder().getText());
			if (!dirMc.exists()) {
				Utils.showErrorMessage("Ordner nicht gefunden: " + dirMc.getPath());
				return;
			}
			if (!dirMc.isDirectory()) {
				Utils.showErrorMessage("Kein Ordner: " + dirMc.getPath());
				return;
			}
			Installer.doInstall(dirMc);
			Utils.showMessage(MineWarsClient.NAME + " wurde erfolgreich installiert.");
			this.dispose();
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public void onClose() {
		System.exit(0);
	}

	private void connEtoC1(final ActionEvent arg1) {
		try {
			this.onInstall();
		} catch (Throwable ivjExc) {
			this.handleException(ivjExc);
		}
	}

	private void connEtoC2(final ActionEvent arg1) {
		try {
			this.onClose();
		} catch (Throwable ivjExc) {
			this.handleException(ivjExc);
		}
	}

	private void initConnections() throws Exception {
		this.getButtonFolder().addActionListener(this.ivjEventHandler);
		this.getButtonInstall().addActionListener(this.ivjEventHandler);
		this.getButtonClose().addActionListener(this.ivjEventHandler);
	}

	private JTextArea getTextArea() {
		if (this.ivjTextArea == null) {
			try {
				(this.ivjTextArea = new JTextArea()).setName("TextArea");
				this.ivjTextArea.setBounds(15, 66, 365, 44);
				this.ivjTextArea.setEditable(false);
				this.ivjTextArea.setEnabled(true);
				this.ivjTextArea.setFont(new Font("Dialog", 0, 12));
				this.ivjTextArea.setLineWrap(true);
				this.ivjTextArea.setOpaque(false);
				this.ivjTextArea.setPreferredSize(new Dimension(365, 44));
				this.ivjTextArea.setText("Dieser Installer installiert " + MineWarsClient.NAME
						+ " in den offiziellen Minecraft Launcher.");
				this.ivjTextArea.setWrapStyleWord(true);
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjTextArea;
	}

	private JLabel getLabelFolder() {
		if (this.ivjLabelFolder == null) {
			try {
				(this.ivjLabelFolder = new JLabel()).setName("LabelFolder");
				this.ivjLabelFolder.setBounds(15, 116, 47, 16);
				this.ivjLabelFolder.setPreferredSize(new Dimension(47, 16));
				this.ivjLabelFolder.setText("Ordner");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjLabelFolder;
	}

	private JTextField getFieldFolder() {
		if (this.ivjFieldFolder == null) {
			try {
				(this.ivjFieldFolder = new JTextField()).setName("FieldFolder");
				this.ivjFieldFolder.setBounds(62, 114, 287, 20);
				this.ivjFieldFolder.setEditable(false);
				this.ivjFieldFolder.setPreferredSize(new Dimension(287, 20));
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjFieldFolder;
	}

	private JButton getButtonFolder() {
		if (this.ivjButtonFolder == null) {
			try {
				(this.ivjButtonFolder = new JButton()).setName("ButtonFolder");
				this.ivjButtonFolder.setBounds(350, 114, 25, 20);
				this.ivjButtonFolder.setMargin(new Insets(2, 2, 2, 2));
				this.ivjButtonFolder.setPreferredSize(new Dimension(25, 20));
				this.ivjButtonFolder.setText("...");
			} catch (Throwable ivjExc) {
				this.handleException(ivjExc);
			}
		}
		return this.ivjButtonFolder;
	}

	public void onFolderSelect() {
		final File dirMc = new File(this.getFieldFolder().getText());
		final JFileChooser jfc = new JFileChooser(dirMc);
		jfc.setFileSelectionMode(1);
		jfc.setAcceptAllFileFilterUsed(false);
		if (jfc.showOpenDialog(this) == 0) {
			final File dir = jfc.getSelectedFile();
			this.getFieldFolder().setText(dir.getPath());
		}
	}

	private void connEtoC4(final ActionEvent arg1) {
		try {
			this.onFolderSelect();
		} catch (Throwable ivjExc) {
			this.handleException(ivjExc);
		}
	}

	class IvjEventHandler implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource() == InstallerFrame.this.getButtonClose()) {
				InstallerFrame.this.connEtoC2(e);
			}
			if (e.getSource() == InstallerFrame.this.getButtonFolder()) {
				InstallerFrame.this.connEtoC4(e);
			}
			if (e.getSource() == InstallerFrame.this.getButtonInstall()) {
				InstallerFrame.this.connEtoC1(e);
			}
		}
	}
}
