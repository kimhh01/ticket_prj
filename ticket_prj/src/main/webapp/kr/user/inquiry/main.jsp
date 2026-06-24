<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>고객센터 | BallPick</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kr/user/inquiry/inquiry.css">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />

<main class="inquiry-page">
    <section class="inquiry-shell">
        <h1 class="inquiry-title">고객님, 무엇을 도와드릴까요?</h1>
        <div class="faq-search">
            <label class="sr-only" for="faqSearch">FAQ 검색</label>
            <input type="search" id="faqSearch" placeholder="궁금한 내용을 검색해 주세요.">
            <button type="button" id="faqSearchButton" aria-label="FAQ 검색">검색</button>
        </div>

        <section class="inquiry-section" aria-labelledby="faqTitle">
            <div class="inquiry-section-head"><h2 id="faqTitle">자주 묻는 질문</h2></div>
            <div class="faq-categories" role="group" aria-label="FAQ 카테고리">
                <button class="faq-category active" type="button" data-category="all">전체</button>
                <button class="faq-category" type="button" data-category="reservation">예매</button>
                <button class="faq-category" type="button" data-category="payment">결제</button>
                <button class="faq-category" type="button" data-category="member">회원</button>
                <button class="faq-category" type="button" data-category="refund">취소/환불</button>
                <button class="faq-category" type="button" data-category="etc">기타</button>
            </div>

            <div class="faq-list" id="faqList">
                <article class="faq-item" data-category="reservation">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>경기 예매는 언제부터 가능한가요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>경기별 예매 오픈 일정은 구단과 경기 일정에 따라 다르며, 메인 및 구단 예매 화면에서 확인할 수 있습니다.</p></div>
                </article>
                <article class="faq-item" data-category="reservation">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>예매한 좌석은 어디에서 확인하나요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>상단의 예매확인/취소 메뉴에서 경기, 좌석, 결제 정보를 확인할 수 있습니다.</p></div>
                </article>
                <article class="faq-item" data-category="payment">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>어떤 결제수단을 사용할 수 있나요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>카드 및 간편결제 지원 범위는 결제 단계에서 안내하며, 실제 결제 연동 후 확정됩니다.</p></div>
                </article>
                <article class="faq-item" data-category="payment">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>결제 중 오류가 발생했어요.</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>중복 결제를 피하기 위해 예매내역을 먼저 확인한 뒤 결제수단과 네트워크 상태를 점검해 주세요.</p></div>
                </article>
                <article class="faq-item" data-category="member">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>회원코드를 잊어버렸어요.</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>로그인 화면의 회원코드 찾기에서 가입 시 등록한 이름과 이메일로 확인할 수 있습니다.</p></div>
                </article>
                <article class="faq-item" data-category="member">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>회원정보는 어디에서 수정하나요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>마이페이지의 회원정보수정 메뉴에서 본인 확인 후 변경할 수 있습니다.</p></div>
                </article>
                <article class="faq-item" data-category="refund">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>예매 취소는 어떻게 하나요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>예매확인/취소에서 대상 예매를 선택하면 취소 가능 여부와 수수료를 확인할 수 있습니다.</p></div>
                </article>
                <article class="faq-item" data-category="refund">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>취소 후 환불은 언제 처리되나요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>환불 시점은 결제수단에 따라 다르며 카드사 또는 결제사 영업일 기준으로 처리됩니다.</p></div>
                </article>
                <article class="faq-item" data-category="etc">
                    <button class="faq-question" type="button" aria-expanded="false"><span class="faq-mark">Q</span>경기 일정이 변경되면 어떻게 알 수 있나요?</button>
                    <div class="faq-answer"><span class="faq-mark">A</span><p>일정 변경 사항은 메인 경기 정보와 관련 공지에서 확인할 수 있습니다.</p></div>
                </article>
            </div>
            <p class="faq-empty" id="faqEmpty" hidden="hidden">검색 결과가 없습니다.</p>
        </section>

        <section class="inquiry-section" aria-labelledby="helpTitle">
            <div class="inquiry-section-head"><h2 id="helpTitle">다른 도움이 필요하신가요?</h2></div>
            <div class="inquiry-help-links">
                <a href="<%=request.getContextPath()%>/user-inquiry/write"><strong>1:1 문의하기</strong><span>궁금한 내용을 남겨 주세요.</span><b aria-hidden="true">›</b></a>
                <a href="<%=request.getContextPath()%>/user-inquiry/list"><strong>내 문의내역</strong><span>문의 처리 상태를 확인하세요.</span><b aria-hidden="true">›</b></a>
            </div>
        </section>

        <section class="inquiry-section inquiry-contact" aria-labelledby="contactTitle">
            <div><h2 id="contactTitle">고객센터 안내</h2><p>평일 09:00~18:00 · 주말 및 공휴일 휴무</p></div>
            <strong>1588-0000</strong>
        </section>
    </section>
</main>

<jsp:include page="/fragment/footer.jsp" />
<script>
(function() {
    const searchInput = document.getElementById("faqSearch");
    const searchButton = document.getElementById("faqSearchButton");
    const categories = Array.from(document.querySelectorAll(".faq-category"));
    const items = Array.from(document.querySelectorAll(".faq-item"));
    const empty = document.getElementById("faqEmpty");
    let activeCategory = "all";

    function closeAll() {
        items.forEach(function(item) {
            item.classList.remove("open");
            item.querySelector(".faq-question").setAttribute("aria-expanded", "false");
        });
    }

    function filterFaq() {
        const keyword = searchInput.value.trim().toLowerCase();
        let visibleCount = 0;
        closeAll();
        items.forEach(function(item) {
            const categoryMatch = activeCategory === "all" || item.dataset.category === activeCategory;
            const keywordMatch = keyword === "" || item.textContent.toLowerCase().includes(keyword);
            const visible = categoryMatch && keywordMatch;
            item.hidden = !visible;
            if (visible) visibleCount += 1;
        });
        empty.hidden = visibleCount !== 0;
    }

    categories.forEach(function(button) {
        button.addEventListener("click", function() {
            categories.forEach(function(category) { category.classList.remove("active"); });
            button.classList.add("active");
            activeCategory = button.dataset.category;
            filterFaq();
        });
    });

    items.forEach(function(item) {
        const question = item.querySelector(".faq-question");
        question.addEventListener("click", function() {
            const willOpen = !item.classList.contains("open");
            closeAll();
            if (willOpen) {
                item.classList.add("open");
                question.setAttribute("aria-expanded", "true");
            }
        });
    });

    searchButton.addEventListener("click", filterFaq);
    searchInput.addEventListener("input", filterFaq);
})();
</script>
</body>
</html>
