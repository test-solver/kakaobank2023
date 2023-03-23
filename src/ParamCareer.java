public enum ParamCareer {

    ELEMENTARY("elem_list", 5000),
    MIDDLE("midd_list",5000),
    HIGH("high_list", 5000),
    COLL("univ_list", 5000),
    SEET("seet_list", 500), //특수학교
    GENERAL("alte_list", 5000); //기타

    private String typeName;
    
    private int perCnt; //타입별 최대 개수

    ParamCareer() {
    }

    ParamCareer(String typeName, int perCnt) {
        this.typeName = typeName;
        this.perCnt = perCnt;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getPerCnt() {
        return perCnt;
    }
}
