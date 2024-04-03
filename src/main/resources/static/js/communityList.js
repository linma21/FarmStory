// util.js 함수 호출
window.onload = function (){
    // 커뮤니티 공통 ///////////////////////////////////////////////////////////////////
    <!-- thymeleaf 변수를 js 파일에서 사용하기 위해 input value 가져오기 -->
    const cateData = document.getElementById("cate");
    const cate = cateData.value;
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
    // community 상단 Nav 표시하기
    if(cate === 'notice'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate5_tit1.png" alt="자주묻는질문"/>
                                                <p> HOME > 커뮤니티 > <em>공지사항</em></p>`;
    } else if(cate === 'menu'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate5_tit2.png" alt="자주묻는질문"/>
                                                <p> HOME > 커뮤니티 > <em>오늘의식단</em></p>`;
    } else if(cate === 'chef'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate5_tit3.png" alt="자주묻는질문"/>
                                                <p> HOME > 커뮤니티 > <em>나도요리사</em></p>`;
    } else if(cate === 'qna'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate5_tit4.png" alt="자주묻는질문"/>
                                                <p> HOME > 커뮤니티 > <em>1:1고객문의</em></p>`;
    } else if(cate === 'faq'){
        communityNav.innerHTML = `<img src="../images/sub_nav_tit_cate5_tit5.png" alt="자주묻는질문"/>
                                                <p> HOME > 커뮤니티 > <em>자주묻는질문</em></p>`;
    }

}