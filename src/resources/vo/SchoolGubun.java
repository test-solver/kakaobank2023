package resources.vo;

public enum SchoolGubun {

    ELEMENTARY("초등학교"),
    MIDDLE("중학교"),
    HIGH("고등학교"),
    COLL("대학교"),
    GENERAL("학교");

    private String gubunName;

    SchoolGubun() {
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

    public static SchoolGubun getSchoolGubun(String schoolGubun) throws Exception {
        if(schoolGubun.equals(ELEMENTARY.getGubunName())){
            return ELEMENTARY;
        }else if(schoolGubun.equals(MIDDLE.getGubunName())){
            return MIDDLE;
        }else if(schoolGubun.equals(HIGH.getGubunName())){
            return HIGH;
        }else if(schoolGubun.equals(COLL.getGubunName())){
            return COLL;
        }else if(schoolGubun.equals(GENERAL.getGubunName())){
            return GENERAL;
        }else{
            throw new Exception("unknown schoolGubun");
        }

    }
}
