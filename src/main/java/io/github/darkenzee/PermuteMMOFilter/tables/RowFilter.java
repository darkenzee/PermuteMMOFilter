package io.github.darkenzee.PermuteMMOFilter.tables;

public interface RowFilter<T>
{
    boolean include(T rowElement);
}
