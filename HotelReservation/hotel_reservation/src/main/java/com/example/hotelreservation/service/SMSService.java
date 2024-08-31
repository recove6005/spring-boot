package com.example.hotelreservation.service;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.lang.String.valueOf;


@Log4j2
@Service
@PropertySource("classpath:properties/naver-sms.properties")
public class SMSService {
    @Value("${SERVICE_ID}")
    private String SERVICE_ID;
    @Value("${API_URL}")
    private String API_URL;
    @Value("${REQUEST_URL}")
    private String REQUEST_URL;
    @Value("${ACCESS_KEY}")
    private String ACCESS_KEY;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${MESSAGE}")
    private String MESSAGE;
    @Value("${FROM_NUMBER}")
    private String FROM_NUMBER;
    private String TIME_STAMP;
    private String FQN;

    // SMS를 보내고, 결과 상태에 따라 인증번호를 반환
    public String get_verify_key(String TO_NUMBER) {
        // 인증번호 생성
        String VERIFY_KEY = create_key();
        return VERIFY_KEY;
    }


    // 요청 보낼 때 사용하는 NAVER CLOUD SERVICE의 시그니쳐 생성 코드
    public String makeSignature(String method, String url) throws Exception {
        String space = " ";					// one space
        String newLine = "\n";					// new line
        //String method = "GET";					// method
        //String url = "/photos/puppy.jpg?query1=&query2";	// url (include query string)
        String timestamp = TIME_STAMP;			// current timestamp (epoch)
        String accessKey = ACCESS_KEY;			// access key id (from portal or Sub Account)
        String secretKey = SECRET_KEY;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    // SMS(휴대폰 인증번호) 전송
    public HttpStatus send_sms(String TO_NUMBER) {
        // 인증번호가 부탁된 메세지 생성
        MESSAGE += " [" + create_key() + "]";
        // 0. 현재 타임 스탬프 값 생성
        TIME_STAMP = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());
        // 1. RestTemplate 객체 생성 : RestTemplate 클래스 사용
        log.info("CREATE REST_TEAMPLATE");
        RestTemplate restTemplate = new RestTemplate();
        // 1-1. 응답 메세지를 받기 위한   HttpComponentsClientHttpRequestFactory 객체 생성 및 RestTemplate에 삽입
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(20000); // 커넥션 타임아웃 20초
        factory.setReadTimeout(20000); // Read 타임아웃 20초
        // HttpClient httpClient = HttpClient.newBuilder().build();
        HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(50).build(); // 최대 커넥션 수 50
        factory.setHttpClient(httpClient);
        // RestTemplate에 삽입
        restTemplate.setRequestFactory(factory);

        // 2. HTTP 헤더 생성
        // 2-1. 헤더에 실어줄 signature key 생성
        REQUEST_URL = REQUEST_URL.replace("{serviceId}", SERVICE_ID);
        FQN = API_URL + REQUEST_URL;
        String SIGNATURE;
        try {
            log.info("CREATED SIGNATURE");
            SIGNATURE = makeSignature("POST", REQUEST_URL);
        } catch (Exception e) {
            log.info("SERVER ERROR");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.info("CREATE HEADER");
        TIME_STAMP = Timestamp.valueOf(LocalDateTime.now()).toString();
        FQN = API_URL + REQUEST_URL;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-ncp-apigw-timestamp", TIME_STAMP);
        httpHeaders.set("x-ncp-iam-access-key", ACCESS_KEY);
        httpHeaders.set("x-ncp-apigw-signature-v2", SIGNATURE);
        log.info("CREATED HEADER : " + httpHeaders);

        // 3. JSON 형태의 body 데이터 생성
        JSONObject body = new JSONObject();
        body.put("type", "SMS");
        body.put("contentType", "COMM");
        body.put("contentCode", "82");
        body.put("from", FROM_NUMBER); // 발송자 번호
        body.put("content", MESSAGE);

        JSONObject messageBodies = new JSONObject();
        messageBodies.put("content", MESSAGE);
        messageBodies.put("to", TO_NUMBER);

        body.put("messages", List.of(messageBodies));

        // 4. body 데이터와 HTTP의 결합
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), httpHeaders);

        // 5. URL과 METHOD 설정
        // 6. HTTP 요청
        ResponseEntity<String> response = restTemplate.postForEntity(FQN, entity, String.class);

        // POST 요청 결과 받아오기
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        return response.getStatusCode(); // 200번 반환
    }

    private String create_key() {
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(10000)); // 0 ~ 9999 까지 랜덤 int 생성
        for(int i = 0; i < 4 - randomNumber.length(); i++) {
            randomNumber = "0" + randomNumber;
        }
        return randomNumber;
    }
}
