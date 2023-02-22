package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class User {
    @Id
    @Column(name = "id", length = 16, unique = true, nullable = false)
    private final UUID id;
    @Column(unique = true, nullable = false)
    private final String username;
    private String nickname;
    @Lob
    @JsonIgnore
    private Blob profilePicture;
    @Lob
    @JsonIgnore
    private Blob profileBanner;
    private String biography;
    private LocalDate joinDate;
    private String role;
    private String mail;
    private String password;

    public User() {
        this.username = "";
        this.id = UUID.randomUUID();
    }

    public User(String username, String nickname, String biography, String mail, String password, Blob [] files) throws IOException {
        this.id = UUID.randomUUID();
        this.username = username;
        this.nickname = nickname;
        this.biography = biography;
        this.joinDate = LocalDate.now();
        this.role = "Registered";
        this.mail = mail;
        this.password = password;
        this.profilePicture = files[0];
        this.profileBanner = files[1];
    }

    /*
    This constructor is for sample data
     */
    public User(String username, String nickname, String biography, String mail, String password, String [] files, LocalDate time, String role) throws IOException {
        this.id = UUID.randomUUID();
        this.username = username;
        this.nickname = nickname;
        this.biography = biography;
        this.joinDate = time;
        this.role = "Registered";
        this.mail = mail;
        this.password = password;
        this.setImages(files);
        this.role = role;
    }

    private void setImages(String [] files) throws IOException {
        Resource image = new ClassPathResource(files[0]);
        this.profilePicture = BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
        image = new ClassPathResource(files[1]);
        this.profileBanner = BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBiography() {
        return biography;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getRole() {
        return role;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
