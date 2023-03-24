* 소스 실행 방법
    /src/Main.java main() 실행

* 디렉토리 설명
    - result.txt        : 결과물
    - kakaobank         : 코드
        /src/resources  : comments.csv, *.jar
        /src/testResult : 프로그램 실행시 결과물 (result.txt, result.log) 생성
        /src/util       : 유틸
        /src/vo         : 프로그램 실행시 필요한 객체
        /src/Main.java  : 프로그램 실행

* 알고리즘 설명
    문제 해석
        - '유효한 학교 이름'의 정의 (아래 A, B를 모두 만족해야함)
            A. 실제 존재하는 학교 (커리어넷 API 이용, 실제 학교명 조회)
            B. 학교 문자열 패턴 : *학교, *초등학교, *중학교, *고등학교, *대학교, *초, *중, *고, *대

    탐색 이전 Main.initializeLog
        1. 로깅 환경 세팅                              Main.initializeLog()
        2. 대한민국의 학교 이름 가져오기 (커리어넷 API 사용)   Main.setRealSchool()

    탐색 과정
        1. comments.csv 읽기 (어절 별로 읽음)
            A. 검사 : 학교 문자열 패턴을 포함했는가?
                a. 학교 구분 값으로 그룹화 (영명고ㅋ난소하고등학교우와신관초ㅋㅋ -> 영명고 | ㅋ난소하고등학교 | 우와신관초)
                b. 각 그룹을 실제 학교명에서 탐색
                    - 먼저 우리나라 학교명 최대 문자열 길이만큼 자름 (커리어넷 참고)
                    - 단, 이때 *초, *고 와 같이 줄임말로 되어있다면 *초등학교, *고등학교로 수정 후 탐색한다
                    - 학교 구분값 앞에 글자 부터 하나씩 늘려가면서 탐색 (육고등학교 -> 체육고등학교 -> 울체육고등학교 -> 서울체육고등학교 -> 일치)
                c. 검사: 실제 학교명과 일치하는가?
                    - '학교 후보' 리스트에 적제

        2. '학교 후보' 리스트 중 실제 학교 탐색
            A. 대한민국 학교 이름 Set에 존재하는 학교 이름을 탐색
                a. 검사 : Set 에 존재하는가?
                    - 결과물 (resultMap)에 카운트와 함께 저장

        3. 결과물 출력
            - resultMap을 result.txt에 출력

        - end.

* Open Source, 라이브러리
    - 커리어넷 API : https://www.career.go.kr/cnet/front/openapi/openApiSchoolCenter.do
    - jackson-annotations-2.14.2.jar
    - jackson-core-2.14.2.jar
    - jackson-databind-2.14.2.jar






