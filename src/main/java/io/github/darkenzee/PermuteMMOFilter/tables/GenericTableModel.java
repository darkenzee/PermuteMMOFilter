package io.github.darkenzee.PermuteMMOFilter.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
import javax.swing.table.DefaultTableModel;
 
public class GenericTableModel<T_Elements> extends DefaultTableModel
{
	private static final long serialVersionUID = -1452078359980995224L;
	
	private final List<Class<?>> m_columnTypes;
    private final RowConverter<T_Elements> m_converter;
    private final RowFilter<T_Elements> m_filter;
    private final List<T_Elements> m_rowObjects = new ArrayList<T_Elements>();
    private int m_filteredCount = 0;
    
    public GenericTableModel(String[] columns, Class<?>[] columnTypes, RowConverter<T_Elements> converter, RowFilter<T_Elements> filter)
    {
        super(columns, 0);
        m_columnTypes = Arrays.asList(columnTypes);
        m_converter = converter;
        m_filter = filter;
    }
    
    public GenericTableModel(String[] columns, Class<?>[] columnTypes, RowConverter<T_Elements> converter)
    {
        this(columns, columnTypes, converter, new RowFilter<T_Elements>() {
            @Override
            public boolean include( T_Elements rowElement )
            {
                return true;
            }});
    }
 
    @Override
    public Class<?> getColumnClass( int columnIndex )
    {
        return m_columnTypes.get(columnIndex);
    }
    
    public void addRow(T_Elements rowElement) {
        if (m_filter.include(rowElement)) {
            addRow(m_converter.convertRow(rowElement));
            m_rowObjects.add(rowElement);
        } else {
            m_filteredCount++;
        }
    }
    
    public void updateModel(T_Elements[] rowElements) {
        clearModel();
        Comparator<T_Elements> comparator = getComparator();
        if (comparator != null) {
            Arrays.sort(rowElements, comparator);
        }
        for (T_Elements rowElement : rowElements) {
            addRow(rowElement);
        }
    }
    
    public void updateModel(List<T_Elements> rowElements) {
        clearModel();
        Comparator<T_Elements> comparator = getComparator();
        if (comparator != null) {
            Collections.sort(rowElements, comparator);
        }
        for (T_Elements rowElement : rowElements) {
            addRow(rowElement);
        }
    }
    
    public void clearModel() {
        setRowCount(0);
        m_rowObjects.clear();
        m_filteredCount = 0;
    }
    
    public T_Elements getDataForRow(int row) {
        return m_rowObjects.get(row);
    }
    
    @Override
    public boolean isCellEditable( int row, int column )
    {
        return false;
    }
    
    public int filteredCount()
    {
        return m_filteredCount;
    }
    
    /*
     * Override hook for custom sorting.
     */
    public Comparator<T_Elements> getComparator() {
        return null;
    }
}
