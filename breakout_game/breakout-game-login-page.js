const nameInput = document.querySelector('.wrap label input');
const loginBtn = document.querySelector('.wrap label a');
loginBtn.addEventListener('click', move_to_game);

function check_name() {
    return /[^/\s]/g.test(nameInput.value);
}

let player = {
    name: '',
    score: 0
}
// 기존의 사용자 정보를 모두 가져옴
let players = JSON.parse(localStorage.getItem('players'));
function move_to_game() {
    if(check_name()) {
        // 사용자 닉네임 저장
        player.name = nameInput.value;
        players.push(player);
        localStorage.setItem('players', JSON.stringify(players));

        // input value 초기화
        nameInput.value = "";

        // 페이지 이동
        window.location.href="breakout-game.html";
    } else {
        alert('닉네임을 입력해 주세요.');
    }
}