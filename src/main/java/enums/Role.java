package enums;

public enum Role {
    ADMIN(1),
    MANAGER(2),
    CLIENT(3);

    int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}