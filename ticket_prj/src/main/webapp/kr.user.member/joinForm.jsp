<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원가입 정보입력 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr.user.member/member.css">
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
<jsp:include page="/include/header.jsp" />

<main class="member-page">
    <section class="member-shell">
        <h1 class="member-title">회원가입</h1>
        <div class="member-steps" aria-label="회원가입 단계">
            <div class="member-step"><span class="member-step-number">1</span>약관동의</div>
            <div class="member-step active"><span class="member-step-number">2</span>정보입력</div>
            <div class="member-step"><span class="member-step-number">3</span>가입완료</div>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="member-error"><%=request.getAttribute("errorMessage")%></div>
        <% } %>

        <form id="joinForm" method="post"
              action="<%=request.getContextPath()%>/member/join-complete" novalidate>
            <input type="hidden" name="smsReceiveYN" value="${smsReceiveYN}">
            <input type="hidden" name="emailReceiveYN" value="${emailReceiveYN}">
            <input type="hidden" id="codeChecked" value="N">

            <table class="member-table">
                <tbody>
                    <tr>
                        <th><label for="memberCode">회원코드 <span class="member-required">*</span></label></th>
                        <td>
                            <div class="member-inline">
                                <input class="member-input" type="text" id="memberCode" name="memberCode"
                                       maxlength="20" autocomplete="username" placeholder="영문 또는 숫자 4~20자">
                                <button class="member-button member-button-light member-button-small"
                                        type="button" id="duplicateButton">중복확인</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="password">비밀번호 <span class="member-required">*</span></label></th>
                        <td>
                            <input class="member-input" type="password" id="password" name="password"
                                   maxlength="20" autocomplete="new-password">
                            <p class="member-help">영문, 숫자, 특수문자를 포함한 8~20자</p>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="passwordConfirm">비밀번호 확인 <span class="member-required">*</span></label></th>
                        <td><input class="member-input" type="password" id="passwordConfirm" name="passwordConfirm"
                                   maxlength="20" autocomplete="new-password"></td>
                    </tr>
                    <tr>
                        <th><label for="name">이름 <span class="member-required">*</span></label></th>
                        <td><input class="member-input" type="text" id="name" name="name"
                                   maxlength="30" autocomplete="name"></td>
                    </tr>
                    <tr>
                        <th><label for="email">이메일 <span class="member-required">*</span></label></th>
                        <td><input class="member-input" type="email" id="email" name="email"
                                   maxlength="100" autocomplete="email" placeholder="example@ballpick.com"></td>
                    </tr>
                    <tr>
                        <th><span class="member-label">휴대폰 <span class="member-required">*</span></span></th>
                        <td>
                            <div class="member-phone">
                                <select class="member-select" id="phone1" name="phone1" aria-label="휴대폰 앞자리">
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                    <option value="016">016</option>
                                    <option value="017">017</option>
                                    <option value="019">019</option>
                                </select>
                                <input class="member-input" type="text" id="phone2" name="phone2"
                                       maxlength="4" inputmode="numeric" aria-label="휴대폰 중간자리">
                                <input class="member-input" type="text" id="phone3" name="phone3"
                                       maxlength="4" inputmode="numeric" aria-label="휴대폰 끝자리">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="member-label">주소 <span class="member-required">*</span></span></th>
                        <td>
                            <div class="member-address">
                                <div class="member-address-top">
                                    <input class="member-input" type="text" id="zipcode" name="zipcode"
                                           readonly placeholder="우편번호">
                                    <button class="member-button member-button-light member-button-small"
                                            type="button" id="postcodeButton">주소검색</button>
                                </div>
                                <input class="member-input" type="text" id="address" name="address"
                                       readonly placeholder="기본주소">
                                <input class="member-input" type="text" id="address2" name="address2"
                                       maxlength="100" placeholder="상세주소">
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <div id="clientError" class="member-error" hidden></div>
            <div class="member-actions">
                <a class="member-button member-button-light"
                   href="<%=request.getContextPath()%>/member/join-agree">이전</a>
                <button class="member-button" type="submit">가입하기</button>
            </div>
        </form>
    </section>
</main>

<jsp:include page="/include/footer.jsp" />
<script>
const codeInput = document.getElementById("memberCode");
const codeChecked = document.getElementById("codeChecked");
const codePattern = /^[A-Za-z0-9]{4,20}$/;
const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,20}$/;
const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

codeInput.addEventListener("input", function() {
    codeChecked.value = "N";
});

document.getElementById("duplicateButton").addEventListener("click", function() {
    const memberCode = document.getElementById("memberCode").value.trim();

    codeChecked.value = "N";

    if (memberCode === "") {
        alert("회원코드를 입력해 주세요.");
        return;
    }

    const idPattern = /^[A-Za-z0-9]{4,20}$/;

    if (!idPattern.test(memberCode)) {
        alert("회원코드는 영문 또는 숫자 4~20자로 입력해 주세요.");
        return;
    }

    fetch("${pageContext.request.contextPath}/member/check-id?memberCode=" + encodeURIComponent(memberCode))
        .then(function(response) {
            return response.text();
        })
        .then(function(result) {
            if (result === "INVALID") {
                codeChecked.value = "N";
                alert("회원코드는 영문 또는 숫자 4~20자로 입력해 주세요.");
            } else if (result === "DUPLICATE") {
                codeChecked.value = "N";
                alert("이미 사용 중인 아이디입니다.");
            } else if (result === "AVAILABLE") {
                codeChecked.value = "Y";
                alert("사용 가능한 아이디입니다.");
            } else {
                codeChecked.value = "N";
                alert("중복확인 중 오류가 발생했습니다.");
            }
        })
        .catch(function() {
            codeChecked.value = "N";
            alert("중복확인 요청에 실패했습니다.");
        });
});

document.getElementById("postcodeButton").addEventListener("click", function() {
    if (!window.daum || !window.daum.Postcode) {
        alert("주소 검색 서비스를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");
        return;
    }
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById("zipcode").value = data.zonecode;
            document.getElementById("address").value = data.roadAddress || data.jibunAddress;
            document.getElementById("address2").focus();
        }
    }).open();
});

document.getElementById("joinForm").addEventListener("submit", function(event) {
    const password = document.getElementById("password").value;
    const passwordConfirm = document.getElementById("passwordConfirm").value;
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone2 = document.getElementById("phone2").value.trim();
    const phone3 = document.getElementById("phone3").value.trim();
    const zipcode = document.getElementById("zipcode").value.trim();
    const address = document.getElementById("address").value.trim();
    const address2 = document.getElementById("address2").value.trim();
    const error = document.getElementById("clientError");
    let message = "";

    if (!codePattern.test(codeInput.value.trim())) {
        message = "회원코드는 영문 또는 숫자 4~20자로 입력해 주세요.";
    } else if (codeChecked.value !== "Y") {
        message = "회원코드 중복확인을 진행해 주세요.";
    } else if (!passwordPattern.test(password)) {
        message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~20자로 입력해 주세요.";
    } else if (password !== passwordConfirm) {
        message = "비밀번호가 일치하지 않습니다.";
    } else if (name === "") {
        message = "이름을 입력해 주세요.";
    } else if (!emailPattern.test(email)) {
        message = "올바른 이메일을 입력해 주세요.";
    } else if (!/^\d{3,4}$/.test(phone2) || !/^\d{4}$/.test(phone3)) {
        message = "휴대폰 번호를 확인해 주세요.";
    } else if (zipcode === "" || address === "" || address2 === "") {
        message = "주소와 상세주소를 입력해 주세요.";
    }

    if (message !== "") {
        event.preventDefault();
        error.textContent = message;
        error.hidden = false;
    }
});
</script>
</body>
</html>
