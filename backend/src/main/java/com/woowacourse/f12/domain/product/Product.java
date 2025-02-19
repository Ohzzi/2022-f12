package com.woowacourse.f12.domain.product;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(name = "NAME_UNIQUE", columnNames = {"name"})})
@Builder
@Getter
public class Product {

    private static final int MAXIMUM_IMAGE_URL_LENGTH = 65535;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false, length = MAXIMUM_IMAGE_URL_LENGTH)
    private String imageUrl;

    @Formula("(SELECT COUNT(1) FROM review r WHERE r.product_id = id)")
    private int reviewCount;

    @Formula("(SELECT IFNULL(AVG(r.rating), 0) FROM review r WHERE r.product_id = id)")
    private double rating;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    protected Product() {
    }

    private Product(final Long id, final String name, final String imageUrl, final int reviewCount, final double rating,
                    final Category category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.category = category;
    }

    public boolean isSameCategory(final Category category) {
        return this.category == category;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        final Product product = (Product) o;
        return Objects.equals(id, product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
