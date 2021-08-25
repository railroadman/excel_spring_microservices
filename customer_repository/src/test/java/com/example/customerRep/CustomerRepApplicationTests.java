package com.example.customerRep;

import com.example.customerRep.dao.CustomerDao;
import com.example.customerRep.dto.CustomerDto;
import com.example.customerRep.entity.CustomerEntity;
import com.example.customerRep.mapper.CustomerMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CustomerRepApplicationTests {
	@Autowired
	CustomerDao customerDao;
	@Autowired
	CustomerMapper customerMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void saveMergeTest(){
		List<CustomerDto> customerDtos = List.of(new CustomerDto(10026L,"3333",0,"sasha","masha",123L),
											new CustomerDto(10027L,"1111",0,"uuuuuuu","masha",123L),
											new CustomerDto(10028L,"2222",0,"sashadd","mashafef",123L));
		List<CustomerEntity> customerEntities= this.customerMapper.map(customerDtos);
		List<Long> ids = customerDao.saveMerge(customerEntities);
		System.out.println(ids);
		System.out.println(ids instanceof List);
		Assert.assertTrue(ids instanceof List);

	}

}
