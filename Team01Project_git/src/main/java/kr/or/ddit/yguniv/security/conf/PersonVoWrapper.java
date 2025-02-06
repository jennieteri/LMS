package kr.or.ddit.yguniv.security.conf;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.RoleVO;
import lombok.Data;
@Data
public class PersonVoWrapper extends User {
    private final PersonVO realUser;
    
    public PersonVoWrapper(PersonVO realUser) {
        super(realUser.getId(), realUser.getPswd(), 
                createAuthorityList(realUser.getPersonType()));
        this.realUser = realUser;
    }


    private static List<GrantedAuthority> createAuthorityList(List<String> personTypes) {
    	return personTypes.stream()
    			.map(type -> new SimpleGrantedAuthority(String.format("ROLE_%s", type)))
    			.collect(Collectors.toList());
    }
}
//@Data
//public class PersonVoWrapper extends User {
//	private final PersonVO realUser;
//	
//	public PersonVoWrapper(PersonVO realUser) {
//		
//		super(realUser.getId(), realUser.getPswd(), 
//				AuthorityUtils.createAuthorityList(String.format("ROLE_%s", realUser.getPersonType())));
//		this.realUser = realUser;
//	}
//}
