package blwsmartware.dao;

import com.vuchungbt.model.UserModel;

public interface IUserDAO extends GenericDAO<UserModel> {
    UserModel findByFbID(String fbID);
    UserModel findByGgID(String ggID);
    UserModel findByID(Long id);
    UserModel findByEmail(String email);
    void update(UserModel newUser);
    void delete(long id);
    Long save(UserModel userModel);
    int count();
//    List<UserModel> findByRoleID(Long id);
//    List<UserModel> findByRoleCode(String code);
    Long getRoleIDByRoleCode(String roleCode);
    String getRoleCodeByRoleId(Long roleId);
}
