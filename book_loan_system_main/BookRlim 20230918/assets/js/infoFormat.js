// 전화 번호 형식 변경
function format_tel(){
  const tel = document.querySelector(".tel");
  const reg_tel = /^(\d{2,3})(\d{3,4})(\d{4})$/;

  tel.innerHTML = tel.innerHTML.replace(reg_tel, `$1-$2-$3`);

}

// 주소 형식 변경
function format_addr(){
    const addr = document.querySelector(".addr");
    let [zipCode, add1, add2] = addr.innerHTML.split(",");

    if(add1 === undefined){
        addr.innerHTML = "주소를 설정 해 주세요!"
    }
    else{
        addr.innerHTML = add1 + ", " + add2 + " ("  + zipCode + ")";
    }
}

format_tel();
format_addr();




