

package th.or.nectec.tanrabad.entity;

import java.util.List;

public class District {
    private String code;
    private String name;
    private String provinceCode;
    private List<Polygon> boundary;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public List<Polygon> getBoundary() {
        return boundary;
    }

    public void setBoundary(List<Polygon> boundary) {
        this.boundary = boundary;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (provinceCode != null ? provinceCode.hashCode() : 0);
        result = 31 * result + (boundary != null ? boundary.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        District district = (District) o;

        if (code != null ? !code.equals(district.code) : district.code != null) return false;
        if (name != null ? !name.equals(district.name) : district.name != null) return false;
        if (provinceCode != null ? !provinceCode.equals(district.provinceCode) : district.provinceCode != null)
            return false;
        return boundary != null ? boundary.equals(district.boundary) : district.boundary == null;

    }
}
