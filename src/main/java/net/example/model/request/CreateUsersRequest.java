package net.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsersRequest {
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    private String username;

    @Email(message = "Please provide a valid email")
    private String email;



    private String address;
}
