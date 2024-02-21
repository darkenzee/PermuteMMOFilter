package io.github.darkenzee.PermuteMMOFilter.tables;

import java.awt.event.MouseEvent;

public interface IRowClickedCallbacker<T>{
    public void rowClicked(MouseEvent e, T selectedItem); 
}