package entities;

public class Time_zone {
    private String kind;
    private String olson_name;
    private String offset;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getOlson_name() {
        return olson_name;
    }

    public void setOlson_name(String olson_name) {
        this.olson_name = olson_name;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
