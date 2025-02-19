package org.example.vetmais.Controller;

import org.example.vetmais.Domain.User;

public interface UserAware {
    void setCurrentUser(User user);
}
