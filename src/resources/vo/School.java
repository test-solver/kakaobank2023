package resources.vo;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class School {

    //개봉
    private String schoolName;

    //중학교
    private SchoolGubun schoolGubun;


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public SchoolGubun getSchoolGubun() {
        return schoolGubun;
    }

    public void setSchoolGubun(SchoolGubun schoolGubun) {
        this.schoolGubun = schoolGubun;
    }

    /**
     * 2글자로부터 학교를 만듬
     * 연대 -> schoolName (연), schoolGubun (대학교)
     */
    public void addFromTwoWord(String word) throws Exception {
        this.schoolName = word.substring(0, 2);
        this.schoolGubun = getScholGubunFromWord(word.substring(1));

    }

    /**
     * 초 - >초등학교
     */
    private SchoolGubun getScholGubunFromWord(String substring) throws Exception {
        if (substring.equals("초")) {
            return SchoolGubun.ELEMENTARY;
        } else if (substring.equals("중")) {
            return SchoolGubun.MIDDLE;
        } else if (substring.equals("고")) {
            return SchoolGubun.HIGH;
        } else if (substring.equals("대")) {
            return SchoolGubun.COLL;
        } else {
            throw new Exception("unknown SchoolGubun");
        }

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
            System.out.println("gggg : "+str);
            throw  new Exception("unknown string");
        }

        return school;


    }
}
