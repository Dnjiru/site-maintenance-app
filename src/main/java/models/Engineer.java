package models;

import java.util.Objects;

public class Engineer {
    private String name;
    private int id;

    public Engineer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return id == engineer.id &&
                Objects.equals(name, engineer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
