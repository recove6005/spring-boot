const canvas = document.querySelector('.canvas');
const ctx = canvas.getContext('2d');
const hpDivs = document.querySelectorAll('.life_wrap div');
const gamestartCommant = document.querySelector('.gamestart_commant');
const gameoverCommant = document.querySelector('.gameover_commant');
const gameclearCommant = document.querySelector('.gameclear_commant');
const scoreDiv = document.querySelector('.score');
const floorDiv = document.querySelector('.floor');

let animation; // 애니메이션 실행 함수(requestAnimationFrame)를 저장할 변수
let score = 0; // 플레이어가 획득한 점수
let floor = 1; // 게임 단계 (난이도 결정)
let life = 3; // life가 모두 소진되면 게임 종료

// canvas style 설정
canvas.style.backgroundColor = 'whitesmoke';
canvas.style.width = '800px';
canvas.style.height = '600px';
canvas.style.border = '5px solid dimgrey';

// 마우스 커서 숨기기
document.body.style.cursor = 'none';

/*** ball 오브젝트 ***/
const ball = {
    x: canvas.width / 2,
    y: canvas.height - 13,
    r: 3,
    dx: 1,
    dy: -1,
    draw_ball() {
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.r, 0, Math.PI*2);
        ctx.fillStyle = 'dimgrey';
        ctx.fill();
        ctx.closePath();
    }
};
function move_ball() {
    ball.x += ball.dx;
    ball.y += ball.dy;
}

const paddle = {
    x: canvas.width / 2 - 20,
    y: canvas.height - 10,
    w: 40,
    h: 1,
    draw_paddle() {
        ctx.beginPath();
        ctx.rect(this.x, this.y, this.w, this.h);
        ctx.fillStyle = 'dimgrey';
        ctx.fill();
        ctx.closePath();
    }
};
// paddle 움직이기 (키보드)
function move_paddle_by_keyboard() {
    if(paddleMoveFlag === true && key === 'ArrowRight') {
        paddle.x += 2;
        paddle.draw_paddle();

        // paddle이 canvas의 오른쪽 끝에 다다르면 멈춤
        if(paddle.x + paddle.w >= canvas.width) {
            paddle.x = paddle.x - 2;
        }
    }
    if(paddleMoveFlag === true && key === 'ArrowLeft') {
        paddle.x -= 2;
        paddle.draw_paddle();

        // paddle이 canvas의 왼쪽 끝에 다다르면 멈춤
        if(paddle.x <= 0) {
            paddle.x = paddle.x + 2;
        }
    }
}
// paddle 조작 이벤트 리스너 (키보드)
let paddleMoveFlag = false;
let key = 'right';
// paddle 조작 이벤트 리스너 (키보드)
document.addEventListener('keydown', e => {
    paddleMoveFlag = true;
    key = e.key;
});
document.addEventListener('keyup', () => {
    paddleMoveFlag = false;
});

/*** paddle 오브젝트 ***/
// paddle 움직이기 (마우스)
// movementX로 처리
// 마우스 왼쪽 이동 시 -, 정지 시 0, 오른쪽 이동 시 + 
// 마우스를 움직이는 속도가 빨라질수록 절댓값도 커진다!
function move_paddle_by_mouse(speed) {
    paddle.x += speed;
    if(paddle.x < 0) paddle.x = 0;
    if(paddle.x + paddle.w > canvas.width) paddle.x = canvas.width - paddle.w;
}
// paddle 조작 이벤트 리스너 (마우스)
document.addEventListener('mousemove', e => {
    move_paddle_by_mouse(e.movementX);
});

/*** brick 오브젝트 ***/
class Brick {
    constructor(x, y) {
        this.x = x;
        this.y = y;
        this.w = 25;
        this.h = 8;
    }
    draw_bricks() {
        ctx.beginPath();
        ctx.rect(this.x, this.y,this.w, this.h);
        ctx.fillStyle = 'dimgrey';
        ctx.fill();
        ctx.closePath();
    }
}
// create_brick() : brick 객체 생성 함수
// 각 벽돌 간 가로 세로 margin : 1
let bricks = []; // 각각의 brick 객체를 저장할 배열 => ball과 충돌 시 해당 벽돌 객체를 배열에서 삭제
function create_bricks() {
    let startX = canvas.width - 270, startY = canvas.height - 140;

    for(let i = 0; i < 9; i++)
        for(let j = 0; j < 5; j++) {
            let x = startX + (i*26);
            let y = startY + (j*9);
            let brick = new Brick(x, y);
            bricks.push(brick);
        }
}
// show_bricks() : 생성된 brick객체들을 실제로 canvas에 그리는 함수
function show_bricks() {
    for(let b of bricks) {
        b.draw_bricks();
    }
}


/*** 충돌 체크 ***/
// paddle collision check
function check_paddle_collision() {
    const ballYBottom = ball.y + ball.r;
    if((ball.x >= paddle.x && ball.x <= paddle.x+paddle.w) && ballYBottom === paddle.y) {
        // ballYBottom >= paddle.y : 공이 바닥에 떨어진 후에도 paddle이 공 위에 있으면 공이 다시 떠오름.
        // paddle에 ball이 닿았을 때
        return true;
    }
    else return false;
}

// brick collision check
let collisionFlag = 0;
// ball이 튕겨나갈 방향을 결정하기 위한 변수 collisionFlag
// 0 : 충돌 없음, 1 : dy 부호 반전, 2 : dx 부호 반전
function check_collision() {
    // bricks 배열을 돌면서 모든 brick들의 충돌을 체크
    for(let i = 0; i < bricks.length; i++) {
        let tb = bricks[i]; // targetBrick

        // // 벽돌 위쪽 충돌 시 1
        // if(targetBrick.x < ball.x && (targetBrick.x+targetBrick.width) > ball.x)
        //     if(ball.y-ball.rad === targetBrick.y+targetBrick.height) {
        //         brickCollisionFlag = 1;
        //         // 충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //
        // // 벽돌 아래쪽 충돌 시 1
        // if(targetBrick.x < ball.x && (targetBrick.x+targetBrick.width) > ball.x)
        //     if(ball.y+ball.rad === targetBrick.y) {
        //         brickCollisionFlag = 1;
        //         // 충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //
        // // 벽돌 오른쪽 충돌 시 2
        // if(targetBrick.y < ball.y && targetBrick.y + targetBrick.height > ball.y)
        //     if(ball.x + ball.rad === targetBrick.x) {
        //         brickCollisionFlag = 2;
        //         // 충돌된 벽돌 삭제
        //         console.log(targetBrick, i);
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //
        // // 벽돌 왼쪽 충돌 시 2
        // if(targetBrick.y < ball.y && targetBrick.y + targetBrick.height > ball.y)
        //     if(ball.x + ball.rad === targetBrick.x + targetBrick.width) {
        //         brickCollisionFlag = 2;
        //         // 충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //
        // // 벽돌 오른쪽 위 모서리 충돌 시 3
        // if((targetBrick.x + targetBrick.width) >= (ball.x-ball.rad) && targetBrick.y <= (ball.y + ball.rad)) {
        //     brickCollisionFlag = 3;
        //     // 충돌된 벽돌 삭제
        //     bricks.splice(i, 1);
        //     // 점수 득점!
        //     score++;
        //     scoreDiv.innerText = score;
        //     return;
        // }
        //
        // // 벽돌 오른쪽 아래 모서리 충돌 시 3
        // if((targetBrick.x + targetBrick.width) >= (ball.x-ball.rad) && (targetBrick.y + targetBrick.height) >= (ball.y-ball.rad)) {
        //     brickCollisionFlag = 3;
        //     // 충돌된 벽돌 삭제
        //     bricks.splice(i, 1);
        //     // 점수 득점!
        //     score++;
        //     scoreDiv.innerText = score;
        //     return;
        // }
        //
        // // 벽돌 왼쪽 위 모서리 충돌 시 3
        // if(targetBrick.x <= (ball.x+ball.rad) && targetBrick.y <= (ball.y - ball.rad)) {
        //     brickCollisionFlag = 3;
        //     // 충돌된 벽돌 삭제
        //     bricks.splice(i, 1);
        //     // 점수 득점!
        //     score++;
        //     scoreDiv.innerText = score;
        //     return;
        // }
        //
        // // 벽돌 왼쪽 아래 모서리 충돌 시 3
        // if(targetBrick.x <= (ball.x+ball.rad) && (targetBrick.y + targetBrick.height) >= (ball.y+ball.rad)) {
        //     brickCollisionFlag = 3;
        //     // 충돌된 벽돌 삭제
        //     bricks.splice(i, 1);
        //     // 점수 득점!
        //     score++;
        //     scoreDiv.innerText = score;
        //     return;
        // }

        // ball의 너비를 사각형으로 두고 계산
        // if(targetBrick.x <= ball.x-ball.r && targetBrick.x+targetBrick.w >= ball.x+ball.r)
        //     if(targetBrick.y === ball.y+ball.r) {
        //         // brick 위쪽 변 충돌 시 1
        //         console.log('위쪽 충돌!');
        //
        //         collisionFlag = 1;
        //         //충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //     else if(targetBrick.y+targetBrick.h === ball.y-ball.r) {
        //         // brick 아래쪽 변 충돌 시 1
        //         console.log('아래쪽 충돌!');
        //
        //         collisionFlag = 1;
        //         //충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //
        //
        // if(targetBrick.y <= ball.y-ball.r && targetBrick.y+targetBrick.h >= ball.y+ball.r)
        //     if(targetBrick.x + targetBrick.w === ball.x-ball.r) {
        //         // brick 오른쪽 변 충돌 시 2
        //         console.log('오른쪽 충돌!');
        //
        //         collisionFlag = 2;
        //         //충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }
        //     else if(targetBrick.x === ball.x+ball.r) {
        //         // brick 왼쪽 변 충돌 시 2
        //         console.log('왼쪽 충돌!');
        //
        //         collisionFlag = 2;
        //         //충돌된 벽돌 삭제
        //         bricks.splice(i, 1);
        //         // 점수 득점!
        //         score++;
        //         scoreDiv.innerText = score;
        //         return;
        //     }

        // 1) brick과의 충돌 여부를 먼저 판단한 후 -> brick 너비 내에 ball이 있을 시 충돌로 판정
        // 2) 위쪽, 아래쪽, 오른쪽, 왼쪽 충돌인지 판단
        // 위 아래 충돌 판정
        if(tb.x <= ball.x && tb.x+tb.w >= ball.x) {
            // if (tb.y <= ball.y-ball.r && tb.y + tb.h >= ball.y-ball.r) {
            //     // 벽돌 아래쪽 충돌
            //     console.log('아래쪽 충돌!');
            //     collisionFlag = 1;
            //     //충돌된 벽돌 삭제
            //     bricks.splice(i, 1);
            //     // 점수 득점!
            //     score++;
            //     scoreDiv.innerText = score;
            //     return;
            // }
            // if (tb.y <= ball.y+ball.r && tb.y+tb.h >= ball.y+ball.r) {
            //     // 벽돌 위쪽 충돌
            //     console.log('위쪽 충돌!');
            //     collisionFlag = 1;
            //     //충돌된 벽돌 삭제
            //     bricks.splice(i, 1);
            //     // 점수 득점!
            //     score++;
            //     scoreDiv.innerText = score;
            //     return;
            // }
            if((tb.y <= ball.y-ball.r && tb.y + tb.h >= ball.y-ball.r) || (tb.y <= ball.y+ball.r && tb.y+tb.h >= ball.y+ball.r)) {
                // 벽돌 아래쪽 충돌 || 벽돌 위쪽 충돌
                collisionFlag = 1;
                //충돌된 벽돌 삭제
                bricks.splice(i, 1);
                // 점수 득점!
                score++;
                scoreDiv.innerText = score;
                return;
            }
        }
        // 오른쪽 왼쪽 충돌 판정
        if(tb.y < ball.y && tb.y+tb.h > ball.y) {
            // if(ball.x-ball.r > tb.x && ball.x-ball.r < tb.x+tb.w) {
            //     // 벽돌 오른쪽 충돌
            //     collisionFlag = 2;
            //     //충돌된 벽돌 삭제
            //     bricks.splice(i, 1);
            //     // 점수 득점!
            //     score++;
            //     scoreDiv.innerText = score;
            //     return;
            // }
            // if(ball.x+ball.r > tb.x && ball.x+ball.r < tb.x+tb.w) {
            //     // 벽돌 왼쪽 충돌
            //     collisionFlag = 2;
            //     //충돌된 벽돌 삭제
            //     bricks.splice(i, 1);
            //     // 점수 득점!
            //     score++;
            //     scoreDiv.innerText = score;
            //     return;
            // }
            if((ball.x-ball.r > tb.x && ball.x-ball.r < tb.x+tb.w) || (ball.x+ball.r > tb.x && ball.x+ball.r < tb.x+tb.w)) {
                // 벽돌 오른쪽 충돌 || 벽돌 왼쪽 충돌
                collisionFlag = 2;
                //충돌된 벽돌 삭제
                bricks.splice(i, 1);
                // 점수 득점!
                score++;
                scoreDiv.innerText = score;
                return;
            }
        }

        // 벽 위쪽 충돌 시 1
        if(ball.y-ball.r <= 0) {
            collisionFlag = 1;
            return;
        }
        // 벽 오른쪽 왼쪽 충돌 시 2
        if(ball.x+ball.r >= canvas.width || ball.x - ball.r <= 0) {
            collisionFlag = 2;
            return;
        }
    }
    // 충돌이 없으면 0
    collisionFlag = 0;
}


/*** 애니메이션 및 게임 동작 ***/
function check_gameover() {
    // ball이 바닥으로 떨어지면 true 아니면 false
    if(ball.y > canvas.height + 50) return true;
    else return false;
}

// set_floor : 단계별로 달라지는 ball.dy, ball.dx, paddle.width를 결정하는 함수
function set_floor(dx, dy, pw) {
    floorDiv.innerText = floor;

    ball.dx = dx;
    ball.dy = dy;
    paddle.width = pw;

    // 게임 시작 시 ball과 paddle을 x축 중앙에 두기
    paddle.x = canvas.width/2 - (paddle.width /2);
    ball.x = canvas.width/2;
    ball.y = canvas.height - 13;

    // brick 생성
    create_bricks();
    ball.draw_ball();
    paddle.draw_paddle();
}
function start_game() {
    // 게임 시작 이벤트 리스너 삭제(키보드)
    document.removeEventListener('keypress', start_game);
    // 게임 시작 이벤트 리스너 삭제(마우스)
    document.removeEventListener('click', start_game);

    if(floor === 1) {
        gamestartCommant.classList.toggle('hidden');
        set_floor(1,-1,40);
        start_animation();
    }
    else {
        gameclearCommant.classList.toggle('hidden');
        // ball 스피드 설정
        let speed = Math.floor(1.2*floor);
        set_floor(speed, -Math.abs(speed),40-(floor*3));
        start_animation();
    }

    // floor 변수에 따라 단계별로 애니메이션 실행
    // switch(floor) {
    //     case 1:
    //         gamestartCommant.classList.toggle('hidden');
    //         set_floor(1,-1,40);
    //         start_animation();
    //         break;
    //     case 2:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(1,-1,37);
    //         start_animation();
    //         break;
    //     case 3:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(1,-1,34);
    //         start_animation();
    //         break;
    //     case 4:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(2,-2,31);
    //         start_animation();
    //         break;
    //     case 5:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(2,-2,28);
    //         start_animation();
    //         break;
    //     case 6:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(2,-2,25);
    //         start_animation();
    //         break;
    //     case 7:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(3,-3,22);
    //         start_animation();
    //         break;
    //     case 8:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(3,-3,19);
    //         start_animation();
    //         break;
    //     case 9:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(3,-3,16);
    //         start_animation();
    //         break;
    //     case 10:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(4,-4,13);
    //         start_animation();
    //         break;
    //     case 11:
    //         gameclearCommant.classList.toggle('hidden');
    //         set_floor(4,-4,11);
    //         start_animation();
    //         break;
    // }
}

// 애니메이션
function start_animation() {
    // 애니메이션 저장 및 canvas 초기화
    animation = requestAnimationFrame(start_animation);
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // brick, paddle, ball 그리기
    show_bricks();
    paddle.draw_paddle();
    ball.draw_ball();

    // paddle(키보드), ball 움직이기
    move_paddle_by_keyboard();
    move_ball();
    // paddle 충돌 (아래쪽 충돌), ball이 오브젝트와 충돌 시 튀어오르는 동작
    if (check_paddle_collision()) ball.dy = -Math.abs(ball.dy);

    // brick 충돌
    check_collision()
    if (collisionFlag === 1) {     // 위쪽, 아래쪽 충돌
        if (ball.dy > 0) ball.dy = -Math.abs(ball.dy);
        else ball.dy = Math.abs(ball.dy);
    }
    if (collisionFlag === 2) {    // 왼쪽, 오른쪽 충돌
        if (ball.dx > 0) ball.dx = -Math.abs(ball.dx);
        else ball.dx = Math.abs(ball.dx);
    }
    // // 공 모서리 충돌
    // // dx dy 부호 반전
    // if (collisionFlag === 3) {
    //     if (ball.dy > 0) ball.dy = -Math.abs(ball.dy);
    //     else ball.dy = Math.abs(ball.dy);
    //     if (ball.dx > 0) ball.dx = -Math.abs(ball.dx);
    //     else ball.dx = Math.abs(ball.dx);
    // }

    // game clear
    if (bricks.length === 0) {
        // game clear 코멘트 삽입
        gameclearCommant.classList.toggle('hidden');

        // canvas clear
        cancelAnimationFrame(animation);
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // 로컬 스토리지에 현재 플레이어의 이름과 마지막 스코어 저장
        save_player_info();

        // 다음 단계로
        floor++;
        document.addEventListener('keypress', start_game);
        document.addEventListener('click', start_game);
    }

    // game over
    if (check_gameover()) {
        if (life === 1) {
            // 마지막 hpDiv 삭제
            life--;
            hpDivs.item(life).classList.toggle('hidden');

            // game over 코멘트 삽입
            gameoverCommant.classList.toggle('hidden');

            // canvas clear
            cancelAnimationFrame(animation);
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            // 로컬 스토리지에 현재 플레이어의 이름과 마지막 스코어 저장
            save_player_info();

            // 아무 키를 누르거나 마우스 클릭 시 랭킹 스코어 창으로 이동
            document.addEventListener('keypress', () => {
                window.location.href = './breakout-game-rank-window.html';
            });
            document.addEventListener('click', () => {
                window.location.href = './breakout-game-rank-window.html';
            });
        } else {
            // 공이 바닥에 떨어질 시 paddle, ball 위치 초기화
            ball.x = canvas.width / 2;
            ball.y = canvas.height - 13;
            // ball의 방향을 결정하는 dx, dy 부호 초기화
            if(ball.dy >= 0) {
                ball.dy = -Math.abs(ball.dy);
            }
            ball.draw_ball();

            paddle.x = canvas.width / 2 - 20;
            paddle.y = canvas.height - 10;
            paddle.draw_paddle();

            // life 차감
            life--;
            hpDivs.item(life).classList.toggle('hidden');
        }
    }
}

function save_player_info() {
    // 로컬 스토리지에 현재 플레이어의 이름과 마지막 스코어 저장
    let players = JSON.parse(localStorage.getItem('players'));
    players[players.length - 1].score = score;
    localStorage.setItem('players', JSON.stringify(players));
}

/*** 아무 키를 누르거나 마우스를 클릭하면 게임이 시작됨 ***/
document.addEventListener('keypress', start_game);
document.addEventListener('click', start_game);
