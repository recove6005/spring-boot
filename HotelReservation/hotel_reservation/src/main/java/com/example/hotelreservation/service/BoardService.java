package com.example.hotelreservation.service;

import com.example.hotelreservation.domains.security.SecurityUser;
import com.example.hotelreservation.domains.vo.BoardVO;
import com.example.hotelreservation.domains.vo.RoomImagesVO;
import com.example.hotelreservation.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    String SAVE_PATH = "C:\\Users\\hanib\\OneDrive\\바탕 화면";

    @Autowired
    private BoardMapper boardMapper;

    public boolean post_board(SecurityUser user, BoardVO boardVO) {
        // 게시물 작성
        List<MultipartFile> files = boardVO.getImages();
        List<RoomImagesVO> roomImagesVOS;
        if(!check_files(files)) return false; // 파일 체크
        // 파일을 로컬에 저장, 이미지 리스트 받아오기
        try {
            roomImagesVOS = save_files(files); // 파일 저장
        } catch (IOException e) {
            return false;
        }
        // 게시자 설정
        boardVO.setUserId(user.getUsername());
        // DB에 방 저장
        boardMapper.post_board(boardVO);
        // 방금 insert했던 방의 이미지들을 insert
        boardMapper.post_images(roomImagesVOS);

        return true;
    }

    // 파일 형식 체크
    private boolean check_files(List<MultipartFile> files) {
        for(MultipartFile file: files) {
            String contentType = file.getContentType();
//            String mimeType = contentType.split("/")[0];
//            if(!mimeType.equals("image")) return null;
            if(contentType.startsWith("image/")) return false;
        }
        return true;
    }

    // 파일을 저장한 후 이름을 저장흐는 LIST를 받아옴
    private List<RoomImagesVO> save_files(List<MultipartFile> files) throws IOException {
        // 이미지들 객체 LIST
        List<RoomImagesVO> imagesVOS = new ArrayList<>();

        // 모든 파일을 순회하면서 이미지명을 설정하고 저장, DB 저장할 객체 생성
        for(MultipartFile file: files) {
            // 파일에서 원본 이미지 파일명을 토대로 새 이름을 설정
            String originFileName = file.getOriginalFilename();
            String saveFileName = UUID.randomUUID()+"_"+originFileName;
            File saveFile = new File(SAVE_PATH, saveFileName);
            // 로컬에 파일 저장
            file.transferTo(saveFile);
            // DB에 저장할 파일 객체 생성 후 이미지명 설정
            RoomImagesVO imagesVO = new RoomImagesVO();
            imagesVO.setRoomImage(saveFileName);
            // 리스트에 넣음
            imagesVOS.add(imagesVO);
        }
        // 이미지들 객체 LIST 반환
        return imagesVOS;
    }
}