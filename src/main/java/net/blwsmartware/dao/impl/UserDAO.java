package net.blwsmartware.dao.impl;

import net.blwsmartware.dao.IUserDAO;
import net.blwsmartware.mapper.UserMapper;
import net.blwsmartware.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO implements IUserDAO {
    @Override
    public UserModel findByFbID(String fbID) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users AS u");
        sql.append(" INNER JOIN roles AS r ON r.id = u.roleid");
        sql.append(" WHERE u.fbID = ?");
        return findOne(sql.toString(), new UserMapper(), fbID);
    }

    @Override
    public UserModel findByGgID(String ggID) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users AS u");
        sql.append(" INNER JOIN roles AS r ON r.id = u.roleid");
        sql.append(" WHERE u.ggID = ?");
        return findOne(sql.toString(), new UserMapper(), ggID);
    }

    @Override
    public UserModel findByID(Long id) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users AS u");
        sql.append(" INNER JOIN roles AS r ON r.id = u.roleid");
        sql.append(" WHERE u.id = ?");
        return findOne(sql.toString(), new UserMapper(), id);
    }

    @Override
    public UserModel findByEmail(String email) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users AS u");
        sql.append(" INNER JOIN roles AS r ON r.id = u.roleid");
        sql.append(" WHERE u.email = ?");
        return findOne(sql.toString(), new UserMapper(), email);
    }

    @Override
    public void update(UserModel newUser){
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        if(newUser.getAvatar()!=null){
            sql.append("avatar=?,");
            params.add(newUser.getAvatar());
        }
        if(newUser.getDescription()!=null){
            sql.append(" description=?,");
            params.add(newUser.getDescription());
        }
        if(newUser.getEmail()!=null){
            sql.append(" email=?,");
            params.add(newUser.getEmail());
        }
        if(newUser.getFbID()!=null){
            sql.append(" fbID=?,");
            params.add(newUser.getFbID());
        }
        if(newUser.getGgID()!=null){
            sql.append(" ggID=?,");
            params.add(newUser.getGgID());
        }
        if (newUser.getLastOnline() != null) {
            sql.append("last_online=?, ");
            params.add(newUser.getLastOnline());
        }
        if (newUser.getModifiedBy() != null) {
            sql.append("updated_by=?, ");
            params.add(newUser.getModifiedBy());
        }
        if (newUser.getName() != null) {
            sql.append("name=?, ");
            params.add(newUser.getName());
        }
        if (newUser.getPagePhoto() != null) {
            sql.append("page_photo=?, ");
            params.add(newUser.getPagePhoto());
        }
        if (newUser.getRoleId() != null) {
            sql.append("roleid=?, ");
            params.add(newUser.getRoleId());
        }
//        if (newUser.getStatus()) {
            sql.append("status=?, ");
            params.add(newUser.getStatus());
//        }
        if (newUser.getThumbnail() != null) {
            sql.append("thumbnail=?, ");
            params.add(newUser.getThumbnail());
        }
        if (newUser.getTitle() != null) {
            sql.append("title=?, ");
            params.add(newUser.getTitle());
        }
        if (newUser.getTel() != null) {
            sql.append("tel=?, ");
            params.add(newUser.getTel());
        }
        if (newUser.getType() != null) {
            sql.append("type=?, ");
            params.add(newUser.getType());
        }
        if(sql.charAt(sql.length()-2) == ','){
            sql.setLength(sql.length()-2);
        }
        sql.append(" WHERE id = ?");
        params.add(newUser.getId());
        update(sql.toString(), params.toArray());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        delete(sql, id);
    }

    @Override
    public Long save(UserModel userModel) {
        StringBuilder sql = new StringBuilder("INSERT INTO users ");
        sql.append(" (name,fbID ,ggID ,tel, status ,roleid,created_by,type,title,thumbnail,description,page_photo,avatar,email)");
        sql.append(" VALUES(?,?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)");
        return insert(sql.toString(), userModel.getName(), userModel.getFbID(), userModel.getGgID(), userModel.getTel(), userModel.getStatus(), userModel.getRoleId(),
                userModel.getCreatedBy(), userModel.getType(), userModel.getTitle(), userModel.getThumbnail(), userModel.getDescription(), userModel.getPagePhoto(),
                userModel.getAvatar(), userModel.getEmail()
        );
    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM users";
        return count(sql);
    }

    @Override
    public Long getRoleIDByRoleCode(String roleCode) {
        String sql_right_to_role = "SELECT fbID,ggID,tel,users.status,users.created_date,users.id,users.updated_date,users.created_by,users.updated_by,type,title,thumbnail,description,last_online,page_photo,avatar,email,roles.id as roleid, roles.code as name FROM users RIGHT JOIN roles ON users.roleid = roles.id where roles.code =?  LIMIT 1";
        String sql = "SELECT id as roleid, code as name FROM roles where roles.code = ?";
        return findOne(sql_right_to_role, new UserMapper(), roleCode).getRoleId();
    }

    @Override
    public String getRoleCodeByRoleId(Long roleId) {
        String sql = "SELECT code FROM roles WHERE id = ?";
//        return findOne(sql,sql,roleId).toString();
        return null;
    }


}
