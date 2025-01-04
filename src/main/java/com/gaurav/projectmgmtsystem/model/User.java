package com.gaurav.projectmgmtsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public String getFullName() {
            return fullName;
      }

      public void setFullName(String fullName) {
            this.fullName = fullName;
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

      public int getProjectSize() {
            return projectSize;
      }

      public void setProjectSize(int projectSize) {
            this.projectSize = projectSize;
      }

      public List<Issue> getIssues() {
            return issues;
      }

      public void setIssues(List<Issue> issues) {
            this.issues = issues;
      }

      private String fullName;
      private String email;       // Correct field name for Lombok to generate getEmail()
      private String password;    // Correct field name for Lombok to generate getPassword()

      private int projectSize;

      @JsonIgnore
      @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
      private List<Issue> issues = new ArrayList<>();
}
