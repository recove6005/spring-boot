package com.example.book.mapper.book;

import com.example.book.domain.vo.BookVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    BookVO get_book_by_no(int no);

    void save(BookVO bookVO);
    List<BookVO> findAllBySearch(
            String field,
            String keyword,
            int page,
            int maxResult,
            int offset,
            String sortBy,
            String order
    );
    int findCountBySearch(String field, String keyword);
    void update(int no, BookVO book);
    void delete(int no);

    BookVO findByNo(int no);
}