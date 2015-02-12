/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Utils.Util;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import models.GroupModel;

/**
 *
 * @author DELL
 */
public class Groups extends javax.swing.JInternalFrame {

    /**
     * Creates new form Groups
     */
    private JDesktopPane desktop;
    private JTextArea taMesage;
    public Groups(JDesktopPane desktop, JTextArea taMessage) {
        initComponents();
        this.dispose();
        this.taMesage = taMessage;
        this.desktop = desktop;
        tblGroups.setRowHeight(36);
        tblGroups.setSelectionBackground(new Color(51, 153, 255));
        tblGroups.setSelectionForeground(new Color(255, 255, 255));
        tblGroups.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblGroups.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblGroups.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblGroups.getColumnModel().getColumn(3).setPreferredWidth(70);
        tblGroups.setShowVerticalLines(false);
        JTableHeader th = tblGroups.getTableHeader();
        th.setBackground(Color.cyan);
        TableColumn column = tblGroups.getColumnModel().getColumn(4);
        column.setPreferredWidth(300);
        column.setCellRenderer(new Groups.ButtonsRenderer());
        column.setCellEditor(new Groups.ButtonsEditor(tblGroups, desktop));
        TableColumnAdjuster tca = new TableColumnAdjuster(tblGroups);
        tca.adjustColumns();
    }

    class MyTable extends AbstractTableModel {

        private GroupInfo[] groups;
        private final String[] columnNames = {"#", "Group Name", "Description", "Number of Members", ""
        };
        private Object[][] data = new Object[0][0];

        public MyTable() {
            groups = new GroupModel().getGroups();
            data = new Object[groups.length][];
            for (int i = 0; i < groups.length; i++) {
                GroupMembers info[] = new GroupModel().getGroupMembers(groups[i]);
                int num = info.length;
                data[i] = new Object[]{new Integer(i + 1), groups[i].getName(), groups[i].getDescription(), num, ""};
            }
        }

        public void loadTable() {
            groups = new GroupModel().getGroups();
            data = new Object[groups.length][];
            for (int i = 0; i < groups.length; i++) {
                GroupMembers info[] = new GroupModel().getGroupMembers(groups[i]);
                int num = info.length;
                data[i] = new Object[]{new Integer(i + 1), groups[i].getName(), groups[i].getDescription(), num, ""};
            }
            this.fireTableDataChanged();
        }

        public GroupInfo getGroup(int rowIndex) {
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
        tblGroups = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Groups");

        tblGroups.setModel(new MyTable());
        tblGroups.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGroupsMouseClicked(evt);
            }
        });
        tblGroups.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblGroupsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblGroups);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addContainerGap())
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

    private void tblGroupsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGroupsKeyReleased
        // TODO add your handling code here:
        int row = tblGroups.getSelectedRow();
        GroupInfo personalInfo = ((Groups.MyTable) tblGroups.getModel()).getGroup(row);
        StringBuffer buf = new StringBuffer();
        String line = System.getProperty("line.separator");
        String name = "Group Name :"+personalInfo.getName().toUpperCase()+line+"Description :"+personalInfo.getDescription();
        buf.append(name);
        taMesage.setText(buf.toString());
    }//GEN-LAST:event_tblGroupsKeyReleased

    private void tblGroupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGroupsMouseClicked
        // TODO add your handling code here:
        int row = tblGroups.getSelectedRow();
        GroupInfo personalInfo = ((Groups.MyTable) tblGroups.getModel()).getGroup(row);
        StringBuffer buf = new StringBuffer();
        String line = System.getProperty("line.separator");
        String name = "Group Name :"+personalInfo.getName().toUpperCase()+line+"Description :"+personalInfo.getDescription();
        buf.append(name);
        taMesage.setText(buf.toString());
    }//GEN-LAST:event_tblGroupsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGroups;
    // End of variables declaration//GEN-END:variables
class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = Arrays.asList(new JButton("View Members"), new JButton("Send Message"), new JButton("Add Members"));

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

        private views.Members members;
        private views.NewMessage mesg;
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
                    if (button.getActionCommand().equalsIgnoreCase("View Members")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        GroupInfo personalInfo = ((Groups.MyTable) table.getModel()).getGroup(row);
                        members = new Members(desktop, table, personalInfo);
                        desktop.add(members);
                        members.setVisible(true);
                        try {
                            members.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Groups.class.getName(), ex.getMessage());
                        }

                    } else if (button.getActionCommand().equalsIgnoreCase("Send Message")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        GroupInfo personalInfo = ((Groups.MyTable) table.getModel()).getGroup(row);
                        mesg = new NewMessage(desktop, personalInfo);
                        desktop.add(mesg);
                        mesg.setVisible(true);
                        try {
                            mesg.setSelected(true);
                            //add.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Groups.class.getName(), ex.getMessage());
                        }

                    } else if (button.getActionCommand().equalsIgnoreCase("Add Members")) {
                        int row = table.convertRowIndexToModel(table.getEditingRow());
                        GroupInfo personalInfo = ((Groups.MyTable) table.getModel()).getGroup(row);
                        add = new AddGroupMembers(desktop, personalInfo, table);
                        desktop.add(add);
                        add.setVisible(true);
                        try {
                            add.setSelected(true);
                            //add.setMaximum(true);
                        } catch (PropertyVetoException ex) {
                            Util.logError(Groups.class.getName(), ex.getMessage());
                        }

                    }

                }
            };
            buttons.get(0).addMouseListener(ml);
            buttons.get(1).addMouseListener(ml);
            buttons.get(2).addMouseListener(ml);
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