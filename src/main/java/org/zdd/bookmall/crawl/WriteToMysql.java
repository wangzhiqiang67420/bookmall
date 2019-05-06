package org.zdd.bookmall.crawl;

import org.zdd.bookmall.model.entity.BookInfo;
import org.zdd.bookmall.model.dao.BookInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class WriteToMysql {

    @Autowired
    private BookInfoMapper bookInfoMapper;

    public void executeInsert(List<BookInfo> bookdatas) throws SQLException
    {
        long start = System.currentTimeMillis() / 1000;
        System.out.println(start);
        for (BookInfo bookdata : bookdatas) {

        }
        for (BookInfo bookdata : bookdatas) {
            bookInfoMapper.insert(bookdata);
        }

        System.out.println("成功插入" + bookdatas.size() + "条");
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(System.currentTimeMillis() / 1000 - start);
    }

}
