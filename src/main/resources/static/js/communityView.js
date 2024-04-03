// util.js 함수 호출
document.write('<script src="../js/util.js"></script>');
window.onload = function (){
    <!-- thymeleaf 변수를 js 파일에서 사용하기 위해 input value 가져오기 -->
    const cateData = document.getElementById("cate");
    const cate = cateData.value;
    const anoData = document.getElementById("ano");
    const ano = anoData.value;
    const commentList = document.getElementById('commentList');
    const commentForm = document.getElementById('commentForm');
    const btnComment = document.getElementById('btnComment');

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
    // 댓글 불러오기 /////////////////////////////////////////////////////////////////////////
    setTimeout(async function () {
        const comments = await fetchGet(`/farmstory/comment/${ano}`);

        if(comments.length > 0){
            // 댓글이 있다면
            for(const comment of comments){
                const commentArticle = `<article>
                                <span class="nick">${comment.nick}</span>
                                <span class="date">${comment.rdate.substring(0, 10)}</span>
                                <p class="content">댓글 내용</p>
                                <div>
                                    <a href="#" data-no="${comment.cno}" data-ano="${comment.ano}" class="remove">삭제</a>
                                    <a href="#" class="modify">수정</a>
                                </div>
                            </article>`;
                // 태그 문자열 삽입
                commentList.insertAdjacentHTML('beforeend', commentArticle);
            }
        }else {
            // 댓글이 없다면
            const commentArticle = `<article><p class="content">댓글이 없습니다. 😥 <br> 첫 번째 댓글을 남겨주세요.</p></article>`;
            commentList.insertAdjacentHTML('beforeend', commentArticle);
        }
    }, 100);

    // 댓글 쓰기 /////////////////////////////////////////////////////////////////////////
    btnComment.onclick = async function (e){
        e.preventDefault();
        const uid = commentForm.uid.value;
        const content = commentForm.content.value;
        const jsonData = {
            "uid": uid,
            "ano": ano,
            "content": content
        };
        console.log(jsonData);
        // 댓글 내용이 있는 경우만 작성 요청
        if(content != null) {
            const data = await fetchPost('/farmstory/comment', jsonData);
            console.log(data);
            const noComment = document.getElementById('noComment');
            // 만약 댓글이 없는 상태였다면, 'noComment' 태그 삭제
            if (noComment) {
                noComment.remove();
            }
            // 새 댓글 목록에 추가
            const commentArticle = `<article>
                                                    <span class="nick">${data.uid}</span>
                                                    <span class="date">${data.rdate.substring(0, 10)}</span>
                                                    <p class="content">${data.content}</p>
                                                    <div>
                                                        <a href="#" data-no="${data.cno}" data-ano="${data.ano}" class="remove">삭제</a>
                                                        <a href="#" class="modify">수정</a>
                                                    </div>
                                                </article>`;
            // 태그 문자열 삽입
            commentList.insertAdjacentHTML('beforeend', commentArticle);
            // 댓글 작성 폼 비우기
            commentForm.content.value = "";
        }
    };
}