package kr.or.ddit.yguniv.security.authorize;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager.Builder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.security.conf.resource.dao.SecuredResourceMapper;
import kr.or.ddit.yguniv.security.conf.resource.vo.SecuredResourceVO;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ReloadableAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

	private final SecuredResourceMapper securedResourceMapper;

	// 현재는 null
	private RequestMatcherDelegatingAuthorizationManager realAuthManager;

	@PostConstruct // 빈 등록이 끝난 다음에 무조건 실행
	public void loadSecuredResource() {
		List<SecuredResourceVO> resList = securedResourceMapper.selectResourceList();
		Builder builder = RequestMatcherDelegatingAuthorizationManager.builder();
		for (SecuredResourceVO res : resList) {
			AntPathRequestMatcher reqMatcher = new AntPathRequestMatcher(res.getResUrl(), res.getResMethod());
			AuthorizationManager<RequestAuthorizationContext> delegateManager = null;
			if (CollectionUtils.isEmpty(res.getAuthorities())) {
				// 누구나접근
				delegateManager = (a, c) -> new AuthorizationDecision(true); // permit all
			} else {
				// 접근제한
				delegateManager = AuthorityAuthorizationManager
						.hasAnyRole(res.getAuthorities().stream().toArray(String[]::new));
			}
			builder.add(reqMatcher, delegateManager);
		}
		this.realAuthManager = builder.build();
	}

	// 권한을 이양하는 작업의 가장마지막에 여기가 실행이 되어야함.
	public void reload() {
		// 여기서 위에있는 loadSecured를 바꿔줘야함 = 한번더 호출하면 수정된 db의 정보를 다시 가져오고 다시 등록됨. 리로딩 끝
		loadSecuredResource();
	}

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {

		return realAuthManager.check(authentication, object);
	}
}
