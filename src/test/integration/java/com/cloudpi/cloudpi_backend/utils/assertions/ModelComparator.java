package com.cloudpi.cloudpi_backend.utils.assertions;

public interface ModelComparator<T1, T2> {

    boolean areEquals(T1 obj1, T2 obj2);

}
