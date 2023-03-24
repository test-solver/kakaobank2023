import com.fasterxml.jackson.databind.ObjectMapper;
import vo.School;
import util.Utils;
import vo.SchoolGubun;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private final static Logger LOGGER = Logger.getLogger("result");

    public static Set<String> schoolsCareer = new HashSet<>(); //커리어넷에서 읽어온 실제 학교

    public static List<School> schoolsFromFile = new ArrayList<>(); // comments.csv에서 읽은 학교

    public static HashMap<School, Integer> resultMap = new HashMap(); // 결과물


    /**
     * 프로그램 진행 순서
     * 1. 로깅 세팅
     * 2. 실제 우리나라 학교 가져오기
     * 3. comments.csv 에서 학교 후보 단어 탐색
     * 4. 학교 후보 단어 중 실제 학교 탐색
     * 5. 결과물 (result.txt) 출력
     */
    public static void main(String[] args) {

        try {
            // 1.
            initializeLog();

            // 2.
            setRealSchool();

            // 3.
            readTextFromFile();

            // 4.
            countSchoolFromList();

            // 5.
            printResult();

            LOGGER.log(Level.INFO, "end success!");

        } catch (Exception e) {
            //TODO : 로깅 전반적으로
            LOGGER.log(Level.WARNING, e.getMessage());
        }

    }

    private static void initializeLog() throws IOException {
        //TODO :  로깅파일이 있다면 해당 파일에 쌓기
        FileHandler fileHandler = new FileHandler(Utils.getProjectDir() + File.separator + "src" + File.separator + "testResult" + File.separator + "result.log");

        LOGGER.addHandler(fileHandler);
        LOGGER.info("log initialized");
    }

    /**
     * 커리어넷 API 이용, 실제 학교 이름 가져오기
     */
    private static void setRealSchool() throws IOException {

        LOGGER.info("carrer API begin");

        getSchoolByExternalAPI(SchoolGubun.ELEMENTARY);
        getSchoolByExternalAPI(SchoolGubun.MIDDLE);
        getSchoolByExternalAPI(SchoolGubun.HIGH);
        getSchoolByExternalAPI(SchoolGubun.COLL);
        getSchoolByExternalAPI(SchoolGubun.SEET);
        getSchoolByExternalAPI(SchoolGubun.GENERAL);

        LOGGER.info("carrer API end successfully");

    }
    private static void findInCareerList(School school) {
        // 일단 30글자로 자르기 (학교명이 30글자 이상인 경우는 없음)
        String schoolName = school.getSchoolName();
        int lengthSchoolName = schoolName.length();
//        if (lengthSchoolName >= LENGTH_MAX_SCHOOL_NAME) {
        if (lengthSchoolName >= school.getSchoolGubun().getMaxLegnth()) {
            schoolName = schoolName.substring(schoolName.length() - school.getSchoolGubun().getMaxLegnth(), schoolName.length());
            school.setSchoolName(schoolName);
            lengthSchoolName = school.getSchoolGubun().getMaxLegnth();
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
        Boolean isExist = schoolsCareer.contains(str);
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
                if (isExist) {
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




    private static void printResult() throws IOException {

        File file = new File(Utils.getProjectDir() + File.separator + "src" + File.separator + "testResult" + File.separator + "result.txt");

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

        for (School school : schoolsFromFile) {
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
     * 커리어 API connection
     * API 정보 (커리어넷 API 홈페이지) : https://www.career.go.kr/cnet/front/openapi/openApiSchoolCenter.do
     *
     * @param schoolGubun : 조회시 사용할 파라미터 정보
     */
    private static void getSchoolByExternalAPI(SchoolGubun schoolGubun) throws IOException {


        URL url = new URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=c40b1a6cb8f89a7111c3314dbfa46bb5&svcType=api&svcCode=SCHOOL&contentType=json&gubun=" + schoolGubun.getParamSchoolName() + "&perPage=" + schoolGubun.getParamPerCnt());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // 서버로부터 데이터 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
            sb.append(line);
        }


        HashMap<String, Object> dataSearch = (HashMap<String, Object>) new ObjectMapper().readValue(sb.toString(), HashMap.class).get("dataSearch");
        ArrayList<LinkedHashMap<String, String>> schoolList = (ArrayList<LinkedHashMap<String, String>>) dataSearch.get("content");

        int maxLength = 0; //학교의 최대 문자열길이
        for (LinkedHashMap<String, String> schoolMap : schoolList) {
            String schoolName = schoolMap.get("schoolName");

            // 대학교일 경우 *대학 or *대학교 로 치환 (명지대학교 인문캠퍼스 -> 명지대학교)
            if (schoolGubun.equals(SchoolGubun.COLL)) {
                String patrnStr = "대학교|대학";
                Matcher matcher = Pattern.compile(patrnStr).matcher(schoolName);

                while (matcher.find()) {
                    String schoolWord = schoolName.substring(0, matcher.end());
                    schoolName = schoolWord;
                }
            }

            if (maxLength < schoolName.length()) {
                maxLength = schoolName.length();
            }

            schoolsCareer.add(schoolName);
        }


        schoolGubun.setMaxLegnth(maxLength);
        LOGGER.info("carrer API " + schoolGubun.getGubunName() + " : " + schoolList.size());

    }


    /**
     * 텍스트 읽기
     */
    public static void readTextFromFile() throws Exception {

        File fileCSV = new File(Utils.getProjectDir() + File.separator + "src" + File.separator + "resources" + File.separator + "comments.csv");
        Scanner sc = new Scanner(fileCSV, "UTF-8");

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
                if (word.length() >= 2 && Utils.hasSchoolStr(word)) {
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
            schoolsFromFile.add(school);
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
                    School school = Utils.makeSchool(word.substring(beginIdx, matcher.end()));
                    schoolsFromFile.add(school);
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

        if (Utils.hasSchoolStr(word)) {
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

