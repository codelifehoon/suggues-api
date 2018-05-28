package somun.common.util;

import org.springframework.util.StringUtils;

public class RandomUti {



    public static String randomString(Integer size){ return randomString (size,null,null);}
    public static String randomString(Integer size, String prefix , String surfix){

        StringBuffer  result = new StringBuffer();

        if (!StringUtils.isEmpty(prefix)) result.append(prefix);

        // 대문자 A-Z 랜덤 알파벳 생성
        for (int i = 1; i <= size; i++) {
            char ch = (char) ((Math.random() * 26) + 65);
            result.append(ch);
        }

        if (!StringUtils.isEmpty(surfix)) result.append(surfix);

        return  result.toString();

    }


    public static  Integer randomNumber(int size){

        if (size > 9) return -1;
        double random = Math.random();
        int value = (int) (random * (Math.pow(11,size))) ;

        return  value ;

    }

}
