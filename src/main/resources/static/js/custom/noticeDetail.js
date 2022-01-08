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
        alert('댓글 내용을 한 글자 이상 작성해주세요!');
        document.getElementById('noReplyContent').focus();
    }else {
        if(confirm('댓글을 저장하시겠습니까?')){
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
                        location.href = 'redirect:/notice/' + noticeNum;
                    }
                    else { // 댓글 저장 실패
                        alert("댓글 등록 오류 ! 다시 시도해주세요.");
                        location.href = '/board';
                    }
                },error: function(request,status,error){
                    console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                }
            });
        }

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

// 댓글 수정
function replyEdit(obj) {
    let noReplyNum = $(obj).parent().parent().children("input[name=noReplyNum]").val();
    let noReplyOriginalContent = $(obj).parent().parent().children('div:eq(3)').text();
    let noReplyWriter = $(obj).parent().parent().children('div:eq(1)').text();
    let noReplyDate = $(obj).parent().parent().children('div:eq(2)').children('.replyModifyDate').text();


    let str = "";
    str += '<div class=\"replyEntity\">';
    str += '<input type=\"hidden\" name=\"noReplyNum\" value='+ noReplyNum +'>';
    str += '<hr>';
    str += '<div class=\"replyImg\"><img src=\"\" width=\"50\" height=\"50\"></div>';
    str += '<div class=\"replyWriter\" name=\"noReplyMember\">'+ noReplyWriter +'</div>';
    str += '<div class=\"replyDate\">';
    str += '<span class=\"replyModifyDate\">'+ noReplyDate +'</span>';
    str += '<a href=\"javascript:void(0);\" onclick=\"replyEditUndo(this);\" class=\"replyRemove\">취소</a>';
    str += '<a href=\"javascript:void(0);\" onclick=\"replyEditSave(this);\" class=\"replyEdit\">저장</a>';
    str += '</div>';
    str += '<textarea class=\"form-control edit-reply\" name=\"noReplyContent\" id=\"editContent\" placeholder=\"수정할 내용을 입력해주세요. (500자 까지 작성 가능)\" maxlength=\"500\" style=\"resize: none;\">'+ noReplyOriginalContent+'</textarea>';
    str += '<div name=\"originalReplyContent\" style=\"display: none\">'+ noReplyOriginalContent +'</div>';
    str += '</div>';

    $(obj).parent().parent().replaceWith(str);
    $('#editContent').focus();

}

// 댓글 수정 - 취소 클릭시
function replyEditUndo(obj) {
    let noReplyNum = $(obj).parent().parent().children("input[name=noReplyNum]").val();
    let noReplyOriginalContent = $(obj).parent().parent().children('div:eq(3)').text();
    let noReplyWriter = $(obj).parent().parent().children('div:eq(1)').text();
    let noReplyDate = $(obj).parent().parent().children('div:eq(2)').children('.replyModifyDate').text();


    let str = "";
    str += '<div class=\"replyEntity\">';
    str += '<input type=\"hidden\" name=\"noReplyNum\" value='+ noReplyNum +'>';
    str += '<hr>';
    str += '<div class=\"replyImg\"><img src=\"\" width=\"50\" height=\"50\"></div>';
    str += '<div class=\"replyWriter\" name=\"noReplyMember\">'+ noReplyWriter +'</div>';
    str += '<div class=\"replyDate\">';
    str += '<span class=\"replyModifyDate\">'+ noReplyDate +'</span>';
    str += '<a href=\"javascript:void(0);\" onclick=\"replyRemove(this);\" class=\"replyRemove\">삭제</a>';
    str += '<a href=\"javascript:void(0);\" onclick=\"replyEdit(this);\" class=\"replyEdit\">수정</a>';
    str += '</div>';
    str += '<div class=\"replyContent\" name=\"noReplyContent\">'+ noReplyOriginalContent +'</div>';
    str += '<div name=\"originalReplyContent\" style=\"display: none\">'+ noReplyOriginalContent +'</div>';
    str += '</div>';

    $(obj).parent().parent().replaceWith(str);
}

// 댓글 수정 - 저장 클릭시
function replyEditSave(obj) {
    let noticeNum = $('#noticeNum').val();
    let noReplyNum = $(obj).parent().parent().children("input[name=noReplyNum]").val();
    let newContent = $("textarea#editContent").val();

    if(confirm("댓글을 수정 하시겠습니까?")) {
        $.ajax({
            url: "/notice/reply/edit/"+noReplyNum,
            type: "POST",
            data: { 'noReplyNum' : noReplyNum,
                'noReplyContent' : newContent,
                'noticeNum' : noticeNum
            },
            success: function (result) {
                if(result > 0){ // 댓글 수정 저장 성공
                    alert("댓글이 수정되었습니다. :)");
                }
                if(result == 0) { // 댓글 수정 저장 실패
                    alert("댓글 수정 오류 ! 다시 시도해주세요.");
                }
                location.href = '/notice/' + noticeNum;

            },error: function(request,status,error){
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        });
    }
}
