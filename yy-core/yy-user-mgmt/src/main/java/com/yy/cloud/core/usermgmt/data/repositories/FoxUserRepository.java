package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxOrganization;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;

/**
 * ClassName: AcmeUser UserRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Apr 23, 2016 4:35:37 PM <br/>
 *
 * @author chenxj
 * @since JDK 1.8
 */
@Repository
public interface FoxUserRepository extends JpaRepository<FoxUser, String> {

    Page<FoxUser> findByStatusAndTenantId(Byte status, String tenantId, Pageable pageable);

    Page<FoxUser> findByUserNameLike(String userName, Pageable pageable);

    Optional<FoxUser> findOneByEmail(String _email);

    Optional<FoxUser> findByLoginName(String _loginName);

    Optional<FoxUser> findByLoginNameAndTypeAndTenantId(String _loginName, Byte type, String tenantId);

    FoxUser findByLoginNameAndType(String _loginName, Byte type);

    List<FoxUser> findByLoginNameOrEmail(String _userName, String _email);

    Page<FoxUser> findByTenantId(Pageable pageable, String tenantId);

    Page<FoxUser> findByTenantIdAndStatusLessThan(String tenantId, Byte status, Pageable pageable);

    Page<FoxUser> findByTenantIdAndStatusLessThanAndUserNameLike(String tenantId, Byte status, String username, Pageable pageable);

    Page<FoxUser> findByIdInAndStatusLessThan(List<String> ids,Byte status, Pageable pageable);

    Page<FoxUser> findByIdInAndStatusLessThanAndUserNameLike(List<String> ids,Byte status, String userNmae, Pageable pageable);

    //@Query(value = "SELECT U.* FROM FOX_USER_ROLE UR LEFT JOIN FOX_USER U ON UR.USER_ID = U.ID LEFT JOIN FOX_ROLE R ON UR.ROLE_ID = R.ID WHERE U.TENANT_ID = ?1 AND R.ROLE_NAME IN ?2 AND U.STATUS < 4", nativeQuery = true
    //)

    @Query(value="SELECT * FROM FOX_USER U WHERE TENANT_ID = ?1 AND STATUS < 4 AND ID IN ( SELECT UR.USER_ID FROM FOX_USER_ROLE UR INNER JOIN FOX_ROLE R ON UR.ROLE_ID = R.ID WHERE R.ROLE_NAME IN ?2)", nativeQuery = true)
        //@Query(value = "SELECT U FROM FoxUserRole UR LEFT JOIN FoxUser U ON UR.userId = U.id LEFT JOIN FoxRole R ON UR.roleId = R.id WHERE U.tenantId = ?1 AND R.roleName IN ?2 AND U.status < 4")
    List<FoxUser> findMppUserByRoleList(String tenantId, List<String> roleNames);

    @Query(value = "SELECT U.* FROM FOX_USER_ROLE UR LEFT JOIN FOX_USER U ON UR.USER_ID = U.ID LEFT JOIN FOX_ROLE R ON UR.ROLE_ID = R.ID WHERE R.ROLE_NAME IN ?1  AND U.STATUS < 4", nativeQuery = true)
        //@Query(value = "SELECT U FROM FoxUserRole UR LEFT JOIN FoxUser U ON UR.userId = U.id LEFT JOIN FoxRole R ON UR.roleId = R.id WHERE R.roleName IN ?1 AND U.status < 4")
    List<FoxUser> findAdmUserByRoleList(List<String> roleNames);

    FoxUser findByLoginNameOrId(String _loginName, String _id);
}