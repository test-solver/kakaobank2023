package util;

import resources.vo.School;
import resources.vo.SchoolGubun;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    /**
     * 학교명이 있는지 정규식 검사
     * @param word 문자열 서울구로구개봉중학교서울구로구개봉중학교ㅋㅋㅋ신관초이라 (개봉중학교, 신관초 유효)
     * @return 학교패턴이 있는지 여부
     */
    public static boolean hasSchoolStr(String word) {
        String isSchool = "^[가-힣]+학교.*$"; // *학교

        String isElementary1 = "^[가-힣]+초.*$"; // *초
        String isElementary2 = "^[가-힣]+초등학교.*$"; // *초등학교

        String isMiddle1 = "^[가-힣]+중.*$"; // *중
        String isMiddle2 = "^[가-힣]+중학교.*$"; // *중학교

        String isHigh1 = "^[가-힣]+고.*$"; // *고
        String isHigh2 = "^[가-힣]+고등학교.*$"; // *고등학교

        String isColl1 = "^[가-힣]+대.*$"; // *대
        String isColl2 = "^[가-힣]+대학교.*$"; // *대학교

        return Pattern.matches(isSchool, word) || Pattern.matches(isElementary1, word) || Pattern.matches(isElementary2, word) || Pattern.matches(isMiddle1, word) || Pattern.matches(isMiddle2, word) || Pattern.matches(isHigh1, word) || Pattern.matches(isHigh2, word) || Pattern.matches(isColl1, word) || Pattern.matches(isColl2, word);
    }


    /**
     * 영명고 -> schoolName (영명), schoolGubun (고등학교)
     * 영명고등학교 ->  schoolName (영명), schoolGubun (고등학교)
     */
    public static School makeSchool(String str) throws Exception {
        School school = new School();

        if (str.matches("^[가-힣]+초|^[가-힣]+고$")) {
            str += "등학교";
        } else if (str.matches("^[가-힣]+중|^[가-힣]+대$")) {
            str += "학교";
        }

        Matcher matcher = Pattern.compile("초등학교|중학교|고등학교|대학교|학교").matcher(str);
        if (matcher.find()) {
            int beginIdx = matcher.start();
//            System.out.println(str.substring(0, beginIdx) + "  " + str.substring(beginIdx ));
            school.setSchoolGubun(SchoolGubun.getSchoolGubun(str.substring(beginIdx)));
            school.setSchoolName(str.substring(0, beginIdx));
        } else{
            throw  new Exception("unknown string");
        }

        return school;


    }
}
