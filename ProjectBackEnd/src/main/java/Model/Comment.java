package Model;


import UserManagement.User;

/**
 * Tweet which have been left in another tweet. It is a subtype of tweet which records who wrote it and in which
 * tweet was it left.
 */
public class Comment extends Tweet{
    private User fromWhom;
    private Tweet toWhich;
}
