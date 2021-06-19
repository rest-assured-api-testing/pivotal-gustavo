package generalSetting;

public class ParametersDefault {
    public static String URL_BASE = "https://www.pivotaltracker.com/services/v5";
    public static String KEY_VALUE = "X-TrackerToken";
    public static String VALUE_KEY = "1d24b2ee47d04c09615c6811a19fba0a";

    public static String PROJECT_ID = "projectId";
    public static String EPIC_ID = "epicId";
    public static String LABEL_ID = "labelsId";
    public static String STORY_ID = "storiesId";
    public static String WORKSPACE_ID = "workspaceId";
    public static String COMMENT_ID = "commentId";
    public static String BLOCKER_ID = "blockersId";

    public static String END_POINT_PROJECT = "/projects";
    public static String END_POINT_PROJECT_TO_INTERACT = "/projects/{projectId}";
    public static String END_POINT_EPIC = "projects/{projectId}/epics/";
    public static String END_POINT_EPIC_TO_INTERACT = "projects/{projectId}/epics/{epicId}";
    public static String END_POINT_STORY = "projects/{projectId}/stories/";
    public static String END_POINT_STORY_TO_INTERACT = "projects/{projectId}/stories/{storiesId}";
    public static String END_POINT_LABEL = "projects/{projectId}/labels/";
    public static String END_POINT_LABEL_TO_INTERACT = "projects/{projectId}/labels/{labelsId}";
    public static String END_POINT_WORKSPACE = "my/workspaces";
    public static String END_POINT_WORKSPACE_TO_INTERACT = "my/workspaces/{workspaceId}";
    public static String END_POINT_COMMENT_IN_STORY = "projects/{projectId}/stories/{storiesId}/comments";
    public static String END_POINT_COMMENT_IN_STORY_TO_INTERACT = "projects/{projectId}/stories/{storiesId}/comments/{commentId}";
    public static String END_POINT_COMMENT_IN_EPIC = "projects/{projectId}/epics/{epicId}/comments";
    public static String END_POINT_COMMENT_IN_EPIC_TO_INTERACT = "projects/{projectId}/epics/{epicId}/comments/{commentId}";
    public static String END_POINT_BLOCKER_IN_STORY = "projects/{projectId}/stories/{storiesId}/blockers";
    public static String END_POINT_BLOCKER_IN_STORY_TO_INTERACT = "projects/{projectId}/stories/{storiesId}/blockers/{blockersId}";

}
