package resources.vo;

import java.util.Objects;

public class SchoolWithCount {

    private School school;
    private int count;

    public SchoolWithCount() {
    }

    public SchoolWithCount(School school, int count) {
        this.school = school;
        this.count = count;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 두 객체의 학교 이름만 같으면 같은 객체로 본다.
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolWithCount that = (SchoolWithCount) o;
//        return count == that.count && Objects.equals(school, that.school);
        return Objects.equals(school, that.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(school, count);
    }
}
