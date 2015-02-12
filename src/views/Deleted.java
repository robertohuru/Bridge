/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ContactInfo;
import controllers.DeletedInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.ContactModel;
import models.DeletedModel;

/**
 *
 * @author DELL
 */
public class Deleted extends javax.swing.JInternalFrame {

    /**
     * Creates new form Deleted
     */
    private JDesktopPane pane;
    private JTextArea taMessage;
    final TableRowSorter<TableModel> sorter;

    public Deleted(JDesktopPane pane, JTextArea taMessage) {
        initComponents();
        this.dispose();
        sorter = new TableRowSorter<TableModel>(tblDeleted.getModel());
        this.pane = pane;
        this.taMessage = taMessage;
        tblDeleted.setRowHeight(36);
        tblDeleted.setSelectionBackground(new Color(51, 153, 255));
        tblDeleted.setSelectionForeground(new Color(255, 255, 255));
        tblDeleted.getColumnModel().getColumn(0).setPreferredWidth(15);
        tblDeleted.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblDeleted.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblDeleted.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblDeleted.setShowVerticalLines(false);
        JTableHeader th = tblDeleted.getTableHeader();
        th.setBackground(Color.cyan);
        TableColumn column = tblDeleted.getColumnModel().getColumn(4);
        column.setPreferredWidth(50);
        column.setCellRenderer(new Deleted.ButtonsRenderer());
        column.setCellEditor(new Deleted.ButtonsEditor(tblDeleted, pane));
        StringBuffer buf = new StringBuffer();
        String newLine = System.getProperty("line.separator");
        buf.append("Deleted Folder" + newLine + "Message Count :" + tblDeleted.getRowCount());
        if (tblDeleted.getRowCount() == 0) {
            taMessage.setText(buf.toString());
        } else {
            if (tblDeleted.getSelectedRow() < 0) {
                taMessage.setText(buf.toString());
            }
        }

        tblDeleted.setRowSorter(sorter);
        tfFilter.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
//        int col = tblDeleted.getColumnModel().getColumnCount() - 1;
//        for (int i = 0; i < col; i++) {
//            TableColumn tm = tblDeleted.getColumnModel().getColumn(i);
//            tm.setCellRenderer(new ColouredCellRenderer());
//        }

    }

    private void newFilter() {

        String text;
        text = tfFilter.getText().trim();
        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    class ColouredCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
            if ((row % 2) == 0) {
                setBackground(Color.MAGENTA);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }
            super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column);
            return this;
        }
    }

    class MyTable extends AbstractTableModel {

        private DeletedInfo[] inbox;
        private final String[] columnNames = {"#", "Message", "Name/Number", "Date Sent", ""
        };
        private Object[][] data;

        public MyTable() {
            inbox = new DeletedModel().getSentMessages();
            data = new Object[inbox.length][];
            Date date;
            String result = "";
            for (int i = 0; i < inbox.length; i++) {
                String name = inbox[i].getSender().trim();
                if(!name.startsWith("0")){
                    name = name.replaceFirst("254", "0");
                }
                ContactInfo cont = new ContactModel().getPerson(name);
                if(cont != null){
                    name = cont.getFirstName()+" "+cont.getLastName();
                }
                if (inbox[i].getDateSent() == null) {
                    result = "Unknown";
                } else {
                    try {
                        date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(inbox[i].getDateSent());
                        result = Utils.Util.DateTimeMysqlFormat(date);
                    } catch (ParseException ex) {
                        Logger.getLogger(Inbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                data[i] = new Object[]{new Integer(i + 1), inbox[i].getMessage(), name, result, ""};
            }
        }

        public void loadTable() {
            inbox = new DeletedModel().getSentMessages();
            data = new Object[inbox.length][];
            Date date;
            String result = "";
            for (int i = 0; i < inbox.length; i++) {
                String name = inbox[i].getSender().trim();
                if(!name.startsWith("0")){
                    name = name.replaceFirst("254", "0");
                }
                ContactInfo cont = new ContactModel().getPerson(name);
                if(cont != null){
                    name = cont.getFirstName()+" "+cont.getLastName();
                }
                if (inbox[i].getDateSent() == null) {
                    result = "Unknown";
                } else {
                    try {
                        date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(inbox[i].getDateSent());
                        result = Utils.Util.DateTimeMysqlFormat(date);
                    } catch (ParseException ex) {
                        Logger.getLogger(Inbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                data[i] = new Object[]{new Integer(i + 1), inbox[i].getMessage(), name, result, ""};
            }
            this.fireTableDataChanged();
        }

        public DeletedInfo getMessage(int rowIndex) {
            return inbox[rowIndex];
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 4;
        }

    }

    public void reload() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Inbox.MyTable) tblDeleted.getModel()).loadTable();
            }
        });
        timer.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeleted = new javax.swing.JTable();
        btnClear = new javax.swing.JCheckBox();
        tfFilter = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Deleted Messages");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/trash.png"))); // NOI18N

        tblDeleted.setModel(new MyTable());
        tblDeleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDeletedMousePressed(evt);
            }
        });
        tblDeleted.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDeletedKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDeleted);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        btnClear.setText("Clear All");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tfFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(293, 293, 293)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(jLabel1)
                    .addComponent(tfFilter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        if (btnClear.isSelected()) {
            int answer = JOptionPane.showConfirmDialog(null, "Do you want to clear all deleted messages ?", "Confirm Delete Messages !!", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (new DeletedModel().deleteAll() > 0) {
                    ((Deleted.MyTable) tblDeleted.getModel()).loadTable();

                } else {
                    JOptionPane.showMessageDialog(null, "Message was not deleted from the database !", "Error Messages !", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        taMessage.setText("");
        btnClear.setSelected(false);
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblDeletedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDeletedKeyReleased
        // TODO add your handling code here:
        int row = tblDeleted.getSelectedRow();
        DeletedInfo personalInfo = ((Deleted.MyTable) tblDeleted.getModel()).getMessage(row);
        taMessage.setText(personalInfo.getMessage());
    }//GEN-LAST:event_tblDeletedKeyReleased

    private void tblDeletedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDeletedMousePressed
        // TODO add your handling code here:
        int row = tblDeleted.getSelectedRow();
        DeletedInfo personalInfo = ((Deleted.MyTable) tblDeleted.getModel()).getMessage(row);
        taMessage.setText(personalInfo.getMessage());
    }//GEN-LAST:event_tblDeletedMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox btnClear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDeleted;
    private javax.swing.JTextField tfFilter;
    // End of variables declaration//GEN-END:variables
class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = Arrays.asList(new JButton("Delete"));

        public ButtonsPanel() {
            super();
            setOpaque(true);
            for (JButton b : buttons) {
                b.setFocusable(true);
                b.setRolloverEnabled(false);
                add(b);
            }

        }

    }

    class ImageButtonPanel extends JPanel {

        public final JButton b = new JButton("Delete");

        public ImageButtonPanel() {
            super();
            setOpaque(true);
            this.setBackground(Color.MAGENTA);
            b.setIcon(new ImageIcon(getClass().getResource("/resources/user4_16.png")));
            b.setFocusable(true);
            b.setRolloverEnabled(true);
            add(b);
        }
    }

    class ButtonsRenderer extends ButtonsPanel implements TableCellRenderer {

        public ButtonsRenderer() {
            super();
            //setName("Table.cellRenderer");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    class ImageButtonRenderer extends ImageButtonPanel implements TableCellRenderer {

        public ImageButtonRenderer() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

            return this;
        }
    }

    class ButtonsEditor extends ButtonsPanel implements TableCellEditor {

        private views.Message messageView;
        private final JDesktopPane desktop;
        private final JTable table;

        public ButtonsEditor(final JTable table, final JDesktopPane desktop) {
            super();
            this.desktop = desktop;
            this.table = table;

            //---->
            //DEBUG: view button click -> control key down + edit button(same cell) press -> remain selection color
            MouseListener ml = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ButtonModel m = ((JButton) e.getSource()).getModel();
                    JButton button = (JButton) e.getSource();
                    if (button.getActionCommand().equalsIgnoreCase("Delete")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        DeletedInfo personalInfo = ((Deleted.MyTable) table.getModel()).getMessage(row);
                        int answer = JOptionPane.showConfirmDialog(null, "Do you really want to delete this Message completely ?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            if (personalInfo.delete() > 0) {
                                ((Deleted.MyTable) table.getModel()).loadTable();
                                StringBuffer buf = new StringBuffer();
                                String newLine = System.getProperty("line.separator");
                                buf.append("Deleted Folder" + newLine + "Message Count :" + ((Deleted.MyTable) table.getModel()).getRowCount());
                                taMessage.setText(buf.toString());
                            } else {
                                JOptionPane.showMessageDialog(null, "Message was not deleted from the database !", "Error Messages !", JOptionPane.ERROR_MESSAGE);
                            }

                        }

                    }
                }
            };
            buttons.get(0).addMouseListener(ml);
            //buttons.get(1).addMouseListener(ml);
            //buttons.get(2).addMouseListener(ml);
            //<----

            buttons.get(0).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.setBackground(table.getSelectionBackground());
            return this;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        //Copid from AbstractCellEditor
        //protected EventListenerList listenerList = new EventListenerList();
        transient protected ChangeEvent changeEvent = null;

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        @Override
        public boolean stopCellEditing() {
            fireEditingStopped();
            return true;
        }

        @Override
        public void cancelCellEditing() {
            fireEditingCanceled();
        }

        @Override
        public void addCellEditorListener(CellEditorListener l) {
            listenerList.add(CellEditorListener.class, l);
        }

        @Override
        public void removeCellEditorListener(CellEditorListener l) {
            listenerList.remove(CellEditorListener.class, l);
        }

        public CellEditorListener[] getCellEditorListeners() {
            return listenerList.getListeners(CellEditorListener.class);
        }

        protected void fireEditingStopped() {
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == CellEditorListener.class) {
                    // Lazily create the event:
                    if (changeEvent == null) {
                        changeEvent = new ChangeEvent(this);
                    }
                    ((CellEditorListener) listeners[i + 1]).editingStopped(changeEvent);
                }
            }
        }

        protected void fireEditingCanceled() {
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == CellEditorListener.class) {
                    // Lazily create the event:
                    if (changeEvent == null) {
                        changeEvent = new ChangeEvent(this);
                    }
                    ((CellEditorListener) listeners[i + 1]).editingCanceled(changeEvent);
                }
            }

        }
    }
}
