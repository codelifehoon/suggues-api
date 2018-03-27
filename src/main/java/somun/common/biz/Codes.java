package somun.common.biz;

public class Codes {


    // 사용자 등록 상태
    public  enum USER_STAT { S1("신규"), S9("기가입");
        private String value;
        USER_STAT(String i) { this.value = i; }
    };

    //자동완성종류
    public  enum AC_KIND { SEARCH("검색"), REGTIT("입력타이틀");
        private String value;
        AC_KIND(String i) { this.value = i; }
    };


    // 일정반복종류
    public  enum EV_REPKIND { NONE("반복없음")  , W1("주마다"), M1("월마다"), Y1("1년마다");
        private String value;
        EV_REPKIND(String i) { this.value = i; }

//        public static EV_REPKIND  getEnum(String value) {
//            return null;
//            if(value == null)
//                throw new IllegalArgumentException();
//            for(EV_REPKIND v : values())
//                if(value.equalsIgnoreCase(v.getValue())) return v;
//            throw new IllegalArgumentException();
//        }
    }

    // 일정상태
    public  enum EV_STAT { S0("등록대기"), S2("등록완료"), S4("삭제"), S5("등록정지");
        private String value;
        EV_STAT(String i) { this.value = i; }

    }








}
