package com.ecommerce.core;

import javax.persistence.*;

/**
 * Created by Krithika on 12/11/15.
 */

@Entity
@Table(name = "RESOURCEMAPPING")
@NamedQueries({
        @NamedQuery(
                name = "com.ecommerce.core.ResourceMapping.findAll",
                query = "SELECT r FROM ResourceMapping r"
        )
})
public class ResourceMapping {

    public ResourceMapping() {

    }

    public ResourceMapping(String resource, String replace) {
        this.replace = replace;
        this.resource = resource;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "resource", nullable = false)
    private String resource;

    @Column(name = "replace", nullable = false)
    private String replace;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getReplace() {
        return replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }
}
