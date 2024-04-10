function notLogin() {
    console.log("g2");
    const confirmed = confirm("로그인 후 사용 가능합니다. 로그인 페이지로 이동하시겠습니까?");
    if (confirmed) {
        window.location.href = "/farmstory/user/login";
    }
}