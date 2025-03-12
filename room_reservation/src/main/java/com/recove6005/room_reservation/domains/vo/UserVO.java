package com.recove6005.room_reservation.domains.vo;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

// Lombok 어노테이션
@Data // getter, setter, toString, equals, hashCode 메서드 자동 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자를 자동 생성
@NoArgsConstructor // 기본 생성자(파라미터 없는 생성자)를 자동 생성
@ToString // 객체를 문자열로 변환할 때 모든 필드의 값을 포함한 문자열을 자동 생성
public class UserVO {
    @NotBlank
    @Length(max = 15)
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 최소 8자, 숫자와 문자를 포함해야 합니다.")
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
