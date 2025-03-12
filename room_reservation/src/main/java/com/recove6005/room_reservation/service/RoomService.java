package com.recove6005.room_reservation.service;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.recove6005.room_reservation.domains.dto.RoomDTO;
import com.recove6005.room_reservation.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class RoomService {
    String IMAGE_FILE_PATH = "C:"; // 파일 시스템에서 파일이 저장된 경로

    @Autowired
    private RoomMapper roomMapper;

    // 모든 room 데이터 가져오기
    public List<RoomDTO> getAllRooms() {
        return roomMapper.getAllRooms(null);
    }

    public ResponseEntity<Resource> getRoomImageFile(String fileName) throws IOException {
        Resource resource = new FileSystemResource(IMAGE_FILE_PATH + fileName); // 지정된 경로의 파일을 Resource 객체로 변환
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath())); // MIME타입(Content-Type)을 자동으로 감지
        return ResponseEntity.ok().headers(headers).body(resource);
        // ResponseEntity.ok() -> HTTP 200 OK 응답 생성
        // headers(headers) -> HTTP 응답 헤더 추가 (파일의 Content-Type 지정)
        // .body(resource) -> 파일 데이터를 응답 본문에 포함
    }

    // roomNo로 특정 room 데이터 가져오기
    public RoomDTO findRoomByRoomNo(int roomNo) {
        return roomMapper.getAllRooms(roomNo).get(0);
    }
}
