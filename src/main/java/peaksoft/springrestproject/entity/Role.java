package peaksoft.springrestproject.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    INSTRUCTOR,
    STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
