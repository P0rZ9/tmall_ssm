package cn.test.tmall.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAnonymousName(String name){
        if(null == name)
            return null;

        if(name.length() == 1){
            return "*";
        }

        if(name.length() == 2){
            name = name.substring(0,1) + "*";
            return name;
        }

        char[] na =name.toCharArray();
        for (int i = 1; i < na.length-1; i++) {
            na[i]='*';
        }

        return new String(na);
    }

}