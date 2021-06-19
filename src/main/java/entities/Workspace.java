package entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Workspace {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String kind;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int person_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> project_ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public List<Integer> getProject_ids() {
        return project_ids;
    }

    public void setProject_ids(List<Integer> project_ids) {
        this.project_ids = project_ids;
    }
}
