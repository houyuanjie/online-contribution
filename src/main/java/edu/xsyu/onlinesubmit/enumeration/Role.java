package edu.xsyu.onlinesubmit.enumeration;

public enum Role {
    /* 用户 */
    USER,
    /* 编辑 */
    EDITOR,
    /* 管理员 */
    ADMIN;

    public static Role from(String role) {
        switch (role) {
            case "USER":
                return USER;
            case "EDITOR":
                return EDITOR;
            case "ADMIN":
                return ADMIN;
            default:
                return null;
        }
    }
}