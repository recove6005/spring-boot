package com.example.book.repository.book;

import com.example.book.domain.vo.LoanVO;
import com.example.book.mapper.book.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// 대출 저장소
// 등록, 수정, 삭제, 조회(단건, 회원별 리스트, 도서별 리스트)
@Repository
public class LoanRepository {
    @Autowired
    LoanMapper loanMapper;
    // 단 건별 조회
    public LoanVO get_loan_by_no(int loanNo) {
        return loanMapper.get_loan_by_no(loanNo);
    }

    // 회원별 조회
    public List<LoanVO> get_loan_by_user(String userId) {
        return loanMapper.get_loan_by_user(userId);
    }

    // 도서별 조회
    public List<LoanVO> get_loan_by_book(int bookNo) {
        return loanMapper.get_loan_by_book(bookNo);
    }

    // 등록
    public void save(LoanVO loan) {
        loanMapper.save(loan);
    }

    // 수정
    public void update(int no, LoanVO loanVO) {
        loanMapper.update(no, loanVO);
    }

    // 삭제
    public void remove(int loanNo) {
        loanMapper.remove(loanNo);
    }
}
