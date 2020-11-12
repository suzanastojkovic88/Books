package books;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

class TableAlign extends DefaultTableCellRenderer {
	public TableAlign() {
		setHorizontalAlignment(JLabel.CENTER);
	}
}