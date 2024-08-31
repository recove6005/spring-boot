const ul = document.querySelector('ul');

// 플레이한 사용자들의 이름과 스코어 정보
const players = JSON.parse(localStorage.getItem('players'));
players.sort((x,y) => -x.score + y.score);
console.log(players);

console.log(players[2].name);

// 100명까지의 랭킹
for(let i = 0; i < 100; i++) {
    if(i === players.length) break;
    if(i === 0) {
        ul.insertAdjacentHTML('beforeend' ,
            `<li class="p-item onerank">
                    <div class="rank"> ${i+1} </div>
                    <div class="name"> ${players[i].name} </div>
                    <div class="score"> ${players[i].score} </div>
                </li>`
        )
    }
    else ul.insertAdjacentHTML('beforeend' ,
        `<li class="p-item">
                    <div class="rank"> ${i+1} </div>
                    <div class="name"> ${players[i].name} </div>
                    <div class="score"> ${players[i].score} </div>
                </li>`
        )
}

document.querySelector('.goback-btn').addEventListener('click', () => {
    window.location.href = "breakout-game-login-page.html";
});