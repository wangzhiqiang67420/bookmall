package org.zdd.bookmall.model.service;

import com.github.pagehelper.PageInfo;
import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.common.pojo.Bar;
import org.zdd.bookmall.common.pojo.Pie;
import org.zdd.bookmall.exception.BSException;
import org.zdd.bookmall.model.entity.BookInfo;

import java.util.List;

public interface IBookInfoService {

    List<BookInfo> findBookListByCateId(int cateId, int currentPage, int pageSize);

    BookInfo findById(Long bookId) throws BSException;

    PageInfo<BookInfo> findBookListByCondition(String keywords, int cateId, int page, int pageSize,int storeId);

    BookInfo queryBookAvailable(Long bookId);

    BSResult saveBook(BookInfo bookInfo, String bookDescStr);

    BSResult updateBook(BookInfo bookInfo, String bookDesc);

    BSResult changeShelfStatus(Long bookId,int shelf);

    BookInfo adminFindById(Long bookId) throws BSException;

    BSResult deleteBook(Long bookId);

    int addLookMount(BookInfo bookInfo);

    List<Pie> getBookViewsPiesByStoreId(Integer storeId);

    Bar getBookSalesBarJson(Integer storeId);
}
