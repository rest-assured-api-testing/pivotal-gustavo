package entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"name"})
public class Project {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String kind;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int version;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int iteration_length;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String week_start_day;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String point_scale;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean point_scale_is_custom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean bugs_and_chores_are_estimatable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean automatic_planning;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean enable_tasks;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Time_zone time_zone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int velocity_averaged_over;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int number_of_done_iterations_to_show;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean has_google_domain;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean enable_incoming_emails;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int initial_velocity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean publicc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean atom_enabled;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String project_type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String start_time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String created_at;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updated_at;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int account_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int current_iteration_number;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean enable_following;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getIteration_length() {
        return iteration_length;
    }

    public void setIteration_length(int iteration_length) {
        this.iteration_length = iteration_length;
    }

    public String getWeek_start_day() {
        return week_start_day;
    }

    public void setWeek_start_day(String week_start_day) {
        this.week_start_day = week_start_day;
    }

    public String getPoint_scale() {
        return point_scale;
    }

    public void setPoint_scale(String point_scale) {
        this.point_scale = point_scale;
    }

    public boolean isPoint_scale_is_custom() {
        return point_scale_is_custom;
    }

    public void setPoint_scale_is_custom(boolean point_scale_is_custom) {
        this.point_scale_is_custom = point_scale_is_custom;
    }

    public boolean isBugs_and_chores_are_estimatable() {
        return bugs_and_chores_are_estimatable;
    }

    public void setBugs_and_chores_are_estimatable(boolean bugs_and_chores_are_estimatable) {
        this.bugs_and_chores_are_estimatable = bugs_and_chores_are_estimatable;
    }

    public boolean isAutomatic_planning() {
        return automatic_planning;
    }

    public void setAutomatic_planning(boolean automatic_planning) {
        this.automatic_planning = automatic_planning;
    }

    public boolean isEnable_tasks() {
        return enable_tasks;
    }

    public void setEnable_tasks(boolean enable_tasks) {
        this.enable_tasks = enable_tasks;
    }

    public Time_zone getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(Time_zone time_zone) {
        this.time_zone = time_zone;
    }

    public int getVelocity_averaged_over() {
        return velocity_averaged_over;
    }

    public void setVelocity_averaged_over(int velocity_averaged_over) {
        this.velocity_averaged_over = velocity_averaged_over;
    }

    public int getNumber_of_done_iterations_to_show() {
        return number_of_done_iterations_to_show;
    }

    public void setNumber_of_done_iterations_to_show(int number_of_done_iterations_to_show) {
        this.number_of_done_iterations_to_show = number_of_done_iterations_to_show;
    }

    public boolean isHas_google_domain() {
        return has_google_domain;
    }

    public void setHas_google_domain(boolean has_google_domain) {
        this.has_google_domain = has_google_domain;
    }

    public boolean isEnable_incoming_emails() {
        return enable_incoming_emails;
    }

    public void setEnable_incoming_emails(boolean enable_incoming_emails) {
        this.enable_incoming_emails = enable_incoming_emails;
    }

    public int getInitial_velocity() {
        return initial_velocity;
    }

    public void setInitial_velocity(int initial_velocity) {
        this.initial_velocity = initial_velocity;
    }

    @JsonProperty("public")
    public boolean isPublicc() {
        return publicc;
    }

    @JsonProperty("public")
    public void setPublicc(boolean publicc) {
        this.publicc = publicc;
    }

    public boolean isAtom_enabled() {
        return atom_enabled;
    }

    public void setAtom_enabled(boolean atom_enabled) {
        this.atom_enabled = atom_enabled;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCurrent_iteration_number() {
        return current_iteration_number;
    }

    public void setCurrent_iteration_number(int current_iteration_number) {
        this.current_iteration_number = current_iteration_number;
    }

    public boolean isEnable_following() {
        return enable_following;
    }

    public void setEnable_following(boolean enable_following) {
        this.enable_following = enable_following;
    }
}
