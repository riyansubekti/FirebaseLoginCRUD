package subekti.riyan.firebaselogincrud.model;

import java.io.Serializable;

public class Users implements Serializable {

    private String nama, alamat, email, password, gender;
    private String key;

    public Users(String nama, String alamat, String email, String password, String gender) {
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "Users{" +
                "nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
