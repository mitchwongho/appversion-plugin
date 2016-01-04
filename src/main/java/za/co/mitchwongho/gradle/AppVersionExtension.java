package za.co.mitchwongho.gradle;

/**
 *
 */
public class AppVersionExtension {
    private Integer code = 1;
    private String name = "1.0.0";

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
