package tool.db;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import tool.ftl.LanguageEnum;

public class Start extends JFrame {
	private static final long serialVersionUID = 7975473610009954340L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JRadioButton config;
	private JRadioButton data;
	private JButton genButton;
	private JButton genButton2;
	private JButton genButton3;
	@SuppressWarnings("rawtypes")
	private JList list;
	private Generator generator;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Start() throws Exception {
		generator = new Generator();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 793);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		data = new JRadioButton("数据");
		data.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					config.setSelected(false);
					list.setListData(DbOpt.getInstance().getDataTables());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		data.setBounds(16, 6, 121, 23);
		contentPane.add(data);

		config = new JRadioButton("配置");
		config.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					data.setSelected(false);
					list.setListData(DbOpt.getInstance().getConfigTables());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		config.setBounds(139, 6, 121, 23);
		contentPane.add(config);

		list = new JList();

		JScrollPane scrollBar = new JScrollPane();
		scrollBar.setBounds(16, 35, 692, 680);
		scrollBar.setViewportView(list);
		contentPane.add(scrollBar);

			genButton = new JButton("java server");
			genButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					for (Object table : list.getSelectedValuesList()) {
						try {
							if (data.isSelected()) {
								generator.generateData((String) table);
							}
							if (config.isSelected()) {
								generator.generateConfig((String) table, LanguageEnum.JAVA_SERVER);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null, "操作成功");
				}
			});
			genButton.setBounds(615, 722, 93, 23);
			contentPane.add(genButton);
		
		genButton2 = new JButton("java client");
		genButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				for (Object table : list.getSelectedValuesList()) {
					try {
						if (config.isSelected()) {
							generator.generateConfig((String) table, LanguageEnum.JAVA_CLIENT);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "操作成功");
			}
		});
		genButton2.setBounds(522, 722, 93, 23);
		contentPane.add(genButton2);
		
		genButton3 = new JButton("as");
		genButton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				for (Object table : list.getSelectedValuesList()) {
					try {
						if (config.isSelected()) {
							generator.generateConfig((String) table, LanguageEnum.AS);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "操作成功");
			}
		});
		genButton3.setBounds(429, 722, 93, 23);
		contentPane.add(genButton3);
	}
}
