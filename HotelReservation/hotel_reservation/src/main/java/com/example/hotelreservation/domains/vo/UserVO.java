package com.example.hotelreservation.domains.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {
    @NotBlank
    @Length(max = 15)
    private String id;
    @NotBlank
    //@Pattern() // 정규식
    private String pw;
    private String nickname;
    @Email
    private String email;
    @NotBlank
    private String tel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @NotBlank
    @Pattern(regexp = "SELLER|USER")
    private String role;
}
