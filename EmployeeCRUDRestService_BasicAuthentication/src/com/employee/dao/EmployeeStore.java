package com.employee.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.employee.dto.EmployeeDto;

public class EmployeeStore {

	private static TreeMap<Integer, EmployeeDto> map = new TreeMap<>();

	static {
		try {
			EmployeeDto employeeDto1 = new EmployeeDto(1, "Bill",
					new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-12"));

			EmployeeDto employeeDto2 = new EmployeeDto(2, "David",
					new SimpleDateFormat("yyyy-MM-dd").parse("1980-10-07"));

			map.put(employeeDto1.getId(), employeeDto1);
			map.put(employeeDto2.getId(), employeeDto2);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<EmployeeDto> getAllEmployees() {

		return new ArrayList<>(map.values());
	}

	public EmployeeDto getEmployeeById(int id) {

		return map.get(id);
	}

	public int addNewEmployee(EmployeeDto employeeDto) {

		int lastId = map.lastKey();
		employeeDto.setId(lastId + 1);

		map.put(employeeDto.getId(), employeeDto);
		return employeeDto.getId();
	}

	public boolean updateEmployee(int id, EmployeeDto employeeDto) {

		if (map.get(id) != null) {
			map.put(id, employeeDto);
			return true;
		}
		return false;
	}

	public boolean removeEmployee(int id) {

		if (map.get(id)!= null) {
			map.remove(id);
			return true;
		}
		return false;
	}
}
