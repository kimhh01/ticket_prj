<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</style>

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