<!-- 페이징 -->
    let totalData = 1000; // 총 글의 갯수
    let dataPerPage = 10; // 한 페이지에 나타낼 글의 갯수
    let pageCount = 5;   // 한 화면에 나타낼 페이지 수

    function paging(totalData, dataPerPage, pageCount, currentPage) {

        console.log("currentPage : " + currentPage);

        let totalPage = Math.ceil(totalData/dataPerPage); // 총 페이지 수
        let pageGroup = Math.ceil(currentPage/pageCount); // 페이지 그룹

        console.log("pageGroup : "+ pageGroup);

        let last = pageGroup * pageCount; // 화면에 보여질 마지막 페이지 번호
        if(last > totalPage) last = totalPage;
        let first = last - (pageCount-1); // 화면에 보여질 첫번째 페이지 번호
        let next = last+1;
        let prev = first-1;

        console.log("last : " + last);
        console.log("first : " + first);
        console.log("next : " + next);
        console.log("prev : " + prev);

        if (totalPage < 1) {
            first = last;
        }
        const pages = $("#pages");
        pages.empty();

        if(first > 5) {
            pages.append("<li class=\"pagination-item\">" + "<a onclick=\
                            GetTarget(" + (prev) + ");\"style='margin-left: 2px'>prev</a></li>");
        }
        for(let j = first; j <= last; j++) {
            if (currentPage === (j)) {
                pages.append("<li class=\"pagination-item\">" + "<a class='active' onclick=\"GetTarget(" + (j) + ");\" style='margin-left: 2px'>" + (j) + "</a></li>");
            } else if (j > 0) {
                pages.append("<li class=\"pagination-item\">" + "<a onclick=\"GetTarget(" + (j) + ")\"style='margin-left: 2px'>" + (j) + "</a></li>");
            }
        }
        if (next >5 && next <totalPage) {
            pages.append("<li class=\"pagination-item\">" + "<a onclick=\"GetTarget(" + (next) + ");\"style='margin-left: 2px'>next</a></li>");
        }
    }

    // $("document").ready(function(){
    //     paging(totalData, dataPerPage, pageCount, 1);
    // });

    // // Search bar - 검색 기능
    // function boardSearchBar(){
    //     console.log("클릭");
    //
    //     let keyword = $(this).val().toLowerCase();
    //     console.log('키워드 : '+keyword);
    //
    //     $("#boardTable tr").filter(function() {
    //         $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    //     });
    // };

    // Search bar - 검색 기능
    function getSearchList(){
        let str = "";
        $.ajax({
            type: 'GET',
            url : "/board/getSearchList",
            data : $("form[name=search-form]").serialize(),
            success : function(result){

                //테이블 초기화
                $('#boardTable > tbody').empty();

                if(result.length >= 1){
                    result.forEach(function(item){
                        str='<tr>'
                        str += "<td><span>"+ item.boardNum +"</span></td>";
                        str += "<td><a href='/board/"+ item.boardNum + "' style=\"text-decoration: none; color: black;\">\n" +
                                    "<span id=\"boardTitleHover\">"+ item.boardTitle +"</span></a></td>";
                        str += "<td><span>" + item.member.memId + "</span></td>";
                        str += "<td><span>"+ item.modifyDate.date.year +"-"+ item.modifyDate.date.month +"-"+ item.modifyDate.date.day +" "+ item.modifyDate.time.hour +":"+ item.modifyDate.time.minute +"</span></td>"
                        str += "<td><span>" + item.boardViews + "</span></td>";
                        str += "</tr>"

                        $('#boardListBody').append(str);
                    })
                }
                if (result.length == 0){
                    str = "<tr><td colspan='5'>검색 결과가 없습니다. </td></tr>"
                    $('#boardListBody').append(str);
                }

            }
        })
    }