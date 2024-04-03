//////////////////////register////////////////////////////////////////////



//유효성 검사에 사용할 상태변수
let isUidOk = false;
let isPassOk = false;
let isNameOk = false;
let isNickOk = false;
let isEmailOk = false;
let isHpOk = false;
let isEmailCodeOk = false;

// 유효성 검사에 사용할 정규표현식
const reUid = /^[a-z]+[a-z0-9]{4,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName = /^[가-힣]{2,10}$/
const reNick = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

window.onload = function () {

    // 아이디 유효성 검사
    const btnCheckUid = document.getElementById('btnCheckUid');
    const resultUid = document.getElementById('result_uid');

    btnCheckUid.onclick = function () {

        const type = this.dataset.type;
        const input = document.registerForm[type];

        console.log('value : ' + input.value);

        // 정규식 검사
        if (!input.value.match(reUid)) {

            console.log("여기에 들어오나?");

            input.classList.add('is-invalid');
            resultUid.innerText = '아이디 형식이 맞지 않습니다.';
            isUidOk = false;
            return;
        }

        async function fetchGet(url) {

            console.log("fetchData1...1");

            try {
                console.log("fetchData1...2");
                const response = await fetch(url);
                console.log("here1");

                if (!response.ok) {
                    console.log("here2");
                    throw new Error('response not ok');
                }

                const data = await response.json();
                console.log("data1 : " + data);
                return data;
            } catch (err) {
                console.log(err)
            }
        }


        setTimeout(async () => {

                console.log('value : ' + input.value);
                console.log('type:' + type);

                const data = await fetchGet(`/farmstory/user/${type}/${input.value}`);

                if (data.result > 0) {

                    input.classList.remove('is-valid'); // 기존의 유효한 클래스를 제거
                    input.classList.add('is-invalid');

                    resultUid.classList.add('invalid-feedback');
                    resultUid.innerText = '이미 사용중인 아이디 입니다.';
                    isUidOk = false;
                } else {

                    input.classList.remove('is-invalid'); // 기존의 무효한 클래스를 제거
                    input.classList.add('is-valid');

                    resultUid.classList.add('valid-feedback');

                    resultUid.innerText = '사용 가능한 아이디 입니다.';
                    isUidOk = true;
                    console.log("isUidOk:" + isUidOk);
                }
            }, 1000
        );
    }


    // 비밀번호 유효성 검사
    const resultPass = document.getElementById('result_pass');

    document.registerForm.pass2.addEventListener('focusout', () => {

        const inputPass1 = document.registerForm.pass;
        const inputPass2 = document.registerForm.pass2;

        console.log("inputPass1 : " + inputPass1);
        console.log("inputPass2 : " + inputPass2);

        if (inputPass1.value === inputPass2.value) {

            if (!inputPass1.value.match(rePass)) {
                inputPass1.classList.add('is-invalid');
                inputPass2.classList.add('is-invalid');
                resultPass.innerText = '비밀번호 형식에 맞지 않습니다.';
                isPassOk = false;
            } else {
                inputPass1.classList.add('is-valid');
                inputPass2.classList.add('is-valid');
                resultPass.innerText = '사용 가능한 비밀번호 입니다.';
                isPassOk = true;
            }
        } else {
            inputPass1.classList.add('is-invalid');
            inputPass2.classList.add('is-invalid');
            resultPass.innerText = '비밀번호가 일치하지 않습니다.';
            isPassOk = false;
        }
    });


    // 이름 유효성 검사
    const resultName = document.getElementById('result_name');

    document.registerForm.name.addEventListener('focusout', () => {
        const value = document.registerForm.name.value;
        const inputName = document.registerForm.name;

        if (!value.match(reName)) {
            inputName.classList.add('is-invalid');
            resultName.classList.add('invalid-feedback');
            resultName.innerText = '이름 형식이 맞지 않습니다.';
            isNameOk = false;
        } else {
            resultName.innerText = '';
            isNameOk = true;
        }
    });

    // 별명 유효성 검사
    const resultNick = document.getElementById('result_nick');

    document.registerForm.nick.addEventListener('focusout', () => {
        const type = document.registerForm.nick.dataset.type;
        const input = document.registerForm[type];

        console.log('value : ' + input.value);

        // 정규식 검사
        if (!input.value.match(reNick)) {
            input.classList.add('is-invalid');
            resultNick.classList.add('invalid-feedback');
            resultNick.innerText = '닉네임 형식이 맞지 않습니다.';
            isNickOk = false;
            return;
        }

        async function fetchGet(url) {

            console.log("fetchData1...1");

            try {
                console.log("fetchData1...2");
                const response = await fetch(url);
                console.log("here1");

                if (!response.ok) {
                    console.log("here2");
                    throw new Error('response not ok');
                }

                const data = await response.json();
                console.log("data1 : " + data);
                return data;
            } catch (err) {
                console.log(err)
            }
        }

        setTimeout(async () => {

            const data = await fetchGet(`/farmstory/user/${type}/${input.value}`);

            if (data.result > 0) {
                input.classList.add('is-invalid');

                resultNick.classList.add('invalid-feedback');
                resultNick.innerText = '이미 사용중인 닉네임 입니다.';
                isNickOk = false;
            } else {
                input.classList.add('is-valid');

                resultNick.classList.add('valid-feedback');
                resultNick.innerText = '사용 가능한 닉네임 입니다.';
                isNickOk = true;

            }
        }, 1000);
    });


    // 휴대폰 유효성 검사
    const resultHp = document.getElementById('result_hp');

    document.registerForm.hp.addEventListener('focusout', () => {
        const type = document.registerForm.hp.dataset.type;
        const input = document.registerForm[type];


        console.log('value : ' + input.value);

        // 정규식 검사
        if (!input.value.match(reHp)) {
            input.classList.add('is-invalid');
            resultHp.classList.add('invalid-feedback');
            resultHp.innerText = '휴대폰 형식이 맞지 않습니다.';
            isHpOk = false;
            return;
        }

        async function fetchGet(url) {

            console.log("fetchData1...1");

            try {
                console.log("fetchData1...2");
                const response = await fetch(url);
                console.log("here1");

                if (!response.ok) {
                    console.log("here2");
                    throw new Error('response not ok');
                }

                const data = await response.json();
                console.log("data1 : " + data);
                return data;
            } catch (err) {
                console.log(err)
            }
        }

        setTimeout(async () => {
            const data = await fetchGet(`/farmstory/user/${type}/${input.value}`);

            if (data.result > 0) {
                input.classList.add('is-invalid');

                resultHp.classList.add('invalid-feedback');
                resultHp.innerText = '이미 사용중인 휴대폰 입니다.';
                isHpOk = false;
            } else {
                input.classList.add('is-valid');

                resultHp.classList.add('valid-feedback');
                resultHp.innerText = '사용 가능한 휴대폰 입니다.';
                isHpOk = true;
            }
        }, 1000);
    });

    // 이메일 유효성 검사
    const divEmailCode = document.getElementById('divEmailCode');

    const inputEmail = document.getElementById('inputEmail');
    const btn_email = document.getElementById('btn_email');
    const resultEmail = document.getElementById('result_email');

    btn_email.onclick = function () {

        const type = this.dataset.type;

        console.log("type : " + type);

        // 유효성 검사
        if (!inputEmail.value.match(reEmail)) {

            inputEmail.classList.add('is-invalid');
            resultEmail.innerText = '이메일 형식이 맞지 않습니다.';
            isEmailOk = false;
            return;
        }

        async function fetchGet(url) {

            console.log("fetchData1...1");

            try {
                console.log("fetchData1...2");
                const response = await fetch(url);
                console.log("here1");

                if (!response.ok) {
                    console.log("here2");
                    throw new Error('response not ok');
                }

                const data = await response.json();
                console.log("data1 : " + data);
                return data;
            } catch (err) {
                console.log(err)
            }
        }


        // 이메일 인증코드 발급 및 중복체크 추가 예정
        setTimeout(async () => {

            console.log('type : ' + type);
            console.log('inputEmail value : ' + inputEmail.value);

            alert('이메일을 보냅니다.');

            const data = await fetchGet(`/farmstory/user/${type}/${inputEmail.value}`);
            console.log('data : ' + data.result);

            if (data.result > 0) {
                inputEmail.classList.add('is-invalid');
                resultHp.innerText = '이미 사용중인 이메일 입니다.';
                isEmailOk = false;
            }

        }, 1000);

        isEmailOk = true;
    }

    // 이메일 인증코드 확인
    const btnCheckEmailCode = document.getElementById('btnCheckEmailCode');
    const inputEmailCode = document.getElementById('inputEmailCode');
    const resultEmailCode = document.getElementById('resultEmailCode');

    btnCheckEmailCode.onclick = async function () {

        async function fetchGet(url) {

            console.log("fetchData1...1");

            try {
                console.log("fetchData1...2");
                const response = await fetch(url);
                console.log("here1");

                if (!response.ok) {
                    console.log("here2");
                    throw new Error('response not ok');
                }

                const data = await response.json();
                console.log("data1 : " + data);
                return data;
            } catch (err) {
                console.log(err)
            }
        }

        const data = await fetchGet(`/farmstory/email/${inputEmailCode.value}`);

        if (!data.result) {
            inputEmail.classList.add('is-invalid');
            inputEmailCode.classList.add('is-invalid');
            resultEmailCode.innerText = '인증코드가 일치하지 않습니다.';
            isEmailCodeOk = false;
        } else {
            inputEmail.classList.add('is-valid');
            inputEmailCode.classList.add('is-valid');
            resultEmailCode.innerText = '이메일이 인증되었습니다.';
            isEmailCodeOk = true;
        }
    }


//우편번호 검색
    function postcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                /*
                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //document.getElementById("sample6_extraAddress").value = extraAddr;

                } else {
                    //document.getElementById("sample6_extraAddress").value = '';
                }
            */

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('inputZip').value = data.zonecode;
                document.getElementById("inputAddr1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("inputAddr2").focus();
            }
        }).open();
    }

    findZip.onclick = function () {
        postcode();
    }

    // 최종 유효성 검사 확인
    document.registerForm.onsubmit = function () {

        if (!isUidOk) {
            alert("아이디가 유효하지 않습니다.!");
            return false;
        }

        if (!isPassOk) {
            alert('비밀번호가 유효하지 않습니다.');
            return false;
        }

        if (!isNameOk) {
            alert('이름이 유효하지 않습니다.');
            return false;
        }

        if (!isNickOk) {
            alert('별명이 유효하지 않습니다.');
            return false;
        }

        if (!isEmailOk) {
            alert('이메일이 유효하지 않습니다.');
            return false;
        }

        if (!isEmailCodeOk) {
            alert('이메일 인증을 해주세요.');
            return false;
        }

        if (!isHpOk) {
            alert('휴대폰이 유효하지 않습니다.');
            return false;
        }

        if (document.getElementById('inputZip').value === '') {
            alert('주소를 입력해주세요');
            return false;
        }

        // 폼 전송
        return true;
    }


//////////////////////////////////////terms//////////////////////////////////////////////////


/////////////////////////////////////////login////////////////////////////////////////////

    const success = [[${success}]];

    if (success == 100) {
        showModal('로그인에 실패했습니다.\n다시 한번 아이디, 비밀번호를 확인 하시기 바랍니다. 😢');
    } else if (success == 200) {
        showModal('회원 가입 되었습니다. 😊');
    } else if (success == 300) {
        showModal('로그아웃 되었습니다. 안녕히 가세요. 😃');
    }
}
