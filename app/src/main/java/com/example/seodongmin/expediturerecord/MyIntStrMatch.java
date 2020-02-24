package com.example.seodongmin.expediturerecord;

/**
 * Created by seodongmin on 2017-04-24.
 */

// 1: 수입 카테고리
// 2: 지출 카테고리
// 3: 지출 수단

public class MyIntStrMatch {

    int type;

    public MyIntStrMatch(int type) {
        this.type = type;
    } //type을 받는다.

    public String Match(int num) {

        switch (this.type) {
            case 1: //수입 카테고리
                switch(num)
                {
                    case -1:
                        return "";
                    case 0:
                        return "월급";
                    case 1:
                        return "보너스";
                    case 2:
                        return "용돈";
                    case 3:
                        return "이자";
                    case 4:
                        return "기타";
                    default:
                        return "";
                }
            case 2: //지출 카테고리

                switch(num)
                {
                    case -1:
                        return "";
                    case 0:
                        return "식료품";
                    case 1:
                        return "외식";
                    case 2:
                        return "교통비";
                    case 3:
                        return "통신비";
                    case 4:
                        return "공납금";
                    case 5:
                        return "수수료";
                    case 6:
                        return "저축";
                    case 7:
                        return "여가";
                    case 8:
                        return "기부";
                    case 9:
                        return "기타";
                    default:
                        return "";
                }

            case 3: //지출 수단
                switch(num)
                {
                    case -1:
                        return "";
                    case 0:
                        return "현금";
                    case 1:
                        return "체크카드";
                    case 2:
                        return "신용카드";
                    default:
                        return "";
                }

            default:
                return "";
        }
    }


}
