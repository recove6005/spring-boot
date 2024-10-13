package com.example.book.mapper.book;

import com.example.book.domain.vo.LoanVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoanMapper {
    LoanVO get_loan_by_no(int no);
    List<LoanVO> get_loan_by_user(String userId);
    List<LoanVO> get_loan_by_book(int bookNo);

    void save(LoanVO loanVO);
    void update(int no, LoanVO loanVO);
    void remove(int no);
}