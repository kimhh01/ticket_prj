from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

import pandas as pd
import oracledb
from datetime import datetime


# =========================
# Oracle DB 설정
# =========================
DB_USER = "team1"
DB_PASSWORD = "tiger"

# 예: localhost:1521/xe
DB_DSN = "211.63.89.137:1521/orcl"


# =========================
# KBO 팀명과 DB TEAM_ID 매핑
# 반드시 본인 TEAM 테이블 기준으로 수정
# =========================
TEAM_ID_MAP = {
    "LG": 1,
    "두산": 2,
    "한화": 3,
    "롯데": 4,
    "KIA": 5,
    "NC": 6,
    "SSG": 7,
    "삼성": 8,
    "키움": 9,
    "KT": 10
}


def to_int(value):
    value = str(value).strip().replace(",", "")

    if value == "" or value == "-":
        return 0

    return int(float(value))


def to_float(value):
    value = str(value).strip().replace(",", "")

    if value == "" or value == "-":
        return 0.0

    return float(value)


def crawl_kbo_team_record():
    options = Options()

    # 작업 스케줄러에서 실행할 때는 headless 권장
    options.add_argument("--headless=new")
    options.add_argument("--window-size=1920,1080")
    options.add_argument("--disable-gpu")

    driver = webdriver.Chrome(options=options)

    try:
        url = "https://www.koreabaseball.com/Record/TeamRank/TeamRankDaily.aspx"
        driver.get(url)

        wait = WebDriverWait(driver, 10)

        table = wait.until(
            EC.presence_of_element_located(
                (By.CSS_SELECTOR, "table.tData")
            )
        )

        headers = []
        th_list = table.find_elements(By.CSS_SELECTOR, "thead th")

        for th in th_list:
            headers.append(th.text.strip())

        rows_data = []
        rows = table.find_elements(By.CSS_SELECTOR, "tbody tr")

        for row in rows:
            cols = row.find_elements(By.TAG_NAME, "td")

            row_data = []

            for col in cols:
                row_data.append(col.text.strip())

            if row_data:
                rows_data.append(row_data)

        df = pd.DataFrame(rows_data, columns=headers)

        return df

    finally:
        driver.quit()


def convert_to_game_record_list(df):
    game_record_list = []

    for _, row in df.iterrows():
        team_name = row["팀명"].strip()

        if team_name not in TEAM_ID_MAP:
            raise Exception(f"TEAM_ID_MAP에 등록되지 않은 팀명입니다: {team_name}")

        game_record = {
            "team_id": TEAM_ID_MAP[team_name],
            "game_cnt": to_int(row["경기"]),
            "win_cnt": to_int(row["승"]),
            "lose_cnt": to_int(row["패"]),
            "draw_cnt": to_int(row["무"]),
            "win_rate": to_float(row["승률"]),
            "score_gap": to_float(row["게임차"])
        }

        game_record_list.append(game_record)

    return game_record_list


def save_game_record_to_db(game_record_list):
    conn = None

    try:
        conn = oracledb.connect(
            user=DB_USER,
            password=DB_PASSWORD,
            dsn=DB_DSN
        )

        cursor = conn.cursor()

        sql = """
            MERGE INTO GAME_RECORD GR
            USING (
                SELECT
                    :team_id AS TEAM_ID,
                    :game_cnt AS GAME_CNT,
                    :win_cnt AS WIN_CNT,
                    :lose_cnt AS LOSE_CNT,
                    :draw_cnt AS DRAW_CNT,
                    :win_rate AS WIN_RATE,
                    :score_gap AS SCORE_GAP
                FROM DUAL
            ) SRC
            ON (
                GR.TEAM_ID = SRC.TEAM_ID
            )
            WHEN MATCHED THEN
                UPDATE SET
                    GR.GAME_CNT = SRC.GAME_CNT,
                    GR.WIN_CNT = SRC.WIN_CNT,
                    GR.LOSE_CNT = SRC.LOSE_CNT,
                    GR.DRAW_CNT = SRC.DRAW_CNT,
                    GR.WIN_RATE = SRC.WIN_RATE,
                    GR.SCORE_GAP = SRC.SCORE_GAP,
                    GR.RECORD_DATE = SYSDATE
            WHEN NOT MATCHED THEN
                INSERT (
                    GAME_RECORD_ID,
                    TEAM_ID,
                    GAME_CNT,
                    WIN_CNT,
                    LOSE_CNT,
                    DRAW_CNT,
                    WIN_RATE,
                    SCORE_GAP,
                    RECORD_DATE
                )
                VALUES (
                    GAME_RECORD_SEQ.NEXTVAL,
                    SRC.TEAM_ID,
                    SRC.GAME_CNT,
                    SRC.WIN_CNT,
                    SRC.LOSE_CNT,
                    SRC.DRAW_CNT,
                    SRC.WIN_RATE,
                    SRC.SCORE_GAP,
                    SYSDATE
                )
        """

        cursor.executemany(sql, game_record_list)

        conn.commit()

        print(f"[{datetime.now()}] GAME_RECORD DB 반영 완료")
        print(f"반영 건수: {len(game_record_list)}")

    except Exception as e:
        if conn:
            conn.rollback()

        print("DB 저장 중 오류 발생:", e)
        raise e

    finally:
        if conn:
            conn.close()


def main():
    print(f"[{datetime.now()}] KBO 팀 기록 크롤링 시작")

    df = crawl_kbo_team_record()

    print("크롤링 결과")
    print(df)

    game_record_list = convert_to_game_record_list(df)

    save_game_record_to_db(game_record_list)

    print(f"[{datetime.now()}] KBO 팀 기록 크롤링 종료")


if __name__ == "__main__":
    main()