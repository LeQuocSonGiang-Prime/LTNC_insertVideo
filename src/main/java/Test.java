import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class Test extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tf_username;
	private JPasswordField  tf_password;
	private JLabel picLabel;
	private JLabel gifLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 365);
		setSize(450, 365);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.black);
		setContentPane(contentPane);
		setVisible(true);

		JPanel panel_header = new JPanel();
		contentPane.add(panel_header, BorderLayout.NORTH);
		panel_header.setLayout(new GridLayout(2, 2));

		JLabel lblNewLabel = new JLabel("Username");
		panel_header.add(lblNewLabel);

		tf_username = new JTextField();
		panel_header.add(tf_username);
		tf_username.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Password");
		panel_header.add(lblNewLabel_1);

		tf_password = new JPasswordField ();
		panel_header.add(tf_password);
		 tf_password.setEchoChar('*');
		tf_password.setColumns(10);

		JPanel panel_footer = new JPanel();
		contentPane.add(panel_footer, BorderLayout.SOUTH);

		JButton btn_login = new JButton("Log in");
		panel_footer.add(btn_login);
		btn_login.addActionListener(this);

		JButton btn_logout = new JButton("Log out");
		panel_footer.add(btn_logout);
		 btn_logout.addActionListener(this);

		//loadVideo(contentPane);
		loadImage(contentPane);
		addUser();
	}

	private void loadImage(JPanel p) {
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("image/image1.jpg"));
			picLabel = new JLabel(new ImageIcon(myPicture));
			p.add(picLabel);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	EmbeddedMediaPlayer mediaPlayer;
	private void loadVideo(JPanel p) {

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		p.add(mediaPlayerComponent);
		mediaPlayer = mediaPlayerComponent.mediaPlayer();
		
		// mediaPlayer.media().play("image/bien.mp4");

		//mediaPlayerComponent.setVisible(false);
		mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void error(MediaPlayer mediaPlayer) {
				System.out.println("Error occurred: " + mediaPlayer.media().info());
				// System.out.println(mediaPlayer.getMediaState());
			}

			@Override
			public void playing(MediaPlayer mediaPlayer) {
				System.out.println("Playing...");
			}

			@Override
			public void finished(MediaPlayer mediaPlaye) {
				System.out.println("Finished");
			//	mediaPlayer.media().play("image/bien.mp4");
			}
		});
	}

	private Map<String, String> user = new HashMap<String, String>();
	private boolean isLogin = false;

	private void addUser() {
		user.put("giang", "123");
	}

	private boolean checkUser(String username, String password) {
		if (!user.containsKey(username))
			return false;
		return user.get(username).equals(password);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isLogin) {
			if (e.getActionCommand().equals("Log out")) {
				mediaPlayerComponent.setVisible(false);
				picLabel.setVisible(true);
				mediaPlayer.release();
				contentPane.remove(mediaPlayerComponent);
				isLogin = false;
			}
		} else {
			if (e.getActionCommand().equals("Log in")) {
				String usename = tf_username.getText().trim();
				String password = tf_password.getText().trim();
				if (checkUser(usename, password)) {
					picLabel.setVisible(false);
					loadVideo(contentPane);
					isLogin = true;
					mediaPlayer.media().play("image/bien.mp4");
				} else {
					JOptionPane.showMessageDialog(null, "Username or password is incorrect!");
					tf_password.setText("");
				}
			}
		}

	}

}
