const roomsSection = document.querySelector('.rooms');

// 서버의 모든 방을 요청
function get_all_rooms() {
    fetch('/rooms')
        .then(response => response.json())
        .then(object => {create_rooms(object)})
        .catch();
}

// 서버에서 받은 모든 방의 데이터를 화면에 출력
function create_rooms(roomList) {
    roomsSection.innerHTML = '';
    for(const room of roomList) {
        const roomNo = room.roomVO.no;
        const title = room.roomVO.title;
        const price = room.roomVO.price;
        const image = room.roomImagesVO[0].roomImage;
        const ratings = room.ratingVO;
        let rate = 0;
        ratings.forEach(v=> {rate += v.score});

        roomsSection.insertAdjacentHTML('beforeend', `
        <div class="room_item_container">
            <div class="room_item_photo">
                <img src="/rooms/image/${image}" alt="room_photo">
            </div>
            <div class="room_item_header">
                <input type="hidden" name="roomNo" value="${roomNo}">
                <div class="room_item_title">${title}</div>
                <a class="room_item_heart" href=""><img src="../../static/image/layout/header/icons8-heart-50.png" alt="" th:src="@{/image/layout/header/icons8-heart-50.png}"></a>
                <a class="room_item_rating" href=""><img src="../../static/image/layout/header/icons8-star-48.png" alt="" th:src="@{/image/layout/header/icons8-star-48.png}"><span>${rate}</span></a>
            </div>
            <div class="room_item_price">
                <span>&#8361;</span>
                <span>${price}</span>
                <span>/박</span>
            </div>
        </div>
        `)
    }
}