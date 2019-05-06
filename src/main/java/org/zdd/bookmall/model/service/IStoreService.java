package org.zdd.bookmall.model.service;

import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.Store;

import java.util.List;

public interface IStoreService {
    Store findStoreByUserId(Integer userId);

    Store findById(int storeId);

    List<Store> findStores();

    BSResult updateStore(Store store);

    BSResult deleteStore(int storeId);

    BSResult addStore(Store store);
}
