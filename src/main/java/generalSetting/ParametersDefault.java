package generalSetting;

public class ParametersDefault {
    public static final String URL_BASE = "https://www.pivotaltracker.com/services/v5";
    public static final String KEY_VALUE = "X-TrackerToken";
    public static final String VALUE_KEY = "1d24b2ee47d04c09615c6811a19fba0a";

    public static final String PROJECT_ID = "projectId";
    public static final String EPIC_ID = "epicId";
    public static final String LABEL_ID = "labelsId";
    public static final String STORY_ID = "storiesId";
    public static final String WORKSPACE_ID = "workspaceId";
    public static final String COMMENT_ID = "commentId";
    public static final String BLOCKER_ID = "blockersId";

    public static final String END_POINT_PROJECT = "/projects";
    public static final String END_POINT_PROJECT_TO_INTERACT = "/projects/{projectId}";
    public static final String END_POINT_EPIC = "projects/{projectId}/epics/";
    public static final String END_POINT_EPIC_TO_INTERACT = "projects/{projectId}/epics/{epicId}";
    public static final String END_POINT_STORY = "projects/{projectId}/stories/";
    public static final String END_POINT_STORY_TO_INTERACT = "projects/{projectId}/stories/{storiesId}";
    public static final String END_POINT_LABEL = "projects/{projectId}/labels/";
    public static final String END_POINT_LABEL_TO_INTERACT = "projects/{projectId}/labels/{labelsId}";
    public static final String END_POINT_WORKSPACE = "my/workspaces";
    public static final String END_POINT_WORKSPACE_TO_INTERACT = "my/workspaces/{workspaceId}";
    public static final String END_POINT_COMMENT_IN_STORY = "projects/{projectId}/stories/{storiesId}/comments";
    public static final String END_POINT_COMMENT_IN_STORY_TO_INTERACT = "projects/{projectId}/stories/{storiesId}/comments/{commentId}";
    public static final String END_POINT_COMMENT_IN_EPIC = "projects/{projectId}/epics/{epicId}/comments";
    public static final String END_POINT_COMMENT_IN_EPIC_TO_INTERACT = "projects/{projectId}/epics/{epicId}/comments/{commentId}";
    public static final String END_POINT_BLOCKER_IN_STORY = "projects/{projectId}/stories/{storiesId}/blockers";
    public static final String END_POINT_BLOCKER_IN_STORY_TO_INTERACT = "projects/{projectId}/stories/{storiesId}/blockers/{blockersId}";

}
