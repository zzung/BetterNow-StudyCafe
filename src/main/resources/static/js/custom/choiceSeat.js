
    /** 개인 좌석 클릭시 자리 번호 띄우기, 시간당 가격 값 설정하기 */
    let selectPersonalSeat = document.querySelectorAll(".seats");
    // 클릭 이벤트 추가
    for (var i = 0; i < selectPersonalSeat.length; i++) {
        selectPersonalSeat[i].addEventListener("click", click);
    }

    /**  그룹 좌석 클릭시 자리 번호 띄우기, 시간당 가격 값 설정하기 */
    let selectGroupSeats = document.getElementsByClassName("groupSeats");
    // 클릭 이벤트 추가
    for (var j = 0; j < selectGroupSeats.length; j++) {
        selectGroupSeats[i].addEventListener("click", click);
    }


    function click (e) {
        let selectedSeat = e.target.value;
        $('#selectedSeat').html('선택한 자리<br><br><h3>' + selectedSeat + '</h3>');

        // 개인 좌석 선택시 좌석 기본 금액
        $('#timePerPrice').val(1500);

        // 그룹 좌석 선택시 그룹 기본 금액
        if ((selectedSeat == 'G1') || (selectedSeat == 'G2') || (selectedSeat == 'G3') || (selectedSeat == 'G4')) {
            $('#timePerPrice').val(4000);
        }
        if ((selectedSeat == 'H1') || (selectedSeat == 'H2') || (selectedSeat == 'H3')) {
            $('#timePerPrice').val(7000);
        }
        if ((selectedSeat == 'P1') || (selectedSeat == 'P2')) {
            $('#timePerPrice').val(10000);
        }

        // 선택된 자리 disabled 속성 추가
        $(e.target).attr('disabled', true);

        // 다른 자리 선택시 disabled 속성 없애기
        let cnt = $('input:button[name=seatNum]:disabled').length;
        let cnt2 = $('input:button[name=groupSeatNum]:disabled').length;
        if ((cnt + cnt2) > 1) {
            $('input[name=seatNum]:disabled').removeAttr("disabled");
            $('input[name=groupSeatNum]:disabled').removeAttr("disabled");
        } else {
            $(e.target).attr('disabled', true);
        }

        // 예약 완료 시 - 사용중인 자리 비활성화 (클래스 추가)
        // $(e.target).addClass('occupiedSeat');

        // 합산 금액 보여주기
        calculate($('#selectSeatTime').val(), $('#timePerPrice').val());
    }

    $('.occupiedSeat').on('click', (e) => {
        alert('이미 사용중인 자리 입니다. 다른 자리를 선택해주세요.');
        $('#selectedSeat').html('선택한 자리<br><br><h3></h3>');
    });


    // 시간 선택
    function selectSeatTime() {
        let selectSeatTime = document.getElementById("selectSeatTime");
        let selectSeatTimeText = selectSeatTime.options[selectSeatTime.selectedIndex].text;
        let selectSeatTimeValue = selectSeatTime.value;

        document.querySelector("#selectedTime").innerHTML = "이용할 시간<br><br><h3>" + selectSeatTimeText + "</h3>";
        calculate($('#selectSeatTime').val(), $('#timePerPrice').val());
    }


    // 숫자로 변경후 합산
    function calculate(time, money) {
        let selectedTotalValue = numberWithCommas(parseInt(time) * parseInt(money));
        document.querySelector("#selectedTotalPay").innerHTML = "결제 금액<br><br><h3>" + selectedTotalValue + "원</h3>";
    }


    // 금액에 콤마 추가
    function numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
// }
