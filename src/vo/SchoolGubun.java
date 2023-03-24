package vo;

/**
 * 학교 구분값
 */

public enum SchoolGubun {

    ELEMENTARY("초등학교", "elem_list", 5000),
    MIDDLE("중학교", "midd_list", 5000),
    HIGH("고등학교", "high_list", 5000),
    COLL("대학", "univ_list", 5000),
    SEET("학교", "seet_list", 500), //특수학교
    GENERAL("학교", "alte_list", 5000); //기타


    //학교 종류
    private String gubunName;

    //학교 이름 최대 문자열 길이
    private int maxLegnth;

    // 커리어 API 조회 > 파라미터
    private String paramSchoolName;

    // 커리어 API 조회 > 조회할 개수

    private int paramPerCnt;

    SchoolGubun() {
    }

    SchoolGubun(String gubunName, String paramSchoolName, int paramPerCnt) {
        this.gubunName = gubunName;
        this.maxLegnth = maxLegnth;
        this.paramSchoolName = paramSchoolName;
        this.paramPerCnt = paramPerCnt;
    }

    SchoolGubun(String gubunName) {
        this.gubunName = gubunName;
    }

    public String getGubunName() {
        return gubunName;
    }

    public void setGubunName(String gubunName) {
        this.gubunName = gubunName;
    }

    public int getMaxLegnth() {
        return maxLegnth;
    }

    public void setMaxLegnth(int maxLegnth) {
        this.maxLegnth = maxLegnth;
    }

    public String getParamSchoolName() {
        return paramSchoolName;
    }

    public void setParamSchoolName(String paramSchoolName) {
        this.paramSchoolName = paramSchoolName;
    }

    public int getParamPerCnt() {
        return paramPerCnt;
    }

    public void setParamPerCnt(int paramPerCnt) {
        this.paramPerCnt = paramPerCnt;
    }

    @Override
    public String toString() {
        return "SchoolGubun{" +
                "gubunName='" + gubunName + '\'' +
                ", maxLegnth=" + maxLegnth +
                '}';
    }


    public static SchoolGubun getSchoolGubun(String schoolGubun) throws Exception {
        if (schoolGubun.equals(ELEMENTARY.getGubunName())) {
            return ELEMENTARY;
        } else if (schoolGubun.equals(MIDDLE.getGubunName())) {
            return MIDDLE;
        } else if (schoolGubun.equals(HIGH.getGubunName())) {
            return HIGH;
        } else if (schoolGubun.equals(COLL.getGubunName())) {
            return COLL;
        } else if (schoolGubun.equals(GENERAL.getGubunName())) {
            return GENERAL;
        } else {
            throw new Exception("unknown schoolGubun");
        }

    }


}
