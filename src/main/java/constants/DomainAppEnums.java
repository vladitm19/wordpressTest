package constants;

public class DomainAppEnums {
    public enum UserRole {
        SUBSCRIBER("subscriber"),
        CONTRIBUTOR("contributor"),
        AUTHOR("author"),
        EDITOR("editor"),
        ADMINISTRATOR("administrator");

        private final String userRole;

        private UserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUserRole() {
            return userRole;
        }

        @Override
        public String toString() {
            return userRole;
        }
    }
}
