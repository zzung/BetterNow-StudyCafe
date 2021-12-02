    // 로그인 전 상태일 경우 (댓글 영역 제한)
    /*
    const writeReplyArea = document.getElementById('writeReply');
    writeReplyArea.setAttribute("disabled","true");
    writeReplyArea.setAttribute("placeholder","댓글 작성은 로그인 후 이용 가능합니다.");
    const saveReplyBtn = document.getElementById('saveReplyBtn');
    saveReplyBtn.setAttribute("disabled", "true");
    */


    // 게시글 삭제
    function boardRemoveBtn() {
        if(confirm('확인 클릭시 영구 삭제됩니다. 해당 게시글을 삭제하시겠습니까?')){
            // 게시글 삭제 후 알림
            alert('삭제되었습니다.');
        }
    }

    // 댓글 작성
    function saveReplyBtn() {
        let replyContent = document.getElementById('writeReply').value;
        let newReplyContent = '<div class="replyEntity">'
        + '<hr>'
        + '<div class="replyImg"><img src="" width="50" height="50"></div>'
        + '<div class="replyWriter">TreeTree</div>'
        + '<div class="replyDate">2021.06.23</div>'
        + '<div class="replyContent">'
        + replyContent
        + '</div>';

        if(!replyContent) {
            alert('댓글 내용을 작성해주세요!');
            document.getElementById('writeReply').focus();
        }else {
            // 댓글 내용 저장 후 알림
            let e = document.getElementsByClassName('replyList');
            alert('댓글이 작성되었습니다.');
            console.log(e.firstChild);
        }
}

    // *** a 태그 href가 아닌 onclick 사용시 차이점 : https://thingsthis.tistory.com/130
    // *** 수정 클릭시 textarea 보이기 : https://cloudstudying.kr/lectures/458
    // 댓글 수정
    function replyEdit(obj) {
        let replyNum = $(obj).parent().parent().children("input[name=replyNum]").val();
    }

    // 댓글 삭제
    function replyRemove(obj) {
        let replyNum = $(obj).parent().parent().children("input[name=replyNum]").val();
        console.log(replyNum);

        if(confirm("댓글을 삭제하시겠습니까?")){
            // + 작성자,관리자 일 경우 조건 넣고 삭제 진행
            $(obj).parent().parent().remove();
            alert("댓글이 삭제되었습니다.");
        }
    }


    // 댓글 더보기 기능
    // https://blog.naver.com/PostView.nhn?blogId=deeperain&logNo=221459867105
    // https://nahosung.tistory.com/62
    // https://stothey0804.github.io/project/moreBtn/
            $(document).ready(function(){
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
                function moreViewBtn() {
                    startIndex += searchStep;
                    readMoreReply(startIndex);
                }


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
                        url: "${contextPath}/member/searchMoreNotify.do", // 주소 수정하기
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
            });

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
