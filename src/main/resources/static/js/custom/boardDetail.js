
    // 게시글 삭제
    function boardRemoveBtn() {
        let boardNum = document.getElementById("boardNum").value;
        console.log(boardNum);
        if(confirm('확인 클릭시 영구 삭제됩니다. 해당 게시글을 삭제하시겠습니까?')){
            $.ajax({
                url: "/board/delete/"+boardNum,
                type: "POST",
                data: {'boardNum':boardNum},
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
        let boReplyContent = document.getElementById('boReplyContent').value;
        let boardNum = document.getElementById('boardNum').value;

        if(boReplyContent.length<2) {
            alert('댓글 내용을 한 글자 이상 작성해주세요!');
            document.getElementById('boReplyContent').focus();
        }else {
            if(confirm('댓글을 작성하시겠습니까?')){
                $.ajax({
                    url: "/board/reply/write",
                    type: "POST",
                    data: {
                        'loginUserId' : loginUserId,
                        'boReplyContent' : boReplyContent,
                        'boardNum' : boardNum
                    },
                    success: function (result) {
                        if(result == "success"){ // 댓글 저장 성공
                            alert("댓글이 등록되었습니다 :)");
                            location.href = 'redirect:/board/' + boardNum;
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
    }


    // 댓글 수정
    function replyEdit(obj) {
        let boReplyNum = $(obj).parent().parent().children("input[name=boReplyNum]").val();
        let boReplyOriginalContent = $(obj).parent().parent().children('div:eq(3)').text();
        let boReplyWriter = $(obj).parent().parent().children('div:eq(1)').text();
        let boReplyDate = $(obj).parent().parent().children('div:eq(2)').children('.replyModifyDate').text();


        let str = "";
        str += '<div class=\"replyEntity\">';
        str += '<input type=\"hidden\" name=\"boReplyNum\" value='+ boReplyNum +'>';
        str += '<hr>';
        str += '<div class=\"replyImg\"><img src=\"\" width=\"50\" height=\"50\"></div>';
        str += '<div class=\"replyWriter\" name=\"boReplyMember\">'+ boReplyWriter +'</div>';
        str += '<div class=\"replyDate\">';
        str += '<span class=\"replyModifyDate\">'+ boReplyDate +'</span>';
        str += '<a href=\"javascript:void(0);\" onclick=\"replyEditUndo(this);\" class=\"replyRemove\">취소</a>';
        str += '<a href=\"javascript:void(0);\" onclick=\"replyEditSave(this);\" class=\"replyEdit\">저장</a>';
        str += '</div>';
        str += '<textarea class=\"form-control edit-reply\" name=\"boReplyContent\" id=\"editContent\" placeholder=\"수정할 내용을 입력해주세요. (500자 까지 작성 가능)\" maxlength=\"500\" style=\"resize: none;\">'+ boReplyOriginalContent+'</textarea>';
        str += '<div name=\"originalReplyContent\" style=\"display: none\">'+ boReplyOriginalContent +'</div>';
        str += '</div>';

        $(obj).parent().parent().replaceWith(str);
        $('#editContent').focus();

    }

    // 댓글 수정 - 취소 클릭시
    function replyEditUndo(obj) {
        let boReplyNum = $(obj).parent().parent().children("input[name=boReplyNum]").val();
        let boReplyOriginalContent = $(obj).parent().parent().children('div:eq(3)').text();
        let boReplyWriter = $(obj).parent().parent().children('div:eq(1)').text();
        let boReplyDate = $(obj).parent().parent().children('div:eq(2)').children('.replyModifyDate').text();


        let str = "";
        str += '<div class=\"replyEntity\">';
        str += '<input type=\"hidden\" name=\"boReplyNum\" value='+ boReplyNum +'>';
        str += '<hr>';
        str += '<div class=\"replyImg\"><img src=\"\" width=\"50\" height=\"50\"></div>';
        str += '<div class=\"replyWriter\" name=\"boReplyMember\">'+ boReplyWriter +'</div>';
        str += '<div class=\"replyDate\">';
        str += '<span class=\"replyModifyDate\">'+ boReplyDate +'</span>';
        str += '<a href=\"javascript:void(0);\" onclick=\"replyRemove(this);\" class=\"replyRemove\">삭제</a>';
        str += '<a href=\"javascript:void(0);\" onclick=\"replyEdit(this);\" class=\"replyEdit\">수정</a>';
        str += '</div>';
        str += '<div class=\"replyContent\" name=\"boReplyContent\">'+ boReplyOriginalContent +'</div>';
        str += '<div name=\"originalReplyContent\" style=\"display: none\">'+ boReplyOriginalContent +'</div>';
        str += '</div>';

        $(obj).parent().parent().replaceWith(str);
    }

    // 댓글 수정 - 저장 클릭시
    function replyEditSave(obj) {
        let boardNum = $('#boardNum').val();
        let boReplyNum = $(obj).parent().parent().children("input[name=boReplyNum]").val();
        let boReplyWriter = $(obj).parent().parent().children('div:eq(1)').text();
        let newContent = $("textarea#editContent").val();

        if(confirm("댓글을 수정 하시겠습니까?")) {
            $.ajax({
                url: "/board/reply/edit/"+boReplyNum,
                type: "POST",
                data: { 'boReplyNum' : boReplyNum,
                    'boReplyContent' : newContent,
                    'boReplyMemId' : boReplyWriter,
                    'boardNum' : boardNum
                },
                success: function (result) {
                    if(result > 0){ // 댓글 수정 저장 성공
                        alert("댓글이 수정되었습니다. :)");
                    }
                    if(result == 0) { // 댓글 수정 저장 실패
                        alert("댓글 수정 오류 ! 다시 시도해주세요.");
                    }
                    location.href = '/board/' + boardNum;

                },error: function(request,status,error){
                    console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                }
            });
        }
    }


    // 댓글 삭제
    function replyRemove(obj) {
        let boardNum = document.getElementById("boardNum").value;
        let boReplyNum = $(obj).parent().parent().children("input[name=boReplyNum]").val();

        if(confirm("삭제시 복구가 불가능합니다. 댓글을 삭제하시겠습니까?")){
            $.ajax({
                url: "/board/reply/delete/"+boReplyNum,
                type: "POST",
                data: { 'boReplyNum' : boReplyNum },
                success: function (result) {
                    if(result > 0){ // 댓글 삭제 성공
                        alert("댓글이 삭제되었습니다.");
                        location.href = '/board/' + boardNum;
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


    // 댓글 더보기 기능
    // https://blog.naver.com/PostView.nhn?blogId=deeperain&logNo=221459867105
    // https://nahosung.tistory.com/62
    // https://stothey0804.github.io/project/moreBtn/
    window.onload = function () {
                // 댓글 총 갯수
                // var replyTotalCnt = '${replyTotalCnt}';
                let replyTotalCnt = document.getElementsByClassName("replyEntity").length;
                console.log(replyTotalCnt);

                // 조회 인덱스
                let startIndex = 1;	// 인덱스 초기값
                let searchStep = 5;	// 5개씩 로딩

                // 페이지 로딩 시 첫 실행
                readMoreReply(startIndex);

                // 더보기 클릭시
                // function moreViewBtn() {
                //     startIndex += searchStep;
                //     readMoreReply(startIndex);
                // }


                // 더보기 실행함수 **
                function readMoreReply(index){
                    let endIndex = index+searchStep-1;	// endIndex설정
                    $.ajax({
                        type: "post",
                        async: "true",
                        dataType: "json",
                        data: {
                            m_id: '${m_id}',
                            startIndex: index,
                            endIndex: endIndex
                        },
                        url: "", // 주소 수정하기
                        success: function (data, textStatus) {
                            let NodeList = "";
                            for(i = 0; i <data.length; i++){
                                NodeList += '<div class="replyEntity">'+
                                    '<hr>'+
                                    '<div class="replyImg"><img src="" width="50" height="50"></div>'+
                                    '<div class="replyWriter">'+ 작성자 + '</div>'+
                                    '<div class="replyDate">' + 날짜 + '</div>'+
                                    '<a href="" class="replyRemove">삭제</a>' +
                                    '<a href="" class="replyEdit">수정</a> ' +
                                    '<div class="replyContent">' + 댓글내용 + '</div>'+
                                    '</div>';
                            }
                            $(NodeList).appendTo($("#replyList")).slideDown();

                            // 더보기 버튼 삭제
                            if(startIndex + searchStep > replyTotalCnt){
                                $('#moreViewArea').remove();
                            }
                        }
                    });
                }
};

function moreContent(id, cnt) {

    $('#startCount').val(list_length);
    $('#viewCount').val(cnt);

    $.ajax({
        type: "post",
        url: "",
        data: $('#moreViewReply').serialize(),
        dataType: "json",
        success: function(result) {
            console.log(result);
            let moreReply = "";
            for(let i=0; i<result.hashMapList.length; i++) {
                moreReply +=
                    '<div>'+
                    '<hr>'+
                    '<div class="replyImg"><img src="" width="50" height="50"></div>'+
                    '<div class="replyWriter">'+ 작성자 + '</div>'+
                    '<div class="replyDate">' + 날짜 + '</div>'+
                    '<a href="" class="replyRemove">삭제</a>' +
                    '<a href="" class="replyEdit">수정</a> ' +
                    '<div class="replyContent">' + 댓글내용 + '</div>'+
                    '</div>';
            }
            moreReply += '<div class="moreView text-center" id="moreView">' +
                '<hr><a href="more_btn" id="javascript:moreContent("more_list",5);">댓글 더보기</a><hr></div>';
            $('')
        }, error: function(error) {
            console.log(error);
        }
    });

}
