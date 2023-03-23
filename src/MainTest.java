public class MainTest {

    public static void main(String[] args) throws Exception {

        String str = "567교시마다배고";
        if (str.matches("^.+중|^.+고$")) {
            System.out.println("22");
        }


//        String str = "학교를고등학교";
//        Matcher matcher = Pattern.compile("초등학교|중학교|고등학교|대학교|학교").matcher(str);
//
//        if (matcher.find()) {
//            int beginIdx = matcher.start();
//            System.out.println(str.substring(0, beginIdx) + "  " + str.substring(beginIdx ));
//            System.out.println(str.substring(0, beginIdx) + " || "+str.substring(beginIdx));
//        } else{
//            throw  new Exception("unknown string");
//    }


//        String str = "호원고등학교\t2\n" +
//                "송화초등학교\t2\n" +
//                "개봉중학교\t51\n" +
//                "태장중학교\t1\n" +
//                "신정중학교\t1\n" +
//                "소하고등학교\t1\n" +
//                "연무중학교\t2\n" +
//                "해강중학교\t8\n" +
//                "서울송화초등학교\t1\n" +
//                "하양여자중학교\t2\n" +
//                "동두천여자중학교\t2\n" +
//                "영천여자중학교\t2\n" +
//                "상지여자고등학교\t1\n" +
//                "창현고등학교\t1";
//
//
//        System.out.println(str);

//
//        String regex = "([ㄱ-ㅎ가-힣]+남)|([ㄱ-ㅎ가-힣]+여)";
//        System.out.println("남".matches(regex));
//        System.out.println("여".matches(regex));
//        System.out.println("나나".matches(regex));
//


        //문자열 배열로 자르기
//        String str = "진짜동두천여자중학교저희는급식을먹기위해이학교";
        // 중학교 중학교 학교
        //
//        String[] schools = str.split("중학교|학교");

//        System.out.println(schools.toString());

        // 나개중학교나아개봉중학교시낭영고래신관초ㅋㅋ
        // 나개중학교나아개봉중학교시낭영고래신관초ㅋㅋ
//        School school = new School();
//        String str = "진짜동두천여자중학교저희는급식을먹기위해이학교";
//        String str = "고소하고";
//        String str = "나개중학교나아개봉중학교시낭영고래신관초ㅋㅋ";
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
//        String patrnStr =  "초등학교|.+중학교|고등학교|대학교|초|중|.+고|대";
//        String patrnStr =  "중학교";
//        String patrnStr =  "Windows";
//        String patrnStr =  ".+중학교|.+초|.+중|.+고|.+대";
//        String patrnStr =  ".+중학교|.+중|.+초";
////        String patrnStr =  "((?:\\S+)(?:XP|98))";
////        Pattern ptrn = Pattern.compile("(.+)학교|(.+)초|(.+)초등학교|(.+)중|(.+)중학교|(.+)고|(.+)고등학교|(.+)대|(.+)대학교");
//        Pattern ptrn = Pattern.compile("초등학교|중학교|고등학교|대학교|학교").matcher(str);;
//        Matcher matcher = Pattern.compile(patrnStr).matcher(str);
////        Matcher matcher = ptrn.matcher(str);
//        int beginIdx = 0;
//        while (matcher.find()) {
//            System.out.println(matcher.start() +" ~ "+matcher.end());
//            System.out.println(str.substring(beginIdx, matcher.end()));
//            beginIdx = matcher.end();
//        }

//        String word = "서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교서울구로구개봉중학교";
//        String word = "서울구로구개봉중학교서울구로구개봉중학교서울구중학교";
//        String word = "진짜동두천여자중학교저희는급식을먹기위해이학교";
////        TODO #1 : 산초고 -> 산초고등학교? 산초등학교?
//        String word = "나개중학교나아개봉중학교시낭영고래신관초고ㅋㅋ중중나중";
//        String patrnStr =  "초등학교|중학교|고등학교|대학교|초|중|고|대";
//
////        String[] schools = word.split(patrnStr);
////        String[] schools = word.split("중학교|초");
////        for(String school : schools){
////            System.out.println(school);
////        }
//        Matcher matcher = Pattern.compile(patrnStr).matcher(word);
////
//        int beginIdx = 0;
////
//        while (matcher.find()) {
//            System.out.println(matcher.start() +" ~ "+matcher.end());
//            System.out.println(word.substring(beginIdx, matcher.end()));
//            beginIdx = matcher.end();
//        }


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
