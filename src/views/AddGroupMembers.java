/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ContactInfo;
import controllers.GroupInfo;
import controllers.GroupMembers;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import models.ContactModel;

/**
 *
 * @author DELL
 */
public class AddGroupMembers extends javax.swing.JInternalFrame {

    /**
     * Creates new form AddGroupMembers
     */
    private JDesktopPane desktop;
    private JRadioButton cb[];
    private GroupInfo group;
    String tableName;
    private JTable table;

    public AddGroupMembers(JDesktopPane desktop, GroupInfo group, JTable table) {
        initComponents();
        this.desktop = desktop;
        this.group = group;
        this.table = table;
        this.setTitle("Add Members to " + group.getName().toUpperCase() + " Group");
        tableName = group.getTableName();

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
        jScrollPane2 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMembers = new javax.swing.JTextArea();
        cbkSelect = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        btnSelectAll = new javax.swing.JCheckBox();
        btnClose = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Add Group Members");

        jScrollPane2.setMinimumSize(new java.awt.Dimension(332, 271));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(332, 271));

        panel.setMinimumSize(new java.awt.Dimension(0, 0));
        panel.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 269, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(panel);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("List of Members to add");

        taMembers.setColumns(20);
        taMembers.setLineWrap(true);
        taMembers.setRows(5);
        taMembers.setToolTipText("Enter new Contacts");
        taMembers.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taMembers);

        cbkSelect.setText("Select members  From Contacts");
        cbkSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkSelectActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCopy.setText("Copy Selected >>");
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        btnSelectAll.setText("Select All");
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSelectAll)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbkSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(cbkSelect)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(btnCopy))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSelectAll)))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnClose))
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

    private void cbkSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkSelectActionPerformed
        // TODO add your handling code here:
        ContactInfo[] contact = new ContactModel().getContacts();
        cb = new JRadioButton[contact.length];
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if (cbkSelect.isSelected()) {
            for (int i = 0; i < contact.length; i++) {
                cb[i] = new JRadioButton();
                cb[i].setText(contact[i].getMobNumber());
                cb[i].setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(cb[i]);
                panel.revalidate();
                panel.repaint();
            }
        } else {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();

        }


    }//GEN-LAST:event_cbkSelectActionPerformed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        // TODO add your handling code here:
        Component components[] = panel.getComponents();
        StringBuffer buf = new StringBuffer();
        String newLine = System.getProperty("line.separator");
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JRadioButton) {
                if (((JRadioButton) components[i]).isSelected()) {
                    buf.append(((JRadioButton) components[i]).getText() + newLine);
                }

            }
        }
        taMembers.setText(buf.toString());
    }//GEN-LAST:event_btnCopyActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        GroupMembers members = new GroupMembers();
        String contacts[] = taMembers.getText().split("\n");
        for (int i = 0; i < contacts.length; i++) {
            members.setContact(contacts[i]);
            if (members.save(tableName) > 0) {
                this.dispose();
                ((views.Groups.MyTable) table.getModel()).loadTable();
                JOptionPane.showMessageDialog(null, "Members Saved Successfull", "Success message", JOptionPane.INFORMATION_MESSAGE);

            }
        }


    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        // TODO add your handling code here:
        Component components[] = panel.getComponents();
        if (btnSelectAll.isSelected()) {
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof JRadioButton) {
                    ((JRadioButton) components[i]).setSelected(true);

                }
            }
        }else{
            for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JRadioButton) {
                ((JRadioButton) components[i]).setSelected(false);

            }
        }
        }


    }//GEN-LAST:event_btnSelectAllActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox btnSelectAll;
    private javax.swing.JCheckBox cbkSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JTextArea taMembers;
    // End of variables declaration//GEN-END:variables
}
