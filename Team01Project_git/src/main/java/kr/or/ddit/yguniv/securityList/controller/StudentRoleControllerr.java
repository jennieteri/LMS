package kr.or.ddit.yguniv.securityList.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.employee.dao.EmployeeMapper;
import kr.or.ddit.yguniv.professor.dao.professorMapper;
import kr.or.ddit.yguniv.securityList.dao.RoleMapper;
import kr.or.ddit.yguniv.securityList.service.RoleService;
import kr.or.ddit.yguniv.student.service.StudentServiceImpl;
import kr.or.ddit.yguniv.vo.EmployeeVO;
import kr.or.ddit.yguniv.vo.StudentVO;


@Controller
@RequestMapping("/role")
public class StudentRoleControllerr {
	
	public static final String MODELNAME ="student";
	

    @Autowired
    private StudentServiceImpl service;
    
    @Autowired
    private EmployeeMapper mapper;
    
    @Autowired
    private professorMapper proMapper;
    
    @Autowired
    CommonCodeServiceImpl cocoService;
    
    @Autowired 
    private RoleService roleService;
    
    @Autowired 
    private RoleMapper roleMapper;
    
    
    @ModelAttribute(MODELNAME)
	public StudentVO student() {
		return new StudentVO();
	}

   @GetMapping("/allRole")
   public String selectStudent(
		
		  Model model

		   ) {
	   
	   model.addAttribute("student",service.selectStudentList());
	   
	   
	   model.addAttribute("employee",mapper.selectEmployeeList());
	   model.addAttribute("professor",proMapper.selectProfessorList());
	   
	   // 학년코드 
		model.addAttribute("gradeList", cocoService.getCodeList("YEAR"));
		// 학과 
		model.addAttribute("departmentList", cocoService.getDepartmentList());
		//학적상태
		model.addAttribute("streCateList", cocoService.getCodeList("GR01"));
		
		
		// 교직원 부서 
		List<EmployeeVO> employees = mapper.selectEmployeeList();
		Set<String> onlyDept = new HashSet<>();
		
		for(EmployeeVO employee:employees) {
			onlyDept.add(employee.getEmpDept());
			
		}
		
		model.addAttribute("onlyDept", onlyDept);

	   
	   return "role/roleStudent";
   }
   
   
   //필터링 사람 나오게 할거임 
   @PostMapping("/personFilter")
   @ResponseBody
   public List<?> personFilter(@RequestBody Map<String, String> filters) {
       String role = filters.get("role");


       if (role == null || role.isEmpty()) {
           throw new IllegalArgumentException("Role 값이 없습니다.");
       }

      
       if ("학생".equals(role)) {
           String deptCd = filters.get("studentDept");
           String gradeCd = filters.get("studentGrade");
           
           if (deptCd == null || gradeCd == null) {
               throw new IllegalArgumentException("학생 필터링에 필요한 데이터가 부족합니다.");
           }
           return roleMapper.selectStudentList(deptCd, gradeCd);
       } else if ("교수".equals(role)) {
           String deptNo = filters.get("deptNo");
           if (deptNo == null) {
               throw new IllegalArgumentException("교수 필터링에 필요한 데이터가 부족합니다.");
           }
           return roleMapper.selectProfessorList(deptNo);
       } else if ("교직원".equals(role)) {
           String empDept = filters.get("empDept");
           if (empDept == null) {
               throw new IllegalArgumentException("교직원 필터링에 필요한 데이터가 부족합니다.");
           }
           return roleMapper.selectEmployeeList(empDept);
       }

       // 유효하지 않은 Role 값일 경우 빈 리스트 반환
       return Collections.emptyList();
   }

   
   
	
   

}
