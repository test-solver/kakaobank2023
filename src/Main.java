import com.fasterxml.jackson.databind.ObjectMapper;
import resources.vo.School;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public  static List<School> shcoolListOri = new ArrayList<>();

    public static LinkedHashMap<String, Objects> highSchoolMap = new LinkedHashMap<>();
    public static HashMap<String, Integer> resultMap = new HashMap<>();
    /**
     * 파일에서 문자열 읽어오기
     * 문자열에서 학교 명 찾아내기
     * 
     * TODO: 하양여중 -> 하양여 + 중학교로 바뀜 젠장
     */
    public static void main(String[] args) throws Exception {

        //영명고 -> 영명 + 고등학교 -> 적제
//        Pattern ptrn = Pattern.compile("(.+)학교|(.+)초|(.+)초등학교|(.+)중|(.+)중학교|(.+)고|(.+)고등학교|(.+)대|(.+)대학교");
//        Matcher matcher = ptrn.matcher("고소하고");
//        while (matcher.find()) {
//            System.out.println("zzz : "+matcher.group());
//        }
        

//        String strValue = "영명중";
//
//        if (strValue.matches("^[가-힣]+초|^[가-힣]+고$")) {
//            strValue += "등학교";
//        } else if (strValue.matches("^[가-힣]+중|^[가-힣]+대$")) {
//            strValue += "학교";
//        }
//        System.out.println("strValue : " + strValue);

//        strValue = "Subject: 자바, JavaScript, React, Node.JS";

//        Matcher matcher = Pattern.compile("자바").matcher(strValue);
//        while (matcher.find()) {
//            System.out.println("문자열 \"Java\"의 위치: " + matcher.start());
//        }
//
//
//        Matcher matcher = Pattern.compile("중학교").matcher(strValue);
//        if (matcher.find()) {
//            int beginIdx = matcher.start();
//            System.out.println(strValue.substring(0, beginIdx) + "  " + strValue.substring(beginIdx));
//            }


        List<School> schoolListInCSV = new ArrayList<>();
        String txtOriginal = "";
        readTextFromFile(txtOriginal, schoolListInCSV);
        int a = 0;

    }


    /**
     * 커리어넷 API 이용, 실제 학교 이름 가져오기
     * API 정보 : https://www.career.go.kr/cnet/front/openapi/openApiSchoolCenter.do
     */
    private static void getRealSchools() {
        try {
            URL url = new URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=c40b1a6cb8f89a7111c3314dbfa46bb5&svcType=api&svcCode=SCHOOL&contentType=json&gubun=high_list&perPage=3");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
//            conn.setRequestProperty("auth", "myAuth"); // header의 auth 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

//            MappingChange.Map<String, String> map =  new ObjectMapper().readValue(sb.toString(), MappingChange.Map.class);
            HashMap<String, Object> dataSearch = (HashMap<String, Object>) new ObjectMapper().readValue(sb.toString(), HashMap.class).get("dataSearch");

//            ArrayList<School> highScools = highSchoolMap.get("dataSearch");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 텍스트 읽기
     */
    public static void readTextFromFile(String txt, List<School> schoolListInCSV) throws Exception {

//        File fileCSV = new File("/Volumes/T7/dev/kakaobank/src/resources/comments.csv");
//        File fileTXT = new File("/Volumes/T7/dev/kakaobank/src/resources/comments.csv");
        File fileCSV = new File("D:\\dev\\works_intellij\\kakaobank\\src\\resources\\tmp.txt"); //여기 상대경로!!
        File fileTXT = new File("D:\\dev\\works_intellij\\kakaobank\\src\\resources\\tmp.txt");

        //txt 파일 생성
        Files.copy(fileCSV.toPath(), fileTXT.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Scanner sc = new Scanner(fileTXT, "UTF-8");
        //한줄 씩 읽고
        while (sc.hasNextLine()) {

            //어절씩 끊어 읽음
            while (sc.useDelimiter(" ").hasNext()) {

                //공백제거
                String word = sc.useDelimiter(" ").next();
//                String word = sc.next().replace(" ", "");
                if ("".equals(word)) {
                    continue;
                }

                //특수문자 제거
                word = word.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");

//              2글자 이상이면서, 서울구로구개봉중학교서울구로구개봉중학교서울
                if (word.length() >= 2 && hasSchoolStr(word)) {
                    makeSchoolObjectInList(word, schoolListInCSV);
                }
//            System.out.println(word);
//                checkIsSchool(word);

            }


        }

    }

    /**
     * 1.
     */
    private static void makeSchoolObjectInList(String word, List<School> schoolListInCSV) throws Exception {

        System.out.println(word);

        // 2글자 (영고) -> 영 + 고등학교 -> 적재
        if (word.length() == 2) {
            School school = new School();
            school.addFromTwoWord(word);
        } else {
            //영명고 -> 영명 + 고등학교 -> 적제
            Pattern ptrn = Pattern.compile("(.+)학교|(.+)초|(.+)초등학교|(.+)중|(.+)중학교|(.+)고|(.+)고등학교|(.+)대|(.+)대학교");
            Matcher matcher = ptrn.matcher(word);
            while (matcher.find()) {

                shcoolListOri.add(School.makeSchool(matcher.group()));
            }

        }

    }

    /**
     * 학교패턴의 문자열인지 검사
     */
    public static void checkIsSchool(String word) {
        //서울구로구개봉중학교서울구로구개봉중학교서울
        //영명중학교서울

        if (hasSchoolStr(word)) {
            System.out.println(word);
            int cnt;
            if (resultMap.containsKey(word)) {
                cnt = resultMap.get(word);
                cnt++;
            } else {
                cnt = 1;
            }
            resultMap.put(word, cnt);

        }

    }

    /**
     * 학교명이 있는지 정규식 검사
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

}

