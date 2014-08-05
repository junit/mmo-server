package tool.message;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import tool.ftl.LanguageEnum;
import tool.util.Config;

public class MainUi extends JFrame implements ActionListener {
	private static final long serialVersionUID = -6067261849886344860L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JList list;
	private HashMap<String, String> messagePathes = new HashMap<>();
	private Generator generator = new Generator();
	private JCheckBox javaServer;
	private JCheckBox javaClient;
	private JCheckBox asClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUi frame = new MainUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainUi() throws Exception {
		init();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		javaServer = new JCheckBox("java server");
		javaServer.setBounds(6, 6, 103, 23);
		contentPane.add(javaServer);

		javaClient = new JCheckBox("java client");
		javaClient.setBounds(6, 32, 103, 23);
		contentPane.add(javaClient);

		asClient = new JCheckBox("as client");
		asClient.setBounds(6, 57, 103, 23);
		contentPane.add(asClient);

		list = new JList(messagePathes.keySet().toArray());

		JScrollPane scrollBar = new JScrollPane();
		scrollBar.setBounds(115, 10, 635, 509);
		scrollBar.setViewportView(list);
		contentPane.add(scrollBar);

		JButton create = new JButton("create");
		create.addActionListener(this);
		create.setBounds(681, 529, 93, 23);
		contentPane.add(create);
	}

	public boolean init() throws Exception {
		File dir = new File(Config.getInstance().getMessagePath());
		for (File file : dir.listFiles()) {
			if (!file.getName().endsWith("xml")) {
				continue;
			}
			messagePathes.put(file.getName(), file.getPath());
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (list.getSelectedValuesList().isEmpty()) {
			JOptionPane.showMessageDialog(null, "没有选择xml文件");
			return ;
		}
		
		if (!javaServer.isSelected() && !javaClient.isSelected() && !asClient.isSelected()) {
			JOptionPane.showMessageDialog(null, "没有选择生成类型");
			return ;
		}
		
		try {
			for (Object value : list.getSelectedValuesList()) {
				String path = messagePathes.get(value);
				if (javaServer.isSelected()) {
					generator.generate(LanguageEnum.JAVA_SERVER, path);
				}
				if (javaClient.isSelected()) {
					generator.generate(LanguageEnum.JAVA_CLIENT, path);
				}
				if (asClient.isSelected()) {
					generator.generate(LanguageEnum.AS, path);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "操作失败");
			return ;
		}
		
		JOptionPane.showMessageDialog(null, "操作成功");
	}
}
