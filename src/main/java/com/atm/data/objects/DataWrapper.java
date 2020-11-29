package com.atm.data.objects;

import lombok.Data;

import java.util.function.Supplier;

@Data
public class DataWrapper<T> {

    private T data;

    public static <T> DataWrapper<T> of(T data) {
        DataWrapper<T> dataWrapper = new DataWrapper<>();
        dataWrapper.setData(data);

        return dataWrapper;
    }

    public static <T> DataWrapper<T> of(Supplier<T> supplier) {
        return DataWrapper.of(supplier.get());
    }

    public static DataWrapper<Void> of(Runnable runnable) {
        runnable.run();
        return DataWrapper.of((Void) null);
    }

}
