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

    // 글쓰기 /////////////////////////////////////////////////////////
    btnWrite.onclick = function (e){
        e.preventDefault();
        communityForm.submit();
    };
}