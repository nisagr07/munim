/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author nishant
 */
public class MyCellEditor extends DefaultCellEditor {

    private boolean keyTriggered;

    public MyCellEditor() {
        super(new JTextField());
        final JTextField textField = (JTextField) getComponent();
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!keyTriggered) {
                            textField.selectAll();
                        }
                    }
                });
            }
        });
    }

    public void setKeyTriggered(boolean keyTriggered) {
        this.keyTriggered = keyTriggered;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        final JTextField textField = (JTextField)
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        textField.selectAll();
        textField.setBorder(null);
        return textField;
    }
}
