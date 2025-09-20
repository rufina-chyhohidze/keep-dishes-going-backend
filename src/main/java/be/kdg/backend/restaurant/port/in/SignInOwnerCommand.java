package be.kdg.backend.restaurant.port.in;

import org.springframework.util.Assert;

public record SignInOwnerCommand(String email, String password) {
    public SignInOwnerCommand {
        Assert.hasLength(email, "Email cannot be empty");
        Assert.hasLength(password, "Password cannot be empty");
    }
}
