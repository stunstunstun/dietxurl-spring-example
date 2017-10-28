package com.kakao.dietxurl.domain;

import javax.persistence.*;

/**
 * @author minhyeok
 */
@Entity
@Table(name = "shorten_link", indexes = {
        @Index(name = "origin_link_index", columnList = "origin_link", unique = true),
        @Index(name = "link_id_index", columnList = "link_id", unique = true)})
public class ShortenLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sequence;

    @Column(name = "origin_link", nullable = false)
    private String originLink;

    @Column(name = "link_id", length = 8, nullable = false)
    private String linkId;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ShortenLink that = (ShortenLink) o;
        if (sequence != null ? !sequence.equals(that.sequence) : that.sequence != null)
            return false;
        if (originLink != null ? !originLink.equals(that.originLink) : that.originLink != null)
            return false;
        return linkId != null ? linkId.equals(that.linkId) : that.linkId == null;
    }

    @Override
    public int hashCode() {
        int result = sequence != null ? sequence.hashCode() : 0;
        result = 31 * result + (linkId != null ? linkId.hashCode() : 0);
        result = 31 * result + (originLink != null ? originLink.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("ShortenLink[sequence=%s, linkId=%s, originLink=%s]", sequence.toString(), linkId, originLink);
    }
}