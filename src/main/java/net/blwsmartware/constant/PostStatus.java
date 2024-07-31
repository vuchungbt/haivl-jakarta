package net.blwsmartware.constant;

public enum PostStatus {

    DRAFT(-2),
    PENDING(0),
    PUBLISHED(1),
    ARCHIVED(2),
    DELETED(-1);

    private final int code;

    PostStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
