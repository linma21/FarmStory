package kr.co.farmstory.mapper;

import kr.co.farmstory.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {

    UserDTO selectUserByUid(String uid);
    public void insertUser(UserDTO userDTO);
    public int selectCountUser(@Param("type") String type, @Param("value") String value);

    UserDTO selectUserByNameAndEmail(@Param("name") String name, @Param("email") String email);
    void updateUserPassword(@Param("uid") String uid, @Param("password") String password);
    public UserDTO findById(String uid);

    /*
    public String selectUser(String email);
    public UserDTO selectUser(String name, String email);
    public UserDTO findPass(String uid, String email);

    public void updatePass(String pass,String uid);
    public void updateInfo(UserDTO userDTO);

     */
}
