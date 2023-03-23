import com.fasterxml.jackson.databind.ObjectMapper;
import vo.School;
import util.PatternUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    /**
     * TODO :
     *  1. 영 명 고등학교 -> 이거 유효한 학교이름이라고 봐야하나?
     *  2. 학교 이름인거 같은 애들 모아서 보여주기
     *      대가대 -> 대구가톨릭대학교
     */

    public static final int LENGTH_MAX_SCHOOL_NAME = 30; //우리나라 학교이름 최대길이 TODO : 커리어에서 가져와서 최대값을 할당해주는건?
    public static Set<String> careerSchool = new HashSet<>(); //커리어넷에서 읽어온 진짜 학교

    public static List<School> schoolListOri = new ArrayList<>();

    public static HashMap<School, Integer> resultMap = new HashMap(); // 제시할 정답
    
//    TODO: 정규표현식에 자주쓰이는거 따로 선언하자 (ㄱ-ㅎ|ㅏ-ㅣ|가-힣) 이런거

//    TODO : 파일경로 모두 상대경로!

//    TODO: 지금까지 예외 리스트
    /**
     *
     * "인천 ,청라중학교 ? -> 청라중학교로 인식함 (인천 청라중학교 / 청라중학교 다름)
     *
     * 명지전문대 -> 명지전문대학교로 검색해서 인식 못함 (명지전문대학 이 맞음)
     *      => 그렇다면 대학교는 그냥 "대학"으로 할까?
     * */

    private static void findInCareerList(School school) {
        // 일단 30글자로 자르기 (학교명이 30글자 이상인 경우는 없음)
        String schoolName = school.getSchoolName();
        int lengthSchoolName = schoolName.length();
        if (lengthSchoolName >= LENGTH_MAX_SCHOOL_NAME) {
            schoolName = schoolName.substring(schoolName.length() - LENGTH_MAX_SCHOOL_NAME, schoolName.length());
            school.setSchoolName(schoolName);
            lengthSchoolName = LENGTH_MAX_SCHOOL_NAME;
        }

        int find = 0; //문자열에 실제 학교명 포함 여부

        for (int i = 1; i <= lengthSchoolName; i++) {
            School schoolNew = school.getCopyDeep();
            String str = schoolName.substring(schoolName.length() - i, schoolName.length());
            schoolNew.setSchoolName(str);

            if (isRealSchool(schoolNew)) {
                plusSchoolCount(schoolNew);
            }
        }


    }

    /**
     * 우리나라 진짜 학교에 있는지 검사
     *
     * @param School (학교)
     */
    private static boolean existInCareer(School school) {

        String str = school.getSchoolName() + school.getSchoolGubun().getGubunName();
        Boolean isExist = careerSchool.contains(str);
        return isExist;
    }

    /**
     * 학교가 맞는지 검사
     * Career 학교, result 검사
     */
    public static Boolean isRealSchool(School school) {
        Boolean isExist = existInResult(school) || existInCareer(school);

        if (!isExist) {
            // TODO : 한양여 중학교 -> 한양여자 중학교 변경
            String regex = "([ㄱ-ㅎ가-힣]+남)|([ㄱ-ㅎ가-힣]+여)";
            if (school.getSchoolName().matches(regex)) {
                School school1 = school.getCopyDeep();
                school1.setSchoolName(school1.getSchoolName() + "자");
                String str = school1.getSchoolName() + school1.getSchoolGubun().getGubunName();
                isExist = existInResult(school1) || existInCareer(school1);
                if(isExist){
                    school.copyFrom(school1);
                }
            }
        }

        return isExist;
    }


    /**
     * 학교 회수 카운트 ++
     *
     * @param School (횟수를 늘릴 학교)
     */
    private static void plusSchoolCount(School school) {
        Integer cnt = resultMap.get(school);
        cnt = cnt == null ? 0 : cnt;
        cnt++;
        resultMap.put(school, cnt);
    }

    /**
     * TODO: 하양여중 -> 하양여 + 중학교로 바뀜 젠장 하양여자 + 중학교로 바뀌어야할텐데...
     * 없다면 마지막에 아닌애들끼리 한번 더 돌려보는건 어떤가
     * 하양여중학교 가 없다면 하양여자중학교로 해보는거지
     */
    public static void main(String[] args) throws Exception {

        // 실제 학교 세팅
        setRealSchool();

        // 파일에서 학교 후보들 색출
        readTextFromFile();

        //list에 실제 학교 몇개있는지 체크
        countSchoolFromList();

        //결과물 생성
        printResult();



        int a = 0;

    }

    private static void printResult() throws IOException {



        File file = new File("D:\\dev\\works_intellij\\kakaobank\\src\\resources\\result.txt");

        // 2. 파일 존재여부 체크 및 생성
        if (!file.exists()) {
            file.createNewFile();
        }

        // 3. Writer 생성
        FileWriter fw = new FileWriter(file);
        PrintWriter writer = new PrintWriter(fw);

        // 4. 파일에 쓰기
        for (School school : resultMap.keySet()) {
            writer.println(school.getSchoolName() + school.getSchoolGubun().getGubunName() + "\t" + resultMap.get(school));
        }
        // 5. PrintWriter close
        writer.close();

    }


    /**
     * list에서 학교 몇개인지 세기
     */
    private static void countSchoolFromList() {

        for (School school : schoolListOri) {
            // resultMap에 있으면 실제 학교 이름임 -> count++;
            if (existInResult(school)) {
                plusSchoolCount(school);
            }
            // resltMap에 없으면 -> 실제 학교 리스트에서 탐색
            else {
                findInCareerList(school);
            }
        }


    }


    /**
     * 이미 학교 그룹화에 속해있는가?
     *
     * @param school
     * @return resultMap 에 학교가 있는지 여부
     */
    private static boolean existInResult(School school) {
        return resultMap.containsKey(school);
    }


    /**
     * 커리어넷 API 이용, 실제 학교 이름 가져오기
     * API 정보 :
     */
    private static void setRealSchool() throws IOException {

        // TODO : schoolType -> enum
        getSchoolByExternalAPI(ParamCareer.ELEMENTARY);
        getSchoolByExternalAPI(ParamCareer.MIDDLE);
        getSchoolByExternalAPI(ParamCareer.HIGH);
        getSchoolByExternalAPI(ParamCareer.COLL);
        getSchoolByExternalAPI(ParamCareer.SEET);
        getSchoolByExternalAPI(ParamCareer.GENERAL);


    }


    /**
     * 커리어 API connection
     * API 정보 (커리어넷 API 홈페이지) : https://www.career.go.kr/cnet/front/openapi/openApiSchoolCenter.do
     *
     * @param schoolType 학교 구분 (초등학교, 중학교, 기타 등)
     * @param perPage    호출 시 가져올 개수
     */
    private static void getSchoolByExternalAPI(ParamCareer paramCareer) throws IOException {

        URL url = new URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=c40b1a6cb8f89a7111c3314dbfa46bb5&svcType=api&svcCode=SCHOOL&contentType=json&gubun=" + paramCareer.getTypeName() + "&perPage=" + paramCareer.getPerCnt());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET"); // http 메서드
        conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
        conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

        // 서버로부터 데이터 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
            sb.append(line);
        }


        HashMap<String, Object> dataSearch = (HashMap<String, Object>) new ObjectMapper().readValue(sb.toString(), HashMap.class).get("dataSearch");
        ArrayList<LinkedHashMap<String, String>> schoolList = (ArrayList<LinkedHashMap<String, String>>) dataSearch.get("content");

        for (LinkedHashMap<String, String> schoolMap : schoolList) {
            // TODO : 학교에 공백이 있다면 제거하고 넣을까?
            String schoolName = schoolMap.get("schoolName");
            // 대학교일 경우 대학교 -> 대학
            if(paramCareer.equals(ParamCareer.COLL)){
                schoolName = schoolName.replace("대학교", "대학");
            }
            careerSchool.add(schoolName);
        }

    }


    /**
     * 텍스트 읽기
     */
    public static void readTextFromFile() throws Exception {

//        File fileCSV = new File("/Volumes/T7/dev/works_intellij/kakaobank/src/resources/tmp.csv");
//        File fileTXT = new File("/Volumes/T7/dev/works_intellij/kakaobank/src/resources/tmp.txt");
        File fileCSV = new File("D:\\dev\\works_intellij\\kakaobank\\src\\resources\\comments.csv"); //여기 상대경로!!
        File fileTXT = new File("D:\\dev\\works_intellij\\kakaobank\\src\\resources\\comments.txt");

        //txt 파일 생성
        Files.copy(fileCSV.toPath(), fileTXT.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Scanner sc = new Scanner(fileTXT, "UTF-8");

        //한줄 씩 읽고
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split(" ");
            for (String word : words) {

                //빈 문자열이면 contiue
                if ("".equals(word)) {
                    continue;
                }

                //특수문자 제거
                word = word.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");

                // 2글자 이상 && 학교 패턴이 포함되어있으면
                if (word.length() >= 2 && PatternUtils.hasSchoolStr(word)) {
                    makeSchoolObjectInList(word);
                }
            }

        }

    }

    /**
     * 문자열을 학교 객체로 바꿈 -> 리스트 적재
     *
     * @param word 나개봉중학교나아개봉중학교시낭영고래신관초ㅋㅋ (개봉중학교, 영고, 신관초)
     */
    private static void makeSchoolObjectInList(String word) throws Exception {

        // 2글자 (영고)
        if (word.length() == 2) {
            // 영 + 고등학교 -> 적재
            School school = new School();
            school.addFromTwoWord(word);
            schoolListOri.add(school);
        } else {
            // 영명고 -> 영명 + 고등학교 -> 적제
            //            Pattern ptrn = Pattern.compile("(.+)학교|(.+)초|(.+)초등학교|(.+)중|(.+)중학교|(.+)고|(.+)고등학교|(.+)대|(.+)대학교");
//            Matcher matcher = ptrn.matcher(word);
//            while (matcher.find()) {
//                School school = PatternUtils.makeSchool(matcher.group());
//                schoolListOri.add(school);
//            }

//            String patrnStr =  "초등학교|중학교|고등학교|대학교|.+초|.+중|.+고|.+대";
            String patrnStr = "초등학교|중학교|고등학교|대학|학교|초|중|고|대";
            Matcher matcher = Pattern.compile(patrnStr).matcher(word);

            int beginIdx = 0;

            while (matcher.find()) {
                String schoolWord = word.substring(beginIdx, matcher.end());
                if (schoolWord.length() >= 2) {
                    School school = PatternUtils.makeSchool(word.substring(beginIdx, matcher.end()));
                    schoolListOri.add(school);
                }
                beginIdx = matcher.end();
            }

        }

    }

    /**
     * 학교패턴의 문자열인지 검사
     */
    public static void checkIsSchool(String word) {
        //서울구로구개봉중학교서울구로구개봉중학교서울
        //영명중학교서울

        if (PatternUtils.hasSchoolStr(word)) {
            System.out.println(word);
            int cnt;
            if (resultMap.containsKey(word)) {
                cnt = resultMap.get(word);
                cnt++;
            } else {
                cnt = 1;
            }
//            resultMap.put(word, cnt);

        }

    }


}

