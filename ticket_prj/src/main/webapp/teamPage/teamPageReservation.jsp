<%@page import="java.util.List"%>
<%@page import="user_Team.TeamDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
<%
List<TeamDTO> list=(List<TeamDTO>)request.getAttribute("gameList");
TeamDTO tDTO=new TeamDTO();
%>
<%
//확인용 JSP


SimpleDateFormat output = new SimpleDateFormat("MM.dd");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>예매하기</title>
<style>
    /* 기본 초기화 */
    * {
        margin: 0; padding: 0; box-sizing: border-box;
        text-decoration: none; list-style: none; color: #333;
        font-family: 'Noto Sans KR', sans-serif;
    }
    
    .header-wrap { border-bottom: 1px solid #e0e0e0; background-color: #fff; }
    .header-top { background-color: #f9f9f9; border-bottom: 1px solid #f0f0f0; }
    .header-top-inner { max-width: 1200px; margin: 0 auto; display: flex; justify-content: flex-end; padding: 8px 20px; font-size: 12px; }
    .header-top-inner a { color: #666; margin-left: 20px; }
    .header-top-inner a:hover { color: #000; }
    .header-main { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 25px 20px; }
    .logo a { font-size: 28px; font-weight: 900; color: #e50020; letter-spacing: -1px; }
    .search-box { display: flex; align-items: center; border: 1px solid #ccc; border-radius: 30px; padding: 8px 20px; width: 300px; }
    .search-box input { border: none; outline: none; width: 100%; font-size: 14px; }
    .search-box button { background: none; border: none; cursor: pointer; font-size: 16px; color: #999; }
    .header-nav { max-width: 1200px; margin: 0 auto; display: flex; justify-content: center; gap: 25px; padding: 15px 20px; }
    .header-nav a { font-size: 14px; font-weight: 500; transition: color 0.2s ease-in-out; }
    
    /* 호버 시 빨간색 변경 기능 */
    .header-nav a:hover { color: #e50020; font-weight: bold; }
    
    
    /* ==============경기리스트=================*/
    .game-wrap{
    width:1200px;
    margin:auto;
    background:white;
    border:1px solid #ddd;
    border-top:none;
}

.game-row{
    display:flex;
    align-items:center;
    padding:30px;
    border-bottom:1px solid #eee;
}

.game-date{
    width:150px;
    font-size:40px;
    color:#555;
}

.game-team{
    width:250px;
    font-size:22px;
    font-weight:bold;
}

.game-title{
    width:300px;
}

.game-place{
    width:150px;
}

.game-btn{
    margin-left:auto;
}

.reserve-btn{
    width:150px;
    height:50px;
    border:none;
    background:#ff3d3d;
    color:white;
    font-size:18px;
    border-radius:5px;
    cursor:pointer;
}
    
    
    .team-info{
    background:black;
    color:white;
}

.team-inner{
    width:1200px;
    margin:auto;
    display:flex;
    align-items:center;
    padding:30px;
}

.team-logo{
    width:120px;
    height:120px;
    background:white;
    border-radius:50%;
}

.team-name{
    margin-left:30px;
}

.team-name h1{
    margin-bottom:15px;
}

.team-btns button{
    width:120px;
    height:40px;
    margin-right:10px;
    border:none;
    border-radius:5px;
    cursor:pointer;
}

/* ================= 공지 ================= */

.notice-box{
    width:1200px;
    margin:auto;
    background:white;
    display:flex;
    border:1px solid #ddd;
}

.notice-title{
    width:180px;
    display:flex;
    justify-content:center;
    align-items:center;
    font-size:28px;
    font-weight:bold;
    border-right:1px solid #ddd;
}

.notice-content{
    flex:1;
    padding:20px;
}

.notice-content li{
    margin-bottom:10px;
    color:#555;
}

/* ================= 탭 ================= */

.tabs{
    width:1200px;
    margin:30px auto 0;
    display:flex;
}

.tab{
    flex:1;
    text-align:center;
    padding:20px;
    background:white;
    border:1px solid #ddd;
    font-size:24px;
}

.tab.active{
    font-weight:bold;
}

/* ================= 경기 리스트 ================= */

.game-wrap{
    width:1200px;
    margin:auto;
    background:white;
    border:1px solid #ddd;
    border-top:none;
}

.game-row{
    display:flex;
    align-items:center;
    padding:30px;
    border-bottom:1px solid #eee;
}

.game-date{
    width:150px;
    font-size:40px;
    color:#555;
}

.game-team{
    width:250px;
    font-size:22px;
    font-weight:bold;
}

.game-title{
    width:300px;
}

.game-place{
    width:150px;
}

.game-btn{
    margin-left:auto;
}

.reserve-btn{
    width:150px;
    height:50px;
    border:none;
    background:#ff3d3d;
    color:white;
    font-size:18px;
    border-radius:5px;
    cursor:pointer;
}
    
    /* footer 하단부 CSS*/
    .footer-wrap { background-color: #f8f9fa; border-top: 1px solid #e9ecef; padding: 40px 0; margin-top: 50px; }
    .footer-inner { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; padding: 0 20px; }
    .footer-info { font-size: 12px; color: #777; line-height: 1.8; }
    .footer-info .company-name { font-weight: bold; color: #555; margin-bottom: 10px; display: block; }
    .footer-social { display: flex; gap: 10px; }
    .social-btn { width: 32px; height: 32px; background-color: #dcdcdc; border-radius: 50%; display: inline-block; }
    
</style>
</head>
<body>
<header class="header-wrap">
    <div class="header-top">
        <div class="header-top-inner">
            <a href="#login">로그인</a>
            <a href="#ticket">예매확인/취소</a>
            <a href="#join">회원가입</a>
            <a href="#cs">고객센터</a>
        </div>
    </div>
    <div class="header-main">
        <div class="logo"><a href="/">BallPick⚾</a></div>
        <div class="search-box">
            <input type="text" placeholder="검색어를 입력해 주세요.">
            <button type="button">🔍</button>
        </div>
    </div>
    <nav class="header-nav">
        <a href="/lg">LG트윈스</a>
        <a href="/hanwha">한화이글스</a>
        <a href="/samsung">삼성 라이온즈</a>
        <a href="/kt">kt wiz</a>
        <a href="/kia">KIA 타이거즈</a>
        <a href="/nc">NC 다이노스</a>
        <a href="/ssg">SSG 랜더스</a>
        <a href="/doosan">두산 베어스</a>
        <a href="/kiwoom">키움 히어로즈</a>
        <a href="/event">이벤트</a>
    </nav>
</header>
<section class="team-info">

    <div class="team-inner">

        <div class="team-logo"><img src="<%=tDTO.getTeamHomeImg()%>>"></div>

        <div class="team-name">

            <h1>LG트윈스</h1>

            <div class="team-btns">
                <button>클린예매</button>
                <button>취소표대기</button>
                <button>예매가이드</button>
                <button>구단소개</button>
            </div>

        </div>

    </div>

</section>

<section class="notice-box">

    <div class="notice-title">
        공지사항
    </div>

    <div class="notice-content">
        <ul>
            <li>보안문자 입력 후 예매 가능합니다.</li>
            <li>36개월 이상 어린이는 티켓 구매 후 입장 가능합니다.</li>
            <li>권종 선택 실수로 인한 환불은 불가합니다.</li>
            <li>경기 시작 4시간 전부터 취소 불가입니다.</li>
        </ul>
    </div>

</section>

<div class="tabs">
    <div class="tab active">예매하기</div>
    <div class="tab">공지사항</div>
    <div class="tab">2026 정규리그 안내</div>
</div>

<section class="game-wrap">
	<%
	
	if(list != null){
	    for(TeamDTO tDTO1 : list){%>
    <div class="game-row">

        <div class="game-date">
           <%= output.format(tDTO1.getGameDate()) %>
        </div>

        <div class="game-team">
        	<img src="<%=tDTO1.getTeamHomeImg()%>>"> VS <img src="<%=tDTO1.getTeamOtherImg()%>>"><br>
           <%=tDTO1.getTeamHomeName()%> VS <%=tDTO1.getTeamOtherName()%>
        </div>

        <div class="game-title">
            <%=tDTO1.getStadiumName() %>
        </div>

        <div class="game-btn">
            <button class="reserve-btn">예매하기</button>
        </div>

    </div>
    <%}}//end for %>

   

</section>


<footer class="footer-wrap">
    <div class="footer-inner">
        <div class="footer-info">
            <span class="company-name">(주)볼픽코퍼레이션</span>
            <p>주소: 서울특별시 강남구 테헤란로 000, 0층 (볼픽빌딩) | 대표이사: 홍길동 | 사업자등록번호: 000-00-00000</p>
            <p>대표전화: 1588-0000 | 이메일: cs@ballpick.com | 통신판매업 신고번호: 제 2026-서울강남-0000호</p>
            <p style="margin-top: 15px; color: #aaa;">Copyright © BALLPICK Corporation. All rights reserved.</p>
        </div>
        <div class="footer-social">
            <a href="#" class="social-btn"></a>
            <a href="#" class="social-btn"></a>
            <a href="#" class="social-btn"></a>
            <a href="#" class="social-btn"></a>
        </div>
    </div>
</footer>
</body>