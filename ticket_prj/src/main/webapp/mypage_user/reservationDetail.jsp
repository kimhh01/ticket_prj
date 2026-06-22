<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예매상세</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="myPage.css">

<style>

body{
    background:#fff;
    padding:30px;
}

/* 제목 */

.popup-title{
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding-bottom:20px;
    border-bottom:1px solid #ddd;
    margin-bottom:30px;
}

.popup-title h2{
    font-size:32px;
    font-weight:700;
    margin:0;
}

.close-btn{
    border:none;
    background:none;
    font-size:36px;
    cursor:pointer;
}

/* 섹션 */

.detail-section{
    margin-bottom:40px;
}

.detail-section h3{
    font-size:26px;
    font-weight:700;
    margin-bottom:25px;
}

/* 정보 영역 */

.info-wrap{
    display:flex;
    justify-content:space-between;
}

.info-table{
    width:48%;
}

.info-table tr{
    height:55px;
}

.info-table th{
    width:120px;
    font-weight:600;
}

.status-badge{
    display:inline-block;
    background:#ffe9ea;
    color:#ff5f8f;
    padding:6px 12px;
    border-radius:6px;
    font-size:14px;
    font-weight:600;
}

/* 티켓 테이블 */

.ticket-table{
    width:100%;
    border-collapse:collapse;
}

.ticket-table th{
    background:#f5f5f5;
    height:55px;
    text-align:center;
    border:1px solid #ddd;
}

.ticket-table td{
    height:55px;
    text-align:center;
    border:1px solid #ddd;
}

/* 환불 안내 */

.notice-box{
    border:1px solid #ddd;
    border-radius:8px;
    padding:25px;
    background:#fafafa;
}

.notice-box ul{
    margin:0;
}

.notice-box li{
    margin-bottom:10px;
}

/* 하단 버튼 */

.bottom-btn{
    text-align:right;
    margin-top:40px;
}

.bottom-btn button{
    width:140px;
    height:50px;
    border:1px solid #ddd;
    background:#fff;
    border-radius:6px;
    font-weight:600;
}

</style>

</head>
<body>

<div class="popup-title">
    <h2>예매상세</h2>

    <button class="close-btn"
            onclick="window.close()">
        ×
    </button>
</div>

<!-- 예매정보 -->

<div class="detail-section">

    <h3>예매 정보</h3>

    <div class="info-wrap">

        <table class="info-table">
            <tr>
                <th>경기명</th>
                <td>LG VS 삼성</td>
            </tr>

            <tr>
                <th>경기일시</th>
                <td>2026.06.05 14:00</td>
            </tr>

            <tr>
                <th>경기장</th>
                <td>잠실야구장</td>
            </tr>

            <tr>
                <th>예매상태</th>
                <td>
                    <span class="status-badge">
                        예매완료
                    </span>
                </td>
            </tr>

            <tr>
                <th>예매번호</th>
                <td>B245</td>
            </tr>
        </table>

        <table class="info-table">

            <tr>
                <th>예매자</th>
                <td>김희수</td>
            </tr>

            <tr>
                <th>휴대전화</th>
                <td>010-1234-5678</td>
            </tr>

            <tr>
                <th>예매일시</th>
                <td>2026.05.28 20:30</td>
            </tr>

            <tr>
                <th>결제금액</th>
                <td>60,000원</td>
            </tr>

            <tr>
                <th>결제수단</th>
                <td>신용카드</td>
            </tr>

        </table>

    </div>

</div>

<!-- 좌석 정보 -->

<div class="detail-section">

    <h3>좌석 / 티켓 정보</h3>

    <table class="ticket-table">

        <tr>
            <th>티켓번호</th>
            <th>구역/좌석</th>
            <th>권종</th>
            <th>가격</th>
            <th>상태</th>
        </tr>

        <tr>
            <td>T-123-01</td>
            <td>1루 블루석 1층 L열 12번</td>
            <td>성인</td>
            <td>20,000원</td>
            <td>예매완료</td>
        </tr>

        <tr>
            <td>T-123-02</td>
            <td>1루 블루석 1층 L열 13번</td>
            <td>성인</td>
            <td>20,000원</td>
            <td>예매완료</td>
        </tr>

        <tr>
            <td>T-123-03</td>
            <td>1루 블루석 1층 L열 14번</td>
            <td>성인</td>
            <td>20,000원</td>
            <td>예매완료</td>
        </tr>

    </table>

</div>

<!-- 취소 환불 -->

<div class="detail-section">

    <h3>취소/환불 안내</h3>

    <div class="notice-box">

        <ul>
            <li>경기 시작 7일 전까지 : 100% 환불 (수수료 제외)</li>
            <li>경기 시작 1일 전까지 : 10% 수수료 제외 후 환불</li>
            <li>경기 당일 취소 및 환불 불가</li>
        </ul>

    </div>

</div>

<div class="bottom-btn">

    <button onclick="window.close()">
        닫기
    </button>

</div>

</body>
</html>