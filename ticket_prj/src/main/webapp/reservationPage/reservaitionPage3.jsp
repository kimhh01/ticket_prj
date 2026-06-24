<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>티켓링크 - 스마트 예매</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        /* 글로벌 스타일 (팀 페이지 아이덴티티 계승) */
        body { background-color: #f4f4f4; margin: 0; font-family: 'Malgun Gothic', sans-serif; user-select: none; }
        .res-container { width: 1100px; margin: 20px auto; background: #fff; border: 1px solid #ddd; min-height: 700px; display: flex; flex-direction: column; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }
        
        /* 단계바 (Ticketlink 스타일) */
        .res-step-bar { display: flex; background: #444; color: #fff; list-style: none; padding: 0; margin: 0; }
        .res-step-bar li { flex: 1; padding: 15px; text-align: center; font-size: 13px; color: #bbb; font-weight: bold; position: relative; background: #333; border-right: 1px solid #444; }
        .res-step-bar li.active { color: #fff; background: #1c1c1e; }
        .res-step-bar li.active::after { content: ''; position: absolute; bottom: 0; left: 0; width: 100%; height: 4px; background: #f1404b; }

        /* 레이아웃 구조 */
        .res-content { display: flex; flex: 1; min-height: 600px; }
        .res-main { flex: 1; padding: 25px; border-right: 1px solid #eee; position: relative; }
        .res-side { width: 320px; background: #fff; padding: 20px; display: flex; flex-direction: column; }

        /* 보안인증 (캡차) */
        .auth-overlay { position: absolute; top: 0; left: 0; width: 100%; height: 100%; background: rgba(255,255,255,0.95); z-index: 100; display: flex; align-items: center; justify-content: center; }
        .auth-box { border: 1px solid #ddd; padding: 40px; background: #fff; width: 380px; text-align: center; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .captcha-img { font-size: 32px; font-weight: 900; letter-spacing: 8px; color: #f1404b; background: #f9f9f9; padding: 20px; margin: 20px 0; border: 1px dashed #ccc; font-style: italic; }
        
        /* 사이드바 팀 정보 */
        .match-badge { display: flex; align-items: center; justify-content: center; gap: 10px; margin-bottom: 15px; border: 1px solid #eee; padding: 10px; border-radius: 5px; }
        .match-badge img { width: 40px; }
        .match-text { text-align: center; font-size: 13px; line-height: 1.4; margin-bottom: 20px; border-bottom: 1px solid #eee; padding-bottom: 15px; }

        /* 좌석 선택 리스트 */
        .seat-list { border: 1px solid #ddd; border-radius: 4px; overflow: hidden; margin-top: 20px; }
        .seat-item { padding: 12px 15px; border-bottom: 1px solid #eee; cursor: pointer; display: flex; justify-content: space-between; font-size: 13px; transition: 0.2s; }
        .seat-item:hover { background: #fff5f5; }
        .seat-item.selected { background: #f1404b; color: #fff; }
        .seat-color { width: 12px; height: 12px; display: inline-block; margin-right: 8px; border-radius: 2px; }

        /* 테이블 공통 */
        .res-table { width: 100%; border-collapse: collapse; font-size: 13px; margin-top: 10px; }
        .res-table th { background: #f8f9fa; padding: 12px; border: 1px solid #eee; text-align: left; }
        .res-table td { padding: 12px; border: 1px solid #eee; }
        .input-text { width: 100%; padding: 8px; box-sizing: border-box; border: 1px solid #ddd; outline: none; }

        /* 동의 항목 섹션 */
        .agree-item { display: flex; justify-content: space-between; align-items: center; padding: 8px 0; font-size: 12px; color: #444; border-bottom: 1px solid #f9f9f9; }
        .agree-link { color: #f1404b; text-decoration: underline; cursor: pointer; }

        /* 버튼 영역 */
        .btn-group { margin-top: auto; display: flex; gap: 8px; }
        .btn { flex: 1; padding: 15px; border: none; font-weight: bold; cursor: pointer; border-radius: 4px; font-size: 15px; }
        .btn-red { background: #f1404b; color: #fff; }
        .btn-gray { background: #e1e4e6; color: #333; }
        .hidden { display: none !important; }

        /* 합계 영수증 */
        .invoice { font-size: 13px; color: #666; margin-top: 20px; }
        .invoice-row { display: flex; justify-content: space-between; margin-bottom: 8px; }
        .total-row { border-top: 2px solid #333; padding-top: 10px; margin-top: 10px; font-size: 18px; font-weight: bold; color: #f1404b; }
    </style>
</head>
<body>

<div class="res-container">
    <!-- 단계 표시 -->
    <ul class="res-step-bar">
        <li class="active" id="step-nav-1">등급/좌석선택</li>
        <li id="step-nav-2">권종/할인/매수선택</li>
        <li id="step-nav-3">배송선택/예매확인</li>
    </ul>

    <div class="res-content">
        <!-- 좌측 메인 -->
        <div class="res-main">
            <!-- 보안문자 오버레이 -->
            <div id="auth-overlay" class="auth-overlay">
                <div class="auth-box">
                    <h3>안심예매 서비스</h3>
                    <p style="font-size:12px; color:#888;">부정 예매 방지를 위해 보안문자를 입력해주세요.</p>
                    <div class="captcha-img">KXEDV</div>
                    <input type="text" id="captcha-input" class="input-text" style="text-align:center; font-size:20px; padding:15px;" placeholder="보안문자 입력">
                    <div style="display:flex; gap:10px; margin-top:20px;">
                        <button type="button" class="btn btn-gray" onclick="location.href='teamPage.jsp'">날짜변경</button>
                        <button type="button" class="btn btn-red" onclick="validateCaptcha()">인증하기</button>
                    </div>
                </div>
            </div>

            <!-- STEP 1: 좌석 배치도 -->
            <div id="view-1">
                <h4 style="margin-top:0;">좌석 배치도</h4>
                <div style="background:#eee; height:450px; display:flex; align-items:center; justify-content:center; color:#999; border-radius:10px;">
                    <img src="stadium_map.png" style="max-width:100%;" onerror="this.src='https://via.placeholder.com/600x400?text=Stadium+Map+Layout'">
                </div>
            </div>

            <!-- STEP 2: 매수 선택 -->
            <div id="view-2" class="hidden">
                <h4 style="margin-top:0;">권종/할인/매수 선택</h4>
                <table class="res-table">
                    <thead><tr><th>권종</th><th>가격</th><th>매수선택</th></tr></thead>
                    <tbody>
                        <tr><td>성인 (정가)</td><td>16,000원</td><td><select class="qty-sel" data-price="16000"><option value="0">0</option><option value="1">1</option><option value="2">2</option></select></td></tr>
                        <tr><td>청소년</td><td>11,200원</td><td><select class="qty-sel" data-price="11200"><option value="0">0</option><option value="1">1</option></select></td></tr>
                    </tbody>
                </table>
            </div>

            <!-- STEP 3: 주문자 및 동의 -->
            <div id="view-3" class="hidden">
                <h4 style="margin-top:0;">수령방법</h4>
                <div style="padding:15px; border:1px solid #eee; margin-bottom:20px;">티켓 : <strong>현장수령</strong></div>
                
                <h4>주문자 정보</h4>
                <table class="res-table">
                    <tr><th>이름 *</th><td><input type="text" value="올라검" class="input-text"></td><th>휴대폰 *</th><td><input type="text" value="01045276188" class="input-text"></td></tr>
                    <tr><th>이메일</th><td colspan="3"><input type="text" value="minwoo7513@naver.com" class="input-text"></td></tr>
                </table>

                <h4 style="margin-top:30px;">예매자 확인</h4>
                <div class="agree-container">
                    <div class="agree-item"><div><input type="checkbox" class="chk-essential"> 개인정보 수집·이용 동의</div> <span class="agree-link">[상세보기]</span></div>
                    <div class="agree-item"><div><input type="checkbox"> (선택) 예매 안내 메일 수신</div> <span class="agree-link">[상세보기]</span></div>
                    <div class="agree-item"><div><input type="checkbox" class="chk-essential"> 개인정보 제3자 제공 동의</div> <span class="agree-link">[상세보기]</span></div>
                    <div class="agree-item"><div><input type="checkbox" class="chk-essential"> KBO리그 SAFE캠페인 동의</div> <span class="agree-link">[상세보기]</span></div>
                    <div class="agree-item"><div><input type="checkbox" class="chk-essential"> 암표매매 제재 동의</div> <span class="agree-link">[상세보기]</span></div>
                </div>
            </div>
        </div>

        <!-- 우측 사이드바 -->
        <div class="res-side">
            <div class="match-badge">
                <img src="lotte.png" onerror="this.src='https://via.placeholder.com/35?text=LT'">
                <span style="font-weight:bold; color:#999;">VS</span>
                <img src="kia.png" onerror="this.src='https://via.placeholder.com/35?text=KA'">
            </div>
            <div class="match-text">
                <strong>롯데 vs KIA</strong><br>
                KIA 챔피언스필드<br>
                2026.06.02(화) 18:30
            </div>

            <div id="side-step-1">
                <span style="font-size:12px; font-weight:bold;">등급 선택</span>
                <div class="seat-list">
                    <div class="seat-item" onclick="selectSeat(this, '1루 K9', '112블록 11열 7번')">
                        <span><span class="seat-color" style="background:#1d70e2;"></span>1루 K9</span>
                        <strong>1석</strong>
                    </div>
                    <div class="seat-item" onclick="selectSeat(this, '3루 K7', '120블록 5열 2번')">
                        <span><span class="seat-color" style="background:#be2a2b;"></span>3루 K7</span>
                        <strong>5석</strong>
                    </div>
                </div>
            </div>

            <div id="side-info" class="hidden">
                <div style="background:#f9f9f9; padding:15px; border-radius:5px; font-size:13px; line-height:1.6;">
                    <strong>예매정보</strong><br>
                    <span id="res-grade" style="color:#f1404b; font-weight:bold;">미선택</span><br>
                    <span id="res-detail">좌석을 선택하세요</span>
                </div>
                
                <div class="invoice">
                    <div class="invoice-row"><span>티켓금액</span><span id="inv-price">0원</span></div>
                    <div class="invoice-row"><span>예매수수료</span><span>0원</span></div>
                    <div class="total-row"><span>총 결제</span><span id="inv-total">0원</span></div>
                </div>

                <!-- 취소 동의 (3단계 노출) -->
                <div id="cancel-agree-box" class="hidden" style="margin-top:15px; font-size:11px;">
                    <input type="checkbox" id="chk-cancel"> <label for="chk-cancel">취소기한 및 수수료 동의 (필수)</label>
                </div>

                <div style="margin-top:20px;">
                    <span style="font-size:12px; font-weight:bold; color:#666;">결제수단 선택</span>
                    <div style="margin-top:10px; font-size:12px;">
                        <input type="radio" name="pay" id="toss" checked> <label for="toss"><strong>toss</strong> 가상계좌</label>
                    </div>
                </div>
            </div>

            <div class="btn-group" id="btns-1">
                <button class="btn btn-red" onclick="goStep(2)">다음단계</button>
            </div>
            <div class="btn-group hidden" id="btns-2">
                <button class="btn btn-gray" onclick="goStep(1)">이전단계</button>
                <button class="btn btn-red" onclick="goStep(3)">다음단계</button>
            </div>
            <div class="btn-group hidden" id="btns-3">
                <button class="btn btn-gray" onclick="goStep(2)">이전단계</button>
                <button class="btn btn-red" style="background:#e51a24;" onclick="finalPay()">결제하기</button>
            </div>
        </div>
    </div>
</div>

<script src="https://js.tosspayments.com/v1/payment"></script>
<script>
    let selectedGrade = "";
    let finalAmount = 0;

    // 1. 보안문자 인증
    function validateCaptcha() {
        if($("#captcha-input").val().toUpperCase() === "KXEDV") {
            $("#auth-overlay").fadeOut();
        } else {
            alert("보안문자가 틀렸습니다.");
        }
    }

    // 2. 좌석 선택 (글자 클릭 시 지정)
    function selectSeat(el, grade, detail) {
        $(".seat-item").removeClass("selected");
        $(el).addClass("selected");
        selectedGrade = grade;
        $("#res-grade").text(grade);
        $("#res-detail").text(detail);
        $("#side-info").removeClass("hidden");
    }

    // 3. 단계 이동 (버튼 중복 오류 해결)
    function goStep(n) {
        if(n === 2 && selectedGrade === "") { alert("좌석을 먼저 선택해주세요."); return; }
        
        $(".res-step-bar li").removeClass("active");
        $("#step-nav-" + n).addClass("active");
        
        // 메인 뷰 전환
        $("#view-1, #view-2, #view-3").addClass("hidden");
        $("#view-" + n).removeClass("hidden");
        
        // 버튼 그룹 전환 (기존 오류 수정)
        $("#btns-1, #btns-2, #btns-3").addClass("hidden");
        $("#btns-" + n).removeClass("hidden");

        // 사이드바 전용 처리
        if(n >= 2) {
            $("#side-step-1").addClass("hidden");
            $("#side-info").removeClass("hidden");
        } else {
            $("#side-step-1").removeClass("hidden");
        }

        if(n === 3) $("#cancel-agree-box").removeClass("hidden");
        else $("#cancel-agree-box").addClass("hidden");
    }

    // 4. 금액 계산
    $(".qty-sel").change(function(){
        let total = 0;
        $(".qty-sel").each(function(){
            total += (parseInt($(this).val()) * parseInt($(this).data("price")));
        });
        finalAmount = total;
        $("#inv-price, #inv-total").text(total.toLocaleString() + "원");
    });

    // 5. 최종 결제 (토스 가상계좌)
    function finalPay() {
        let allChecked = true;
        $(".chk-essential").each(function(){ if(!$(this).is(":checked")) allChecked = false; });
        if(!$("#chk-cancel").is(":checked")) { alert("취소 수수료 동의가 필요합니다."); return; }
        if(!allChecked) { alert("필수 약관에 모두 동의해주세요."); return; }

        const tossPayments = TossPayments('test_ck_D5371W6k69VvAn9lAnG3VN0M7XBa');
        tossPayments.requestPayment('가상계좌', {
            amount: finalAmount,
            orderId: 'ORDER-' + new Date().getTime(),
            orderName: '야구 티켓 예매 - ' + selectedGrade,
            customerName: '올라검',
            successUrl: window.location.origin + '/success',
            failUrl: window.location.origin + '/fail',
        });
    }
</script>
</body>
</html>