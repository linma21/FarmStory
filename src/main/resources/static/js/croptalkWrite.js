// util.js 함수 호출
document.write('<script src="../js/util.js"></script>');
window.onload = function (){
    <!-- thymeleaf 변수를 js 파일에서 사용하기 위해 input value 가져오기 -->
    const cateData = document.getElementById("cate");
    const cate = cateData.value;
    const btnWrite = document.getElementById('btnWrite');
    const communityForm = document.getElementById('communityForm');
    // 커뮤니티 공통 ///////////////////////////////////////////////////////////////////
    const cateLi = document.querySelectorAll(".lnb li");
    const communityNav = document.getElementById('communityNav');

    // aside 현재 카테고리 표시하기 - 반복처리
    cateLi.forEach(function(item) {

        const dataCate = item.getAttribute("data-cate");

        // 현재 cate와 li의 data-cate 값을 비교하여 일치하는 경우
        if (dataCate === cate) {
            // 해당 요소에 클래스 추가
            item.classList.add("on");
        }
    });
    // croptalk 상단 Nav 표시하기
    if(cate === 'story'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate3_tit1.png" alt="농작물이야기"/>
                                                <p> HOME > 농작물이야기 > <em>농작물이야기</em></p>`;
    } else if(cate === 'grow'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate3_tit2.png" alt="귀농학교"/>
                                                <p> HOME > 농작물이야기 > <em>귀농학교</em></p>`;
    } else if(cate === 'chef'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate3_tit3.png" alt="텃밭가꾸기"/>
                                                <p> HOME > 농작물이야기 > <em>텃밭가꾸기</em></p>`;
    }

    // 글쓰기 /////////////////////////////////////////////////////////
    btnWrite.onclick = function (e){
        e.preventDefault();
        communityForm.submit();
    };
}