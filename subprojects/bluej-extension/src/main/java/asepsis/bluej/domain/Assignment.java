package asepsis.bluej.domain;

import static com.google.common.base.Objects.toStringHelper;

public class Assignment {
    private String name;

    public Assignment(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assignment that = (Assignment) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("name", getName())
            .toString();
    }
}
