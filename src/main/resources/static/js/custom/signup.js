window.onload = function () {
    let memPwd = document.getElementById("memPwd");
    let pwdCheck = document.getElementById("pwdCheck");
    let pwdCheckMsg = document.getElementById("pwdCheckMsg");

    pwdCheck.addEventListener("blur", function (e) {
       if(memPwd.value != pwdCheck.value) {
           pwdCheckMsg.innerText = '비밀번호가 일치하지 않습니다.';
       }else {
           pwdCheckMsg.innerText = '';
       }
    });
};