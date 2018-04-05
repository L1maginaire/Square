package com.example.square.utils.pagination;

/**
 * Created by l1maginaire on 4/5/18.
 */

import io.reactivex.Observable;

public interface PagingListener<T> {
    Observable<T> onNextPage(Integer page);
}