package books;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Books extends JFrame {

	static JLabel lTitle, lAuthor, lGenre, lYear;

	static JTextField fTitle, fAuthor, fGenre, fYear;

	static Object[][] data;

	static Object[] columns = { "Title", "Author", "Genre", "Year of publication" };

	static DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
		public Class getColumnClass(int column) {
			Class returnValue;

			if ((column >= 0) && (column < getColumnCount())) {
				returnValue = getValueAt(0, column).getClass();
			} else {

				returnValue = Object.class;
			}
			return returnValue;
		}
	};

	static JTable table = new JTable(tableModel);

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Connection conn = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost/books?useSSL=false", "root", "root");

			Statement s = conn.createStatement();

			String query = "SELECT title, author, genre, yearPubl from book";

			ResultSet rows = s.executeQuery(query);

			Object[] row;

			while (rows.next()) {

				row = new Object[] { rows.getString(1), rows.getString(2), rows.getString(3), rows.getInt(4) };

				tableModel.addRow(row);
			}
		}

		catch (SQLException ex) {

			System.out.println("SQLException: " + ex.getMessage());

			System.out.println("VendorError: " + ex.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		table.setFont(new Font("Serif", Font.BOLD, 15));

		table.setRowHeight(table.getRowHeight() + 10);

		table.setAutoCreateRowSorter(true);

		JScrollPane sp = new JScrollPane(table);

		frame.add(sp, BorderLayout.CENTER);

		JButton addBook = new JButton("Add book");

		addBook.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String sTitle = "", sAuthor = "", sGenre = "", sYear = "";

				sTitle = fTitle.getText();
				sAuthor = fAuthor.getText();
				sGenre = fGenre.getText();
				sYear = fYear.getText();

				int year = Integer.parseInt(sYear);

				Object[] book = { sTitle, sAuthor, sGenre, year };
				tableModel.addRow(book);

			}

		});

		JButton removeBook = new JButton("Remove book");

		removeBook.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				tableModel.removeRow(table.getSelectedRow());

			}

		});

		lTitle = new JLabel("Title");
		lAuthor = new JLabel("Author");
		lGenre = new JLabel("Genre");
		lYear = new JLabel("Year of publication");

		fTitle = new JTextField(10);
		fAuthor = new JTextField(10);
		fGenre = new JTextField(10);
		fYear = new JTextField(10);

		JPanel input = new JPanel();

		input.add(lTitle);
		input.add(fTitle);
		input.add(lAuthor);
		input.add(fAuthor);
		input.add(lGenre);
		input.add(fGenre);
		input.add(lYear);
		input.add(fYear);
		input.add(addBook);
		input.add(removeBook);

		frame.add(input, BorderLayout.SOUTH);

		frame.setSize(1200, 300);
		frame.setVisible(true);

		TableColumn tc = table.getColumn("Year of publication");
		TableAlign ta = new TableAlign();
		tc.setCellRenderer(ta);
	}

}