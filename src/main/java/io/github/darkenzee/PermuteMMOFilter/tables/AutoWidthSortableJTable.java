package io.github.darkenzee.PermuteMMOFilter.tables;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
 
public class AutoWidthSortableJTable<T> extends JTable
{
	private static final long serialVersionUID = -5441137141858206L;
	
    private final TableModelListener listener = new TableModelListener() {
        @Override
        public void tableChanged( TableModelEvent e )
        {
            resetColumnWidths();
            doLayout();
        }
    };
    
    private TableSorter tableSorter;
    
    public AutoWidthSortableJTable()
    {
        super();
        setAutoscrolls(true);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setBorder(null);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setFont(new Font("monospaced", Font.PLAIN, 12));
    }
    
    @Override
    public Component prepareRenderer( TableCellRenderer renderer, int row, int column )
    {
        Component component = super.prepareRenderer(renderer,row,column);
        int rendererWidth = component.getPreferredSize().width;
        TableColumn tableColumn = getColumnModel().getColumn(column);
        tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width,tableColumn.getPreferredWidth()));
        return component;
    }
    
    private void resetColumnWidths()
    {
        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn column = getColumnModel().getColumn(i);
            int headerWidth = getTableHeader().getDefaultRenderer().getTableCellRendererComponent(this,column.getHeaderValue(), false, false, -1, i).getPreferredSize().width;
            column.setPreferredWidth(Math.max(75, headerWidth));
        }
    }
    
    @Override
    @Deprecated
    /**
     * Deprecated in favour of setActualModel, as that enforces the typing.
     * @param dataModel The TableModel, which should be of type GenericMetTableModel<T>. Use setActualModel to enforce this.
     */
    public void setModel( TableModel dataModel )
    {
        if (getModel() != null) {
            getModel().removeTableModelListener(listener);
        }
        dataModel.addTableModelListener(listener);
        getTableSorter().setTableModel(dataModel);
        getTableSorter().setTableHeader(getTableHeader());
        super.setModel(getTableSorter());
    }
    
    public void setActualModel( GenericTableModel<T> dataModel )
    {
        //This method is to enforce the typing. Unfortuantely we can't type the other classes in use, and this type information is discarded.
        //So the default method is being used, as it is called in places outside of our code. This is essentially just to force typing.
        setModel(dataModel);
    }
    
    @Override
    public int getSelectedRow()
    {
        int ret = super.getSelectedRow();
        if (ret != -1) {
            ret = getTableSorter().indexOfViewToModel(ret);
        }
        return ret;
    }
    
    public int[] getSelectedModelRows() {
      int[] ret = getSelectedRows();
      for (int i = 0; i < ret.length; i++) {
        ret[i] = getTableSorter().indexOfViewToModel(ret[i]);
      }
      return ret;
    }
    
    @SuppressWarnings("unchecked")
    public GenericTableModel<T> getActualModel()
    {
        TableModel model = getTableSorter().getTableModel();
        if(model instanceof GenericTableModel) {
            return (GenericTableModel<T>) getTableSorter().getTableModel();
        } 
        return null;
    }
    
    protected TableSorter getTableSorter() {
        if (tableSorter == null) {
            tableSorter = new TableSorter();
        }
        return tableSorter;
    }
    
    
    public T getSelectedValue() {
        int selectedRow = getSelectedRow();
        if ( selectedRow != -1 ) {
            GenericTableModel<T> model = getActualModel();
            return model.getDataForRow(getSelectedRow());
        }
        return null;
    }
    
    public List<T> getSelectedValues() {
      List<T> values = new ArrayList<>();
      for (int selectedRow : getSelectedModelRows()) {
        GenericTableModel<T> model = getActualModel();
        values.add(model.getDataForRow(selectedRow));
      }
      return values;
    }
    
    public void onRowDoubleClicked(final IRowClickedCallbacker<T> callback){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if ( e.getClickCount() == 2 ) {
                    T value = getSelectedValue();
                    if (value != null) {
                        callback.rowClicked(e, value);
                    }
                }
            }
        });        
    }
    public void onRowSingleClicked(final IRowClickedCallbacker<T> callback){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if ( e.getClickCount() == 1 ) {
                    T value = getSelectedValue();
                    if (value != null) {
                        callback.rowClicked(e, value);
                    }
                }
            }
        });    
    }
}
