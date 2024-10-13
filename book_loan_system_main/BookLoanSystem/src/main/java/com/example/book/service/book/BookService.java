package com.example.book.service.book;

import com.example.book.controller.book.domain.ReservationRegForm;
import com.example.book.controller.book.domain.LoanRegForm;
import com.example.book.domain.vo.BookVO;
import com.example.book.domain.vo.LoanVO;
import com.example.book.domain.vo.ReservationVO;
import com.example.book.repository.book.BookRepository;
import com.example.book.repository.book.LoanRepository;
import com.example.book.repository.book.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;

    // 도서 검색
    public List<BookVO> listSearch(String field, String keyword,int page,int maxResult,String sortBy, String order) {
        return bookRepository.findAllBySearch(field,keyword,page,maxResult,sortBy,order);
    }

    // 검색결과 도서 수량
    public int listSearch(String field, String keyword) {
        return bookRepository.findCountBySearch(field,keyword);
    }

    // 예약
    public void reservation(ReservationRegForm form){
        ReservationVO reservation = new ReservationVO();
        reservation.setBookNo(form.getBookNo());
        reservation.setUserId(form.getUserId());
        reservation.setDate(LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    // 예약 취소
    public void reservationCancel(int reservationNo){
        reservationRepository.remove(reservationNo);
    }

    // 대출
    public void loan(LoanRegForm form, long period){
        LoanVO loan = new LoanVO();
        loan.setUserId(form.getUserId());
        loan.setBookNo(form.getBookNo());
        loan.setLoanDate(LocalDateTime.now());
        loan.setReturnDate(loan.getLoanDate().plusDays(period));
        loanRepository.save(loan);
    }

    // 대출 반납
    public void loanReturn(int loanNo){
        loanRepository.remove(loanNo);
    }

    // 도서 상세보기
    public BookVO info(int no){
        return bookRepository.findByNo(no);
    }
}
