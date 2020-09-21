package com.yy.cloud.core.usermgmt.data.repositories;

import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface YYUserRepository extends JpaRepository<YYUser, String> {

    Page<YYUser> findByIdInAndStatusLessThan(List<String> ids, Byte status, Pageable pageable);

    @Query(value = "SELECT U.* FROM YY_USER_ROLE UR LEFT JOIN YY_USER U ON UR.USER_ID = U.ID LEFT JOIN YY_ROLE R ON UR.ROLE_ID = R.ID WHERE R.ROLE_NAME IN ?1  AND U.STATUS < 4", nativeQuery = true)
    List<YYUser> findAdmUserByRoleList(List<String> roleNames);

    YYUser findByLoginNameOrIdAndStatusLessThan(String _loginName, String _id, Byte status);

    YYUser findByLoginNameAndStatusLessThan(String loginname, Byte status);

    YYUser findByLoginNameOrIdAndStatus(String _loginName, String _id, Byte status);

    /**
     * Fetch user with user status.
     *
     * @param status
     * @param _adminName
     * @param _page
     * @return
     */
    Page<YYUser> findByStatusLessThanAndLoginNameIsNotLike(Byte status, String _adminName, Pageable _page);

    /**
     * Fetch user with userinfo's deptId
     *
     * @param status
     * @param _deptId
     * @param _adminName
     * @param _page
     * @return
     */
    Page<YYUser> findByStatusLessThanAndUserInfoDeptIdIsAndLoginNameIsNotLike(Byte status, String _deptId, String _adminName, Pageable _page);

    /**
     * Try to fetch user.
     *
     * @param status
     * @param _userName
     * @param _deptId
     * @param _adminName
     * @param _page
     * @return
     */
    Page<YYUser> findByStatusLessThanAndLoginNameContainingAndLoginNameNotLike(Byte status, String _userName, String _adminName,
                                                                                                      Pageable _page);
    /**
     * Try to fetch user.
     *
     * @param status
     * @param _userName
     * @param _deptId
     * @param _adminName
     * @param _page
     * @return
     */
    Page<YYUser> findByStatusLessThanAndLoginNameContainingAndUserInfoDeptIdIsAndAndLoginNameNotLike(Byte status, String _userName,  String _deptId, String _adminName,
																				Pageable _page);

    @Modifying
    @Query("update YYUser f set f.status=?1 where f.id=?2")
    public int setStatusFor(Byte status, String id);

    Page<YYUser> findByStatusLessThanAndUserInfoDeptId(Byte status, String orgId, Pageable pageable);

    List<YYUser> findByStatusLessThanAndUserInfoDeptId(Byte status, String orgId);

    List<YYUser> findByStatusLessThan(Byte _status);

    Page<YYUser> findByStatus(Byte status, Pageable pageable);
}