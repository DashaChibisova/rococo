package guru.qa.rococo.db.repository;

import guru.qa.rococo.db.model.PaintingEntity;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UserAuthEntity createInAuth(UserAuthEntity user);

    UserEntity createInUserdata(UserEntity user);

    Optional<UserAuthEntity> findByIdInAuth(UUID id);

    Optional<UserEntity> findByIdInUserdata(UUID id);

    void deleteInAuthById(UUID id);

    void deleteInUserdataById(UUID id);

    UserAuthEntity findUserAuthByName(String name);

    void deleteUserAuthByName(String name);

    UserEntity findUserdataByName(String name);

    void deleteUserdataByName(String name);

}
