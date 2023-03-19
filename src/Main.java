import com.sun.xml.internal.ws.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Character.SPACE_SEPARATOR;

public class Main {

    public static HashMap<String, Integer> resultMap = new HashMap<>();

    /**
     * 파일에서 문자열 읽어오기
     * 문자열에서 학교 명 찾아내기
     */
    public static void main(String[] args) throws IOException {
//        String isSchool = "^[가-힣]+학교$"; // *학교
//
//        String isElementary1 = "^[가-힣]+초$"; // *초
//        String isElementary2 = "^[가-힣]+초등학교$"; // *초등학교
//
//        String isMiddle1 = "^[가-힣]+중$"; // *중
//        String isMiddle2 = "^[가-힣]+중학교$"; // *중학교
//
//        String isHigh1 = "^[가-힣]+고$"; // *고
//        String isHigh2 = "^[가-힣]+고등학교$"; // *고등학교
//
//        String isColl1 = "^[가-힣]+대$"; // *대
//        String isColl2 = "^[가-힣]+대학교$"; // *대학교
//
//        String word = "서울구로구개봉중학교서울구로구개봉중학교서울";
//        String isMiddle = "^([가-힣]+중학교)|([가-힣]+중)$"; // *중학교
//        String[] aaa = word.split(isMiddle);
//        if (Pattern.matches(isMiddle, word)) {
//            System.out.println(word);
//        }

        String txtOriginal = "";
        readTextFromFile(txtOriginal);
        int a = 0;
    }

    /**
     * 텍스트 읽기
     */
    public static void readTextFromFile(String txt) throws IOException {

        File fileCSV = new File("/Volumes/T7/dev/kakaobank/src/resources/comments.csv");
        File fileTXT = new File("/Volumes/T7/dev/kakaobank/src/resources/comments.csv");

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
                word = word.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "");
//                서울구로구개봉중학교서울구로구개봉중학교서울
//            System.out.println(word);
                checkIsSchool(word);

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
        String isSchool = "^[가-힣]+학교$"; // *학교

        String isElementary1 = "^[가-힣]+초$"; // *초
        String isElementary2 = "^[가-힣]+초등학교$"; // *초등학교

        String isMiddle1 = "^[가-힣]+중$"; // *중
        String isMiddle2 = "^[가-힣]+중학교$"; // *중학교

        String isHigh1 = "^[가-힣]+고$"; // *고
        String isHigh2 = "^[가-힣]+고등학교$"; // *고등학교

        String isColl1 = "^[가-힣]+대$"; // *대
        String isColl2 = "^[가-힣]+대학교$"; // *대학교

        return Pattern.matches(isSchool, word)
                || Pattern.matches(isElementary1, word) || Pattern.matches(isElementary2, word)
                || Pattern.matches(isMiddle1, word) || Pattern.matches(isMiddle2, word)
                || Pattern.matches(isHigh1, word) || Pattern.matches(isHigh2, word)
                || Pattern.matches(isColl1, word) || Pattern.matches(isColl2, word);

    }

}