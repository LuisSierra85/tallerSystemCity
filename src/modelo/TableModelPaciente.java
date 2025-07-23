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
public class TableModelPaciente extends AbstractTableModel{
    
     private String[] columns;
     private Object[][] rows;
     
public TableModelPaciente (){}

public TableModelPaciente(Object[][] data, String[] columnsName){
        this.columns = columnsName;
        this.rows = data;
}


    public int getRowCount() {
    
        return this.rows.length;
        
    }

 
    public int getColumnCount() {
    
        return this.columns.length;
        
    }

   
    public Object getValueAt(int rowIndex, int columnIndex) {
    
        return this.rows[rowIndex][columnIndex];
        
    }
    
    public String getColumnName(int col){
        
        return this.columns[col];
        
    }


    
}
