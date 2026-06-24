<%@page import="java.util.List"%>
<%@page import="kr.user.team.TeamDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
List<TeamDTO> list=(List<TeamDTO>)request.getAttribute("gameList");
SimpleDateFormat output = new SimpleDateFormat("MM.dd");
TeamDTO tDTO = null;

//임시 가 데이터
if(list == null){
    list = new ArrayList<TeamDTO>();

    TeamDTO game1 = new TeamDTO();
    game1.setTeamHomeName("LG 트윈스");
    game1.setTeamOtherName("두산 베어스");
    game1.setStadiumName("잠실야구장");
    game1.setTeamHomeImg("images/lg.png");
    game1.setTeamOtherImg("images/doosan.png");
    game1.setGameDate(new Date());

    TeamDTO game2 = new TeamDTO();
    game2.setTeamHomeName("한화 이글스");
    game2.setTeamOtherName("KIA 타이거즈");
    game2.setStadiumName("대전 한화생명 볼파크");
    game2.setTeamHomeImg("images/hanwha.png");
    game2.setTeamOtherImg("images/kia.png");
    game2.setGameDate(new Date());

    list.add(game1);
    list.add(game2);
}

if(list != null && !list.isEmpty()){
    tDTO = list.get(0);
}


//팀코드를 받고 그 팀코드에 따른 url을 받을 예정
String teamUrl = "";

switch(tDTO.getTeamCode()){
    case 1:
        teamUrl = "https://www.lgtwins.com";
        break;
    case 2:
        teamUrl = "https://www.doosanbears.com";
        break;
    case 3:
        teamUrl = "https://www.hanwhaeagles.co.kr";
        break;
}
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

.game-team-img{
    
}
.game-team-name{
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
    background:#000;
    border-top:3px solid #a50034;
}

.team-inner{
    width:1200px;
    margin:auto;
    display:flex;
    align-items:center;
    padding:25px 30px;
}

.team-logo{
    width:120px;
    height:120px;
    background:#fff;
    border-radius:50%;
    display:flex;
    justify-content:center;
    align-items:center;
}

.team-logo img{
    width:90px;
    height:90px;
    object-fit:contain;
}

.team-name{
    margin-left:30px;
}

.team-name h1{
    color:#fff;
    font-size:36px;
    margin-bottom:15px;
}

.team-btns button{
    width:100px;
    height:35px;
    margin-right:8px;
    border:none;
    border-radius:4px;
    background:#fff;
    font-size:12px;
    cursor:pointer;
}

/* ================= 공지 ================= */

.notice-box{
    width:1200px;
    margin:auto;
    display:flex;
    border:1px solid #ddd;
    background:#fff;
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
    padding:15px 25px;
}

.notice-content li{
    color:#d40000;
    font-size:13px;
    margin-bottom:8px;
}

/* ================= 탭 ================= */

.tabs{
    width:1200px;
    margin:30px auto 0;
    display:flex;
}

.tab{
    flex:1;
    height:60px;
    line-height:60px;
    text-align:center;
    border:1px solid #ddd;
    background:#fff;
    font-size:18px;
}

.tab.active{
    font-weight:bold;
    border-top:3px solid #333;
}

/* ================= 경기 리스트 ================= */

.game-wrap{
    width:1200px;
    margin:auto;
    background:#fff;
    border:1px solid #ddd;
    border-top:none;
}

.game-row{
    display:flex;
    align-items:center;
    height:100px;
    border-bottom:1px solid #eee;
    padding:0 20px;
}

.game-date{
    width:120px;
    text-align:center;
}

.game-date .day{
    font-size:38px;
    color:#666;
    line-height:40px;
}

.game-date .time{
    font-size:12px;
    color:#999;
}

.game-team{
    width:220px;
    text-align:center;
    font-size:14px;
}

.game-team img{
    width:45px;
    height:45px;
    object-fit:contain;
    vertical-align:middle;
}

.game-title{
    width:250px;
    font-size:15px;
    font-weight:bold;
}

.game-place{
    width:150px;
    font-size:12px;
    color:#888;
}

.game-btn{
    margin-left:auto;
}

.reserve-btn{
    width:90px;
    height:36px;
    border:none;
    border-radius:4px;
    background:#ff4040;
    color:#fff;
    font-size:12px;
    font-weight:bold;
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
<script type="text/javascript">

</script>
</head>
<body>
<jsp:include page="../pragments/header.jsp"/>
<%if(tDTO != null){ %>
<section class="team-info">

    <div class="team-inner">

        <div class="team-logo"><img src="<%=tDTO.getTeamHomeImg()%>"></div>

        <div class="team-name">

            <h1><%=tDTO.getTeamHomeName()%></h1>

            <div class="team-btns">
                <button class="cleanBtn">클린예매</button>
                <button class="cancelBtn">취소표대기</button>
                <button class="guideBtn">예매가이드</button>
                <a href="teamIntroduce.do?teamCode=<%=teamName%>"
class="team-btn">
    구단소개
</a>
            </div>

        </div>

    </div>

</section>
<%} %>

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
	if(list != null && !list.isEmpty()){
	    for(TeamDTO game : list){%>
    <div class="game-row">

        <div class="game-date">
           <%= output.format(game.getGameDate()) %>
        </div>

        <div class="game-team-img">
        	<img src="<%=game.getTeamHomeImg()%>"> VS <img src="<%=game.getTeamOtherImg()%>"><br>
        </div>
        <div class="game-team-name">
         <span><%=game.getTeamHomeName()%> VS <%=game.getTeamOtherName()%></span>
        </div>

        <div class="game-title">
            <%=game.getStadiumName() %>
        </div>

        <div class="game-btn">
            <button class="reserve-btn">예매하기</button>
        </div>

    </div>
    <%}}//end for %>

   

</section>


<footer class="footer-wrap">
   <jsp:include page="../pragments/footer.jsp"/>
</footer>
</body>