package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.db.model.Authority;
import guru.qa.rococo.db.model.AuthorityEntity;
import guru.qa.rococo.db.model.UserAuthEntity;
import guru.qa.rococo.db.model.UserEntity;
import guru.qa.rococo.db.repository.UserRepository;
import guru.qa.rococo.db.repository.UserRepositoryHibernate;
import guru.qa.rococo.jupiter.annotation.TestUser;
import guru.qa.rococo.jupiter.model.TestData;
import guru.qa.rococo.jupiter.model.UserJson;
import guru.qa.rococo.utils.DataUtils;

import java.util.*;

public class HibernateCreteUserExtension extends CreateUserExtension {

    private static UserRepository userRepository = new UserRepositoryHibernate();

    @Override
    public UserJson createUser(TestUser dbUser) {
        String username = dbUser.username().isEmpty()
                ? DataUtils.generateRandomUsername()
                : dbUser.username();
        String password = dbUser.password().isEmpty()
                ? "12345"
                : dbUser.password();

        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setUsername(username);
        userAuth.setPassword(password);
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);
        AuthorityEntity[] authorities = Arrays.stream(Authority.values()).map(
                a -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(a);
                    return ae;
                }
        ).toArray(AuthorityEntity[]::new);

        userAuth.addAuthorities(authorities);

        UserEntity user = new UserEntity();
        user.setUsername(username);

        UserAuthEntity inAuth = userRepository.createInAuth(userAuth);
        UserEntity inUserdata = userRepository.createInUserdata(user);


        return new UserJson(
                inUserdata.getId(),
                inUserdata.getUsername(),
                inUserdata.getFirstname(),
                inUserdata.getLastname(),
                inUserdata.getAvatar() == null ? "" : new String(inUserdata.getAvatar()),
                new TestData(
                        password,
                        inAuth.getId()

                )
        );
    }

    @Override
    public void deleteUser(UUID authId, UUID userdataId) {
        userRepository.deleteInAuthById(authId);
        userRepository.deleteInUserdataById(userdataId);
    }


}
