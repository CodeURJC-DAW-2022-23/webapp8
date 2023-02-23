package com.TwitterClone.ProjectBackend.Model;

import com.TwitterClone.ProjectBackend.userManagement.User;

/**
 * It is an item left in a tweet which registered who gave it. Needs a tweet to exist and always belong to one
 */
public class Retweet {
    private User giver;
}
