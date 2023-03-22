import resources.vo.School;
import resources.vo.SchoolGubun;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {

    public static  void  main(String[] args) throws Exception {

        School school = new School("고소하", SchoolGubun.HIGH);

        // 일단 30글자로 자르기 (학교명이 30글자 이상인 경우는 없음)
        String schoolName = school.getSchoolName();
        int lengthSchoolName = schoolName.length();
        if (lengthSchoolName >= Main.LENGTH_MAX_SCHOOL_NAME) {
            schoolName = schoolName.substring(schoolName.length() - Main.LENGTH_MAX_SCHOOL_NAME, schoolName.length());
            school.setSchoolName(schoolName);
            lengthSchoolName = Main.LENGTH_MAX_SCHOOL_NAME;
        }

        // 뒤에서부터 한글자씩 늘려가며 일치하는 학교가 있나 검사
        // 고영명고 -> 2개 (영명고, 고영명고)
        int find = 0;
        for (int i = 1; i <= lengthSchoolName; i++) {
            String str = schoolName.substring(schoolName.length() - i, schoolName.length());
            school.setSchoolName(str);
            System.out.println(school.toString());
        }

        System.out.println(school.toString());


    }
}
