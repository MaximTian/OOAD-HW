package src.main.java.TestServer.datamodel;
// package dataModel;

public class Movie {
    private String name;
    private String intro;
    private String avatar;
    
    public Movie() {}
    
    public Movie(String name, String intro, String avatar) {
        this.name = name;
        this.intro = intro;
        this.avatar = avatar;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIntro() {
        return intro;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIntro(String intro) {
        this.intro = intro;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
