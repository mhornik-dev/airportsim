package com.airportsim.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Group<T> {
    private Long id;
    private List<T> members;

    public Group() {
        this.members = new ArrayList<>();
    }

    public Group(Long id) {
        this.id = id;
        this.members = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public List<T> getMembers() {
        return members;
    }

    public void addMember(T member) {
        members.add(member);
    }

    public int getSize() {
        return members.size();
    }

    public void removeMember(T member) {
        members.remove(member);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group<?> group = (Group<?>) o;
        return id != null ? id.equals(group.id) : group.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
