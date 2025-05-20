package core;

public class Person implements Comparable<Person> {
    private final String name;
    private final int ID;

    public Person( int ID, String name) {
        this.name = name;
        this.ID = ID;
    }

    @Override
    public int compareTo(Person o) {
        return this.ID - o.ID;
    }

    @Override
    public boolean equals (Object o) {
        if(o == this) return true;
        if(o instanceof Person p) {
            return this.ID == p.ID;
        }
        return false;
    }
}
