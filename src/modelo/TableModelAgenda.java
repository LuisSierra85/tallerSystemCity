/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Luis Sierra
 */
public class TableModelAgenda extends AbstractTableModel {
    
    private String[] columns;
    private Object[][] rows;

    public TableModelAgenda() {
    }

    public TableModelAgenda(Object[][] rows, String[] columns) {
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public int getRowCount() {
        return this.rows.length;
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.rows[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int col) {
        return this.columns[col];
    }
}
