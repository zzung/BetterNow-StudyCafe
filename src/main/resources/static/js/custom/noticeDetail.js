// 게시글 삭제
function noticeRemoveBtn() {
    let noticeNum = document.getElementById("noticeNum").value;
    console.log(noticeNum);

    if(confirm('확인 클릭시 영구 삭제됩니다. 해당 게시글을 삭제하시겠습니까?')){
        $.ajax({
            url: "/notice/delete/"+noticeNum,
            type: "POST",
            data: {'noticeNum':noticeNum},
            success: function (result) {
                if(result>0) {
                    alert("게시글이 정상적으로 삭제되었습니다.");
                    location.href = '/board';
                }
                if(result==0) {
                    alert("게시글 삭제 도중 오류가 발생했습니다. 다시 시도해주세요.");
                    location.href = '/board';
                }
            },error: function (error) {
                console.log(error);
            }
        });

    }
}


// 댓글 작성 ajax
function saveReplyBtn() {
    let loginUserId = document.getElementById('loginUserId').value;
    let noReplyContent = document.getElementById('noReplyContent').value;
    let noticeNum = document.getElementById('noticeNum').value;

    if(noReplyContent.length<2) {
        alert('댓글 내용을 작성해주세요!');
        document.getElementById('noReplyContent').focus();
    }else {
        $.ajax({
            url: "/notice/reply/write",
            type: "POST",
            data: {
                'loginUserId' : loginUserId,
                'noReplyContent' : noReplyContent,
                'noticeNum' : noticeNum
            },
            success: function (result) {
                if(result == "success"){ // 댓글 저장 성공
                    alert("댓글이 등록되었습니다 :)");
                    location.href = '/notice/' + noticeNum;
                }
                if(result == "") { // 댓글 저장 실패
                    alert("댓글 등록 오류 ! 다시 시도해주세요.");
                    location.href = '/board';
                }
            },error: function(request,status,error){
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }
}


// 댓글 삭제
function replyRemove(obj) {
    let noticeNum = document.getElementById("noticeNum").value;
    let noReplyNum = $(obj).parent().parent().children("input[name=noReplyNum]").val();
    console.log(noReplyNum);

    if(confirm("삭제시 복구가 불가능합니다. 댓글을 삭제하시겠습니까?")){
        $.ajax({
            url: "/notice/reply/delete/"+noReplyNum,
            type: "POST",
            data: { 'noReplyNum' : noReplyNum },
            success: function (result) {
                if(result > 0){ // 댓글 삭제 성공
                    alert("댓글이 삭제되었습니다.");
                    location.href = '/notice/' + noticeNum;
                }
                if(result == 0) { // 댓글 삭제 실패
                    alert("댓글 삭제 오류 ! 다시 시도해주세요.");
                    location.href = '/board';
                }
            },error: function(request,status,error){
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }
}