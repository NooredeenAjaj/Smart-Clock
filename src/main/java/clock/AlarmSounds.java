package clock;

public enum AlarmSounds {
    AI_VOICE("Ai voice"),
    ALARM_SOUND_1("Alarm sound 1"),
    ALARM_SOUND_2("Alarm sound 2"),
    ALARM_SOUND_3("Alarm sound 3");

    private final String displayName;

    AlarmSounds(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

