package com.woowacourse.f12.support;

import com.woowacourse.f12.domain.member.Member;
import com.woowacourse.f12.dto.request.auth.GitHubTokenRequest;
import com.woowacourse.f12.dto.response.auth.GitHubProfileResponse;
import com.woowacourse.f12.dto.response.auth.GitHubTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

import static com.woowacourse.f12.support.GitHubProfileFixtures.*;
import static com.woowacourse.f12.support.MemberFixtures.*;

@RestController
public class FakeGitHubApiController {

    private final Map<String, String> codeAndToken = Map.of(CORINNE_GITHUB.getCode(), CORINNE_GITHUB.getToken(),
            MINCHO_GITHUB.getCode(), MINCHO_GITHUB.getToken(), OHZZI_GITHUB.getCode(), OHZZI_GITHUB.getToken());

    private final Map<String, Member> tokenAndMember = Map.of(CORINNE_GITHUB.getToken(), CORINNE.생성(),
            MINCHO_GITHUB.getToken(), MINCHO.생성(), OHZZI_GITHUB.getToken(), OHZZI.생성());

    private final String clientId;
    private final String clientSecret;

    public FakeGitHubApiController(@Value("${github.client.id}") final String clientId,
                                   @Value("${github.client.secret}") final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @PostMapping("/login/oauth/access_token")
    public ResponseEntity<GitHubTokenResponse> issueFakeAccessToken(
            @RequestBody final GitHubTokenRequest gitHubTokenRequest) {
        if (!gitHubTokenRequest.getClientId().equals(clientId) || !gitHubTokenRequest.getClientSecret()
                .equals(clientSecret)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new GitHubTokenResponse(codeAndToken.get(gitHubTokenRequest.getCode())));
    }

    @GetMapping("/user")
    public ResponseEntity<GitHubProfileResponse> showFakeProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) final String authorizationHeaderValue) {
        if (Objects.isNull(authorizationHeaderValue)) {
            return ResponseEntity.badRequest().build();
        }
        final String[] splitValue = authorizationHeaderValue.split(" ");
        if (splitValue.length != 2 || !splitValue[0].equals("token")) {
            return ResponseEntity.badRequest().build();
        }
        Member member = tokenAndMember.get(splitValue[1]);
        return ResponseEntity.ok(
                new GitHubProfileResponse(member.getGitHubId(), member.getName(), member.getImageUrl()));
    }
}
