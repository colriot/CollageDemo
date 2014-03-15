package com.github.collagedemo.operators;

import com.github.collagedemo.model.IGResponse;
import rx.Observable;
import rx.functions.Func1;

/**
* @author Sergey Ryabov
*         Date: 13.03.14
*/
public class IGResponseToInnerData<T> implements Func1<IGResponse<T>, Observable<T>> {
    @Override
    public Observable<T> call(IGResponse<T> response) {
        return Observable.from(response.getData());
    }
}
