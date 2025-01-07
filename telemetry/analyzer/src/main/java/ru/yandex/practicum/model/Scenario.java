package ru.yandex.practicum.model;

import java.util.HashMap;
import java.util.Map;

public class Scenario {
	@OneToMany
	@MapKeyColumn(
			table = "scenarios_conditions",
			name = "sensor_id")
	@JoinTable(
			name = "scenarios_conditions",
			joinColumns = @JoinColumn(name = "scenario_id"),
			inverseJoinColumns = @JoinColumn(name = "condition_id"))
	private Map<String, Condition> conditions = new HashMap<>();
}
