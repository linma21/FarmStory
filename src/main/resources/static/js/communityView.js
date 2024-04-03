// util.js 함수 호출
document.write('<script src="../js/util.js"></script>');
window.onload = function (){
    <!-- thymeleaf 변수를 js 파일에서 사용하기 위해 input value 가져오기 -->
    const cateData = document.getElementById("cate");
    const cate = cateData.value;
    const anoData = document.getElementById("ano");
    const ano = anoData.value;
    const userData = document.getElementById("user");
    const user = userData.value;

    const commentList = document.getElementById('commentList');
    const commentForm = document.getElementById('commentForm');
    const commentRemove = document.getElementById('commentRemove');
    const commentModify = document.getElementById('commentModify');
    const contentTextarea = document.getElementById('contentTextarea');
    const btnComment = document.getElementById('btnComment');
    const btnArtRemove = document.getElementById('btnArtRemove');
    const btnArtModify = document.getElementById('btnArtModify');
    const fileLinks = document.getElementsByClassName('fileLink');

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
    // 페이지 로드시 textarea 자동 높이 조절
    const textareas = document.querySelectorAll('textarea');

    // 선택된 각 textarea 요소에 대해 autoResize 함수를 호출합니다.
    textareas.forEach(textarea => {
        autoResize(textarea);
    });
    // 파일 다운로드 - 다운로드 카운트 칼럼 생기면
    /*
    for (const fileLink of fileLinks) {
        // 파일 다운로드 요청과 동시에 다운로드 카운트 요청
        fileLink.onclick = async function () {
            const fno = this.dataset.fno;

            const count = this.nextElementSibling.innerText;
            this.nextElementSibling.innerHTML = parseInt(count) + 1;
        }
    }
    
     */
    // 댓글 불러오기 /////////////////////////////////////////////////////////////////////////
    setTimeout(async function () {
        const comments = await fetchGet(`/farmstory/comment/${ano}`);

        if(comments.length > 0){
            // 댓글이 있다면
            for(const comment of comments){
                // 댓글  HTML 생성
                const lineCount = comment.content.split('\n').length;
                let commentArticle = "";
                const commentTop = `<div class="card mt-3 comment " id="comment">
                                            <div class="card-body">
                                                <div class="d-flex flex-start">
                                                    <div class="w-100">
                                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                                            <h6 class="text-primary fw-bold mb-0">
                                                                ${comment.nick}
                                                                <span class="text-dark ms-2"></span>
                                                            </h6>
                                                            <div class="ml-auto">
                                                                <p class="mb-0">${comment.rdate.substring(0, 10)}</p>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                                            <textarea onInput="autoResize(this)" rows="${lineCount}" class="form-control commentText" rows="3" readonly>${comment.content}</textarea>
                                                        </div>
                                                        <div class="float-end">
                                                        </div>`;
                const commentBtns = `<div class="d-flex justify-content-between align-items-center">
                                                <p class="small mb-0" style="color: #aaa;">
                                                    <a href="#" class="link-grey btnModify" data-mode="modify" id="commentModify" data-cno="${comment.cno}"> 수정</a> •
                                                    <a href="#" class="link-grey btnRemove" data-mode="remove" id="commentRemove" data-cno="${comment.cno}"> 삭제</a>
                                                </p>
                                            </div>`;
                const commentEnd = `</div>
                                        </div>
                                    </div>
                                </div>`;

                if (user === comment.uid) {
                    // 현재 접속 사용자가 댓글 작성자일 경우
                    commentArticle = commentTop + commentBtns + commentEnd;
                    // 태그 문자열 삽입
                    commentList.insertAdjacentHTML('beforeend', commentArticle);
                }else {
                    // 사용자와 댓글 작성자가 다를 경우
                    commentArticle = commentTop + commentEnd;
                    // 태그 문자열 삽입
                    commentList.insertAdjacentHTML('beforeend', commentArticle);
                }
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
            const lineCount = data.content.split('\n').length;
            // 새 댓글 목록에 추가
            const commentArticle = `<div class="card mt-3 comment " id="comment">
                                            <div class="card-body">
                                                <div class="d-flex flex-start">
                                                    <div class="w-100">
                                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                                            <h6 class="text-primary fw-bold mb-0">
                                                                ${data.nick}
                                                                <span class="text-dark ms-2"></span>
                                                            </h6>
                                                            <div class="ml-auto">
                                                                <p class="mb-0">${data.rdate.substring(0, 10)}</p>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                                             <textarea onInput="autoResize(this)" rows="${lineCount}" class="form-control commentText" rows="3" readonly>${data.content}</textarea>
                                                        </div>
                                                        <div class="float-end">
                                                         </div>
                                                        <!-- 댓글 작성자와 현재 사용자가 동일한 경우에만 삭제 및 수정 버튼을 표시 -->
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <p class="small mb-0" style="color: #aaa;">
                                                                     <a href="#" class="link-grey btnModify" data-mode="modify" data-cno="${data.cno}"><i class="bi bi-pencil-fill"></i> 수정</a> •
                                                                     <a href="#" class="link-grey btnRemove" data-mode="remove" data-cno="${data.cno}"><i class="bi bi-trash3-fill"></i> 삭제</a>
                                                             </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>`;
            // 태그 문자열 삽입
            commentList.insertAdjacentHTML('beforeend', commentArticle);
            // 댓글 작성 폼 비우기
            commentForm.content.value = "";
        }
    };
/*
    // 댓글 수정 삭제 /////////////////////////////////////////////////////////////////////////
    document.addEventListener('click', async function (e) {

        // a태그 이고, data-mode 가 있는 경우 => 다른 페이지 이동, 댓글 등록 등은 제외 해야함
        if (e.target.tagName === 'A' && e.target.dataset.mode != null) {
            e.preventDefault();
            const comment = e.target.closest('.comment');
            const btnModify = comment.getElementsByClassName('btnModify')[0];
            const btnRemove = comment.getElementsByClassName('btnRemove')[0];
            const cno = e.target.dataset.cno;
            const textarea = comment.getElementsByTagName('textarea')[0];
            // 댓글 삭제 /////////////////////////////////////////////////////////////////////////
            if (e.target.dataset.mode == 'remove') {

                console.log("cno : " + cno);

                const data = await fetchDelete(`/farmstory/comment/${cno}`);
                if (data) {
                    comment.remove();
                }
                // 댓글 수정 /////////////////////////////////////////////////////////////////////////
            } else if (e.target.dataset.mode == 'modify') {
                // 댓글 수정 모드
                textarea.readOnly = false;
                textarea.style.outline = "1px solid #111"
                textarea.focus();
                textarea.setSelectionRange(textarea.value.length, textarea.value.length);

                btnModify.textContent = '수정';
                btnModify.dataset.mode = 'update';
                btnRemove.textContent = '취소';
                btnRemove.dataset.mode = 'cancel';

                // 수정 취소 클릭
            } else if (e.target.dataset.mode == 'cancel') {
                // 수정 모드 해제
                textarea.readOnly = true;
                textarea.style.outline = "none"
                btnModify.dataset.mode = 'modify';
                btnRemove.textContent = ' 삭제';
                btnRemove.dataset.mode = 'remove';
                btnModify.textContent = ' 수정';

                // 수정 완료 클릭
            } else if (e.target.dataset.mode == 'update') {
                const jsonData = {
                    "cno": cno,
                    "content": textarea.value
                };

                console.log(jsonData);
                const data = await fetchPut('/farmstory/comment', jsonData);

                // 수정 모드 해제
                textarea.readOnly = true;
                textarea.style.outline = "none"
                btnModify.dataset.mode = 'modify';
                btnRemove.textContent = ' 삭제';
                btnRemove.dataset.mode = 'remove';
                btnModify.textContent = ' 수정';
            }
            // 게시글 삭제 수정 ///////////////////////////////////////////////////////////
            // 게시글 수정 시작 ///////////////////////////////////////////////////////////
        }
        /*
        else if(e.target.tagName === 'BUTTON') {

            if (e.target.id === 'btnArtModify' && e.target.dataset.art === 'modify') {
                // 수정 모드
                contentTextarea.readOnly = false;
                btnArtRemove.innerText = '취소';
                btnArtRemove.dataset.art = 'artCancel';
                btnArtModify.innerText = '수정완료';
                e.target.dataset.art = 'submit';

                // 게시글 수정 정보 전송 ///////////////////////////////////////////////////////////
            } else if (e.target.id === 'btnArtModify' && e.target.dataset.art === 'submit') {
                e.preventDefault();
                const communityForm = document.getElementById('communityForm');
                communityForm.submit();
                // 수정 모드 해제
                contentTextarea.readOnly = true;
                btnArtRemove.innerText = '삭제';
                btnArtRemove.dataset.art = 'remove';
                e.target.dataset.art = 'modify';

                // 게시글 수정 취소  //////////////////////////////////////////////////////////
            } else if (e.target.id === 'btnArtRemove' && e.target.dataset.art === 'artCancel') {
                // 사용자 의사 재확인
                if (confirm('게시글을 수정을 취소하시겠습니까?')) {
                    // 수정 모드 해제
                    contentTextarea.readOnly = true;
                    btnArtRemove.innerText = '삭제';
                    btnArtRemove.dataset.art = 'remove';
                    e.target.dataset.art = 'modify';
                }
                // 게시글 삭제 //////////////////////////////////////////////////////////
            } else if (e.target.id === 'btnArtRemove' && e.target.dataset.art === 'remove') {
                // 사용자 의사 재확인
                if (confirm('게시글을 삭제하시겠습니까?')) {
                    const data = await fetchDelete(`/farmstory/community/${ano}`);
                    if (data) {
                        alert("삭제 되었습니다.");
                        location.href = '/farmstory/community/list?cate=' + cate;
                    }
                }
            }
        }
    });
 */

}
// 텍스트 입력시 textarea 자동 높이 조절 - onload 밖에 둬야함
function autoResize(textarea) {
    // 텍스트 영역의 스크롤 높이 설정을 임시로 해제
    textarea.style.height = 'auto';

    // 텍스트 영역의 스크롤 높이를 내용에 맞게 조절
    textarea.style.height = textarea.scrollHeight + 'px';
}