package com.osmos.qa.models;

public class Lead {

    private String name;
    private String email;
    private String priority;
    private String status;
    private boolean qualified;
    private boolean emailOptIn;
    private String notes;

    private Lead(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.priority = builder.priority;
        this.status = builder.status;
        this.qualified = builder.qualified;
        this.emailOptIn = builder.emailOptIn;
        this.notes = builder.notes;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public boolean isQualified() { return qualified; }
    public boolean isEmailOptIn() { return emailOptIn; }
    public String getNotes() { return notes; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name;
        private String email;
        private String priority;
        private String status;
        private boolean qualified;
        private boolean emailOptIn;
        private String notes;

        public Builder setName(String name) { this.name = name; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPriority(String priority) { this.priority = priority; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setQualified(boolean qualified) { this.qualified = qualified; return this; }
        public Builder setEmailOptIn(boolean emailOptIn) { this.emailOptIn = emailOptIn; return this; }
        public Builder setNotes(String notes) { this.notes = notes; return this; }

        public Lead build() { return new Lead(this); }
    }
}
