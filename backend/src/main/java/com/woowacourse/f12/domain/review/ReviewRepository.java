package com.woowacourse.f12.domain.review;

import com.woowacourse.f12.domain.member.Member;
import com.woowacourse.f12.domain.product.Product;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @Query("select r from Review r where r.product.id = :productId")
    Slice<Review> findPageByProductId(Long productId, Pageable pageable);

    Slice<Review> findPageBy(Pageable pageable);

    boolean existsByMemberAndProduct(Member member, Product product);

    Optional<Review> findByMemberAndProduct(Member member, Product product);

    Slice<Review> findPageByMemberId(Long memberId, Pageable pageable);
}
