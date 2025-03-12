package com.example.hotelreservation.service;

import com.example.hotelreservation.domains.dto.RoomDTO;
import com.example.hotelreservation.mapper.ReserveMapper;
import com.example.hotelreservation.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class RoomService {
    String IMAGE_FILE_PATH = "C:\\Users\\hanib\\OneDrive\\바탕 화면";

    @Autowired
    private RoomMapper roomMapper;

    public List<RoomDTO> get_all_rooms() {
        return roomMapper.get_all_rooms(null);
    }
    public ResponseEntity<Resource> get_room_image_file(String fileName) throws IOException {
        Resource resource = new FileSystemResource(IMAGE_FILE_PATH + fileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Contenty-Type", Files.probeContentType(resource.getFile().toPath()));
        return  ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

    public RoomDTO find_room_by_roonNo(int roomNo) {
        return roomMapper.get_all_rooms(roomNo).get(0);
    }
}