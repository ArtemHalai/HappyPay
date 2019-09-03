package enums;

public enum Role {
    ADMIN(1),
    CLIENT(2);

    int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}