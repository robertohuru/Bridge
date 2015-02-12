/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Utils.Util;
import controllers.ContactInfo;
import controllers.GroupInfo;
import controllers.GroupMembers;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
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
import models.GroupModel;

/**
 *
 * @author DELL
 */
public class Members extends javax.swing.JInternalFrame {

    /**
     * Creates new form Members
     */
    private JDesktopPane desktop;
    private GroupInfo info;
    private String name;
    private String tblName;
    private JTable myTable;
    public Members(JDesktopPane desktop,JTable myTable, GroupInfo info) {
        this.desktop = desktop;
        this.info = info;
        this.myTable = myTable;
        name = info.getName().toUpperCase();
        tblName = info.getTableName();
        initComponents();
        int screenWidthHeight[] = Util.getScreenSize();
        setLocation((desktop.getWidth() / 4), 0);
        this.setTitle(name + " GROUP MEMBERS");
        tblMembers.setRowHeight(25);
        tblMembers.setSelectionBackground(new Color(51, 153, 255));
        tblMembers.setSelectionForeground(new Color(255, 255, 255));
        tblMembers.getColumnModel().getColumn(0).setPreferredWidth(15);
        tblMembers.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblMembers.setShowVerticalLines(false);
        TableColumn column = tblMembers.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column.setCellRenderer(new Members.ButtonsRenderer());
        column.setCellEditor(new Members.ButtonsEditor(tblMembers, desktop));
        JTableHeader th = tblMembers.getTableHeader();
        th.setBackground(Color.cyan);
    }

    class MyTable extends AbstractTableModel {

        private GroupMembers[] groups;
        private final String[] columnNames = {"#", "Name/Number", ""
        };
        private Object[][] data = new Object[0][0];

        public MyTable() {
            groups = new GroupModel().getGroupMembers(info);
            data = new Object[groups.length][];
            for (int i = 0; i < groups.length; i++) {
                String name = groups[i].getContact().trim();
                if(!name.startsWith("0")){
                    name = name.replaceFirst("254", "0");
                }
                ContactInfo cont = new ContactModel().getPerson(name);
                if(cont != null){
                    name = cont.getFirstName()+" "+cont.getLastName();
                }
                if(cont != null){
                    name = cont.getFirstName()+" "+cont.getLastName();
                }
                data[i] = new Object[]{new Integer(i + 1), name, ""};
            }
        }

        public void loadTable() {
            groups = new GroupModel().getGroupMembers(info);
            data = new Object[groups.length][];
            for (int i = 0; i < groups.length; i++) {
                String name = groups[i].getContact().trim();
                if(!name.startsWith("0")){
                    name = name.replaceFirst("254", "0");
                }
                ContactInfo cont = new ContactModel().getPerson(name);
                if(cont != null){
                    name = cont.getFirstName()+" "+cont.getLastName();
                }
                data[i] = new Object[]{new Integer(i + 1), name, ""};
            }
            this.fireTableDataChanged();
        }

        public GroupMembers getMember(int rowIndex) {
            return groups[rowIndex];
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
            return columnIndex == 2;
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
        tblMembers = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Group Members");

        tblMembers.setModel(new MyTable());
        jScrollPane1.setViewportView(tblMembers);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMembers;
    // End of variables declaration//GEN-END:variables
class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = Arrays.asList(new JButton("Send Message"), new JButton("Delete"));

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

    class ButtonsEditor extends ButtonsPanel implements TableCellEditor {

        private views.NewMessage members;
        private views.AddGroupMembers add;
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
                    if (button.getActionCommand().equalsIgnoreCase("Send Message")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        GroupMembers personalInfo = ((Members.MyTable) table.getModel()).getMember(row);
                        members = new NewMessage(desktop, personalInfo);
                        desktop.add(members);
                        members.setVisible(true);
                        try {
                            members.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Members.class.getName(), ex.getMessage());
                        }

                    } else if (button.getActionCommand().equalsIgnoreCase("Delete")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        GroupMembers personalInfo = ((Members.MyTable) table.getModel()).getMember(row);
                        ContactInfo cont = new ContactModel().getPerson(personalInfo.getContact().trim());
                        String contactname = personalInfo.getContact();
                        if(cont != null){
                            contactname = cont.getFirstName()+" "+cont.getLastName();
                        }
                        int answer = JOptionPane.showConfirmDialog(null, "Do you want to delete "+contactname+"  from "+name+" group?", "Confirm Delete Messages !!", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            if (personalInfo.delete(tblName) > 0) {
                                ((Members.MyTable) table.getModel()).loadTable();
                                ((views.Groups.MyTable) myTable.getModel()).loadTable();
                                JOptionPane.showMessageDialog(null, "Member Deleted Successfully !", "Success Message !", JOptionPane.INFORMATION_MESSAGE);
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Member Not Deleted !", "Error Message !", JOptionPane.ERROR_MESSAGE);
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
