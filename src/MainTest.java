import resources.vo.School;
import resources.vo.SchoolGubun;
import util.PatternUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {

    public static  void  main(String[] args) throws Exception {


        //문자열 배열로 자르기
//        String str = "진짜동두천여자중학교 천여자중학교 저희는급식을먹기위해이학교";
        // 중학교 중학교 학교
        //
//        String[] schools = word.split("중학교|학교");
//        System.out.println(schools.toString());

        String s = "${test.one}${test.two}\n${test.one}${test.two()}\n${test.one}${\"hello\"}";
        Pattern pattern = Pattern.compile("\\$\\{(\"[^\"]*\"|\\w+(?:\\(\\))?(?:\\.\\w+(?:\\(\\))?)*)}");

        // 나개중학교나아개봉중학교시낭영고래신관초ㅋㅋ
        // 나개중학교나아개봉중학교시낭영고래신관초ㅋㅋ
//        School school = new School();
//        String str = "진짜동두천여자중학교저희는급식을먹기위해이학교";
        String str = "고소하고";
//        String str = "sstowKGHweofjKGHsdfjk";
//        String str = "Window98 1WindowXP,Windowsss";
//        String str = "나개중학교나시낭영고래신관초ㅋㅋ";
//        String str = "나개봉중학교나아개봉중학교";
//        String str = "나개봉아개붖";

        // TODO : 진짜동두천여자중학교저희는급식을먹기위해이학교
        // TODO : 고소하고
//        String patrnStr =  "(.+)학교|(.+)초|(.+)초등학교|(.+)중|(.+)중학교|(.+)고|(.+)고등학교|(.+)대|(.+)대학교";
//        String patrnStr =  "(.+)(중학교)|(.+)(초)";
//        String patrnStr =  "(\\S+)(중학교|초)";
//        String patrnStr =  "(.+)(KGH)";
        String patrnStr =  "초등학교|중학교|고등학교|대학교|초|중|.+고|대";
//        String patrnStr =  "((?:\\S+)(?:XP|98))";
//        Pattern ptrn = Pattern.compile("(.+)학교|(.+)초|(.+)초등학교|(.+)중|(.+)중학교|(.+)고|(.+)고등학교|(.+)대|(.+)대학교");
//        Pattern ptrn = Pattern.compile("초등학교|중학교|고등학교|대학교|학교").matcher(str);;
        Matcher matcher = Pattern.compile(patrnStr).matcher(str);
//        Matcher matcher = ptrn.matcher(str);
        int beginIdx = 0;
        while (matcher.find()) {
//            System.out.println(matcher.group() + " : "+matcher.group(1)+matcher.group(2));
            System.out.println(matcher.start() +" ~ "+matcher.end());
            System.out.println(str.substring(beginIdx, matcher.end()));
            beginIdx = matcher.end();
        }


//        Matcher matcher = Pattern.compile("초등학교|중학교|고등학교|대학교|학교").matcher(str);
//        if (matcher.find()) {
//            int beginIdx = matcher.start();
////            System.out.println(str.substring(0, beginIdx) + "  " + str.substring(beginIdx ));
//            school.setSchoolGubun(SchoolGubun.getSchoolGubun(str.substring(beginIdx)));
//            school.setSchoolName(str.substring(0, beginIdx));
//            System.out.println(school.toString());
//        }
//
//        System.out.println(school.toString());


    }
}
