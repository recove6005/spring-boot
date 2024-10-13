package com.example.book.service.admin;

import com.example.book.controller.admin.domain.AdminLoginForm;
import com.example.book.controller.admin.domain.BookRegForm;
import com.example.book.controller.admin.domain.BookUpdateForm;
import com.example.book.domain.vo.BookVO;
import com.example.book.repository.admin.AdminRepository;
import com.example.book.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final BookRepository bookRepository;

    public String login(AdminLoginForm form) {
        return adminRepository.login(form.getId(), form.getPw());
    }

    public void regBook(BookVO book){
        // 책 번호(no)는 시퀀스, 상태(state)는 대출가능으로 자동저장
        bookRepository.save(book);
    }
    public void updateBook(int no, BookVO book){
        bookRepository.update(no,book);
    }

    public void deleteBook(int no){
        bookRepository.delete(no);
    }


}
