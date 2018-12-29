package pl.bit4mation.treeset.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tree_nodes")
public class TreeNode {

    @Id
    @GeneratedValue
    private Long    id;

    @ManyToOne (fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="parentId", insertable = false, updatable = false)
    @JsonIgnore
    private TreeNode parent;

    @Column(name = "parentId")
    private Long parentId;

    private Integer number;

    protected TreeNode () {}

    public TreeNode (Integer number) {
        this.number = number;
    }

    public Long     getId       () { return this.id; }
    public Integer  getNumber   () { return this.number; }
    public void     setNumber   (Integer number) { this.number = number; }

    public TreeNode getParent   () { return this.parent; }
    public void     setParent   (TreeNode parent) {this.parent = parent;}

    public Long     getParentId () { return this.parentId; }
    public void     setParentId (Long parentId) {this.parentId = parentId;}

    @Override
    public String toString () {
        return String.format("id=%d, number=%d", id, number);
    }

}
