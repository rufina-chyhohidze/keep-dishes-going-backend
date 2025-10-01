package be.kdg.backend.restaurant.domain;

import java.util.Map;

public record OpeningHours(Map<String, String> weeklySchedule) {
}
