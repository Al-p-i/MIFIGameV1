package dao;

import oldaccountserver.UserProfile;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by apomosov on 21.05.16.
 */
public interface UserProfileDAO {
    void addUserProfile(UserProfile userProfile);
    @Nullable
    UserProfile getUserByLogin(String login);
    List<UserProfile> listUserProfiles();
}
