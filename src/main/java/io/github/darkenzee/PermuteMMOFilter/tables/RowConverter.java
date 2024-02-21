package io.github.darkenzee.PermuteMMOFilter.tables;

public interface RowConverter<T> {
    public Object[] convertRow(T rowElement);
}
