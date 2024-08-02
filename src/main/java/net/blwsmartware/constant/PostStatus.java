package net.blwsmartware.constant;

public enum PostStatus {

    DRAFT(-2),
    DELETED(-1),
    PENDING(0),
    PUBLISHED(0),
    ARCHIVED(2);

    private final int code;

    PostStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PostStatus fromCode(int code) {
        for (PostStatus status : PostStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus code: " + code);
    }
}
