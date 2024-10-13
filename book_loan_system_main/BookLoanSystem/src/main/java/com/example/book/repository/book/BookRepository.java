package com.example.book.repository.book;

import com.example.book.domain.vo.BookVO;
import com.example.book.mapper.book.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// 도서 저장소 등록, 수정, 삭제, 조회(단 건, 검색, 검색 결과로 나온 총 도서 수량)
@Repository
public class BookRepository {
    @Autowired
    BookMapper bookMapper;

    // 단 건별 조회
    public BookVO get_book_by_no(int no) {
        return bookMapper.get_book_by_no(no);
    }

    // 검색 결과 도서 리스트
    public List<BookVO> findAllBySearch(String field, String keyword, int page, int maxResult, String sortBy, String order) {
        List<BookVO> bookVOS = bookMapper.findAllBySearch(field, keyword, page, maxResult, page*maxResult, sortBy, order);
        return bookVOS;
    }

    // 검색 결과로 나온 총 도서 수량 (view 페이징 시 필요)
    public int findCountBySearch(String field, String keyword){
        return bookMapper.findCountBySearch(field, keyword);
    }

    // 등록
    public void save(BookVO book) {
        bookMapper.save(book);
    }

    // 수정
    public void update(int no, BookVO bookVO) {
        bookMapper.update(no, bookVO);
    }

    // 도서 정보 삭제
    public void delete(int no) {
        bookMapper.delete(no);
    }

    public BookVO findByNo(int no) {
        return bookMapper.findByNo(no);
    }
}