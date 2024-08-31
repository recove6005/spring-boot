// 전화번호 11자리 문자열 생성


const telNum = document.querySelector('.telInput');
const verifyCodeInput = document.querySelector('.codeInput');



// 인증번호받기 버튼을 눌렀을 때 인증번호 받아오기
function get_verify_code() {
    // 휴대폰 번호를 전달해서 GET 요청
    fetch(`/user/sms?telNum=${telNum}`)
        .then(response => response.json())
        .then(object => {
            if(object) console.log('The code is created successfully.');
            else console.log('Code Creation is failed.');
        })
        .catch();
}

function check_verify_code() {
    const key = verifyCodeInput.value;
    fetch(`/user/sms/verify?key=${key}`)
        .then(response => response.json())
        .then(object => {
            alert('Your verification is ');
            if(object) console.log("The verification is succed.");
            else console.log("The verification is failed.");
        })
        .catch();
}

function verified() {

}