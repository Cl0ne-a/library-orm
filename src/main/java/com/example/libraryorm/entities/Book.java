package com.example.libraryorm.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NamedEntityGraph(
        name = "book-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "author",
                        subgraph = "author-subgraph"),

                @NamedAttributeNode(value = "genre",
                        subgraph = "genre-subgraph"),

                @NamedAttributeNode(value = "comments",
                        subgraph = "comments-subgraph")},

        subgraphs = {
                @NamedSubgraph(
                        name = "author-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("name")
                        }),
                @NamedSubgraph(
                        name = "genre-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("genre")
                        }),
                @NamedSubgraph(
                        name = "comments-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("comment"),
                                @NamedAttributeNode("book")
                        })
        }
)
@Table(name = "book")
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", unique = true)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

    @ManyToOne(cascade = CascadeType.ALL)
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
    private List<Comment> comments;
}
