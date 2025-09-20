package be.kdg.backend.restaurant.port.in;

import org.springframework.util.Assert;

public record RegisterOwnerCommand(String email, String password, String name) {
    public RegisterOwnerCommand {
        Assert.hasLength(email, "email cannot be empty");
        Assert.hasLength(password, "password cannot be empty");
        Assert.hasLength(name, "name cannot be empty");
    }
}