package DVD_rental;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Client {

	public void RentServer() {

	}

	static Registry registry;
	static Interface stub;

	private JPanel contentPane;
	JFrame frame;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) throws Exception {

		try {
			// Getting the registry
			registry = LocateRegistry.getRegistry(null);

			// Looking up the registry for the remote object
			stub = (Interface) registry.lookup("Interface");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	JButton RefreshBtn;
	private JLabel TimeLbl;
	private JLabel DateLbl;
	private JButton HelpButton;

	public JPanel MyPanel;
	public JPanel RentalPanel;

	private JTable FilmTable;
	private JTable MyFilmsTable;

	Calendar borrower = new GregorianCalendar();

	DefaultTableModel tableModel = new DefaultTableModel() {
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			default:
				return String.class;
			}
		}
	};

	DefaultTableModel myFilmsTableModel;

	// Jtable objects
	Object row_data[] = new Object[4];
	Object myFilms_row_data[] = new Object[3];

	public void clock() {

		Thread clock = new Thread() {
			public void run() {
				try {
					for (;;) {
						Calendar cal = new GregorianCalendar();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH) + 1;
						int year = cal.get(Calendar.YEAR);

						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR_OF_DAY);

						TimeLbl.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":"
								+ String.format("%02d", second));
						DateLbl.setText(String.format("%02d", day) + "-" + String.format("%02d", month) + "-" + year);

						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		clock.start();
	}

	public void rentFilm() throws Exception {
		String checkedFilm = "";
		stub.newFilmCount_Clear();
		for (int i = 0; i < FilmTable.getRowCount(); i++) {
			Boolean checked = Boolean.valueOf(FilmTable.getValueAt(i, 0).toString());
			checkedFilm = FilmTable.getValueAt(i, 2).toString();
			if (checked) {
				if (Integer.valueOf(checkedFilm) < 1) {
					JOptionPane.showMessageDialog(new JFrame(), FilmTable.getValueAt(i,1) + " is unavailable", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					stub.newFilmCount_Add(String.valueOf(Integer.valueOf(checkedFilm)));
				} else {

					stub.newFilmCount_Add(String.valueOf(Integer.valueOf(checkedFilm) - 1));

					stub.myFilmName_Add(FilmTable.getValueAt(i, 1).toString());
					stub.myFilmFormat_Add(FilmTable.getValueAt(i, 3).toString());
					stub.myFilmTime_Add(String.format("%02d", borrower.get(Calendar.DAY_OF_MONTH)) + "-"
							+ String.format("%02d", borrower.get(Calendar.MONTH) + 3) + "-"
							+ borrower.get(Calendar.YEAR));
				}
			} else {
				stub.newFilmCount_Add(String.valueOf(Integer.valueOf(checkedFilm)));
			}
		}

		try {
			stub.writeToMyFilms();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		refresh();
		refresh();
		
	}

	public void refresh() {
		try {
			int rowCount = tableModel.getRowCount();
			for (int i = rowCount - 1; i >= 0; i--) {
				tableModel.removeRow(i);
			}

			int rowCount2 = myFilmsTableModel.getRowCount();
			for (int i = rowCount2 - 1; i >= 0; i--) {
				myFilmsTableModel.removeRow(i);
			}

			stub.readFilms();
			stub.readMyFilms();

			stub.combinedNewData();
			stub.combinedMyData();

			int film_nameSize = stub.filmName_Value().size();

			int myFilm_nameSize = stub.myFilmName_Value().size();

			stub.writeToFilms();
			stub.writeToMyFilms();

			for (int i = 0; i < film_nameSize; i++) {

				row_data[1] = stub.filmName_Value().get(i);
				row_data[2] = stub.filmCount_Value().get(i);
				row_data[3] = stub.filmFormat_Value().get(i);
				tableModel.addRow(row_data);
				tableModel.setValueAt(false, i, 0);
			}
			for (int i = 0; i < myFilm_nameSize; i++) {
				if (stub.myFilmName_Value().get(i) != null) {
					myFilms_row_data[0] = stub.myFilmName_Value().get(i);
					myFilms_row_data[1] = stub.myFilmFormat_Value().get(i);
					myFilms_row_data[2] = stub.myFilmTime_Value().get(i);
					myFilmsTableModel.addRow(myFilms_row_data);
				} else {
				}

			}

			stub.myFilmName_Clear();
			stub.myFilmTime_Clear();
			stub.myFilmFormat_Clear();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Client() throws RemoteException {
		// menu window
		frame = new JFrame();
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 716, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		// Refresh data Button
		RefreshBtn = new JButton("Refresh");
		RefreshBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		RefreshBtn.setBounds(574, 11, 121, 45);
		contentPane.add(RefreshBtn);

		TimeLbl = new JLabel("Time");
		TimeLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		TimeLbl.setBounds(10, 7, 69, 14);
		contentPane.add(TimeLbl);

		DateLbl = new JLabel("Date");
		DateLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		DateLbl.setBounds(89, 7, 76, 14);
		contentPane.add(DateLbl);

		HelpButton = new JButton("HELP");
		HelpButton.setForeground(new Color(139, 0, 0));
		HelpButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		HelpButton.setBounds(471, 11, 89, 23);
		contentPane.add(HelpButton);
		HelpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), "Tick a film in the table, then get it using <Rent a film> Button. Your films are in <MyFilms> Tab. <Refresh> data, when it is necessary", "Dialog",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		frame.getContentPane().setLayout(null);
		JTabbedPane TabbedPane = new JTabbedPane(JTabbedPane.TOP);

		TabbedPane.setBounds(0, 32, 467, 481);
		frame.getContentPane().add(TabbedPane);

		RentalPanel = new JPanel();
		RentalPanel.setBackground(Color.LIGHT_GRAY);
		TabbedPane.addTab("Film Library", null, RentalPanel, null);

		JScrollPane rentalScrollPane = new JScrollPane();
		rentalScrollPane.setBounds(10, 42, 405, 320);
		RentalPanel.add(rentalScrollPane);

		FilmTable = new JTable();
		FilmTable.setRowSelectionAllowed(false);
		FilmTable.setFont(new Font("Tahoma", Font.BOLD, 11));
		FilmTable.setBackground(Color.LIGHT_GRAY);
		FilmTable.setAutoCreateRowSorter(true);
		rentalScrollPane.setViewportView(FilmTable);

		String columns[] = { "Select", "Film", "Count", "Format" };

		tableModel.setColumnIdentifiers(columns);
		FilmTable.setModel(tableModel);

		try {
			stub.readFilms();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < stub.filmName_Value().size(); i++) {

			row_data[1] = stub.filmName_Value().get(i);
			row_data[2] = stub.filmCount_Value().get(i);
			row_data[3] = stub.filmFormat_Value().get(i);
			tableModel.addRow(row_data);
			tableModel.setValueAt(false, i, 0);
		}

		JButton rentBtn = new JButton("Rent a film");
		rentBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		rentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					rentFilm();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		rentBtn.setBounds(510, 204, 137, 58);
		contentPane.add(rentBtn);

		MyPanel = new JPanel();
		MyPanel.setBackground(Color.GRAY);
		TabbedPane.addTab("My Films", null, MyPanel, null);

		RefreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
				refresh();
			}
		});

		JScrollPane myFilmsScrollPane = new JScrollPane();
		myFilmsScrollPane.setBounds(10, 42, 405, 320);
		MyPanel.add(myFilmsScrollPane);

		MyFilmsTable = new JTable();
		MyFilmsTable.setRowSelectionAllowed(false);
		MyFilmsTable.setFont(new Font("Tahoma", Font.BOLD, 11));
		MyFilmsTable.setBackground(Color.LIGHT_GRAY);

		myFilmsScrollPane.setViewportView(MyFilmsTable);

		String myFilms_columns[] = { "Film", "Format", "Expiration Date" };

		myFilmsTableModel = (DefaultTableModel) MyFilmsTable.getModel();

		myFilmsTableModel.setColumnIdentifiers(myFilms_columns);
		MyFilmsTable.setModel(myFilmsTableModel);

		try {
			stub.readMyFilms();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < stub.myFilmName_Value().size(); i++) {
			myFilms_row_data[0] = stub.myFilmName_Value().get(i);
			myFilms_row_data[1] = stub.myFilmFormat_Value().get(i);
			myFilms_row_data[2] = stub.myFilmTime_Value().get(i);
			myFilmsTableModel.addRow(myFilms_row_data);
		}

		clock();
	}

}
