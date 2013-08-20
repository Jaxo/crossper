package com.crossper.services;

/**
 * User: dpol
 * Date: 7/3/13
 * Time: 6:13 PM
 */
public interface AsyncRequestCallback<T> {
    void success(T data);
    void error(T data);
    void complete(T data);
}
