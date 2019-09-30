package enums;

/**
 *  Role that can be used
 *  <li>{@link #ADMIN}</li>
 *  <li>{@link #CLIENT}</li>
 */
public enum Role {
    ADMIN(1),
    CLIENT(2);

    int roleId;

    /**
     * Sole constructor. It is not possible to invoke this constructor.
     * It is for use by code emitted by the compiler in response to enum type declarations.
     * @param roleId The int value of enum constant, which is the identifier used to declare it.
     */
    Role(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the value of {@link #roleId}.
     *
     * @return the value of {@link #roleId}.
     */
    public int getRoleId() {
        return roleId;
    }
}