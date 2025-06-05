package com.rathon.manatee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ManateeApplication {

	private static final Logger log = LoggerFactory.getLogger(ManateeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ManateeApplication.class, args);
	}


	@Bean
	CommandLineRunner seedData(
			CompanyRepository companyRepo,
			OrgUnitRepository orgUnitRepo,
			PositionRepository positionRepo,
			EmployeeRepository employeeRepo,
			UserAccountRepository userRepo) {

		return args -> {
			// Positions
			Position ceo = positionRepo.save(new Position("사장"));
			Position vp = positionRepo.save(new Position("부사장"));
			Position seniorDirector = positionRepo.save(new Position("전무"));
			Position executiveDirector = positionRepo.save(new Position("상무"));
			Position associateExecutiveDirector = positionRepo.save(new Position("이사"));
			Position executiveManager = positionRepo.save(new Position("부장"));
			Position associateDirector = positionRepo.save(new Position("차장"));
			Position manager = positionRepo.save(new Position("과장"));
			Position assistantManager = positionRepo.save(new Position("대리"));
			Position associate = positionRepo.save(new Position("주임"));
			Position staff = positionRepo.save(new Position("사원"));
			Position intern = positionRepo.save(new Position("인턴"));

			// Companies
			Company hanbit = companyRepo.save(new Company(
					"한빛전자",
					"서울특별시 강남구 테헤란로 123",
					"전자제품 제조",
					"123-45-67890"
			));

			Company mirae = companyRepo.save(new Company(
					"미래소프트",
					"경기도 성남시 분당구 정자일로 45",
					"소프트웨어 개발",
					"987-65-43210"
			));

			// OrgUnits (Departments)
			OrgUnit rd = new OrgUnit("연구개발부", "부서", "RND001", hanbit, null, new ArrayList<>(), new ArrayList<>());
			OrgUnit hr = new OrgUnit("인사부", "부서", "HR001", hanbit, null, new ArrayList<>(), new ArrayList<>());
			OrgUnit sales = new OrgUnit("영업부", "부서", "SAL001", mirae, null, new ArrayList<>(), new ArrayList<>());
			OrgUnit support = new OrgUnit("고객지원부", "부서", "SUP001", mirae, null, new ArrayList<>(), new ArrayList<>());

			orgUnitRepo.saveAll(List.of(rd, hr, sales, support));

			// Employees + Accounts
			Employee e1 = new Employee("철수", "김", "cs.kim@hanbit.com", "010-1111-1111", ceo, rd, hanbit, null);
			Employee e2 = new Employee("영희", "이", "yh.lee@hanbit.com", "010-2222-2222", executiveManager, rd, hanbit, null);
			Employee e3 = new Employee("민수", "박", "ms.park@hanbit.com", "010-3333-3333", staff, hr, hanbit, null);
			Employee e4 = new Employee("지훈", "최", "jh.choi@hanbit.com", "010-4444-4444", intern, hr, hanbit, null);
			Employee e5 = new Employee("세진", "오", "sj.oh@mirae.com", "010-5555-5555", executiveManager, sales, mirae, null);
			Employee e6 = new Employee("가영", "윤", "gy.yoon@mirae.com", "010-6666-6666", staff, sales, mirae, null);
			Employee e7 = new Employee("성우", "홍", "sw.hong@mirae.com", "010-7777-7777", staff, support, mirae, null);
			Employee e8 = new Employee("수현", "김", "sh.kim@mirae.com", "010-8888-8888", intern, support, mirae, null);

			employeeRepo.saveAll(List.of(e1, e2, e3, e4, e5, e6, e7, e8));

			// Assign Employees to OrgUnits
			rd.getEmployees().addAll(List.of(e1, e2));
			hr.getEmployees().addAll(List.of(e3, e4));
			sales.getEmployees().addAll(List.of(e5, e6));
			support.getEmployees().addAll(List.of(e7, e8));
			orgUnitRepo.saveAll(List.of(rd, hr, sales, support));

			// User Accounts
			UserAccount u1 = new UserAccount("cs.kim", "hashedpass1", true, Role.ADMIN, e1);
			UserAccount u2 = new UserAccount("yh.lee", "hashedpass2", true, Role.MANAGER, e2);
			UserAccount u3 = new UserAccount("ms.park", "hashedpass3", true, Role.STAFF, e3);
			UserAccount u4 = new UserAccount("sj.oh", "hashedpass4", true, Role.MANAGER, e5);
			UserAccount u5 = new UserAccount("sw.hong", "hashedpass5", true, Role.STAFF, e7);

			userRepo.saveAll(List.of(u1, u2, u3, u4, u5));

			// Re-assign user accounts to employees now that accounts are persisted
			e1.setUserAccount(u1);
			e2.setUserAccount(u2);
			e3.setUserAccount(u3);
			e5.setUserAccount(u4);
			e7.setUserAccount(u5);
			employeeRepo.saveAll(List.of(e1, e2, e3, e5, e7));
		};
	}

}
