// 아이디 찾기
function findId() {
    let phone = $("#findIdByPhone").val();

    if(phone.length!=11){
        alert("숫자 11자리를 정확히 입력해주세요.");
    }
    if (phone.length==11) {
        $.ajax({
            url: "/findId",
            type: "POST",
            data: {'phone' : phone},
            success: function (result) {
                if(result !=""){
                    $('#findId-modal-body').html("해당 회원 정보와 일치하는 아이디는 <b>"+result+"</b> 입니다.");
                }else {
                    $('#findId-modal-body').text("해당 회원 정보와 일치하는 아이디가 없습니다.");
                    $('#goLoginPage').hide();
                }
            },error: function (error) {
                console.log(error);
            }
        });
        $('#findIdBtn').attr('data-toggle','modal');
        $('#findIdBtn').attr('data-target','#findIdModal');

    };
};

// 비밀번호 찾기
function findPwd() {
    let memId = $("#findPwdByMemId").val();
    let phone = $("#findPwdByPhone").val();

    if((memId.length<9)||(phone.length!=11)) {
        alert('입력란을 정확히 채워주세요.');
    }
    if((memId.length>9)&&(phone.length==11)) {
        $.ajax({
            url: "/findPwd",
            type: "POST",
            data: {'memId':memId, 'phone':phone},
            success: function (result) { //result=memId
                if(result == ""){
                    $('#changePwdForm').hide();
                    $('#findPwd-modal-body').text("해당 회원 정보와 일치하는 아이디가 없습니다.");
                }
                if(result != "") {
                    $('#findPwd-modal-body').html("<b>"+result+"</b> 님, 새로운 비밀번호를 설정해주세요.");
                    $('#findPwd-modal-footer').hide();
                    $('#changePwdForm').show();
                    $('#changePwdByMemId').val(result);
                    console.log('changePwdByMemId에 memId 다시 넣음');
                }
            },error: function (error) {
                console.log(error);
            }
        });
        $('#findPwdBtn').attr('data-toggle','modal');
        $('#findPwdBtn').attr('data-target','#findPwdModal');
    };

};


// 모달 비밀번호 변경시 비밀번호 일치 하지 않을 때 이벤트
window.onload = function () {
    let changePwd = document.getElementById("changePwd");
    let changePwdCheck = document.getElementById("changePwdCheck");
    let changePwdCheckMsg = document.getElementById("changePwdCheckMsg");
    let changePwdInput = document.getElementById("changePwdInput");

    changePwd.addEventListener("blur", function (e) {
        if(changePwd.value.length<8) {
            changePwdCheckMsg.innerText = '비밀번호가 일치하지 않습니다.';
        }
    });

    changePwdCheck.addEventListener("blur", function (e) {
        if(changePwd.value != changePwdCheck.value) {
            changePwdCheckMsg.innerText = '비밀번호가 일치하지 않습니다.';
            changePwdInput.disabled = true;
        }if(changePwd.value == changePwdCheck.value) {
            changePwdCheckMsg.innerText = '';
            changePwdInput.disabled = false;
        }
    });
};


// 비밀번호 변경 모달 ajax
function changePwdBtn() {
    let memPwd = $('#changePwd').val();
    let memId = $('#changePwdByMemId').val();
    console.log('비밀번호 변경 버튼 클릭, '+memPwd);
    console.log('비밀번호 변경 버튼 클릭2, '+memId);

    $.ajax({
        url: "/updatePwd",
        type: "POST",
        data: {'memPwd':memPwd, 'memId':memId },
        success: function (result) {
            if(result == 1) {
                alert("회원님의 비밀번호가 변경되었습니다. 로그인 페이지로 이동합니다. :)");
                location.href = '/login';
            }
            if(result == 0) {
                alert("비밀번호 변경 오류 ! 다시 시도해주세요.");
                location.href = '/find/Info';
            }
        },error: function (error) {
            console.log(error);
        }
    });

};