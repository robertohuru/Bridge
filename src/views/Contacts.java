/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Utils.Util;
import controllers.ContactInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import models.ContactModel;

/**
 *
 * @author DELL
 */
public class Contacts extends javax.swing.JInternalFrame {

    /**
     * Creates new form Contacts
     */
    private JDesktopPane desktop;
    public Contacts(JDesktopPane desktop) {
        initComponents();
        this.desktop = desktop;
        setSize(desktop.getWidth(), desktop.getHeight());
        tblContacts.setRowHeight(36);
        tblContacts.setSelectionBackground(new Color(51, 153, 255));
        tblContacts.setSelectionForeground(new Color(255, 255, 255));
        tblContacts.getColumnModel().getColumn(0).setPreferredWidth(15);
        tblContacts.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblContacts.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblContacts.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblContacts.setShowVerticalLines(false);
        JTableHeader th = tblContacts.getTableHeader();
        th.setBackground(Color.cyan);
        TableColumn column = tblContacts.getColumnModel().getColumn(3);
        column.setPreferredWidth(200);
        column.setCellRenderer(new Contacts.ButtonsRenderer());
        column.setCellEditor(new Contacts.ButtonsEditor(tblContacts, desktop));
    }

    class MyTable extends AbstractTableModel {

        private ContactInfo[] inbox;
        private final String[] columnNames = {"#", "Name", "Phone Number", ""
        };
        private Object[][] data = new Object[0][0];

        public MyTable() {
            inbox = new ContactModel().getContacts();
            data = new Object[inbox.length][];
            Date date;
            String result = "";
            for (int i = 0; i < inbox.length; i++) {

                data[i] = new Object[]{new Integer(i + 1), inbox[i].getFirstName() + " " + inbox[i].getLastName(), inbox[i].getMobNumber(), ""};
            }
        }

        public void loadTable() {
            inbox = new ContactModel().getContacts();
            data = new Object[inbox.length][];
            Date date;
            String result = "";
            for (int i = 0; i < inbox.length; i++) {

                data[i] = new Object[]{new Integer(i + 1), inbox[i].getFirstName() + " " + inbox[i].getLastName(), inbox[i].getMobNumber(), ""};
            }
            this.fireTableDataChanged();
        }

        public ContactInfo getMessage(int rowIndex) {
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
            return columnIndex == 3;
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
        tblContacts = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Contacts");

        tblContacts.setModel(new MyTable());
        jScrollPane1.setViewportView(tblContacts);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblContacts;
    // End of variables declaration//GEN-END:variables
class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = Arrays.asList(new JButton("View Details"), new JButton("View Chat"));

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

        public final JButton b = new JButton("View");

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

        private views.Chat chat;
        private views.AddContact add;
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
                    if (button.getActionCommand().equalsIgnoreCase("view Details")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        ContactInfo personalInfo = ((Contacts.MyTable) table.getModel()).getMessage(row);
                        add = new AddContact(desktop, personalInfo);
                        desktop.add(add);
                        add.setVisible(true);
                        try {
                            add.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Contacts.class.getName(), ex.getMessage());
                            Logger.getLogger(Contacts.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (button.getActionCommand().equalsIgnoreCase("View Chat")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        ContactInfo personalInfo = ((Contacts.MyTable) table.getModel()).getMessage(row);
                        chat = new Chat(desktop, personalInfo);
                        desktop.add(chat);
                        chat.setVisible(true);
                        try {
                            chat.setSelected(true);
                            chat.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Contacts.class.getName(), ex.getMessage());
                            Logger.getLogger(Contacts.class.getName()).log(Level.SEVERE, null, ex);
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
