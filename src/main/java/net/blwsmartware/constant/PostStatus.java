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
}
