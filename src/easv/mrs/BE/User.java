package easv.mrs.BE;

public class User {
    int userId;
    String userName;
    public User(int userId, String userName){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "userId=" + userId + ", userName='" + userName;
    }
}

