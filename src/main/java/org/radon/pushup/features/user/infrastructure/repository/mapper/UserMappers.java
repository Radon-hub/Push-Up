package org.radon.pushup.features.user.infrastructure.repository.mapper;

import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.user.domain.Authority;
import org.radon.pushup.features.user.domain.Role;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.infrastructure.repository.entities.AuthorityEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMappers {

    public static AuthorityEntity fromAuthorityToAuthorityEntity(Authority authority) {
        return new AuthorityEntity(authority.getAuthority());
    }

    public static Authority fromAuthorityEntityToAuthority(AuthorityEntity authorityEntity) {
        return new Authority(authorityEntity.getAuthority());
    }

    public static RoleEntity fromRoleToRoleEntity(Role role){
        return new RoleEntity(
                role.getRole(),
                role.getAuthorities().stream().map(UserMappers::fromAuthorityToAuthorityEntity).collect(Collectors.toSet())
        );
    }

    public static Role fromRoleEntityToRole(RoleEntity roleEntity){
        return new Role(
                roleEntity.getName(),
                roleEntity.getAuthorities().stream().map(UserMappers::fromAuthorityEntityToAuthority).collect(Collectors.toSet())
        );
    }

    public static UserEntity fromUserToUserEntity(User user) {
        return new UserEntity(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                fromRoleToRoleEntity(user.getRole())
        );
    }

    public static UserEntity fromUserToUserEntity(User user,Tenant tenant,RoleEntity roleEntity) {
        return new UserEntity(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                fromTenantToTenantEntity(tenant),
                roleEntity
        );
    }


    public static User fromUserEntityToUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                fromRoleEntityToRole(userEntity.getRole()),
                userEntity.getApps().stream().map(UserMappers::fromAppEntityToApp).collect(Collectors.toSet()),
                userEntity.getCreated_at(),
                userEntity.getUpdated_at()
        );
    }

    public static AppEntity fromAppToAppEntity(App app) {
        return new AppEntity(
                app.getId(),
                app.getApi_key(),
                app.getName(),
                app.getPlatform(),
                app.getStatus(),
                fromTenantToTenantEntity(app.getTenant()),
                Set.of(),
                app.getCreated_at(),
                app.getUpdated_at()
        );
    }

    public static App fromAppEntityToApp(AppEntity appEntity) {
        return new App(
                appEntity.getId(),
                fromTenantEntityToTenant(appEntity.getTenant()),
                appEntity.getApi_key(),
                appEntity.getName(),
                appEntity.getPlatform(),
                appEntity.getStatus(),
                appEntity.getCreated_at(),
                appEntity.getUpdated_at()
        );
    }


    public static Tenant fromTenantEntityToTenant(TenantEntity tenantEntity) {
        return new Tenant(
                tenantEntity.getId(),
                tenantEntity.getName(),
                tenantEntity.getStatus(),
                tenantEntity.getUsers().stream().map(UserMappers::fromUserEntityToUser).collect(Collectors.toSet()),
                tenantEntity.getApps().stream().map(UserMappers::fromAppEntityToApp).collect(Collectors.toSet()),
                tenantEntity.getCreated_at(),
                tenantEntity.getUpdated_at()
        );
    }

    public static TenantEntity fromTenantToTenantEntity(Tenant tenant) {
        return new TenantEntity(
                tenant.getId(),
                tenant.getName(),
                tenant.getStatus(),
                tenant.getApps().stream().map(UserMappers::fromAppToAppEntity).collect(Collectors.toSet()),
                tenant.getUsers().stream().map(UserMappers::fromUserToUserEntity).collect(Collectors.toSet()),
                tenant.getCreated_at(),
                tenant.getUpdated_at()
        );
    }


}
