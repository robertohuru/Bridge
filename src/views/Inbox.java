/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ContactInfo;
import controllers.DeletedInfo;
import controllers.InboxController;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.ContactModel;
import models.InBoxModel;

/**
 *
 * @author DELL
 */
public class Inbox extends javax.swing.JInternalFrame {

    /**
     * Creates new form Inbox
     */
    private String port;
//    private Worker worker;
    private JDesktopPane pane;
    private JTextArea taMessage;
    final TableRowSorter<TableModel> sorter;

    public Inbox(JDesktopPane pane, JTextArea taMessage) {
        initComponents();
        this.dispose();
        sorter = new TableRowSorter<TableModel>(table.getModel());
        this.pane = pane;
        this.taMessage = taMessage;

        table.setRowHeight(36);
        table.setSelectionBackground(new Color(51, 153, 255));
        table.setSelectionForeground(new Color(255, 255, 255));
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setShowVerticalLines(false);
        TableColumn column = table.getColumnModel().getColumn(4);
        column.setPreferredWidth(200);
        column.setCellRenderer(new Inbox.ButtonsRenderer());
        column.setCellEditor(new Inbox.ButtonsEditor(table, pane));
        StringBuffer buf = new StringBuffer();
        String newLine = System.getProperty("line.separator");
        buf.append("Inbox Folder" + newLine + "Message Count :" + table.getRowCount());
        if (table.getRowCount() == 0) {
            taMessage.setText(buf.toString());
        } else {
            if (table.getSelectedRow() < 0) {
                taMessage.setText(buf.toString());
            }
        }
        reload();

        table.setRowSorter(sorter);
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
        JTableHeader th = table.getTableHeader();
        th.setBackground(Color.cyan);

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

        private InboxController[] inbox;
        private ContactInfo contact;
        private final String[] columnNames = {"#", "Message", "Name/Number", "Date Sent", ""
        };
        private Object[][] data = new Object[0][0];

        public MyTable() {
            inbox = new InBoxModel().getMessages();
            data = new Object[inbox.length][];
            Date date;
            String result = "";
            for (int i = 0; i < inbox.length; i++) {
                String name = inbox[i].getSender().trim();
                if (!name.startsWith("0")) {
                    name = name.replaceFirst("254", "0");
                }
                ContactInfo cont = new ContactModel().getPerson(name);
                if (cont != null) {
                    name = cont.getFirstName() + " " + cont.getLastName();
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
            inbox = new InBoxModel().getMessages();
            data = new Object[inbox.length][];
            Date date;
            String result = "";
            for (int i = 0; i < inbox.length; i++) {
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
                String name = inbox[i].getSender().trim();
                if (!name.startsWith("0")) {
                    name = name.replaceFirst("254", "0");
                }
                ContactInfo cont = new ContactModel().getPerson(name);
                if (cont != null) {
                    name = cont.getFirstName() + " " + cont.getLastName();
                }

                data[i] = new Object[]{new Integer(i + 1), inbox[i].getMessage(), name, result, ""};
            }
            this.fireTableDataChanged();
        }

        public InboxController getMessage(int rowIndex) {
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
                ((Inbox.MyTable) table.getModel()).loadTable();
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
        table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfFilter = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setClosable(true);
        setTitle("Recieved Messages");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/inbox.png"))); // NOI18N

        table.setModel(new MyTable());
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMousePressed(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setText("Search");

        jCheckBox1.setText("Delete All Recieved Messages");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()) {
            int answer = JOptionPane.showConfirmDialog(this, "Do you want to Delete all Recieved messages ?", "Confirm Delete Messages !", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                InboxController info[] = new InBoxModel().getMessages();
                DeletedInfo deleted = new DeletedInfo();
                for (int i = 0; i < info.length; i++) {
                    new InBoxModel().delete(info[i]);
                    deleted.setSender(info[i].getSender());
                    deleted.setMessage(info[i].getMessage());
                    deleted.setDateSent(info[i].getDateSent());
                    deleted.save();

                }
                ((Inbox.MyTable) table.getModel()).loadTable();

            }
        }
        taMessage.setText("");
        jCheckBox1.setSelected(false);

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        InboxController personalInfo = ((Inbox.MyTable) table.getModel()).getMessage(row);
        taMessage.setText(personalInfo.getMessage());
    }//GEN-LAST:event_tableKeyReleased

    private void tableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMousePressed
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        InboxController personalInfo = ((Inbox.MyTable) table.getModel()).getMessage(row);
        taMessage.setText(personalInfo.getMessage());
    }//GEN-LAST:event_tableMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField tfFilter;
    // End of variables declaration//GEN-END:variables
class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = Arrays.asList(new JButton("Reply"), new JButton("Delete"));

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

        public final JButton b = new JButton("Reply");

        public ImageButtonPanel() {
            super();
            setOpaque(true);

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
                    if (button.getActionCommand().equalsIgnoreCase("Reply")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        InboxController personalInfo = ((Inbox.MyTable) table.getModel()).getMessage(row);
                        messageView = new Message(personalInfo);
                        messageView.setVisible(true);

                    } else if (button.getActionCommand().equalsIgnoreCase("delete")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        InboxController personalInfo = ((Inbox.MyTable) table.getModel()).getMessage(row);
                        int answer = JOptionPane.showConfirmDialog(null, "Do you really want to delete this Message ", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            DeletedInfo deleted = new DeletedInfo();
                            if (personalInfo.delete() > 0) {
                                deleted.setSender(personalInfo.getSender());
                                deleted.setMessage(personalInfo.getMessage());
                                deleted.setDateSent(personalInfo.getDateSent());
                                deleted.save();
                                ((Inbox.MyTable) table.getModel()).loadTable();
                                StringBuffer buf = new StringBuffer();
                                String newLine = System.getProperty("line.separator");
                                buf.append("InBox Folder" + newLine + "Message Count :" + ((Inbox.MyTable) table.getModel()).getRowCount());
                                taMessage.setText(buf.toString());

                            } else {
                                JOptionPane.showMessageDialog(null, "Message was not deleted from the database !", "Error Messages !", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }

                }
            };
            buttons.get(0).addMouseListener(ml);
            buttons.get(1).addMouseListener(ml);
            //buttons.get(2).addMouseListener(ml);
            //<----

            buttons.get(0).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

            buttons.get(1).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Object o = table.getModel().getValueAt(table.getSelectedRow(), 0);
                    int row = table.convertRowIndexToModel(table.getEditingRow());
                    /* System.out.println(row);
                     int dateOfBirth = ((RegisteredPerson.MyTableModel)table.getModel()).getPersonalInfo(row).getHeight();
                     System.out.println(dateOfBirth);*/
                    Object o = table.getModel().getValueAt(row, 0);
                    fireEditingStopped();

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
