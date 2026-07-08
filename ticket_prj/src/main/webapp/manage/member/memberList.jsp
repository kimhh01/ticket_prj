<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="member" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>

<style>
body {
    margin: 0;
    background: #f4f4f7;
    font-family: 'Noto Sans KR', sans-serif;
    color: #222;
}

.layout {
    display: flex;
}

.content {
    flex: 1;
    padding: 32px;
    background: #fff;
    min-height: calc(100vh - 60px);
}

.page-title {
    font-size: 35px;
    font-weight: 700;
    margin-bottom: 20px;
}

.tab-menu {
    display: flex;
    gap: 20px;
    margin-bottom: 20px;
}

.tab-menu a {
    text-decoration: none;
    color: #777;
    font-size: 14px;
    padding-bottom: 8px;
}

.tab-menu a.active {
    color: #ff5b68;
    border-bottom: 2px solid #ff5b68;
    font-weight: 700;
}

.search-area {
    display: flex;
    gap: 8px;
    margin-bottom: 18px;
}

.search-area input {
    width: 420px;
    padding: 10px 14px;
    border: 1px solid #ddd;
    border-radius: 6px;
}

.search-area button,
.search-area a {
    padding: 10px 18px;
    border: 1px solid #ddd;
    background: #fff;
    border-radius: 6px;
    cursor: pointer;
    color: #333;
    text-decoration: none;
    font-size: 13px;
}

.search-area .active {
    background: #eef5ff;
    color: #2374ff;
    border-color: #2374ff;
    font-weight: 700;
}

.member-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 14px;
}

.member-table th,
.member-table td {
    padding: 14px 12px;
    border-bottom: 1px solid #eee;
    text-align: left;
}

.member-table th {
    color: #666;
    font-weight: 600;
    background: #fafafa;
}

.member-name {
    font-weight: 700;
    color: #222;
    text-decoration: none;
}

.member-name:hover {
    color: #ff5b68;
}

.badge {
    display: inline-block;
    padding: 4px 10px;
    border-radius: 20px;
    font-size: 12px;
}

.badge.vip {
    background: #eaf2ff;
    color: #2374ff;
}

.badge.normal {
    background: #f1f1f1;
    color: #555;
}

.state-normal {
    color: #0aaf55;
    font-weight: 600;
}

.state-stop {
    color: #ff5b68;
    font-weight: 600;
}

.pagination {
    margin-top: 24px;
    text-align: center;
}

.pagination a {
    display: inline-block;
    padding: 7px 11px;
    margin: 0 2px;
    border: 1px solid #ddd;
    border-radius: 5px;
    color: #333;
    text-decoration: none;
    font-size: 13px;
}

.pagination a.active {
    background: #ff5b68;
    color: #fff;
    border-color: #ff5b68;
}

</style>
</head>

<body>

<jsp:include page="../common/topBar.jsp"/>

<div class="layout">
    <jsp:include page="../common/sideBar.jsp"/>

    <main class="content">
        <h2 class="page-title">회원 관리</h2>

        <div class="tab-menu">
            <a href="${pageContext.request.contextPath}/admin/member" class="active">회원 관리</a>
            <a href="${pageContext.request.contextPath}/admin/member/discount">회원 할인</a>
        </div>

        <form class="search-area" method="get"
              action="${pageContext.request.contextPath}/admin/member">

            <input type="text" name="search" value="${param.search}"
                   placeholder="이름, 이메일, 전화번호 검색...">

            <button type="submit">검색</button>

            <a href="${pageContext.request.contextPath}/admin/member"
               class="${empty param.gradeFilter ? 'active' : ''}">
                전체
            </a>

            <button type="submit" name="gradeFilter" value="ACTIVE"
                    class="${param.gradeFilter eq 'ACTIVE' ? 'active' : ''}">
                활성
            </button>

            <button type="submit" name="gradeFilter" value="VIP"
                    class="${param.gradeFilter eq 'VIP' ? 'active' : ''}">
                VIP
            </button>
        </form>

        <table class="member-table">
            <thead>
                <tr>
                    <th><input type="checkbox"></th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>등급</th>
                    <th>가입일</th>
                    <th>상태</th>
                </tr>
            </thead>

            <tbody>
                <c:choose>
                    <c:when test="${empty memberList}">
                        <tr>
                            <td colspan="6" style="text-align:center; padding:40px;">
                                조회된 회원이 없습니다.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="mlDTO" items="${memberList}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="memberId" value="${mlDTO.memberId}">
                                </td>

                                <td>
                                    <a class="member-name"
                                       href="${pageContext.request.contextPath}/admin/member/detail?memberId=${mlDTO.memberId}">
                                        ${mlDTO.memberName}
                                    </a>
                                    <br>
                                    <small>${mlDTO.memberTel}</small>
                                </td>

                                <td>${mlDTO.memberEmail}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${mlDTO.memberGrade eq 'VIP'}">
                                            <span class="badge vip">VIP</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge normal">일반</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>${mlDTO.joinDate}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${mlDTO.memberState eq '활성'}">
                                            <span class="state-normal">활성</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="state-stop">${mlDTO.memberState}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <div class="pagination">
            <c:if test="${brDTO.page > 1}">
                <a href="${pageContext.request.contextPath}/admin/member?page=${brDTO.page - 1}&search=${param.search}&gradeFilter=${param.gradeFilter}">
                    이전
                </a>
            </c:if>

            <c:forEach var="i" begin="1" end="${brDTO.totalPage}">
                <a href="${pageContext.request.contextPath}/admin/member?page=${i}&search=${param.search}&gradeFilter=${param.gradeFilter}"
                   class="${brDTO.page eq i ? 'active' : ''}">
                    ${i}
                </a>
            </c:forEach>

            <c:if test="${brDTO.page < brDTO.totalPage}">
                <a href="${pageContext.request.contextPath}/admin/member?page=${brDTO.page + 1}&search=${param.search}&gradeFilter=${param.gradeFilter}">
                    다음
                </a>
            </c:if>
        </div>
    </main>
</div>

</body>
</html>