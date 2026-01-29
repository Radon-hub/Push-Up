package org.radon.pushup.features.user.domain;

import java.util.Set;

public class Role {

    private String role;

    private Set<Authority> authorities;

    public Role(String role, Set<Authority> authorities) {
        this.role = role;
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

}
