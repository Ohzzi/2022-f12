package com.woowacourse.f12.dto.response.member;

import com.woowacourse.f12.domain.member.Following;
import com.woowacourse.f12.domain.member.Member;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberPageResponse {

    private boolean hasNext;
    private List<MemberWithProfileProductResponse> items;

    private MemberPageResponse() {
    }

    private MemberPageResponse(final boolean hasNext, final List<MemberWithProfileProductResponse> items) {
        this.hasNext = hasNext;
        this.items = items;
    }

    public static MemberPageResponse fromNotFollowees(final Slice<Member> followeesSlice) {
        final List<MemberWithProfileProductResponse> memberResponses = followeesSlice.getContent()
                .stream()
                .map(member -> MemberWithProfileProductResponse.of(member, false))
                .collect(Collectors.toList());
        return new MemberPageResponse(followeesSlice.hasNext(), memberResponses);
    }

    public static MemberPageResponse fromFollowees(final Slice<Member> followeesSlice) {
        final List<MemberWithProfileProductResponse> memberResponses = followeesSlice.getContent()
                .stream()
                .map(member -> MemberWithProfileProductResponse.of(member, true))
                .collect(Collectors.toList());
        return new MemberPageResponse(followeesSlice.hasNext(), memberResponses);
    }

    public static MemberPageResponse of(final Slice<Member> slice, List<Following> followings) {
        final List<MemberWithProfileProductResponse> memberResponses = slice.getContent()
                .stream()
                .map(member -> MemberWithProfileProductResponse.of(member, isFollowing(followings, member)))
                .collect(Collectors.toList());
        return new MemberPageResponse(slice.hasNext(), memberResponses);
    }

    private static boolean isFollowing(final List<Following> followings, final Member member) {
        return followings.stream()
                .anyMatch(it -> it.getFolloweeId().equals(member.getId()));
    }
}
