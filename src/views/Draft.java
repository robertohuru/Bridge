/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Utils.Util;
import controllers.ContactInfo;
import controllers.DeletedInfo;
import controllers.DraftInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
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
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import models.ContactModel;
import models.DraftModel;

/**
 *
 * @author DELL
 */
public class Draft extends javax.swing.JInternalFrame {

    /**
     * Creates new form Draft
     */
    private JDesktopPane pane;
    private JTextArea taMessage;

    public Draft(JDesktopPane pane, JTextArea taMessage) {
        initComponents();
        this.pane = pane;
        this.taMessage = taMessage;
        tblDraft.setRowHeight(36);
        tblDraft.setSelectionBackground(new Color(51, 153, 255));
        tblDraft.setSelectionForeground(new Color(255, 255, 255));
        tblDraft.getColumnModel().getColumn(0).setPreferredWidth(15);
        tblDraft.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDraft.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblDraft.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblDraft.setShowVerticalLines(false);
        JTableHeader th = tblDraft.getTableHeader();
        th.setBackground(Color.cyan);
        TableColumn column = tblDraft.getColumnModel().getColumn(4);
        column.setPreferredWidth(200);
        column.setCellRenderer(new Draft.ButtonsRenderer());
        column.setCellEditor(new Draft.ButtonsEditor(tblDraft, pane));
        StringBuffer buf = new StringBuffer();
        String newLine = System.getProperty("line.separator");
        buf.append("Draft Folder"+newLine+"Message Count :"+tblDraft.getRowCount());
        if (tblDraft.getRowCount() == 0) {
            taMessage.setText(buf.toString());
        } else {
            if (tblDraft.getSelectedRow() < 0) {
                taMessage.setText(buf.toString());
            }
        }
//        int col = tblDraft.getColumnModel().getColumnCount() - 1;
//        for (int i = 0; i < col; i++) {
//            TableColumn tm = tblDraft.getColumnModel().getColumn(i);
//            tm.setCellRenderer(new ColouredCellRenderer());
//        }
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

        private DraftInfo[] inbox;
        private ContactInfo contact;
        private final String[] columnNames = {"#", "Message", "Number/Name", "Date Sent", ""
        };
        private Object[][] data = new Object[0][0];

        public MyTable() {
            inbox = new DraftModel().getDrafts();
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
                        Logger.getLogger(Draft.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(inbox[i].getReciever().startsWith("0")){
                    contact = new ContactModel().getPerson(inbox[i].getReciever().trim());
                }else{
                    contact = new ContactModel().getPerson("0" + inbox[i].getReciever().trim().substring(3));
                }
                
                String name = inbox[i].getReciever();
                if (contact != null) {
                    name = contact.getFirstName() + " " + contact.getLastName();
                }
                data[i] = new Object[]{new Integer(i + 1), inbox[i].getMessage(), name, result, ""};
            }
        }

        public void loadTable() {
            inbox = new DraftModel().getDrafts();
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
                if(inbox[i].getReciever().startsWith("0")){
                    contact = new ContactModel().getPerson(inbox[i].getReciever());
                }else{
                    contact = new ContactModel().getPerson("0" + inbox[i].getReciever().substring(3));
                }
                String name = inbox[i].getReciever();
                if (contact != null) {
                    name = contact.getFirstName() + " " + contact.getLastName();
                }

                data[i] = new Object[]{new Integer(i + 1), inbox[i].getMessage(), name, result, ""};
            }
            this.fireTableDataChanged();
        }

        public DraftInfo getMessage(int rowIndex) {
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
        tblDraft = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Drafts");

        tblDraft.setModel(new MyTable());
        tblDraft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDraftMousePressed(evt);
            }
        });
        tblDraft.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDraftKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDraft);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblDraftKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDraftKeyReleased
        // TODO add your handling code here:
        int row = tblDraft.getSelectedRow();
        DraftInfo personalInfo = ((Draft.MyTable) tblDraft.getModel()).getMessage(row);
        taMessage.setText(personalInfo.getMessage());
    }//GEN-LAST:event_tblDraftKeyReleased

    private void tblDraftMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDraftMousePressed
        // TODO add your handling code here:
        int row = tblDraft.getSelectedRow();
        DraftInfo personalInfo = ((Draft.MyTable) tblDraft.getModel()).getMessage(row);
        taMessage.setText(personalInfo.getMessage());
    }//GEN-LAST:event_tblDraftMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDraft;
    // End of variables declaration//GEN-END:variables
class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = Arrays.asList(new JButton("Send"), new JButton("Delete"));

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

        public final JButton b = new JButton("Send");

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

        private views.NewMessage messageView;
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
                    if (button.getActionCommand().equalsIgnoreCase("Send")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        DraftInfo personalInfo = ((Draft.MyTable) table.getModel()).getMessage(row);
                        messageView = new NewMessage(desktop,personalInfo);
                       messageView.setVisible(true);
                       desktop.add(messageView);
                        try {
                            messageView.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Draft.class.getName(), ex.getMessage());
                        }

                    } else if (button.getActionCommand().equalsIgnoreCase("delete")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        DraftInfo personalInfo = ((Draft.MyTable) table.getModel()).getMessage(row);
                        int answer = JOptionPane.showConfirmDialog(null, "Do you really want to delete this Message ", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            if (personalInfo.delete() > 0) {
                                ((Draft.MyTable) table.getModel()).loadTable();
                                StringBuffer buf = new StringBuffer();
                                String newLine = System.getProperty("line.separator");
                                buf.append("Draft Folder" + newLine + "Message Count :" + ((Draft.MyTable) table.getModel()).getRowCount());
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
